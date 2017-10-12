package com.example.matheus.arduinotemp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Matheus on 12/10/2017.
 */

public class Preferences {
    private static final Preferences ourInstance = new Preferences();
    public static Preferences getInstance() {
        return ourInstance;
    }


    private Preferences() {
    }


    public void save(Context context, String content){
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preferences_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(context.getString(R.string.saved_ip),content);
        editor.commit();
    }



    public String retrieve(Context context){
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preferences_key),Context.MODE_PRIVATE);
        //String ip = context.getResources().getString(R.string.saved_ip);
        String ip = preferences.getString(context.getString(R.string.saved_ip),"default");
        return ip;
    }
}
