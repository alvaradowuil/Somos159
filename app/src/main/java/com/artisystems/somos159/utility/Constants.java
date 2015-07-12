package com.artisystems.somos159.utility;

import org.json.JSONArray;

/**
 * Created by wuilder on 11/07/15.
 */
public class Constants {
    public static String GET_ACITIVITIES = "http://192.168.0.159:8000/api/activities/";
    public static String POST_REGISTER_USER = "http://192.168.0.159:8000/api/persons/";
    public static String PUT_REGISTER_GCM = "http://192.168.0.159:8000/api/registergcm/";
    public static String GET_LOGIN = "http://192.168.0.159:8000/api/login?user=";
    public static String GET_COMMUNITIES = "http://192.168.0.159:8000/api/communities/";

    public static boolean isExit = false;
    public static JSONArray activitiesArray = null;
    public static String PRINCIPAL_COLOR = "#ff643a39";
}
