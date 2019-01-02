package ts.trainticket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class SalesTimeActivity extends AppCompatActivity {

    private Button common_head_back_btn = null;
    private TextView title_head_tv = null;
    private TextView sale_time_begin = null;
    private TextView getSale_time_end = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saletime);

        initHeads();
        initViews();
    }

    private void initHeads() {
        common_head_back_btn = (Button) findViewById(R.id.common_head_back_btn);
        common_head_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        title_head_tv = (TextView) findViewById(R.id.title_head_tv);
        title_head_tv.setText("Sales Time");
    }

    private void initViews() {
        sale_time_begin = (TextView) findViewById(R.id.sale_time_begin);
        getSale_time_end = (TextView) findViewById(R.id.sale_time_end);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String beginDate = df.format(new Date());
        sale_time_begin.setText(beginDate);
        getSale_time_end.setText(getDate(30));
    }

    private String getDate(int num) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, num);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }


}
