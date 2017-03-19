package com.lightedcode.kongalite.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lightedcode.kongalite.R;
import com.lightedcode.kongalite.adapters.CategoryAdapter;
import com.lightedcode.kongalite.adapters.PromotionsAdapter;
import com.lightedcode.kongalite.adapters.SecondAdapter;
import com.lightedcode.kongalite.controllers.Connectivity;
import com.lightedcode.kongalite.fragments.Homeviews;
import com.lightedcode.kongalite.models.ListCategory;
import com.lightedcode.kongalite.models.Promo;

import java.util.ArrayList;

public class HomeandKitchen extends AppCompatActivity {
    private PromotionsAdapter adapterClass;

private Promo cat;
    ArrayList<Promo> data = new ArrayList<>();
    RecyclerView.LayoutManager lg;
    RecyclerView rv;
    DatabaseReference dref;
    ProgressDialog pd;
    Connectivity connectivity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ActionBar actionBar  = getSupportActionBar();
        actionBar.setTitle("Homes and Kitchen");
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        showDialog();
        initialiseAdapter();

    }


    private void initialiseAdapter() {
        rv = (RecyclerView) findViewById(R.id.list_recyclerview);
        lg = new LinearLayoutManager(getApplicationContext());
        connectivity = new Connectivity(this);
        dref = FirebaseDatabase.getInstance().getReference("Categories").child("HomeandKitchen").getRef();
        pd.show();
        if (connectivity.isConnected()) {
            dref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    pd.dismiss();
                    getData(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    String error = databaseError.getDetails();
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

                }
            });

        } else {
            pd.dismiss();
            Toast.makeText(getApplicationContext(), "Not Internet Conection", Toast.LENGTH_LONG).show();
            return;
        }
        rv.setLayoutManager(lg);
        rv.setHasFixedSize(false);
        rv.setAdapter(adapterClass);


    }

    private void showDialog() {
        pd = new ProgressDialog(this);
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.setIcon(R.drawable.ic_loading);
        pd.setCancelable(false);
    }

    private void getData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            cat = ds.getValue(Promo.class);
            data.add(cat);
        }
        if (data.size() > 0) {
            adapterClass = new PromotionsAdapter(getApplicationContext(), data);
            rv.setLayoutManager(lg);
            rv.setAdapter(adapterClass);
        } else {
            Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();

        }
    }
}
