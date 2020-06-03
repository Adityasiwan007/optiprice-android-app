package com.ril.digitalwardrobeAI;

import android.content.Context;
import android.content.SharedPreferences;

public  class PreferenceManager {
    public static String IP = "IP";
    public  static  String USER_NAME="USER_NAME";
    public static String LOGGED_IN="LOGGED_IN";


    SharedPreferences sharedpreferences;
    private Context _context;

    public PreferenceManager(Context con) {
        this._context = con;
    }


    public void setIp(String ips, String ip) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(IP, Context.MODE_PRIVATE).edit();
        editor.putString(ips, ip);
        editor.commit();
    }

    public String getIp(String ips) {
        SharedPreferences sharedPrefs = _context.getSharedPreferences(IP, Context.MODE_PRIVATE);
        String ip = sharedPrefs.getString(ips, "ril");
        return ip;
    }


    public void setUserName(String userName, String name) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(userName, name);
        editor.commit();
    }

    public String getUserName(String userName) {
        SharedPreferences sharedPrefs = _context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE);
        String ip = sharedPrefs.getString(userName, "");
        return ip;
    }

    public void setIsLoggedIn(String isLoggedIn, boolean isFirst) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(LOGGED_IN, Context.MODE_PRIVATE).edit();
        editor.putBoolean(isLoggedIn, isFirst);
        editor.commit();
    }

    public boolean getIsLoggedIn(String isLoggedIn) {
        SharedPreferences sharedPrefs = _context.getSharedPreferences(LOGGED_IN, Context.MODE_PRIVATE);
        boolean ip = sharedPrefs.getBoolean(isLoggedIn, false);
        return ip;
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}