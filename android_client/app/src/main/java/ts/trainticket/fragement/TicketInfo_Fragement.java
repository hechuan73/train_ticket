package ts.trainticket.fragement;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alertdialogpro.AlertDialogPro;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;


import java.security.spec.ECField;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import ts.trainticket.AddPsActivity;
import ts.trainticket.OrderDetailActivity;
import ts.trainticket.R;
import ts.trainticket.utils.CustomDialog;
import ts.trainticket.domain.ContactPath;
import ts.trainticket.domain.Contacts;
import ts.trainticket.domain.OrderTicketsInfo;

import ts.trainticket.httpUtils.RxHttpUtils;
import ts.trainticket.httpUtils.UrlProperties;
import ts.trainticket.utils.ApplicationPreferences;
import ts.trainticket.utils.ClearEditTextTools;
import ts.trainticket.utils.ServerConstValues;

public class TicketInfo_Fragement extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swiper = null;
    private RecyclerView recyclerView = null;

    private EditText msg_edit = null;
    private ImageView msg_delImg = null;


    private TextView res_go_s;
    private TextView res_go_st;

    private TextView res_go_p;

    private TextView res_go_e;
    private TextView res_go_et;

    private TextView res_go_seat;
    private TextView res_go_value;

    private TextView res_path_date;

    TextView presentAccountTv = null;

    private TextView quit_btnTv = null;
    private TextView sureBtnTv = null;

    private ListView listView = null;
    TextView add_pesgr_tv = null;
    TextView add_child_tv = null;
    private TextView delPesger = null;
    private TextView contacts_tip = null;
    private ImageView contacts_tipimg = null;

    List<Map<String, Object>> passenger_list = new ArrayList<Map<String, Object>>();


    private View inflate;

    private Dialog dialog = null;

    private TextView by_submit = null;
    private TextView ticket_money = null;

    private TextView addPsId = null;
    private TextView flushPsId = null;

    List<Contacts> tempContactsList = null;
    List<Contacts> selectedContacts = new ArrayList<>();
    int pasengerCheckBox[] = new int[20];
    ContactPath path;
    private ImageView animationIV;
    private AnimationDrawable animationDrawable;


    CustomDialog customDialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_go_reserve, container, false);
        initView(view);
        initTable(view);
        initData(view);
        initSubmit(view);

        return view;
    }

    private void initTable(View view) {
        res_go_s = (TextView) view.findViewById(R.id.res_go_startStation);
        res_go_st = (TextView) view.findViewById(R.id.res_go_startTime);
        res_go_e = (TextView) view.findViewById(R.id.res_go_arriveStation);
        res_go_et = (TextView) view.findViewById(R.id.res_go_endTime);
        res_go_p = (TextView) view.findViewById(R.id.res_go_pathname);
        res_go_seat = (TextView) view.findViewById(R.id.res_go_chooseSeat);
        res_go_value = (TextView) view.findViewById(R.id.res_go_chooPrice);
        presentAccountTv = (TextView) view.findViewById(R.id.presentAccount_id);

    }

    private void initData(View view) {
        String ticket_detail = getArguments().getString("ticket_detail");
        Gson gson = new Gson();
        path = gson.fromJson(ticket_detail, ContactPath.class);

        String seatType = getArguments().getString("seatType");
        String seatPrice = getArguments().getString("seatPrice");

        res_go_p.setText(path.getPathName());
        res_go_s.setText(path.getStartStation());
        res_go_st.setText(path.getStartTime().substring(0, 5));
        res_go_e.setText(path.getArriveStation());
        res_go_et.setText(path.getArriveTime().substring(0, 5));
        res_go_seat.setText(seatType + "   ");
        res_go_value.setText(seatPrice);
        res_path_date.setText(path.getPathDate());
        String accountId = ApplicationPreferences.getOneInfo(getContext(), ApplicationPreferences.ACCOUNT_ID);
        if (accountId != null || accountId != "")
            presentAccountTv.setText(accountId);
        else
            presentAccountTv.setText("Not Login");
    }

    private void initDialog() {
        dialog = new Dialog(getContext(), R.style.ActionSheetDialogStyle);

        inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_pesger, null);

        quit_btnTv = (TextView) inflate.findViewById(R.id.quit_btn_asid);
        quit_btnTv.setOnClickListener(this);
        sureBtnTv = (TextView) inflate.findViewById(R.id.sure_btn_asid);
        sureBtnTv.setOnClickListener(this);

        animationIV = (ImageView) inflate.findViewById(R.id.addpsw_animationIV);
        animationIV.setImageResource(R.drawable.pic_loading);
        animationDrawable = (AnimationDrawable) animationIV.getDrawable();
        animationDrawable.start();
        contacts_tip = (TextView) inflate.findViewById(R.id.contacts_tip_id);
        contacts_tipimg = (ImageView) inflate.findViewById(R.id.contacts_tip_img);
        addPsId = (TextView) inflate.findViewById(R.id.add_psid);
        flushPsId = (TextView) inflate.findViewById(R.id.flush_psid);
        swiper = (SwipeRefreshLayout) inflate.findViewById(R.id.refresh_aps_table);
        swiper.setOnRefreshListener(this);
        swiper.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        recyclerView = (RecyclerView) inflate.findViewById(R.id.view_aps_table);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        addPsId.setOnClickListener(this);
        flushPsId.setOnClickListener(this);

        addToBtnController(addPsId, flushPsId, sureBtnTv);

        dialog.setContentView(inflate);

        Window dialogWindow = dialog.getWindow();

        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay();

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (int) (d.getHeight() * 0.6);
        dialogWindow.setAttributes(lp);
        dialog.show();
        getDataFromServer();
    }

    private void initView(View view) {

        msg_edit = (EditText) view.findViewById(R.id.msg_phonenumber);
        msg_edit.setText(ApplicationPreferences.getOneInfo(getContext(), ApplicationPreferences.ACCOUNT_DOCUMENT_EMAIL));

        msg_delImg = (ImageView) view.findViewById(R.id.msg_del_phonenumber);
        ClearEditTextTools.addclearListener(msg_edit, msg_delImg);

        listView = (ListView) view.findViewById(R.id.add_passenger_list);

        add_pesgr_tv = (TextView) view.findViewById(R.id.add_pesgr_tv);
        add_pesgr_tv.setOnClickListener(new AddPsgerListener());

        add_child_tv = (TextView) view.findViewById(R.id.add_child_tv);
        add_child_tv.setOnClickListener(this);

        delPesger = (TextView) view.findViewById(R.id.add_del_passenger);
        ticket_money = (TextView) view.findViewById(R.id.ticket_money_id);
        res_path_date = (TextView)view.findViewById(R.id.res_path_date_id);

    }

    public String getNowTime() {
        Date d = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }


    private void initSubmit(View view) {
        by_submit = (TextView) view.findViewById(R.id.by_submit1);
        by_submit.setOnClickListener(this);
    }

    private void isOrdered() {
        if (selectedContacts.size() == 0) {
            AlertDialogPro.Builder builder = new AlertDialogPro.Builder(getContext());
            builder.setMessage("Please add the passengers first!").setPositiveButton("I see", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        } else {
            customDialog = new CustomDialog(getContext(), R.style.CustomDialog);
            customDialog.show();

            sendOrderToServer();

        }
    }

    // submit order
    public void sendOrderToServer() {

        if (selectedContacts.size() > 0) {
            String contactsId = selectedContacts.get(0).getId();
            String pathName = res_go_p.getText().toString();
            int seatType = ServerConstValues.getSeatType(res_go_seat.getText().toString());
            String buyDate = res_path_date.getText().toString();

            String startStation = res_go_s.getText().toString();
            String endStation = res_go_e.getText().toString();
            int assurance = 0;
            int foodType = 0;

            OrderTicketsInfo orderTicketsInfo = new OrderTicketsInfo(contactsId, pathName, seatType, buyDate, startStation, endStation, assurance, foodType);

            MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");
            RequestBody requestBody = RequestBody.create(mediaType, new Gson().toJson(orderTicketsInfo));

            String loginId = ApplicationPreferences.getOneInfo(getContext(), ApplicationPreferences.ACCOUNT_ID);
            String token = ApplicationPreferences.getOneInfo(getContext(), ApplicationPreferences.ACCOUNT_TOKEN);

            String preserveUrl = UrlProperties.clientIstioIp;
            if (pathName.contains("G") || pathName.contains("D")) {
                preserveUrl += UrlProperties.gdPreserve;
            } else {
                preserveUrl += UrlProperties.otherPreserve;
            }

            subscription = RxHttpUtils.postWithHeader(preserveUrl, loginId, token, requestBody, getContext())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            unlockClick();
                            customDialog.dismiss();
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(String responseResult) {
                            unlockClick();
                            if (responseResult != null && !responseResult.equals("")) {
                                JSONObject jsonObject = JSON.parseObject(responseResult);
                                if ("Success".equals(jsonObject.getString("message"))) {
                                    Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                                    intent.putExtra("reserve_result", responseResult);
                                    startActivity(intent);
                                    customDialog.dismiss();
                                } else {
                                    customDialog.dismiss();
                                    Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "The order has been submitted unsuccessfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            AlertDialogPro.Builder builder = new AlertDialogPro.Builder(getContext());
            builder.setMessage("Please add the passengers first!").setPositiveButton("I see", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.by_submit1:
                isOrdered();
                break;
            case R.id.add_child_tv:
                AlertDialogPro.Builder builder = new AlertDialogPro.Builder(getContext());

                builder.setMessage("Children who are 1.2-1.5 meters tall need to buy children's tickets. Children's tickets are " +
                        "temporarily charged for adult fares, and the difference is refunded after the ticket is issued.")
                        .setPositiveButton("i see", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                break;
            case R.id.add_psid:
                Intent addPsIntent = new Intent(getContext(), AddPsActivity.class);
                startActivityForResult(addPsIntent, AddPsActivity.ADD_PS_REQUEST_CODE);
                break;
            case R.id.flush_psid:
                contacts_tipimg.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                animationIV.setVisibility(View.VISIBLE);
                animationDrawable.start();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        getDataFromServer();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 500);
                break;
            case R.id.sure_btn_asid:
                int countSelectedNum = 0;
                for (int i = 0; i < pasengerCheckBox.length; i++) {
                    if (pasengerCheckBox[i] == 1)
                        countSelectedNum++;
                }
                if (countSelectedNum > 1) {
                    Toast.makeText(getContext(), "you can add only  one passenger every time", Toast.LENGTH_SHORT).show();
                } else if (selectedContacts.size() == 1 && countSelectedNum == 1) {
                    Toast.makeText(getContext(), "you can add only  one passenger every time", Toast.LENGTH_SHORT).show();
                } else {
                    getSelectPsShow();
                    dialog.dismiss();
                }
                break;
            case R.id.quit_btn_asid:
                pasengerCheckBox = new int[20];
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    class AddPsgerListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            initDialog();
        }
    }

    // show select passenger
    public void getSelectPsShow() {
        int isHasSelected = 0;
        if (tempContactsList != null) {
            int ticketPrice = Integer.parseInt(res_go_value.getText().toString().substring(1, res_go_value.getText().toString().length()));
            double sumMoney = Integer.parseInt(ticket_money.getText().toString().substring(1, ticket_money.getText().toString().length()));

            for (int i = 0; i < tempContactsList.size(); i++) {
                if (pasengerCheckBox[i] == 1) {
                    for (int e = 0; e < selectedContacts.size(); e++) {
                        if (selectedContacts.get(e).getName().equals(tempContactsList.get(i).getName())) {
                            isHasSelected = 1;
                        }
                    }
                    if (isHasSelected != 1) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("passengerName", tempContactsList.get(i).getName());
                        map.put("passengerType", ServerConstValues.EASY_PASSENGER_TYPES[Integer.parseInt(tempContactsList.get(i).getDocumentType())]);
                        map.put("passengerIdcard", tempContactsList.get(i).getDocumentNumber());
                        passenger_list.add(map);
                        pasengerCheckBox[i] = 0;

                        sumMoney = sumMoney + (ticketPrice);

                        selectedContacts.add(tempContactsList.get(i));
                    } else {
                        AlertDialogPro.Builder builder = new AlertDialogPro.Builder(getContext());
                        builder.setMessage("You have chosen the passenger!").setPositiveButton("I See", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                }
            }

            Double D1 = new Double(sumMoney);
            ticket_money.setText("$" + (D1.intValue()));
            showPesger();
        }

        pasengerCheckBox = new int[20];
    }

    private void showPesger() {
        listView.setAdapter(new AddPsgerAdapter(getContext(), passenger_list));
    }

    // get this account's common contacts
    public void getDataFromServer() {

        String listStationUri = UrlProperties.clientIstioIp + UrlProperties.findContacts;

        String loginId = ApplicationPreferences.getOneInfo(getContext(), ApplicationPreferences.ACCOUNT_ID);
        String token = ApplicationPreferences.getOneInfo(getContext(), ApplicationPreferences.ACCOUNT_TOKEN);

        subscription = RxHttpUtils.getWithHeader(listStationUri, loginId, token, getContext())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        unlockClick();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(String responseResult) {
                        unlockClick();

                        if (responseResult != null && !responseResult.equals("")) {

                            List<Contacts> contactsList = new ArrayList<>();
                            JSONArray jsonArray = JSON.parseArray(responseResult);
                            Iterator it = jsonArray.iterator();

                            while (it.hasNext()) {
                                JSONObject consObj = (JSONObject) it.next();
                                Contacts contacts1 = new Contacts(consObj.getString("id"),
                                        consObj.getString("accountId"), consObj.getString("name"),
                                        consObj.getString("documentType"), consObj.getString("documentNumber"),
                                        consObj.getString("phoneNumber"));
                                contactsList.add(contacts1);
                            }

                            if (contactsList.size() > 0) {
                                showTable(contactsList);
                                contacts_tip.setVisibility(View.GONE);
                                contacts_tipimg.setVisibility(View.GONE);
                            } else {
                                contacts_tip.setVisibility(View.VISIBLE);
                                contacts_tipimg.setVisibility(View.VISIBLE);
                            }
                            animationDrawable.stop();
                            animationIV.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);

                        } else {
                            animationDrawable.stop();
                            animationIV.setVisibility(View.GONE);
                            contacts_tipimg.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "No Common Contacts", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // show new add passenger
    private void showTable(List<Contacts> contactses) {
        tempContactsList = contactses;
        APSAdapter myAdapter = new APSAdapter(tempContactsList);
        myAdapter.setHasStableIds(true);
        recyclerView.setAdapter(myAdapter);
    }

    public class AddPsgerAdapter extends BaseAdapter {

        private List<Map<String, Object>> data;
        private LayoutInflater layoutInflater;
        private Context context;

        public AddPsgerAdapter(Context context, List<Map<String, Object>> data) {
            this.context = context;
            this.data = data;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            AddPsger addPsger = null;
            if (convertView == null) {
                addPsger = new AddPsger();

                convertView = layoutInflater.inflate(R.layout.item_add_passengers, null);
                addPsger.delTv = (TextView) convertView.findViewById(R.id.add_del_passenger);
                addPsger.psName = (TextView) convertView.findViewById(R.id.add_passenger);
                addPsger.psType = (TextView) convertView.findViewById(R.id.add_peger_type);
                addPsger.psIdcard = (TextView) convertView.findViewById(R.id.add_pesger_idcard);

                addPsger.delTv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        selectedContacts.remove(position);
                        passenger_list.remove(position);
                        pasengerCheckBox = new int[20];
                        int ticketPrice = Integer.parseInt(res_go_value.getText().toString().substring(1, res_go_value.getText().toString().length()));
                        int sumMoney = Integer.parseInt(ticket_money.getText().toString().substring(1, ticket_money.getText().toString().length()));
                        sumMoney = sumMoney - ticketPrice;
                        ticket_money.setText("$" + sumMoney);
                        showPesger();
                    }
                });
                convertView.setTag(addPsger);
            } else {
                addPsger = (AddPsger) convertView.getTag();
            }
            addPsger.psName.setText((String) data.get(position).get("passengerName"));
            addPsger.psType.setText((data.get(position).get("passengerType") + ""));
            addPsger.psIdcard.setText((String) data.get(position).get("passengerIdcard"));
            return convertView;
        }

        public final class AddPsger {
            private TextView delTv;
            private TextView psName;
            private TextView psType;
            private TextView psIdcard;
        }
    }


    class APSAdapter extends RecyclerView.Adapter<APSAdapter.APSViewHolder> {

        private List<Contacts> list;

        public APSAdapter(List<Contacts> list) {
            this.list = list;
        }

        @Override
        public APSViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_apslist, viewGroup, false);
            return new APSViewHolder(view);
        }

        @Override
        public void onBindViewHolder(APSViewHolder cpholder, final int i) {
            cpholder.contactsName.setText(list.get(i).getName());
            cpholder.contactsType.setText(ServerConstValues.EASY_PASSENGER_TYPES[Integer.parseInt(list.get(i).getDocumentType())]);
            cpholder.sidCard.setText(list.get(i).getDocumentNumber());
            cpholder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true)
                        pasengerCheckBox[i] = 1;
                    else
                        pasengerCheckBox[i] = 0;
                }
            });
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class APSViewHolder extends RecyclerView.ViewHolder {

            private CheckBox checkBox;
            private TextView contactsName;
            private TextView contactsType;
            private TextView sidCard;

            public APSViewHolder(View itemView) {
                super(itemView);
                checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_asid);
                contactsName = (TextView) itemView.findViewById(R.id.name_aps_id);
                contactsType = (TextView) itemView.findViewById(R.id.type_aps_id);
                sidCard = (TextView) itemView.findViewById(R.id.card_pas_id);
            }
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataFromServer();
                swiper.setRefreshing(false);
            }
        }, 500);
    }
}