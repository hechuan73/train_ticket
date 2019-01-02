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
import rx.subscriptions.CompositeSubscription;
import ts.trainticket.LoginActivity;
import ts.trainticket.R;
import ts.trainticket.domain.RegisterInfo;
import ts.trainticket.httpUtils.RxHttpUtils;
import ts.trainticket.httpUtils.UrlProperties;


public class SignUp_Fragement extends BaseFragment {

    private EditText email_editText = null;
    private EditText password_editText = null;
    private Button btnSignUp;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_signup, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        email_editText = (EditText) view.findViewById(R.id.email_signUp_id);
        password_editText = (EditText) view.findViewById(R.id.password_signUp_id);

        btnSignUp = (Button) view.findViewById(R.id.signin_btn_sid);
        btnSignUp.setOnClickListener(new SignInListener());
    }


    private class SignInListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            lockClick();
            signInToServer();
        }
    }

    private void signInToServer() {
        final String email = email_editText.getText().toString();
        final String password = password_editText.getText().toString();

        RegisterInfo registerInfo = new RegisterInfo(email, password);

        MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");
        RequestBody requestBody = RequestBody.create(mediaType, new Gson().toJson(registerInfo));

        String registerUri = UrlProperties.clientIstioIp + UrlProperties.register;

        subscription = RxHttpUtils.postWithOutHeader(registerUri,requestBody , getContext())
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
                            Intent intent = new Intent();
                            intent.putExtra(LoginActivity.KEY_ACCOUNT_NAME, email);
                            intent.putExtra(LoginActivity.KEY_ACCOUNT_PASSWORD, password);
                            Toast.makeText(getContext(), "register success", Toast.LENGTH_SHORT).show();
                            getActivity().setResult(LoginActivity.SIGN_IN_RESULT_CODE, intent);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), "register failed, service unavailability", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStop() {
        if (compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
        }
        unlockClick();
        super.onStop();
    }
}
