package ts.trainticket.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationPreferences {

    public static final String PREFERENCES_NAME = "TrainTicket";

    public static final String STATE_OFFLINE = "stateOffline";
    public static final String STATE_ONLINE = "stateOnline";

    public static final String ACCOUNT_STATE = "accountState";

    public static final String USER_NAME = "userName";


    public static final String ACCOUNT_ID = "account_id";
    public static final String ACCOUNT_PASSWORD = "account_password";
    public static final String ACCOUNT_GENDER = "account_gender";
    public static final String ACCOUNT_NAME = "account_name";
    public static final String ACCOUNT_DOCUMENT_TYPE = "account_document_type";
    public static final String ACCOUNT_DOCUMENT_NUM = "account_document_num";
    public static final String ACCOUNT_DOCUMENT_EMAIL = "account_document_eamil";
    public static final String ACCOUNT_TOKEN = "account_token";

    //
    public static void setUserInfo(Context context, String account_id, String account_password, String account_gender,
                                   String account_name, String account_document_type, String account_document_num,
                                   String account_document_eamil, String account_token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(ACCOUNT_ID, account_id);
        editor.putString(ACCOUNT_PASSWORD, account_password);
        editor.putString(ACCOUNT_GENDER, account_gender);
        editor.putString(ACCOUNT_NAME, account_name);
        editor.putString(ACCOUNT_DOCUMENT_TYPE, account_document_type);
        editor.putString(ACCOUNT_DOCUMENT_NUM, account_document_num);
        editor.putString(ACCOUNT_DOCUMENT_EMAIL, account_document_eamil);
        editor.putString(ACCOUNT_TOKEN, account_token);
        editor.apply();
    }

    public static boolean isUserOnLine(Context context) {
        String state = getOneInfo(context, ACCOUNT_STATE);
        return STATE_ONLINE.equals(state);
    }

    public static void clearLoginInfo(Context context) {
        setOneInfo(context, ACCOUNT_STATE, STATE_OFFLINE);
    }

    public static void setOneInfo(Context context, String key, String values) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, values);
        editor.apply();
    }

    public static String getOneInfo(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static void setMemoryPaths(Context context, List<Object> paths) {
        JSONArray jsonPaths = new JSONArray(paths);
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("MemoryPaths1", jsonPaths.toString());
        editor.apply();
    }

    public static List<Object> getMemoryPaths(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return JsonUtil.getListFromJsonString(preferences.getString("MemoryPaths1", ""));
    }


    public static void setMemoryCities(Context context, List<Object> cities) {
        JSONArray jsonCities = new JSONArray(cities);
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("MemoryCities", jsonCities.toString());
        editor.apply();
    }

    public static List<Object> getMemoryCitiess(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return JsonUtil.getListFromJsonString(preferences.getString("MemoryCities", ""));
    }

    public static void setAllCities(Context context, List<Object> cities) {
        JSONArray jsonArray = new JSONArray(cities);
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("allCities", jsonArray.toString());
        editor.apply();
    }


    public static List<Object> getAllCities(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return JsonUtil.getListFromJsonString(preferences.getString("allCities", ""));
    }


    public static void setStayTimeStation(Context context, List<Object> stationTimes) {
        JSONArray jsonArray = new JSONArray(stationTimes);
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("stationTimes", jsonArray.toString());
        editor.apply();
    }

    public static List<Object> getStayTimeStation(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return JsonUtil.getListFromJsonString(preferences.getString("stationTimes", ""));
    }
}
