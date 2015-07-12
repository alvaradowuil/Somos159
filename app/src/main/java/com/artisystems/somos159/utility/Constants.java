package com.artisystems.somos159.utility;

import org.json.JSONArray;

/**
 * Created by wuilder on 11/07/15.
 */
public class Constants {


    public static String GET_ACITIVITIES = "http://104.236.241.155:9020/api/activities/";
    public static String POST_REGISTER_USER = "http://104.236.241.155:9020/api/persons/";
    public static String PUT_REGISTER_GCM = "http://104.236.241.155:9020/api/registergcm/";
    public static String GET_LOGIN = "http://104.236.241.155:9020/api/login?user=";
    public static String GET_COMMUNITIES = "http://104.236.241.155:9020/api/communities/";

    public static boolean isExit = false;
    public static JSONArray activitiesArray = null;
    public static String PRINCIPAL_COLOR = "#ff0d6061";
}
