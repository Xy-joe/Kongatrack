package com.lightedcode.kongalite.views;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lightedcode.kongalite.R;

public class Forgotpassword extends AppCompatActivity implements View.OnClickListener {
EditText mail;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        button = (Button)findViewById(R.id.fp_btn);
        mail = (EditText)findViewById(R.id.mail);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == button){
            Toast.makeText(Forgotpassword.this, "Sorry no action assigned yet", Toast.LENGTH_LONG).show();

        }
    }
}
