package ts.trainticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ts.trainticket.fragement.OrderDetail_Fragement;


public class OrderDetailActivity extends AppCompatActivity {

    private TextView headText = null;
    private OrderDetail_Fragement goBuy_fragement;
    // head
    private Button head_back_btn = null;
    private ImageView go_to_orderBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_buy);
        initHead();
        initFragment();
        initData();
        showFragment();
    }

    private void initHead() {
        headText = (TextView) findViewById(R.id.title_head_tv);
        headText.setText("Order Detail");

        head_back_btn = (Button) findViewById(R.id.common_head_back_btn);
        head_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Intent cityIntent = new Intent(getApplication(), TicketReserveActivity.class);
                //   startActivity(cityIntent);
                finish();
            }
        });

        go_to_orderBtn = (ImageView) findViewById(R.id.go_to_orderList);
        go_to_orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ordesIntent = new Intent(getApplication(), OrdersActivity.class);
                startActivity(ordesIntent);
            }
        });

    }

    private void initFragment() {
        goBuy_fragement = new OrderDetail_Fragement();
    }

    private void initData() {
        Bundle bundle = new Bundle();
        bundle.putString("reserve_result", getIntent().getStringExtra("reserve_result"));

        bundle.putString("ordersResult", getIntent().getStringExtra("ordersResult"));
        goBuy_fragement.setArguments(bundle);
    }

    private void showFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_buy_container, goBuy_fragement, "")
                .commit();
    }
}
