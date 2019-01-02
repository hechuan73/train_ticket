package ts.trainticket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ts.trainticket.fragement.AddPs_Fragement;


public class AddPsActivity extends AppCompatActivity {

    private AddPs_Fragement addPs_fragement = null;
    private Button buttonHead = null;
    private TextView headText = null;

    public static final int ADD_PS_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpassger);
        initHeads();
        initFragment();
        showFragment();
    }

    private void initHeads() {
        buttonHead = (Button) findViewById(R.id.common_head_back_btn);
        headText = (TextView) findViewById(R.id.title_head_tv);
        headText.setText("Add Passenger");
        buttonHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initFragment(){
        addPs_fragement = new AddPs_Fragement();
    }

    private void showFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_addps_fragment_container,addPs_fragement,"")
                .commit();
    }
}
