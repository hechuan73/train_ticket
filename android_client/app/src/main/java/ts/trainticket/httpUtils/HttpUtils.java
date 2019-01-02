package ts.trainticket.httpUtils;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import ts.trainticket.utils.ApplicationPreferences;

public class HttpUtils {


    public static String postDataWithOutHeader(String url_post, RequestBody requestBody ) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        //
        Request request = new Request.Builder().addHeader("Cookie","YsbCaptcha=B5D850BB397A4725B1C985955B2738F0").url(url_post).post(requestBody).build();
        Response response = okHttpClient.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().string();
        }else{
            throw new IOException("Unexcepted cde " + response);
        }
    }



    public static String postDataWithHeader(String url_post, String loginId, String loginToken, RequestBody requestBody ) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();

        //  "jenkins-timestamper-offset=-28800000; loginId="+ loginId+"; JSESSIONID=7E2A0BE075AF166D9FAAF91D0FBB537D; loginToken="+loginToken +"; YsbCaptcha=E2DDD2D58ACD4E40BDD874BBBEF1F5AD";
        String cookie = "loginId="+ loginId+"; loginToken="+loginToken +";";

        Request request = new Request.Builder()
                .addHeader("Cookie",cookie)
                .url(url_post)
                .post(requestBody)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().string();
        }else{
            throw new IOException("Unexcepted cde " + response);
        }
    }




    public static String sendGetRequestWithOutHeader(String uri) {
        String info = null;
        String allInfo = "";
        InputStream tempInput = null;
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(12000);
            connection.setReadTimeout(20000);

            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            tempInput = connection.getInputStream();
            Log.i("TAG", "uri" + uri);
            if (tempInput != null) {
                InputStreamReader reader = new InputStreamReader(tempInput, "utf-8");
                BufferedReader br = new BufferedReader(reader);

                while ((info = br.readLine()) != null) {
                    Log.i("TAG", "info" + info);
                    allInfo += info;
                }
                br.close();
                reader.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allInfo;
    }

    public static String sendGetRequestWithHeader(String uri, String loginId, String loginToken) {
        String info = null;
        String allInfo = "";
        InputStream tempInput = null;
        URL url = null;
        HttpURLConnection connection = null;
        // "jenkins-timestamper-offset=-28800000; loginId="+ loginId+"; JSESSIONID=7E2A0BE075AF166D9FAAF91D0FBB537D; loginToken="+loginToken +"; YsbCaptcha=E2DDD2D58ACD4E40BDD874BBBEF1F5AD";
        String cookie = "loginId="+ loginId+";  loginToken="+loginToken +"; ";
        try {
            url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(12000);
            connection.setReadTimeout(20000);
            connection.setRequestProperty("Cookie",cookie);

            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            tempInput = connection.getInputStream();
            Log.i("TAG", "uri" + uri);
            if (tempInput != null) {
                InputStreamReader reader = new InputStreamReader(tempInput, "utf-8");
                BufferedReader br = new BufferedReader(reader);

                while ((info = br.readLine()) != null) {
                    Log.i("TAG", "info" + info);
                    allInfo += info;
                }
                br.close();
                reader.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allInfo;
    }

}
