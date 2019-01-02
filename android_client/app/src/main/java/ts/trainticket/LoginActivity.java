package ts.trainticket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ts.trainticket.fragement.Login_Fragment;


public class LoginActivity extends AppCompatActivity {

    public static final int SIGN_IN_REQUEST_CODE = 0;

    public static final int SIGN_IN_RESULT_CODE = 1;


    public static final String KEY_ACCOUNT_NAME = "keyAccountName";
    public static final String KEY_ACCOUNT_PASSWORD = "keyAccountPassword";

    private Button buttonHead = null;
    private TextView headText = null;

    private Login_Fragment login_fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initHeads();
        initFragment();
        showFragment();

    }

    private void initHeads() {
        buttonHead = (Button) findViewById(R.id.common_head_back_btn);
        buttonHead.setVisibility(View.GONE);
        headText = (TextView) findViewById(R.id.title_head_tv);
        headText.setText("Sign In");
    }

    private void initFragment(){
        login_fragment = new Login_Fragment();
    }
    private void showFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_login_container,login_fragment,"")
                .commit();
    }
}
