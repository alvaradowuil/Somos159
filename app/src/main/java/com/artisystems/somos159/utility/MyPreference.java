package com.artisystems.somos159.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wuilder on 25/04/15.
 */
public class MyPreference {

    public static final String USER_ID = "pref_user_id";
    public static final String MESSAGE_NOTIFICATION = "pref_message_notification";

    public static SharedPreferences sharedPreferences;

    public static void initial(Context context){
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences(
                    context.getPackageName(), Context.MODE_PRIVATE);
        }
    }

    public static void setValueString(Context context, String key, String value){
        initial(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    };

    public static void setValueBoolean(Context context, String key, Boolean value){
        initial(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    };

    public static void setValueInt(Context context, String key, Integer value){
        initial(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static String getUserId(){
      return sharedPreferences.getString(USER_ID, "");
    };

    public static void setMessageNotification(Context context, String message){
        setValueString(context, MESSAGE_NOTIFICATION, message + getMessageNotification(context));
    };

    public static String getMessageNotification(Context context){
        initial(context);
        return sharedPreferences.getString(MESSAGE_NOTIFICATION, "");
    }

    public static void clearPreference(){
        sharedPreferences.edit().clear().commit();
    }

}
