package ts.trainticket.fragement;

import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Subscription;

public class BaseFragment extends Fragment{

    protected Subscription subscription = null;
    protected List<View>views = new ArrayList<View>();

    // 锁住点击事件
    protected void lockClick() {
        for(View view:views) {
            view.setClickable(false);
        }
    }

    // 打开点击事件
    protected void unlockClick() {
        for(View view: views) {
            view.setClickable(true);
        }
    }


    protected void addToBtnController(View... views) {
        Collections.addAll(this.views, views);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(null != subscription && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
