package com.artisystems.somos159.utility;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

/**
 * Created by wuilder on 15/04/15.
 */
public class RequestWs {

    public JSONObject getJsonObject(String url, Context context){
        JSONObject resultJsonObject = null;

        try{
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            try {
                HttpResponse response = client.execute(request);
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                String responseString = convertStreamToString(inputStream);
                Log.e("ARTI", "responseString = " + responseString);
                if (response.getStatusLine().getStatusCode() == 200) {
                    resultJsonObject = new JSONObject(responseString);
                } else {
                    resultJsonObject = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return resultJsonObject;
    }

    public JSONArray getJsonArray(String url, Context context){
        JSONArray resultJsonArray = null;

        try{
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            try {
                HttpResponse response = client.execute(request);
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                String responseString = convertStreamToString(inputStream);
                if (response.getStatusLine().getStatusCode() == 200) {
                    resultJsonArray = new JSONArray(responseString);
                } else {
                    resultJsonArray = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return resultJsonArray;
    }

    public void putJsonObject(String urlString, List<NameValuePair> pairs, Context context){
        try{
            URL url = new URL(urlString);
            HttpClient client = new DefaultHttpClient();
            HttpPut put= new HttpPut(String.valueOf(url));

            put.setEntity(new UrlEncodedFormEntity(pairs));

            HttpResponse response = client.execute(put);

            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();
            String responseString = convertStreamToString(inputStream);
            Log.e("ARTI", "put = " + responseString);
            if (response.getStatusLine().getStatusCode() == 200) {
//                resultJsonArray = new JSONArray(responseString);
            } else {
//                resultJsonArray = null;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public JSONObject postJsonObject(String urlString, List<NameValuePair> pairs, Context context){
        JSONObject resultJsonObject = null;
        try{
            URL url = new URL(urlString);
            HttpClient client = new DefaultHttpClient();
            HttpPost put= new HttpPost(String.valueOf(url));

            put.setEntity(new UrlEncodedFormEntity(pairs));

            HttpResponse response = client.execute(put);

            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();
            String responseString = convertStreamToString(inputStream);
            Log.e("ARTI", "post = " + responseString);
//            if (response.getStatusLine().getStatusCode() == 200) {
                resultJsonObject = new JSONObject(responseString);
//            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return resultJsonObject;
    }

//    public JSONObject postJsonObject(String url, Context context, MultipartEntity multipartEntity){
//        JSONObject resultJsonObject = null;
//
//        try{
//            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
//            HttpPost post = new HttpPost(url);
//            HttpResponse response;
//            BasicHeader contentType = new BasicHeader(
//                    HTTP.DEFAULT_CONTENT_CHARSET, "UTF-8");
//            post.setHeader(contentType);
//            post.setEntity(multipartEntity);
//            try {
//                response = defaultHttpClient.execute(post);
//                if (response.getStatusLine().getStatusCode() == 200) {
//                    HttpEntity entity = response.getEntity();
//                    InputStream inputStream = entity.getContent();
//                    String responseString = convertStreamToString(inputStream);
//                    resultJsonObject = new JSONObject(responseString);
//                }
//            } catch (ClientProtocolException e) {
//                e.printStackTrace();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//
//        return resultJsonObject;
//    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
