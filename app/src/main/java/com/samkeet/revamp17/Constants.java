package com.samkeet.revamp17;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Frost on 12-03-2017.
 */

public class Constants {

    public final static String VERSION_CODE = "1.1";

    public static class URLs {
        public static String BASE = "http://revamp.e-lemon-ators.club/v1/";
        public static String GET_EVENTS = "getevents.php";
        public static String REGISTRATION = "registrations.php";
        public static String PAYMENT_REG = "payment_reg.php";
        public static String PAYMENT_TRANSFER = "payment_transfer.php";
        public static String AUTHORIZATION = "authorization.php";
        public static String VERSION = "version.php";
        public static String BACKSTAGE = "backstage.php";
        public static String SIGNUP = "signup.php";
        public static String COSIGNUP = "cosignup.php";
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

        public static Integer[] getColors(int size) {

            Integer[] data = new Integer[36];
            data[0] = R.color.color_1;
            data[1] = R.color.color_2;
            data[2] = R.color.color_3;
            data[3] = R.color.color_4;
            data[4] = R.color.color_5;
            data[5] = R.color.color_6;
            data[6] = R.color.color_7;
            data[7] = R.color.color_8;
            data[8] = R.color.color_9;
            data[9] = R.color.color_10;
            data[10] = R.color.color_11;
            data[11] = R.color.color_12;
            data[12] = R.color.color_13;
            data[13] = R.color.color_14;
            data[14] = R.color.color_15;
            data[15] = R.color.color_16;
            data[16] = R.color.color_17;
            data[17] = R.color.color_18;
            data[18] = R.color.color_19;
            data[19] = R.color.color_20;
            data[20] = R.color.color_21;
            data[21] = R.color.color_22;
            data[22] = R.color.color_23;
            data[23] = R.color.color_24;
            data[24] = R.color.color_25;
            data[25] = R.color.color_26;
            data[26] = R.color.color_27;
            data[27] = R.color.color_28;
            data[28] = R.color.color_29;
            data[29] = R.color.color_30;
            data[30] = R.color.color_1;
            data[31] = R.color.color_2;
            data[32] = R.color.color_3;
            data[33] = R.color.color_4;
            data[34] = R.color.color_5;
            data[35] = R.color.color_6;

            Integer[] colors = new Integer[size];
            for (int i = 0; i < size; i++) {
                colors[i] = data[i];
            }

            return colors;
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
