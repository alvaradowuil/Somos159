package com.artisystems.somos159;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artisystems.somos159.utility.Constants;

import org.json.JSONArray;
import org.json.JSONObject;


public class DetailCommunityActivity extends ActionBarActivity {

    private TextView nameTextView;
    private TextView descriptionTextView;
    private TextView quantityTextView;
    private TextView sectorTextView;
    private JSONObject community;
    private LinearLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_community);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Constants.PRINCIPAL_COLOR)));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        nameTextView = (TextView)findViewById(R.id.community_name);
        descriptionTextView = (TextView)findViewById(R.id.community_desciption);
        quantityTextView = (TextView)findViewById(R.id.community_quantity);
        sectorTextView = (TextView)findViewById(R.id.community_sector);
        contentLayout = (LinearLayout)findViewById(R.id.community_content_layout);

        Bundle bundle = getIntent().getExtras();
        try{
            community = new JSONObject(bundle.getString("community"));

            nameTextView.setText(community.getString("name"));
            descriptionTextView.setText(community.getString("purpose"));
            quantityTextView.setText(community.getString("quantity"));
            sectorTextView.setText(community.getJSONObject("sector").getString("name"));
            JSONArray objectivesArray = community.getJSONArray("objectives");
            Log.e("ARTI", objectivesArray.toString());
            for (int i = 0; i < objectivesArray.length(); i++){
                JSONObject objective = objectivesArray.getJSONObject(i);
                TextView objectiveTextView = new TextView(this);
                objectiveTextView.setText(objective.getString("description"));
                contentLayout.addView(objectiveTextView, params);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_community, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
