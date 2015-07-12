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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.artisystems.somos159.gcm.GcmRegistrationAsyncTask;
import com.artisystems.somos159.utility.Constants;
import com.artisystems.somos159.utility.MyPreference;
import com.artisystems.somos159.utility.RequestWs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CommunitiesActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private JSONArray resultJsonArray;
    int[] idsItem = {R.id.item_community_name, R.id.item_community_descriiption, R.id.item_community_quantity, R.id.item_community_sector};
    String[] idsMap = {"name", "purpose", "quantity", "sector"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communities);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Constants.PRINCIPAL_COLOR)));

        listView = (ListView)findViewById(R.id.listView);
        getComunities();
    }

    private void getComunities() {
        new AsyncTask<String, Integer, Boolean>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(String... params) {
                try{
                    RequestWs requestWs = new RequestWs();
                    String url = Constants.GET_COMMUNITIES;
                    resultJsonArray = requestWs.getJsonArray(url, CommunitiesActivity.this);
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
                    fillListCommunities();
                }
            }
        }.execute();
    }

    private void fillListCommunities() {
        List<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();

        for(int i = 0; i < Constants.activitiesArray.length(); i++){
            try{
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", resultJsonArray.getJSONObject(i).getString("name"));
                map.put("purpose", resultJsonArray.getJSONObject(i).getString("purpose"));
                map.put("logo", resultJsonArray.getJSONObject(i).getString("logo"));
                map.put("quantity", resultJsonArray.getJSONObject(i).getString("quantity"));
                map.put("sector", resultJsonArray.getJSONObject(i).getJSONObject("sector").getString("name"));
                dataList.add(map);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        listView.setAdapter(new SimpleAdapter(CommunitiesActivity.this, dataList, R.layout.item_communiti, idsMap, idsItem));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try{
            JSONObject select = resultJsonArray.getJSONObject(position);
            Intent intent = new Intent(CommunitiesActivity.this, DetailCommunityActivity.class);
            intent.putExtra("community", select.toString());
            startActivity(intent);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
