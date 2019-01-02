package ts.trainticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ts.trainticket.fragement.CityChoose_Fragement;


public class CityChooseActivity extends AppCompatActivity {
    public static final int CITY_CHOOSE_REQUEST_CODE = 1;

    private CityChoose_Fragement cityChooseFragement;

    // head
    private Button head_back_btn = null;
    private TextView headText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_choose);
        initViews();
    }

    private void initViews() {
        initHead();
        initFragment();
        showFragment();

    }

    private void initHead() {
        headText = (TextView) findViewById(R.id.title_head_tv);
        headText.setText("Search Station");
        head_back_btn = (Button) findViewById(R.id.common_head_back_btn);
        head_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cityIntent = new Intent(getApplication(), MainActivity.class);
                startActivityForResult(cityIntent, CityChooseActivity.CITY_CHOOSE_REQUEST_CODE);
            }
        });
    }

    private void initFragment() {
        cityChooseFragement = new CityChoose_Fragement();
    }

    private void showFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_city_fragment_container, cityChooseFragement, "")
                .commit();
    }
}
