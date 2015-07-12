package com.artisystems.somos159.utility;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.artisystems.somos159.R;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by wuilder on 11/07/15.
 */
public class AdapterAcitivies extends BaseAdapter {
    private JSONArray data;
    private Context context;

    public AdapterAcitivies(JSONArray data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.length();
    }

    @Override
    public JSONObject getItem(int position) {
        try{
            return data.getJSONObject(position);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = View.inflate(context, R.layout.item_activities, null);
        } else {

        }

        return convertView;
    }

    static class ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;
        TextView addressTextView;
        Button miniusButton;
        Button plusButton;
    }
}
