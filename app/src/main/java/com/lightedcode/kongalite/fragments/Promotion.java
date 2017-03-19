package com.lightedcode.kongalite.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lightedcode.kongalite.R;
import com.lightedcode.kongalite.adapters.PromotionsAdapter;
import com.lightedcode.kongalite.controllers.Connectivity;
import com.lightedcode.kongalite.models.Promo;

import java.util.ArrayList;

/**
 * Created by joebuntu on 3/12/17.
 */

public class Promotion extends Fragment implements PromotionsAdapter.ClickListner{
    com.lightedcode.kongalite.models.Promo promo;
    PromotionsAdapter adapter;
    RecyclerView rv;
    View myView;
     DatabaseReference dref;
    ProgressDialog pd;
    RecyclerView.LayoutManager lg;
    Connectivity connectivity;
    ArrayList<com.lightedcode.kongalite.models.Promo> data = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView  = inflater.inflate(R.layout.fragment_promotions, container, false);
        showDialog();
        adaptersetup();
        adapter.setClickListner(this);
        return myView;
    }
    private void adaptersetup(){
        connectivity = new Connectivity(getActivity());
        adapter = new PromotionsAdapter(getContext(), data);
        adapter.setClickListner(this);
        rv = (RecyclerView) myView.findViewById(R.id.list_recyclerview);
        dref = FirebaseDatabase.getInstance().getReference("Promotions").getRef();
        pd.show();
        if (connectivity.isConnected()) {
            pd.dismiss();
            dref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        promo = ds.getValue(com.lightedcode.kongalite.models.Promo.class);
                        data.add(promo);
                    }
                    if (data.size() > 0) {
                        lg = new LinearLayoutManager(getContext());
                        rv.setLayoutManager(lg);
                        rv.setItemAnimator(new DefaultItemAnimator());
                        adapter = new PromotionsAdapter(getContext(), data);
                        rv.setLayoutManager(lg);
                        rv.setAdapter(adapter);
                        pd.dismiss();
                    } else {
                        Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Error reading from database", Toast.LENGTH_LONG).show();
                    adapter.notifyDataSetChanged();

                }
            });
        }else {
            pd.dismiss();
            Toast.makeText(getContext(),"No Internet Connections, could not load content",Toast.LENGTH_LONG).show();
        }

    }
    private void showDialog() {
        pd = new ProgressDialog(getContext());
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
    }

    @Override
    public void Itemclicked(View view, int position) {

    }
}
