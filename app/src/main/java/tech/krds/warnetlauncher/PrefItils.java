package tech.krds.warnetlauncher;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class PrefItils {
    private static final String PREF_KIOSK_MODE = "pref_kiosk_mode";
    private static final String PREFIX = "ngecheat_mandul_7_turunan_";
    private static final String SUFFIX = "_allah_maha_melihat";

    private static  final  String nama = PREFIX + "nama" + SUFFIX;

    private static  final  String start = PREFIX + "start" + SUFFIX;

    private static  final String detik = PREFIX + "detik" + SUFFIX;
    private static  final String menit = PREFIX + "menit" + SUFFIX;
    private static  final String jam = PREFIX +  "jam" + SUFFIX;

    private static final String cost = PREFIX +  "cost" + SUFFIX;

    private static final String locked = PREFIX +  "locked" + SUFFIX;



    public static String getNama(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(nama, "");
    }

    public static void setNama(String namax, Context context) {


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(nama, namax).apply();
    }

    public static String getStart(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(start, "");
    }

    public static void setStart(String startx, Context context) {


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(start, startx).apply();
    }

    public static String getDetik(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(detik, "0");
    }

    public static void setDetik(String detikx, Context context) {


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(detik, detikx).apply();
    }

    public static String getMenit(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(menit, "0");
    }

    public static void setMenit(String menitx, Context context) {


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(menit, menitx).apply();
    }

    public static String getJam(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(jam, "0");
    }

    public static void setJam(String jamx, Context context) {


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(jam, jamx).apply();
    }

    public static String getCost(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(cost, "");
    }

    public static void setCost(String costx, Context context) {


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(cost, costx).apply();
    }

    public static Boolean getLocked(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(locked, true);
    }

    public static void setLocked(Boolean cost, Context context) {


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(locked, cost).apply();
    }


    public static String getWarnetName(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString("warnetname","Nama Warnet");
    }

    public static String getWarnetAddress(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString("warnetadress","Alamat Warnet");
    }

    public static String getWarnetEmail(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString("warnetemail","Email");
    }



    public static void setWarnetName(final String name, final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString("warnetname", name).apply();
    }

    public static void setWarnetAddress(final String name, final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString("warnetadress", name).apply();
    }

    public static void setWarnetEmail(final String name, final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString("warnetemail", name).apply();
    }
}