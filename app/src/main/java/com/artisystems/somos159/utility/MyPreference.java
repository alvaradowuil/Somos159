package com.artisystems.somos159.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wuilder on 25/04/15.
 */
public class MyPreference {

    public static final String USER_ID = "pref_user_id";
    public static final String CURRENT_LATITUDE = "pref_current_atitude";
    public static final String CURRENT_LONGITUDE = "pref_current_longitude";
    public static final String MESSAGE_NOTIFICATION = "pref_message_notification";
    public static final String GROUP_SOCKET = "pref_group_socket";
    public static final String BRIGADA_ID = "pref_brigada_id";
    public static final String GROUP_NOTICE = "pref_group_notice";
    public static final String GROUP_STATE = "pref_group_state";


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

    public static void setCurrentLocation(Context context, Double latitude, Double longitude){
        initial(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CURRENT_LATITUDE, String.valueOf(latitude));
        editor.putString(CURRENT_LONGITUDE, String.valueOf(longitude));
        editor.commit();
    }

    public static Double getLatitude(Context context){
        return Double.parseDouble(sharedPreferences.getString(CURRENT_LATITUDE, ""));
    }

    public static Double getLongitude(Context context){
        return Double.parseDouble(sharedPreferences.getString(CURRENT_LONGITUDE, ""));
    }

    public static String getMessageNotification(Context context){
        initial(context);
        return sharedPreferences.getString(MESSAGE_NOTIFICATION, "");
    }

    public static String getGroupSocket(Context context){
        initial(context);
        return sharedPreferences.getString(GROUP_SOCKET, "");
    }

    public static int getBrigadaId(Context context){
        initial(context);
        return sharedPreferences.getInt(BRIGADA_ID, 0);
    }

    public static int getBroupNoticeId(Context context){
        initial(context);
        return sharedPreferences.getInt(GROUP_NOTICE, 0);
    }

    public static int getGroupState(Context context){
        initial(context);
        return sharedPreferences.getInt(GROUP_STATE, 0);
    }

    public static void clearPreference(){
        sharedPreferences.edit().clear().commit();
    }

}
