package com.samkeet.revamp17;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Frost on 12-03-2017.
 */

public class Constants {

    public static class URLs {
        public static String BASE = "http://smartreva.16mb.com/Revamp17/";
        public static String GET_EVENTS = "getevents.php";
        public static String REGISTRATION = "registrations.php";
        public static String PAYMENT_REG = "payment_reg.php";
        public static String PAYMENT_TRANSFER = "payment_transfer.php";
        public static String AUTHORIZATION = "authorization.php";
    }

    public static class SharedPreferenceData {
        public static SharedPreferences sharedPreferences = null;
        public static SharedPreferences.Editor editor = null;

        public static String SHAREDPREFERENCES = "Revamp17";
        public static String IS_LOGGED_IN = "isloggedin";
        public static String MOBILE_NO = "mobile_no";
        public static String TOKEN = "token";
        public static String TYPE = "type";


        public static void initSharedPreferenceData(SharedPreferences sharedPreferences1) {
            sharedPreferences = sharedPreferences1;
            editor = sharedPreferences.edit();
        }

        public static boolean isSharedPreferenceInited() {
            if (sharedPreferences != null)
                return true;
            return false;
        }

        public static String getIsLoggedIn() {
            return sharedPreferences.getString(IS_LOGGED_IN, "NOT_AVALIBLE");
        }

        public static String getMobileNo() {
            return sharedPreferences.getString(MOBILE_NO, "NOT_AVALIBLE");
        }

        public static String getTOKEN() {
            return sharedPreferences.getString(TOKEN, "NOT_AVALIBLE");
        }

        public static String getTYPE() {
            return sharedPreferences.getString(TYPE, "NOT_AVALIBLE");
        }

        public static void setTYPE(String type) {
            editor.putString(TYPE, type);
            editor.apply();
        }

        public static void setIsLoggedIn(String isLoggedIn) {
            editor.putString(IS_LOGGED_IN, "yes");
            editor.apply();
        }

        public static void setTOKEN(String token) {
            editor.putString(TOKEN, token);
            editor.apply();
        }

        public static void setMobileNo(String userId) {
            editor.putString(MOBILE_NO, userId);
            editor.apply();
        }

        public static void clearData() {
            editor.clear();
            editor.apply();

        }
    }

    public static class Methods {

        public static boolean networkState(Context context, ConnectivityManager comman) {
            boolean wifi = comman.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
            boolean data = comman.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();

            if (wifi || data) {
                return true;
            } else {
                Toast toast = Toast.makeText(context, "No Internet Connnection!!! Try Again", Toast.LENGTH_LONG);
                toast.show();
                return false;
            }
        }

        public static boolean checkForSpecial(String s) {
            Pattern p = Pattern.compile("[^A-Za-z0-9 -.@]");
            Matcher m = p.matcher(s);
            boolean b = m.find();
            return b;
        }
    }

    public static class Events {

        public static JSONArray culturalEvents;
        public static JSONArray technicalEvents;
        public static JSONArray managementEvents;
        public static JSONArray sportsEvents;

        public static boolean isCulturalAvalible = false;
        public static boolean isTechnicalAvalible = false;
        public static boolean isManagementAvalible = false;
        public static boolean isSportsAvalible = false;

    }

}
