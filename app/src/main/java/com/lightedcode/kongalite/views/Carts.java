package com.lightedcode.kongalite.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.lightedcode.kongalite.R;

/**
 * Created by joebuntu on 3/12/17.
 */

public class Carts  extends AppCompatActivity {
    View myView;
     Toolbar toolbar;
    DatabaseReference dref;
    FirebaseAuth auth;
    TextView total, price, description, discount, name;
    ImageView dp;
    Button order, calculate;
    FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_charts);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cart");
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

    }

    private void  checkauthstate(){

        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){

                }
            }
        });

    }
    public void layoutsetup(){

    }
}
