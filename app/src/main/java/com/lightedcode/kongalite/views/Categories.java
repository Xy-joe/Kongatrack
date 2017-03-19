package com.lightedcode.kongalite.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lightedcode.kongalite.R;
import com.lightedcode.kongalite.adapters.CategoryAdapter;
import com.lightedcode.kongalite.fragments.Homeviews;
import com.lightedcode.kongalite.models.ListCategory;

import java.util.ArrayList;
import java.util.List;

public class Categories extends AppCompatActivity {
    private CategoryAdapter adapterClass;

public ListCategory cat;
    ArrayList<ListCategory> data = new ArrayList<>();
    RecyclerView.LayoutManager lg;
    RecyclerView rv;
    int position = 1;
    View myView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ActionBar actionBar  = getSupportActionBar();
        actionBar.setTitle("Categories");
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        initialiseAdapter();

    }


    private void initialiseAdapter(){
        rv = (RecyclerView)findViewById(R.id.list_recyclerview);
        String[] names = getResources().getStringArray(R.array.categories);
        for (String Names: names ){
            cat = new ListCategory(Names,position);
            data.add(cat);
            position++;
                    }
        adapterClass = new CategoryAdapter(getApplicationContext(),data);
        lg = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(lg);
        rv.setHasFixedSize(false);
        rv.setAdapter(adapterClass);


    }

 public class Cat {
     String names;
     Context ctx;
     int number;
     public String getNames() {
         return names;
     }

     public int getNumber() {
         return number;
     }

     public void setNumber(int number) {
         this.number = number;
     }

     public void setNames(String names) {
         this.names = names;
     }
     public Cat(Context ctx, String names, int number){
         this.names = names;
         this.ctx = ctx;
         this.number = number;

     }

    }
}
