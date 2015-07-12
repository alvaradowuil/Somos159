package com.artisystems.somos159;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.artisystems.somos159.utility.Constants;


public class DetailActivity extends ActionBarActivity {
    String idActivity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(Constants.PRINCIPAL_COLOR)));

        Bundle bundle = getIntent().getExtras();
        idActivity = bundle.getString("id");
        Toast.makeText(DetailActivity.this, idActivity, Toast.LENGTH_LONG).show();
    }

}
