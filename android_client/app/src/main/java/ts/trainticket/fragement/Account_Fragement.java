package ts.trainticket.fragement;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alertdialogpro.AlertDialogPro;
import com.dd.CircularProgressButton;

import ts.trainticket.AboutActivity;
import ts.trainticket.ContactsActivity;
import ts.trainticket.LoginActivity;
import ts.trainticket.OrdersActivity;
import ts.trainticket.R;
import ts.trainticket.utils.ApplicationPreferences;

public class Account_Fragement extends BaseFragment implements View.OnClickListener {


    private TextView title_personinfo_tv;

    private CircularProgressButton button = null;
    private TextView tv = null;
    private TextView accountName_textView = null;
    private TextView eamil_textView = null;


    private LinearLayout loginPanel_iid = null;
    private LinearLayout userInfo_panel_iid = null;

    private LinearLayout contacts_Layout = null;
    private LinearLayout orders_Layout = null;

    private LinearLayout about_mid = null;
    private LinearLayout logout_mid = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_account, container, false);
        initViews(view);

        return view;
    }

    private void initViews(View view) {
        title_personinfo_tv = (TextView) view.findViewById(R.id.title_personinfo_tv);
        title_personinfo_tv.setText("Account");


        loginPanel_iid = (LinearLayout) view.findViewById(R.id.loginPanel_layout_id);
        userInfo_panel_iid = (LinearLayout) view.findViewById(R.id.userInfo_panel_iid);


        accountName_textView = (TextView) view.findViewById(R.id.account_name_id);
        eamil_textView = (TextView) view.findViewById(R.id.account_eamil_id);

        contacts_Layout = (LinearLayout) view.findViewById(R.id.contacts_layout_id);
        orders_Layout = (LinearLayout) view.findViewById(R.id.orders_layout_id);

        about_mid = (LinearLayout) view.findViewById(R.id.about_layout_id);
        logout_mid = (LinearLayout) view.findViewById(R.id.logout_layout_id);

        loginPanel_iid.setOnClickListener(this);

        contacts_Layout.setOnClickListener(this);
        orders_Layout.setOnClickListener(this);

        about_mid.setOnClickListener(this);
        logout_mid.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (ApplicationPreferences.isUserOnLine(getContext())) {
            showUserInfoPanel();
            showUserInfo();
            showMenuOrNot(true);
        } else {
            showLoginPanel();
            showMenuOrNot(false);
        }
    }

    private void showLoginPanel() {
        userInfo_panel_iid.setVisibility(View.GONE);
        loginPanel_iid.setVisibility(View.VISIBLE);
    }

    private void showUserInfoPanel() {
        userInfo_panel_iid.setVisibility(View.VISIBLE);
        loginPanel_iid.setVisibility(View.GONE);
    }

    private void showMenuOrNot(boolean showOrNot) {
        if (showOrNot) {
            contacts_Layout.setVisibility(View.VISIBLE);
            orders_Layout.setVisibility(View.VISIBLE);
            logout_mid.setVisibility(View.VISIBLE);
        } else {
            contacts_Layout.setVisibility(View.GONE);
            orders_Layout.setVisibility(View.GONE);
            logout_mid.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (!ApplicationPreferences.isUserOnLine(getContext())) {
            showLoginPanel();
            return;
        }
        showUserInfoPanel();
        showUserInfo();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showUserInfo() {
        String accountName = ApplicationPreferences.getOneInfo(getContext(), ApplicationPreferences.ACCOUNT_NAME);
        String eamil = ApplicationPreferences.getOneInfo(getContext(), ApplicationPreferences.ACCOUNT_DOCUMENT_EMAIL);
        accountName_textView.setText(accountName);
        eamil_textView.setText(eamil);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contacts_layout_id:
                startActivity(new Intent(getActivity(), ContactsActivity.class));
                break;
            case R.id.orders_layout_id:
                startActivity(new Intent(getActivity(), OrdersActivity.class));
                break;
            case R.id.loginPanel_layout_id:
                startActivityForResult(new Intent(getContext(), LoginActivity.class), LoginActivity.SIGN_IN_REQUEST_CODE);
                break;
            case R.id.logout_layout_id:
                AlertDialogPro.Builder builder = new AlertDialogPro.Builder(getContext());
                builder.setMessage("Are you sure you want to log out?").setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ApplicationPreferences.clearLoginInfo(getContext());
                        showLoginPanel();
                        showMenuOrNot(false);
                    }
                }).setNegativeButton("Cancel", null).show();
                break;
            case R.id.about_layout_id:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;

            default:
                break;
        }
    }
}
