package com.smn.update;

import android.content.Context;
import android.util.Log;

public class UpdateChecker {


    public static void checkForDialog(Context context,String url) {
        if (context != null) {
            new CheckUpdateTask(context,url, Constants.TYPE_DIALOG, false).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }
    }


    public static void checkForNotification(Context context,String url) {
        if (context != null) {
            new CheckUpdateTask(context, url,Constants.TYPE_NOTIFICATION, false).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }

    }


}
