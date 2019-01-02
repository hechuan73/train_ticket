package ts.trainticket.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import ts.trainticket.LoginActivity;
import ts.trainticket.MainActivity;
import ts.trainticket.R;
import ts.trainticket.SignUpActivity;
import ts.trainticket.domain.Account;
import ts.trainticket.domain.LoginInfo;
import ts.trainticket.domain.LoginResult;
import ts.trainticket.httpUtils.RxHttpUtils;
import ts.trainticket.httpUtils.UrlProperties;
import ts.trainticket.utils.ApplicationPreferences;


public class Login_Fragment extends BaseFragment implements View.OnClickListener {

    private EditText eamil_sign_editText = null;
    private EditText password_sign_editText = null;

    private Button loginBtn = null;
    private Button signinBtn = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_login, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        eamil_sign_editText = (EditText) view.findViewById(R.id.eamil_SignIn_Id);
        password_sign_editText = (EditText) view.findViewById(R.id.password_signIn_id);

        loginBtn = (Button) view.findViewById(R.id.login_acId);
        loginBtn.setOnClickListener(this);
        signinBtn = (Button) view.findViewById(R.id.signin_acId);
        signinBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_acId:
                loginGetData();
                break;
            case R.id.signin_acId:
                startActivityForResult(new Intent(getActivity(), SignUpActivity.class), SignUpActivity.SIGN_UP_REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    public void loginGetData() {
        String email = eamil_sign_editText.getText().toString();
        String password = password_sign_editText.getText().toString();
        // todo   useless in our system
        String verifyCode = "abcd";

        if ((email == null || email == "") || (password == null || password == "")) {
            Toast.makeText(getContext(), "email or password is empty ", Toast.LENGTH_SHORT).show();
        } else {
            String getUserUri = UrlProperties.clientIstioIp + UrlProperties.login;
            final LoginInfo loginInfo = new LoginInfo(email, password, verifyCode);

            MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");
            RequestBody requestBody = RequestBody.create(mediaType, new Gson().toJson(loginInfo));

            subscription = RxHttpUtils.postWithOutHeader(getUserUri, requestBody, getContext())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {

                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            unlockClick();
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(String responseResult) {
                            unlockClick();
                            if (responseResult != null && !responseResult.equals("")) {
                                Gson gson = new Gson();

                                LoginResult loginResult = gson.fromJson(responseResult, LoginResult.class);
                                if (loginResult.getStatus()) {
                                    Account account = loginResult.getAccount();

                                    ApplicationPreferences.setUserInfo(getContext(), account.getId()+"",
                                            account.getPassword(),account.getGender()+"", account.getName(),
                                            account.getDocumentType()+"",account.getDocumentNum(),account.getEmail(),
                                            loginResult.getToken());

                                    Toast.makeText(getContext(), "login Success", Toast.LENGTH_SHORT).show();

                                    ApplicationPreferences.setOneInfo(getContext(), ApplicationPreferences.ACCOUNT_STATE, ApplicationPreferences.STATE_ONLINE);

                                    getActivity().setResult(MainActivity.SIGN_IN_RESULT);
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getContext(), "login failed", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(getContext(), "login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (SignUpActivity.SIGN_UP_REQUEST_CODE == requestCode && LoginActivity.SIGN_IN_RESULT_CODE == resultCode && null != data) {
            eamil_sign_editText.setText(data.getStringExtra(LoginActivity.KEY_ACCOUNT_NAME));
            password_sign_editText.setText(data.getStringExtra(LoginActivity.KEY_ACCOUNT_PASSWORD));
        }
    }

    @Override
    public void onStop() {
        if (null != subscription && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        unlockClick();
        super.onStop();
    }
}