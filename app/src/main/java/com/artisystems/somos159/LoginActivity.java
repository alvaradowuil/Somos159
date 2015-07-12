package com.artisystems.somos159;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.artisystems.somos159.gcm.GcmRegistrationAsyncTask;
import com.artisystems.somos159.utility.Constants;
import com.artisystems.somos159.utility.MyPreference;
import com.artisystems.somos159.utility.RequestWs;

import org.json.JSONObject;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener{

    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView enterTextView;
    private TextView registerTextView;
    private JSONObject resultJsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Constants.PRINCIPAL_COLOR)));

        usernameEditText = (EditText)findViewById(R.id.login_username);
        passwordEditText = (EditText)findViewById(R.id.login_password);
        enterTextView = (TextView)findViewById(R.id.login_button);
        registerTextView = (TextView)findViewById(R.id.login_register_button);

        enterTextView.setOnClickListener(this);
        registerTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == enterTextView){
            sendData();
        } else if (view == registerTextView){
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
    }

    private void sendData() {
        new AsyncTask<String, Integer, Boolean>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(String... params) {
                try{
                    RequestWs requestWs = new RequestWs();
                    String url = Constants.GET_LOGIN + usernameEditText.getText().toString().trim() + "&password=" + passwordEditText.getText().toString();
                    resultJsonObject = requestWs.getJsonObject(url, LoginActivity.this);
                    return true;
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (result){
                    try{
                        MyPreference.setValueString(LoginActivity.this, MyPreference.USER_ID, resultJsonObject.getString("id"));
                        new GcmRegistrationAsyncTask(LoginActivity.this).execute();
                        finish();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }.execute();
    }
}
