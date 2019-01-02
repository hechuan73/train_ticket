package ts.trainticket.httpUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.squareup.okhttp.RequestBody;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class RxHttpUtils {

    // post  without loginid , loginToken
    public static Observable postWithOutHeader(final String url, final RequestBody jsonstr , final Context context) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String > subscriber) {
                if (null == url || !isNetWorkConnected(context)) {
                    subscriber.onError(new Throwable("likely not connect to network"));
                    return;
                }
                try {
                    String temp = HttpUtils.postDataWithOutHeader(url,jsonstr);
                    subscriber.onNext(temp);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable("likely not connect to server"));
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    // post  with loginid , loginToken
    public static Observable postWithHeader(final String url, final String loginId,final String loginToken, final RequestBody jsonstr , final Context context) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String > subscriber) {
                if (null == url || !isNetWorkConnected(context)) {
                    subscriber.onError(new Throwable("likely not connect to network"));
                    return;
                }
                try {
                    String temp = HttpUtils.postDataWithHeader(url,loginId, loginToken, jsonstr);
                    subscriber.onNext(temp);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable("likely not connect to server"));
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    // get   without loginId  loginToken
    public static Observable getWithOutHeader(final String url, final Context context) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String > subscriber) {
                if (null == url || !isNetWorkConnected(context)) {
                    subscriber.onError(new Throwable("likely not connect to network"));
                    return;
                }
                try {
                    String temp = HttpUtils.sendGetRequestWithOutHeader(url);
                    subscriber.onNext(temp);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable("likely not connect to server"));
                }
            }
        }).subscribeOn(Schedulers.io());
    }




    // get   loginId  loginToken
    public static Observable getWithHeader(final String url, final String loginId,final String loginToken, final Context context) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String > subscriber) {
                if (null == url || !isNetWorkConnected(context)) {
                    subscriber.onError(new Throwable("likely not connect to network"));
                    return;
                }
                try {
                    String temp = HttpUtils.sendGetRequestWithHeader(url,loginId, loginToken);
                    subscriber.onNext(temp);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable("likely not connect to server"));
                }
            }
        }).subscribeOn(Schedulers.io());
    }


    private static boolean isNetWorkConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }


}
