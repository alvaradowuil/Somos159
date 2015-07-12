package com.artisystems.somos159;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.artisystems.somos159.gcm.GcmRegistrationAsyncTask;
import com.artisystems.somos159.utility.Constants;
import com.artisystems.somos159.utility.MyPreference;
import com.artisystems.somos159.utility.RequestWs;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class RegisterActivity extends ActionBarActivity implements View.OnClickListener{

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private Spinner sexSpinner;
    private EditText phoneEditText;
    private EditText addressEditText;
    private Spinner typeDocumentSpinner;
    private EditText documentEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private String[] sexArray = {"Hombre", "Mujer"};
    private String[] typeDocumentArray = {"DPI", "Pasaporte"};
    private Button registerButton;
    private DatePicker datePicker;

    private JSONObject resultJsonObject = null;

    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Constants.PRINCIPAL_COLOR)));

        firstNameEditText = (EditText)findViewById(R.id.register_firstname);
        lastNameEditText = (EditText)findViewById(R.id.register_lastname);
        sexSpinner = (Spinner)findViewById(R.id.register_sex);
        phoneEditText = (EditText)findViewById(R.id.register_phone);
        addressEditText = (EditText)findViewById(R.id.register_address);
        typeDocumentSpinner = (Spinner)findViewById(R.id.register_type_document);
        documentEditText = (EditText)findViewById(R.id.register_document);
        emailEditText = (EditText)findViewById(R.id.register_email);
        passwordEditText = (EditText)findViewById(R.id.register_password);
        registerButton = (Button)findViewById(R.id.register_button);
        registerButton.setOnClickListener(this);
        datePicker = (DatePicker)findViewById(R.id.register_datePicker);

        sexSpinner.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, R.layout.item_spinner, R.id.item_spinner_textview, sexArray));
        typeDocumentSpinner.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, R.layout.item_spinner, R.id.item_spinner_textview, typeDocumentArray));

    }

    @Override
    public void onClick(View view) {
        if (view == registerButton){
            if (validateFields()){
                    sendData();
            } else {
                Toast.makeText(this, "Debes ingresar tu correo y tu clave para continuar", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void sendData() {

        new AsyncTask<String, Integer, Boolean>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setMessage("Espera un momento");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected Boolean doInBackground(String... params) {
                try{
                    RequestWs requestWs = new RequestWs();

                    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("first_name", firstNameEditText.getText().toString()));
                    pairs.add(new BasicNameValuePair("last_name", lastNameEditText.getText().toString()));
                    pairs.add(new BasicNameValuePair("phone", phoneEditText.getText().toString()));
                    pairs.add(new BasicNameValuePair("direction", addressEditText.getText().toString()));
                    pairs.add(new BasicNameValuePair("birth_date", getDateString()));
                    pairs.add(new BasicNameValuePair("email", emailEditText.getText().toString()));
                    pairs.add(new BasicNameValuePair("sex", String.valueOf(sexSpinner.getSelectedItemPosition() + 1)));
                    pairs.add(new BasicNameValuePair("town", "1"));
                    pairs.add(new BasicNameValuePair("document_type", String.valueOf(typeDocumentSpinner.getSelectedItemPosition() + 1)));
                    pairs.add(new BasicNameValuePair("id_number", documentEditText.getText().toString()));
                    pairs.add(new BasicNameValuePair("password", passwordEditText.getText().toString()));
                    resultJsonObject = requestWs.postJsonObject(Constants.POST_REGISTER_USER, pairs, RegisterActivity.this);
                    return true;
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                progressDialog.dismiss();
                if (result){
                    Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                    try{
                        MyPreference.setValueString(RegisterActivity.this, MyPreference.USER_ID, resultJsonObject.getString("id"));
                        new GcmRegistrationAsyncTask(RegisterActivity.this).execute();
                        finish();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }.execute();

    }

    private String getDateString() {
        int   day  = datePicker.getDayOfMonth();
        int   month= datePicker.getMonth() + 1;
        int   year = datePicker.getYear();
        Log.e("ARTI", "anio = " + year);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String formatedDate = sdf.format(new Date(year, month, day));
//        Log.e("ARTI", "fecha = " + formatedDate);
        return "" + year + "-" + month + "-" + day ;
    }

    public boolean validateFields(){
        if (emailEditText.getText().toString().equalsIgnoreCase("") || passwordEditText.getText().toString().equalsIgnoreCase("")){
            return false;
        }

        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_register, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
