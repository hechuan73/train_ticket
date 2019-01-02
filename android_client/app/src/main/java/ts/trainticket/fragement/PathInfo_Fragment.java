package ts.trainticket.fragement;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ts.trainticket.LoginActivity;
import ts.trainticket.Meituan.MeiTuanListView;
import ts.trainticket.R;
import ts.trainticket.TicketReserveActivity;
import ts.trainticket.domain.ContactPath;
import ts.trainticket.domain.Ticket;

import ts.trainticket.utils.ApplicationPreferences;
import ts.trainticket.utils.CalendarUtil;


public class PathInfo_Fragment extends BaseFragment implements MeiTuanListView.OnMeiTuanRefreshListener {


    private Button btnDate = null;
    private Calendar startDate = null;

    private MeiTuanListView mListView;
    private MeituanAdapter mAdapter;
    private final static int REFRESH_COMPLETE = 0;
    private List<TicketRes_Item> mDatas;

    private InterHandler mInterHandler = new InterHandler(this);

    private TextView by_pathName = null;

    private TextView startStation = null;
    private TextView startTime = null;

    private TextView endStation = null;
    private TextView endTime = null;
    private ContactPath path = null;


    private static class InterHandler extends Handler {
        private WeakReference<PathInfo_Fragment> mActivity;

        public InterHandler(PathInfo_Fragment activity) {
            mActivity = new WeakReference<PathInfo_Fragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            PathInfo_Fragment activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case REFRESH_COMPLETE:
                        activity.mListView.setOnRefreshComplete();
                        activity.mAdapter.notifyDataSetChanged();
                        activity.mListView.setSelection(0);
                        break;
                }
            } else {
                super.handleMessage(msg);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_buy_ticket, container, false);
        initView(view);
        initData(getArguments().getString("pathInfo"));
        initRefresh(view);
        return view;
    }

    private void initView(View view) {
        by_pathName = (TextView) view.findViewById(R.id.by_pathname);

        btnDate = (Button) view.findViewById(R.id.btn_byticket_date);
        //btnDate.setOnClickListener(new DateChooseListener());
        addToBtnController(btnDate);

        if (null == startDate) {
            startDate = Calendar.getInstance();
        }
        changeShowDate();

        startStation = (TextView) view.findViewById(R.id.by_startStation);
        startTime = (TextView) view.findViewById(R.id.by_startTime);

        endStation = (TextView) view.findViewById(R.id.by_arriveStation);
        endTime = (TextView) view.findViewById(R.id.by_endTime);
    }


    private void initData(String gsonStr) {
        Gson gson = new Gson();
        path = gson.fromJson(gsonStr, ContactPath.class);
        btnDate.setText(path.getPathDate());
        by_pathName.setText(path.getPathName());
        startStation.setText(path.getStartStation());
        startTime.setText(path.getStartTime().substring(0, 5));
        endStation.setText(path.getArriveStation());
        endTime.setText(path.getArriveTime().substring(0, 5));
        List<TicketRes_Item> tempDatas = new ArrayList<>();
        mDatas = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            tempDatas.add(new TicketRes_Item(Ticket.EASY_SEAT_TYPES[i], path.getPrices()[i], path.getSeats()[i]));
        }
        Collections.sort(tempDatas, new SortByTicketsNum());
        for (int i = 0; i < tempDatas.size(); i++) {
            if (tempDatas.get(i).getLeftTickets() > 0) {
                mDatas.add(tempDatas.get(i));
            }
        }
    }

    private void initRefresh(View view) {
        mListView = (MeiTuanListView) view.findViewById(R.id.listview);
        showItemTicket(mDatas);
    }

    private void showItemTicket(List<TicketRes_Item> mDatas1) {
        mAdapter = new MeituanAdapter(mDatas1);
        mListView.setAdapter(mAdapter);
        mListView.setOnMeiTuanRefreshListener(this);
    }



    class MeituanAdapter extends BaseAdapter {

        private List<TicketRes_Item> list;

        public MeituanAdapter(List<TicketRes_Item> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            MeituanViewHolder viewHolder = null;
            if (view == null) {
                viewHolder = new MeituanViewHolder();
                view = LayoutInflater.from(getContext()).inflate(R.layout.item_seat_type_price, null);
                viewHolder.seatType = (TextView) view.findViewById(R.id.res_ticketType);
                viewHolder.seatPrice = (TextView) view.findViewById(R.id.res_tickePrice);
                viewHolder.leftTickets = (TextView) view.findViewById(R.id.res_leftticket);
                viewHolder.reserveBtn = (Button) view.findViewById(R.id.res_btn);
                view.setTag(viewHolder);
            } else {
                viewHolder = (MeituanViewHolder) view.getTag();
            }
            viewHolder.seatType.setText(list.get(position).getSeatType());
            viewHolder.seatPrice.setText("$" + (int) (list.get(position).getSeatPrice()));
            viewHolder.leftTickets.setText(list.get(position).getLeftTickets() + "p");
            viewHolder.reserveBtn.setText("reserve");
            addToBtnController(viewHolder.reserveBtn);

            viewHolder.reserveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean isLogin = ApplicationPreferences.isUserOnLine(getContext());
                    Intent intent = null;
                    if (isLogin) {
                        intent = new Intent(getActivity(), TicketReserveActivity.class);
                        Gson gson = new Gson();
                        intent.putExtra("ticket_detail", gson.toJson(path));
                        intent.putExtra("seatType", list.get(position).getSeatType());
                        intent.putExtra("seatPrice", "$" + (int) (list.get(position).getSeatPrice()));
                    } else {
                        Toast.makeText(getContext(), "you need to login first！", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getActivity(), LoginActivity.class);
                    }
                    startActivity(intent);
                }
            });
            return view;
        }

        class MeituanViewHolder {
            private TextView seatType;
            private TextView seatPrice;
            private TextView leftTickets;
            private Button reserveBtn;
        }
    }

    class TicketRes_Item {
        String seatType;
        double seatPrice;
        int leftTickets;

        public String getSeatType() {
            return seatType;
        }

        public void setSeatType(String seatType) {
            this.seatType = seatType;
        }

        public double getSeatPrice() {
            return seatPrice;
        }

        public void setSeatPrice(int seatPrice) {
            this.seatPrice = seatPrice;
        }

        public int getLeftTickets() {
            return leftTickets;
        }

        public void setLeftTickets(int leftTickets) {
            this.leftTickets = leftTickets;
        }

        public TicketRes_Item(String seatType, double seatPrice, int leftTickets) {
            this.seatType = seatType;
            this.seatPrice = seatPrice;
            this.leftTickets = leftTickets;
        }
    }

    class SortByTicketsNum implements Comparator {
        @Override
        public int compare(Object lhs, Object rhs) {
            TicketRes_Item s1 = (TicketRes_Item) lhs;
            TicketRes_Item s2 = (TicketRes_Item) rhs;
            return s2.getLeftTickets() - s1.getLeftTickets();
        }
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    // todo
                    // no api: this place should request data from server
                    mInterHandler.sendEmptyMessage(REFRESH_COMPLETE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private class DateChooseListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            lockClick();
            Calendar now = CalendarUtil.getToday();

            DatePickerDialog datePicker = DatePickerDialog.newInstance(
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
                            startDate.set(year, monthOfYear, dayOfMonth);
                            changeShowDate();
                        }
                    },
                    startDate.get(Calendar.YEAR),
                    startDate.get(Calendar.MONTH),
                    startDate.get(Calendar.DAY_OF_MONTH)
            );
            Calendar lastDay = CalendarUtil.getLastDay();
            datePicker.setMinDate(now);
            datePicker.setMaxDate(lastDay);
            datePicker.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    unlockClick();
                }
            });
            datePicker.show(getActivity().getFragmentManager(), "DatePicker");
        }
    }

    // change date
    private void changeShowDate() {
        SimpleDateFormat tempFromat = new SimpleDateFormat("yyyy-MM-dd");
        // String dateStr = SimpleDateFormat.getDateInstance().format(startDate.getTime());
        String dateStr = tempFromat.format(startDate.getTime());
        if (dateStr.charAt(0) == '0')
            btnDate.setText(dateStr.replaceFirst("0", ""));
        else
            btnDate.setText(dateStr);
    }
}
