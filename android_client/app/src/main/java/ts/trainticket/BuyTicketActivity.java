package ts.trainticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ts.trainticket.fragement.PathInfo_Fragment;


public class BuyTicketActivity extends AppCompatActivity {

    private PathInfo_Fragment buyTicketFragment;

    // head
    private Button head_back_btn = null;
    private TextView headText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);
        initViews();
    }

    private void initViews(){
        initHead();
        initFragment();
        showFragment();

    }
    private void initHead(){
        headText = (TextView) findViewById(R.id.title_head_tv);
        headText.setText("Path Info");
        head_back_btn = (Button) findViewById(R.id.common_head_back_btn);
        head_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cityIntent = new Intent(getApplication(), TravelPathActivity.class);
                startActivityForResult(cityIntent, CityChooseActivity.CITY_CHOOSE_REQUEST_CODE);
            }
        });
    }

    private void initFragment(){
        buyTicketFragment = new PathInfo_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("pathInfo",getIntent().getStringExtra("item_contact_path"));
        buyTicketFragment.setArguments(bundle);
    }
    private void showFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_byt_fragment_container,buyTicketFragment,"")
                .commit();
    }
}
