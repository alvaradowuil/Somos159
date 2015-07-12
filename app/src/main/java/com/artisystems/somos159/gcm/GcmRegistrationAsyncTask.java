package com.artisystems.somos159.gcm;

import android.content.Context;
import android.os.AsyncTask;

import com.artisystems.somos159.utility.Constants;
import com.artisystems.somos159.utility.MyPreference;
import com.artisystems.somos159.utility.RequestWs;
import com.example.wuilder.myapplication.backend.registration.Registration;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuilder on 7/07/15.
 */
public class GcmRegistrationAsyncTask  extends AsyncTask<Void, Void, String> {
    private static Registration regService = null;
    private GoogleCloudMessaging gcm;
    private Context context;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = "87525420606";

    public GcmRegistrationAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (regService == null) {
            Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null);
            regService = builder.build();
        }

//        http://localhost:8000/api/registergcm/1/
        String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(context);
            }
            String regId = gcm.register(SENDER_ID);
            RequestWs requestWs = new RequestWs();
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("idDevice", regId));
            requestWs.putJsonObject(Constants.PUT_REGISTER_GCM + MyPreference.getUserId() + "/?", pairs, context);
        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
//        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
