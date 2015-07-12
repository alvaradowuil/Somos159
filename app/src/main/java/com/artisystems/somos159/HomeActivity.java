package com.artisystems.somos159;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.artisystems.somos159.utility.AdapterAcitivies;
import com.artisystems.somos159.utility.Constants;
import com.artisystems.somos159.utility.RequestWs;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class HomeActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    ListView listView;
    JSONArray activitiesArray = null;
    ProgressDialog progressDialog;
    int[] idsItem = {R.id.item_activities_day, R.id.item_activities_month, R.id.item_activities_name, R.id.item_activities_description};
    String[] idsMap = {"day", "month", "name", "description"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Constants.PRINCIPAL_COLOR)));

        listView = (ListView)findViewById(R.id.listview_activities);
        listView.setOnItemClickListener(this);
        fillActivities();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void fillActivities() {
        if (Constants.activitiesArray != null){

            List<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();

            for(int i = 0; i < Constants.activitiesArray.length(); i++){
                try{
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("day", getPersonDate(Constants.activitiesArray.getJSONObject(i).getString("begin_date").toString()));
                    map.put("month", getMothShortDate(Constants.activitiesArray.getJSONObject(i).getString("begin_date").toString()));
                    map.put("name", Constants.activitiesArray.getJSONObject(i).getString("name").toString());
                    map.put("description", Constants.activitiesArray.getJSONObject(i).getString("description").toString());
                    map.put("id", String.valueOf(Constants.activitiesArray.getJSONObject(i).getInt("id")));
                    dataList.add(map);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            listView.setAdapter(new SimpleAdapter(HomeActivity.this, dataList, R.layout.item_activities, idsMap, idsItem));
        }
    }

    private String getPersonDate(String begin_date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = dateFormat.parse(begin_date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Log.e("ARTI", "date = " + date.toString());
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    private String getMothShortDate(String begin_date) throws ParseException {
        String result = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = dateFormat.parse(begin_date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        switch(calendar.get(Calendar.MONTH)){
            case 0:
                result = "Ene";
                break;
            case 1:
                result = "Feb";
                break;
            case 2:
                result = "Mar";
                break;
            case 3:
                result = "Abr";
                break;
            case 4:
                result = "May";
                break;
            case 5:
                result = "Jun";
                break;
            case 6:
                result = "Jul";
                break;
            case 7:
                result = "Ago";
                break;
            case 8:
                result = "Sep";
                break;
            case 9:
                result = "Oct";
                break;
            case 10:
                result = "Nov";
                break;
            case 11:
                result = "Dic";
                break;
        }

        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            return true;
        } else if (id == R.id.action_communities){
            startActivity(new Intent(HomeActivity.this, CommunitiesActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constants.isExit = true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String, String> map = new HashMap<String, String>();
        map = (HashMap<String, String>)parent.getItemAtPosition(position);
        Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
        intent.putExtra("id", map.get("id"));
        startActivity(intent);
    }

    public class PersonDate{
        private String dateString = "";
        public PersonDate(String dateString) {
            this.dateString = dateString;
        }

        public String dayOfMonth() throws ParseException {
            String result = "";
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date date = dateFormat.parse(this.dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            result = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            return result;
        }
    }
}
