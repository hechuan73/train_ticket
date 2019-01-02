package ts.trainticket.fragement;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import ts.trainticket.R;
import ts.trainticket.adapter.Orders1Adapter;

import ts.trainticket.domain.OrderList;
import ts.trainticket.domain.OrderQueryInfo;
import ts.trainticket.httpUtils.RxHttpUtils;
import ts.trainticket.httpUtils.UrlProperties;
import ts.trainticket.utils.ApplicationPreferences;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;


public class Orders_fragement4 extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swiper = null;
    private RecyclerView recyclerView = null;

    private ImageView animationIV;
    private AnimationDrawable animationDrawable;

    private ImageView reserve_tipsImg4 = null;
    private TextView reserve_tipsTv4 = null;

    private int tagNum = 0;
    private  List<OrderList>  orderList ;
    private  List<OrderList>  otherOrderList ;
    private  List<OrderList> allOrderList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_orders4, container, false);

        initViews(view);
        getOrderListFromServer();
        return view;
    }
    private void initViews(View view) {
        swiper = (SwipeRefreshLayout) view.findViewById(R.id.refresh_orders4_table);
        swiper.setOnRefreshListener(this);
        swiper.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        recyclerView = (RecyclerView) view.findViewById(R.id.view_orders4_table);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        animationIV = (ImageView) view.findViewById(R.id.animationIV_os4);
        animationIV.setImageResource(R.drawable.pic_loading);
        animationDrawable = (AnimationDrawable) animationIV.getDrawable();
        animationDrawable.start();
        reserve_tipsImg4 = (ImageView) view.findViewById(R.id.reserve_tips_img_4);
        reserve_tipsTv4 = (TextView) view.findViewById(R.id.reserve_tips_4);
    }


    private void getOrderListFromServer(){
        tagNum = 0;
        orderList = new ArrayList<>();
        otherOrderList = new ArrayList<>();
        allOrderList = new ArrayList<>();

        String [] ordersUrl = new String[]{UrlProperties.clientIstioIp + UrlProperties.orderQuery};
        for(int i =0; i<ordersUrl.length; i++){
            getOrderDataFromServer(ordersUrl[i]);
        }
    }


    public void getOrderDataFromServer( String ordersUrl) {
        OrderQueryInfo orderQueryInfo = new OrderQueryInfo(null, null, null, null, 0, false, false, false);

        MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");
        RequestBody requestBody = RequestBody.create(mediaType, new Gson().toJson(orderQueryInfo));


        String loginId = ApplicationPreferences.getOneInfo(getContext(), ApplicationPreferences.ACCOUNT_ID);
        String token = ApplicationPreferences.getOneInfo(getContext(), ApplicationPreferences.ACCOUNT_TOKEN);

        subscription = RxHttpUtils.postWithHeader(ordersUrl, loginId, token, requestBody, getContext())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        unlockClick();
                        showStates();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(String responseResult) {
                        unlockClick();
                        if (responseResult != null && !responseResult.equals("")) {
                            Gson gson = new Gson();
                            List<OrderList> orderLists = new ArrayList<OrderList>();
                            Type listType = new TypeToken<List<OrderList>>() {
                            }.getType();
                            orderLists = gson.fromJson(responseResult, listType);

                            showTable(orderLists);
                        } else {
                            showStates();
                        }
                    }
                });
    }



    private void showTable(List<OrderList> ordersList1) {
        tagNum++;
        if(tagNum == 1)
            orderList.addAll(ordersList1);
        if(tagNum == 1) {
            allOrderList.addAll(orderList);
            List<OrderList> temps = new ArrayList<>();
            for (OrderList order : allOrderList) {
                if ("3".equals(order.getStatus()+"") || "4".equals(order.getStatus()+"") || "5".equals(order.getStatus()+"")) {
                    temps.add(order);
                }
            }
            if(temps.size() == 0){
                showStates();
            }else{
                animationDrawable.stop();
                animationIV.setVisibility(View.GONE);
            }
            Orders1Adapter myAdapter = new Orders1Adapter(temps,getContext());
            myAdapter.setHasStableIds(true);
            recyclerView.setAdapter(myAdapter);
        }

    }


    private void showStates(){
        reserve_tipsTv4.setVisibility(View.VISIBLE);
        reserve_tipsImg4.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        animationDrawable.stop();
        animationIV.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getOrderListFromServer();
                swiper.setRefreshing(false);
            }
        }, 500);
    }
}