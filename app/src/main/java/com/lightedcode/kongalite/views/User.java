package com.lightedcode.kongalite.views;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lightedcode.kongalite.R;

public class User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("Profile");
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
    }
}
