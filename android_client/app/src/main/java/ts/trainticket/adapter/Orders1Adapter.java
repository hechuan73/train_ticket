package ts.trainticket.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.gson.Gson;

import java.util.List;

import ts.trainticket.OrderDetailActivity;
import ts.trainticket.R;
import ts.trainticket.domain.Ticket;
import ts.trainticket.domain.OrderList;
import ts.trainticket.utils.CalendarUtil;


public class Orders1Adapter extends RecyclerView.Adapter<Orders1Adapter.Orders1ViewHolder> {

    private List<OrderList> ordersList;
    private Context context;

    public Orders1Adapter(List<OrderList> ordersList, Context context) {
        this.ordersList = ordersList;
        this.context = context;
    }

    @Override
    public Orders1ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_orders1, viewGroup, false);
        return new Orders1ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Orders1ViewHolder cpholder, int i) {
        cpholder.beginCityTv.setText(ordersList.get(i).getFrom());
        cpholder.endCityTv.setText(ordersList.get(i).getTo());

        String orderStates = Ticket.ORDER_STATE[Integer.parseInt(ordersList.get(i).getStatus()+"")];
        cpholder.orderStateTv.setText(orderStates);


        String pathStartDate = CalendarUtil.getYMD(ordersList.get(i).getTravelDate());
        String takeTime = CalendarUtil.getHMS(ordersList.get(i).getTravelTime());
        String time = pathStartDate + " - " + takeTime.substring(0, 5);
        cpholder.beginTimeTv.setText(time);

        cpholder.priceTv.setText("$" + ordersList.get(i).getPrice());

        String seatInfo = ordersList.get(i).getTrainNumber() + " " + Ticket.FULL_SEAT_TYPES[ordersList.get(i).getSeatClass() - 2];
        cpholder.seatTypeTv.setText(seatInfo);

        cpholder.idcardTv.setText( ordersList.get(i).getId());

        final int position = i;
        cpholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("ordersResult", new Gson().toJson(ordersList.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    class Orders1ViewHolder extends RecyclerView.ViewHolder {

        private TextView beginCityTv = null;
        private TextView endCityTv = null;
        private TextView orderStateTv = null;
        private TextView beginTimeTv = null;
        private TextView priceTv = null;
        private TextView seatTypeTv = null;
        private TextView reOrderTv = null;
        private TextView idcardTv = null;

        public Orders1ViewHolder(View itemView) {
            super(itemView);
            beginCityTv = (TextView) itemView.findViewById(R.id.beginCity_oid);
            endCityTv = (TextView) itemView.findViewById(R.id.endCity_oid);
            orderStateTv = (TextView) itemView.findViewById(R.id.orderState_oid);
            beginTimeTv = (TextView) itemView.findViewById(R.id.beginTime_oid);
            priceTv = (TextView) itemView.findViewById(R.id.price_oid);
            seatTypeTv = (TextView) itemView.findViewById(R.id.seatType_oid);
            reOrderTv = (TextView) itemView.findViewById(R.id.reOrder_oid);
            idcardTv = (TextView) itemView.findViewById(R.id.idcard_id);
        }
    }
}