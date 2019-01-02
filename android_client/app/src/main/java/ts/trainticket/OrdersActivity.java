package ts.trainticket;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import ts.trainticket.fragement.BaseFragment;
import ts.trainticket.fragement.Orders_fragement1;
import ts.trainticket.fragement.Orders_fragement2;
import ts.trainticket.fragement.Orders_fragement3;
import ts.trainticket.fragement.Orders_fragement4;


public class OrdersActivity extends AppCompatActivity {


    List<BaseFragment> fragmentList = null;
    private Orders_fragement1 of1 = null;
    private Orders_fragement2 of2 = null;
    private Orders_fragement3 of3 = null;
    private Orders_fragement4 of4 = null;

    private FragmentPagerAdapter mPageAdapter = null;

    private TextView tv1 = null;
    private TextView tv2 = null;
    private TextView tv3 = null;
    private TextView tv4 = null;

    private ViewPager mViewPager = null;
    private View tabLine = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        initAdapter();
        initView();
    }

    private void initAdapter() {
        fragmentList = new ArrayList<>();
        of1 = new Orders_fragement1();
        of2 = new Orders_fragement2();
        of3 = new Orders_fragement3();
        of4 = new Orders_fragement4();
        fragmentList.add(of1);
        fragmentList.add(of2);
        fragmentList.add(of3);
        fragmentList.add(of4);
        mPageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
    }

    private void initView() {
        tv1 = (TextView) findViewById(R.id.tab_o1);
        tv2 = (TextView) findViewById(R.id.tab_o2);
        tv3 = (TextView) findViewById(R.id.tab_o3);
        tv4 = (TextView) findViewById(R.id.tab_o4);

        tv1.setOnClickListener(new TabOnClickListener());
        tv2.setOnClickListener(new TabOnClickListener());
        tv3.setOnClickListener(new TabOnClickListener());
        tv4.setOnClickListener(new TabOnClickListener());

        tabLine = findViewById(R.id.tab_line);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOverScrollMode(mViewPager.OVER_SCROLL_NEVER);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int lineWidth = getLineWidth(4);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tabLine.getLayoutParams();
                params.leftMargin = (int) ((positionOffset+position)*lineWidth+42);
                tabLine.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                changeTabColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public int getLineWidth(int tabCount){
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int lineWidth = metric.widthPixels/tabCount;
        return lineWidth;
    }
    private void changeTabColor(int position){
        resetTabColor();
        switch (position){
            case 0:
                tv1.setTextColor(Color.parseColor("#fed137"));
                break;
            case 1:
                tv2.setTextColor(Color.parseColor("#fed137"));
                break;
            case 2:
                tv3.setTextColor(Color.parseColor("#fed137"));
                break;
            case 3:
                tv4.setTextColor(Color.parseColor("#fed137"));
                break;
            default:
                break;
        }
    }
    /***
     * c
     */
    private void resetTabColor(){
        tv1.setTextColor(Color.parseColor("#ffffff"));
        tv2.setTextColor(Color.parseColor("#ffffff"));
        tv3.setTextColor(Color.parseColor("#ffffff"));
        tv4.setTextColor(Color.parseColor("#ffffff"));
    }

    private class TabOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            resetTabColor();
            switch (v.getId()){
                case R.id.tab_o1:
                    mViewPager.setCurrentItem(0);
                    tv1.setTextColor(Color.parseColor("#fed137"));
                    break;
                case R.id.tab_o2:
                    mViewPager.setCurrentItem(1);
                    tv2.setTextColor(Color.parseColor("#fed137"));
                    break;
                case R.id.tab_o3:
                    mViewPager.setCurrentItem(2);
                    tv3.setTextColor(Color.parseColor("#fed137"));
                    break;
                case R.id.tab_o4:
                    mViewPager.setCurrentItem(3);
                    tv4.setTextColor(Color.parseColor("#fed137"));
                    break;
                default:
                    break;
            }
        }
    }
}
