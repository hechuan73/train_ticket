package ts.trainticket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ts.trainticket.fragement.SignUp_Fragement;
import ts.trainticket.utils.ApplicationPreferences;


public class SignUpActivity extends AppCompatActivity {
    public static final int SIGN_UP_REQUEST_CODE = 0;
    private SignUp_Fragement signIn_fragement = null;
    private Button buttonHead = null;
    private TextView headText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        initHeads();
        initFragment();
        showFragment();
    }
    private void initHeads() {
        buttonHead = (Button) findViewById(R.id.common_head_back_btn);
        buttonHead.setVisibility(View.GONE);
        headText = (TextView) findViewById(R.id.title_head_tv);

        boolean isOnline = ApplicationPreferences.isUserOnLine(this);
        if(isOnline){
            String userName = ApplicationPreferences.getOneInfo(this, ApplicationPreferences.USER_NAME);
            if(userName != null || userName != "")
                headText.setText("Modify information");
        }
        else
            headText.setText("Sign Up");
    }

    private void initFragment(){
        signIn_fragement = new SignUp_Fragement();
        boolean isOnline = ApplicationPreferences.isUserOnLine(this);

            Bundle bundle = new Bundle();
            if(isOnline)
                bundle.putString("is_modify", "true");
            else
                bundle.putString("is_modify", "false");
            signIn_fragement.setArguments(bundle);

    }

    private void showFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_signin_container,signIn_fragement,"")
                .commit();
    }
}
