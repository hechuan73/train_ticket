package ts.trainticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ts.trainticket.fragement.Contacts_Fragement;

public class ContactsActivity extends AppCompatActivity{

    private Contacts_Fragement contacts_fragement = null;
    private Button common_head_back_btn = null;
    private TextView title_head_tv = null;
    private ImageView add_pasBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        initFragment();
        initBackBtn();
        showFragment();

    }


    private void initFragment() {
        contacts_fragement = new Contacts_Fragement();
    }

    private void initBackBtn() {
        title_head_tv = (TextView) findViewById(R.id.title_head_tv);
        title_head_tv.setText("Contacts");
        add_pasBtn = (ImageView)findViewById(R.id.addw_pas_id);
        add_pasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), AddPsActivity.class);
                startActivity(intent);
            }
        });
        common_head_back_btn = (Button) findViewById(R.id.common_head_back_btn);
        common_head_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_contacts_container, contacts_fragement, "")
                .commit();
    }
}
