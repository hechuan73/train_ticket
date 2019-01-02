package ts.trainticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import ts.trainticket.fragement.TravelPath_Fragment;


public class TravelPathActivity extends AppCompatActivity {

    private Button common_head_back_btn = null;
    private TextView title_head_tv = null;

    private TravelPath_Fragment contactPathFragment;
    private LinearLayout back_btnLyt= null;
    public static final int PATH_CHOOSE_RESULT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactpath);
        initFragment();
        initBackBtn();
        showFragment();
    }


    private void initFragment() {
        contactPathFragment = new TravelPath_Fragment();

        //
        Bundle bundle = new Bundle();
        bundle.putString("start_city", getIntent().getStringExtra("start_city"));
        bundle.putString("end_city", getIntent().getStringExtra("end_city"));
        bundle.putString("pathStartDate",getIntent().getStringExtra("pathStartDate"));

        bundle.putString("start_time",getIntent().getStringExtra("start_time"));
        bundle.putString("arrive_time",getIntent().getStringExtra("arrive_time"));

        bundle.putString("car_typegd",getIntent().getStringExtra("car_typegd"));
        bundle.putString("car_typez",getIntent().getStringExtra("car_typez"));
        bundle.putString("car_typet",getIntent().getStringExtra("car_typet"));
        bundle.putString("car_typek",getIntent().getStringExtra("car_typek"));
        contactPathFragment.setArguments(bundle);
    }

    private void initBackBtn() {
        common_head_back_btn = (Button) findViewById(R.id.common_head_back_btn);

        title_head_tv = (TextView) findViewById(R.id.title_head_tv);
        title_head_tv.setText("Travel Path");


        back_btnLyt = (LinearLayout) findViewById(R.id.back_btn_lid);
        back_btnLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cityIntent = new Intent(getApplication(), MainActivity.class);
                startActivity(cityIntent);
            }
        });
    }

    private void showFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_contactpath_container, contactPathFragment, "")
                .commit();
    }
}
