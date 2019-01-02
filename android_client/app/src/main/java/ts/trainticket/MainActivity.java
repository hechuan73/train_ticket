package ts.trainticket;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import ts.trainticket.fragement.Account_Fragement;
import ts.trainticket.fragement.Reserve_Fragment;
import ts.trainticket.fragement.Station_Fragment;


public class MainActivity extends AppCompatActivity {
    public static final int CITY_CHOOSE_RESULT = 1;
    public static final int SIGN_IN_RESULT = 2;
    private FragmentTabHost mTabHost;
    private LayoutInflater layoutInflater;
    private Class fragmentArray[] = {Reserve_Fragment.class, Station_Fragment.class, Account_Fragement.class};
    private int imageViewArray[] = {R.drawable.one_change_icon_image, R.drawable.two_change_icon_image, R.drawable.four_change_icon_image};
    private String textViewArray[] = {"ticket reserve", "station search", "account"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        layoutInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(R.id.id_tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.id_nav_table_content);

        int count = fragmentArray.length;
        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(textViewArray[i]).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
        }
    }

    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.item_bottom_nav, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.nav_icon_iv);
        imageView.setImageResource(imageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.nav_text_tv);
        textView.setText(textViewArray[index]);
        return view;
    }
}