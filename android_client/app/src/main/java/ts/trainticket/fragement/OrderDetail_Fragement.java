package ts.trainticket.fragement;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alertdialogpro.AlertDialogPro;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import ts.trainticket.MainActivity;
import ts.trainticket.Meituan.RightMarkView;
import ts.trainticket.R;
import ts.trainticket.utils.CustomDialog;
import ts.trainticket.domain.Contacts;
import ts.trainticket.domain.CancelOrderInfo;
import ts.trainticket.domain.CancelOrderResult;
import ts.trainticket.domain.PaymentInfo;
import ts.trainticket.domain.PreserveOrderResult;
import ts.trainticket.httpUtils.RxHttpUtils;
import ts.trainticket.httpUtils.UrlProperties;
import ts.trainticket.utils.ApplicationPreferences;
import ts.trainticket.utils.CalendarUtil;
import ts.trainticket.utils.ServerConstValues;


public class OrderDetail_Fragement extends BaseFragment implements View.OnClickListener {

    private ImageView show_tag_picImg = null;
    private TextView show_top_tipsTv = null;
    private TextView show_tips2Tv = null;
    private LinearLayout count_time_layout = null;
    private RightMarkView markView = null;
    private TextView cancel_order;
    private Button pay_btn = null;
    private TextView add_accountTv = null;

    private LinearLayout order_detailLayout = null;

    // 0   1    2    3    4
    private String[] tipText = new String[]{"Not Paid", "Paid & Not Collected", "Collected", "Cancel & Rebook", "Cancel", "Refunded", "Used", "Other"};

    private int[] imagesTip = new int[]{
            R.drawable.hb_youji_icon,
            R.drawable.tab_ticket_selected_new,
            R.drawable.cb_checked,
            R.drawable.hb_yiquxiao_icon,
            R.drawable.delete_bg,
            R.drawable.delete_bg,
            R.drawable.cb_checked,
            R.drawable.hb_yiquxiao_icon,
    };

    private String[] cancelBtnTips = new String[]{"Cancel", "Refunded", "waiting", "To refund", "Cancelled ,To Re-book", "Refunded, To Re-book", "To Re-book", "To Re-book"};
    private String[] tipBottomBtnText = new String[]{"Pay", "Paid", "Paid", "Paid", "Canceled", "Refunded", "Completed", "unkonwn"};


    private TextView beginDate = null;
    private TextView endDate = null;

    private TextView coachNumber = null;

    private TextView beginPlace = null;
    private TextView pathName = null;
    private TextView duraTime = null;

    private TextView seatNumber = null;

    private TextView endPlace = null;

    private ListView selected_psg_list = null;
    List<Contacts> contacts = null;
    private TextView total_Money;

    private String seatTypeValue;


    LinearLayout show_toPayPanel = null;

    CustomDialog dialog = null;

    String reserve_result = "";
    String orders_Result = "";

    String presentState = "";
    String takeTime = "";
    String takeDate = "";

    PreserveOrderResult preOrderResult = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_orderdetail, container, false);
        initCircleView(view);
        initView(view);
        showData();
        return view;
    }

    public void showData() {

        reserve_result = getArguments().getString("reserve_result");
        orders_Result = getArguments().getString("ordersResult");

        Gson gson = new Gson();
        if (reserve_result != null && reserve_result != "") {
            JSONObject orderPreObj = JSON.parseObject(reserve_result);
            if ("Success".equals(orderPreObj.getString("message"))) {
                JSONObject orderObj = orderPreObj.getJSONObject("order");
                preOrderResult = gson.fromJson(orderObj.toString(), PreserveOrderResult.class);
                showOrderResult(preOrderResult);
            }
        } else if (orders_Result != null && orders_Result != "") {

            dialog = new CustomDialog(getContext(), R.style.CustomDialog);
            dialog.show();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 500);

            Gson gson2 = new Gson();
            preOrderResult = gson2.fromJson(orders_Result, PreserveOrderResult.class);
            showOrderResult(preOrderResult);
        }
    }

    public void showOrderResult(PreserveOrderResult preserveOrderResult) {
        order_detailLayout.setVisibility(View.VISIBLE);

        add_accountTv.setText(preserveOrderResult.getAccountId());

        beginDate.setText(CalendarUtil.dateToWeek(CalendarUtil.getDate(preOrderResult.getTravelDate())));

        endDate.setText(CalendarUtil.getHM(preOrderResult.getTravelTime()));
        pathName.setText(preOrderResult.getTrainNumber() + "");

        beginPlace.setText(preOrderResult.getFrom());
        coachNumber.setText("Coach Num:" + preOrderResult.getCoachNumber());

        endPlace.setText(preOrderResult.getTo());
        seatNumber.setText("Site Num:" + preOrderResult.getSeatNumber());


        presentState = preOrderResult.getStatus() + "";
        takeTime = CalendarUtil.getHMS(preOrderResult.getTravelTime());

        showState(preOrderResult.getStatus(), takeTime);

        seatTypeValue = preOrderResult.getSeatClass() + "";
        contacts = new ArrayList<>();


        Contacts contact = new Contacts();
        contact.setId(preOrderResult.getAccountId());
        contact.setDocumentNumber(preOrderResult.getContactsDocumentNumber());
        contact.setDocumentType(preOrderResult.getDocumentType() + "");
        contact.setName(preOrderResult.getContactsName());
        contact.setPrice(preOrderResult.getPrice());
        contact.setOrderId(preOrderResult.getId());
        contacts.add(contact);
        total_Money.setText("$" + preOrderResult.getPrice());
        showPesger(contacts);
    }

    private void showState(int status, String takeTime) {
        show_tag_picImg.setBackgroundResource(imagesTip[status]);
        show_top_tipsTv.setText(tipText[status]);

        if (status != 0) {

            count_time_layout.setVisibility(View.GONE);
            markView.setVisibility(View.GONE);

            show_tips2Tv.setVisibility(View.VISIBLE);
            show_tag_picImg.setVisibility(View.VISIBLE);
        }
//        if (CalendarUtil.compareTimeDate(takeTime) && CalendarUtil.compare_date(takeDate) && status == 1)
//            cancel_order.setText("dispatched");
//        else
//            cancel_order.setText(cancelBtnTips[status]);

        pay_btn.setText(tipBottomBtnText[status]);
    }

    private void initCircleView(View view) {
        markView = (RightMarkView) view.findViewById(R.id.activity_right_mark_rmv);
        markView.setColor(Color.parseColor("#FF4081"), Color.YELLOW);
        markView.setStrokeWidth(8f);
        markView.start();
    }


    private void initView(View view) {
        show_tag_picImg = (ImageView) view.findViewById(R.id.show_tag_pic);
        show_top_tipsTv = (TextView) view.findViewById(R.id.show_top_tips);
        show_tips2Tv = (TextView) view.findViewById(R.id.show_tips2_id);
        count_time_layout = (LinearLayout) view.findViewById(R.id.count_time_layoutId);
        add_accountTv = (TextView) view.findViewById(R.id.odt_account_id);

        cancel_order = (TextView) view.findViewById(R.id.cancel_order_sid);
        cancel_order.setOnClickListener(this);
        pay_btn = (Button) view.findViewById(R.id.pay_btn_id);
        pay_btn.setOnClickListener(this);

        show_toPayPanel = (LinearLayout) view.findViewById(R.id.show_to_payPanel);
        order_detailLayout = (LinearLayout) view.findViewById(R.id.order_detail_id);

        selected_psg_list = (ListView) view.findViewById(R.id.selected_psg_list);


        beginDate = (TextView) view.findViewById(R.id.odt_beginDate_id);
        endDate = (TextView) view.findViewById(R.id.odt_endDate_id);

        coachNumber = (TextView) view.findViewById(R.id.odt_coachNumber_id);

        beginPlace = (TextView) view.findViewById(R.id.odt_beginPlace_id);
        pathName = (TextView) view.findViewById(R.id.odt_pathName_id);
        duraTime = (TextView) view.findViewById(R.id.odt_duraTime_id);

        seatNumber = (TextView) view.findViewById(R.id.odt_seatNumber_id);

        endPlace = (TextView) view.findViewById(R.id.odt_endPlace_id);
        total_Money = (TextView) view.findViewById(R.id.totalMoney_id);

    }

    private void showPesger(List<Contacts> contacts) {
        selected_psg_list.setAdapter(new SelectedPsgerAdapter(getContext(), contacts));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_order_sid:
                orderOrNot(presentState);
                break;
            case R.id.pay_btn_id:
                gotoPay(presentState);
                break;
            default:
                break;
        }
    }

    // unpaid 0   1   2 cancel   3  4  complate
    public void gotoPay(String satus) {
        switch (satus) {
            case "0":
                payOrder(preOrderResult.getId(), preOrderResult.getTrainNumber());
                break;
            case "1":
                Toast.makeText(getContext(), "You have purchased tickets, please do not repeat the operation.", Toast.LENGTH_SHORT).show();
                break;
            case "2":
                Toast.makeText(getContext(), "You have checked the tickets. Do not repeat the operation.", Toast.LENGTH_SHORT).show();
                break;
            case "3":
                Toast.makeText(getContext(), "You have reserved, please do not repeat the operation.", Toast.LENGTH_SHORT).show();
                break;
            case "4":
                Toast.makeText(getContext(), "You have cancelled, please click to reserve", Toast.LENGTH_SHORT).show();
                break;
            case "5":
                Toast.makeText(getContext(), "You have cancelled, please click to reserve", Toast.LENGTH_SHORT).show();
                break;
            case "6":
                Toast.makeText(getContext(), "You have finished, please click Reservation", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getContext(), "Unknown click operation error", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public void orderOrNot(String status) {
        switch (status) {
            case "0":
                AlertDialogPro.Builder builder2 = new AlertDialogPro.Builder(getContext());
                builder2.setMessage("Are you sure you want to cancel the order?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelOrder(preOrderResult.getId());

                    }
                }).setNegativeButton("Cancel", null).show();
                break;
            case "1":
                AlertDialogPro.Builder builder3 = new AlertDialogPro.Builder(getContext());
                builder3.setMessage("Are you sure you want a refund?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelOrder(preOrderResult.getId());
                    }
                }).setNegativeButton("Cancel", null).show();
                break;
            case "2":
            case "3":
            case "4":
            case "5":
                Intent intent = new Intent(getActivity(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    public void payOrder(String orderId, String tripId) {

        PaymentInfo paymentInfo = new PaymentInfo(orderId, tripId);

        MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");
        RequestBody requestBody = RequestBody.create(mediaType, new Gson().toJson(paymentInfo));

        String loginId = ApplicationPreferences.getOneInfo(getContext(), ApplicationPreferences.ACCOUNT_ID);
        String token = ApplicationPreferences.getOneInfo(getContext(), ApplicationPreferences.ACCOUNT_TOKEN);

        String insidePayUrl = UrlProperties.clientIstioIp + UrlProperties.inside_payment;
        dialog = new CustomDialog(getContext(), R.style.CustomDialog);
        dialog.show();


        subscription = RxHttpUtils.postWithHeader(insidePayUrl, loginId, token, requestBody, getContext())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        unlockClick();
                        dialog.dismiss();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(String responseResult) {
                        unlockClick();
                        if ("true".equals(responseResult)) {
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Request data failed", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                        presentState = "1";
                        showState(Integer.parseInt(presentState), takeTime);
                    }
                });
    }

    public void cancelOrder(String orderId) {

        final CancelOrderInfo cancelOrderInfo = new CancelOrderInfo(orderId);

        MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");
        RequestBody requestBody = RequestBody.create(mediaType, new Gson().toJson(cancelOrderInfo));

        String loginId = ApplicationPreferences.getOneInfo(getContext(), ApplicationPreferences.ACCOUNT_ID);
        String token = ApplicationPreferences.getOneInfo(getContext(), ApplicationPreferences.ACCOUNT_TOKEN);

        String cancelOrderUrl = UrlProperties.clientIstioIp + UrlProperties.cancelOrder;

        dialog = new CustomDialog(getContext(), R.style.CustomDialog);
        dialog.show();
        subscription = RxHttpUtils.postWithHeader(cancelOrderUrl, loginId, token, requestBody, getContext())
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
                        dialog.dismiss();
                        if (responseResult != null && responseResult != "") {
                            // {"status":true,"message":"Success."}
                            Gson gson = new Gson();
                            CancelOrderResult cancelOrderResult = gson.fromJson(responseResult, CancelOrderResult.class);
                            if (cancelOrderResult.isStatus()) {
                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Request data failed", Toast.LENGTH_SHORT).show();
                        }
                        presentState = "5";
                        showState(Integer.parseInt(presentState), takeTime);
                    }
                });
    }


    public class SelectedPsgerAdapter extends BaseAdapter {

        private List<Contacts> data;
        private LayoutInflater layoutInflater;
        private Context context;

        public SelectedPsgerAdapter(Context context, List<Contacts> data) {
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
            SelectedPsger addPsger = null;
            if (convertView == null) {
                addPsger = new SelectedPsger();
                convertView = layoutInflater.inflate(R.layout.item_selected_passenger, null);
                addPsger.psName = (TextView) convertView.findViewById(R.id.pas_name_sid);
                addPsger.psType = (TextView) convertView.findViewById(R.id.pas_type_sid);
                addPsger.psIdcard = (TextView) convertView.findViewById(R.id.pas_idcard_sid);
                addPsger.seatType = (TextView) convertView.findViewById(R.id.pas_seatType_sid);
                addPsger.seatPrice = (TextView) convertView.findViewById(R.id.pas_price_sid);
                addPsger.odt_status = (TextView) convertView.findViewById(R.id.odt_status_id);
                addPsger.odt_ordernum = (TextView) convertView.findViewById(R.id.odt_ordernum_id);
                convertView.setTag(addPsger);
            } else {
                addPsger = (SelectedPsger) convertView.getTag();
            }

            addPsger.psName.setText(data.get(position).getName());
            addPsger.psType.setText(ServerConstValues.EASY_PASSENGER_TYPES[Integer.parseInt(data.get(position).getDocumentType())]);

            addPsger.psIdcard.setText(data.get(position).getDocumentNumber());
            addPsger.seatType.setText(ServerConstValues.SEAT_TYPES[Integer.parseInt(seatTypeValue) - 2]);
            addPsger.seatPrice.setText("$" + data.get(position).getPrice());

            addPsger.odt_ordernum.setText(data.get(position).getOrderId());
            return convertView;
        }

        public final class SelectedPsger {
            private TextView psName;
            private TextView psType;
            private TextView psIdcard;
            private TextView seatType;
            private TextView seatPrice;
            private TextView odt_status;
            private TextView odt_ordernum;
        }
    }

    public static String getStayTime(String arriveTime, String startTime) {
        String[] ahm = arriveTime.split(":");
        String[] shm = startTime.split(":");
        int ah = Integer.parseInt(ahm[0]);
        int am = Integer.parseInt(ahm[1]);
        int sh = Integer.parseInt(shm[0]);
        int sm = Integer.parseInt(shm[1]);

        if (sm < am) {
            sm += 60;
            sh--;
        }
        if (sh < ah) {
            sh += 24;
        }
        if (sh != ah) {
            return (sh - ah) + "时" + (sm - am) + "分";
        } else {
            return (sm - am) + "分";
        }
    }
}
