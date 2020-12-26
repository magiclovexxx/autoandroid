package com.startup.shoppyauto.System;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class PermissionSupport {

    public static final int READ_PERMISSIONS_CONTACTS = 0;
    public static final int READ_PERMISSIONS_READ_SMS = 1;
    public static final int READ_PERMISSIONS_STORAGE = 2;
    public static final int READ_PERMISSIONS_READ_PHONE_STATE = 3;
    public static final int READ_PERMISSIONS_ALL = 4;
    private static Activity activity;
    private static PermissionSupport support;


    public PermissionSupport(Activity activity) {
        this.activity = activity;
    }

    public static PermissionSupport getInstall(Activity activity) {
        if (support == null || activity != support.activity)
            support = new PermissionSupport(activity);
        return support;
    }

    public static Boolean hasPermissionGranted(String permission) {
        Boolean status = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
        Log.d("PermissionSupport", "hasPermissionGranted: " + permission + ": " + status);
        return status;
    }

    public static Boolean hasPermissionGranted(Context context, String permission) {
        Boolean status = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        Log.d("PermissionSupport", "hasPermissionGranted: " + permission + ": " + status);
        return status;
    }

    public static Boolean requestPermissionAll() {
        if ( hasPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)
                && hasPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            return true;
        if (Build.VERSION.SDK_INT < 23) return true;

        activity.requestPermissions(new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                READ_PERMISSIONS_ALL);
        return false;
    }

    public static Boolean requestPermissionStore() {
        if (hasPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            return true;
        if (Build.VERSION.SDK_INT < 23) return true;

        activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                READ_PERMISSIONS_STORAGE);
        return false;
    }


    public static Boolean requestPermissionContact() {
        if (hasPermissionGranted(Manifest.permission.READ_CONTACTS)) return true;
        if (Build.VERSION.SDK_INT < 23) return true;

        activity.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                READ_PERMISSIONS_CONTACTS);
        return false;
    }

    public static Boolean requestPermissionPhoneState() {
        if (hasPermissionGranted(Manifest.permission.READ_PHONE_STATE)) return true;
        if (Build.VERSION.SDK_INT < 23) return true;

        activity.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                READ_PERMISSIONS_READ_PHONE_STATE);
        return false;
    }

    public static Boolean requestPermissionReadSms() {
        if (hasPermissionGranted(Manifest.permission.READ_SMS) && hasPermissionGranted(Manifest.permission.SEND_SMS) && hasPermissionGranted(Manifest.permission.RECEIVE_SMS))
            return true;
        if (Build.VERSION.SDK_INT < 23) return true;

        activity.requestPermissions(new String[]{Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS},
                READ_PERMISSIONS_READ_SMS);
        return false;
    }


}