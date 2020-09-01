package com.startup.shoppyauto.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

public class DataSharePre {

    public static void saveDataSharedInt(Context context, String key, int value){

        context.getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        Log.d("Toantq", "save data to shared " );
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public static void saveDataSharedString(Context context, String key, String value){

        context.getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key,value);
        editor.commit();
    }


    public static int getDataSharedInt(Context context, String key){

        SharedPreferences sharedPref = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        int data = sharedPref.getInt(key, 0);
        return data;
    }

    public static String getDataSharedString(Context context, String key){

        SharedPreferences sharedPref = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String data = sharedPref.getString(key, "");
        return data;
    }
}
