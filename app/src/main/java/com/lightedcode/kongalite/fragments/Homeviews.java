package com.lightedcode.kongalite.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.TestMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lightedcode.kongalite.ClickListner;
import com.lightedcode.kongalite.R;
import com.lightedcode.kongalite.adapters.Home_Adapter;
import com.lightedcode.kongalite.adapters.SecondAdapter;
import com.lightedcode.kongalite.models.Content;
import com.lightedcode.kongalite.views.Categories;
import com.lightedcode.kongalite.views.FashionCategories;
import com.lightedcode.kongalite.views.TechCategories;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by joebuntu on 3/12/17.
 */

public class Homeviews extends Fragment implements SecondAdapter.ClickListner {
    View myView;
    ViewPager intrview;
    Home_Adapter adapter;
    LayoutInflater inflater;
    Content data, data1;
    LinearLayout hotp, newp;
    TextView hottext, newtext;
    ArrayList<Content> contents = new ArrayList<>();
    ArrayList<Content> contents2 = new ArrayList<>();
    RecyclerView.LayoutManager lm;
    int [] imgresource = {R.raw.lap1,R.raw.refri1,R.raw.suit3,R.raw.shoe1,R.raw.undi1,R.raw.menundi,
    R.raw.tv1,R.raw.suit6};
    int [] exhibit = {R.raw.wed1,R.raw.wed3,R.raw.shoe,R.raw.undi4,R.raw.speaker,R.raw.desk,
            R.raw.vision,R.raw.royaloak};
    int position=0;
    int positi = 0;
   ViewGroup contain;
    public SecondAdapter secondAdapter, second;
    RecyclerView rv,rv2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if (getArguments() != null){
           inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           myView = inflater.inflate(R.layout.fragment_home, contain,false);

       }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView  = inflater.inflate(R.layout.fragment_home, container, false);
        layoutfunctions();
        exhibit1();
        exhibit();
        hottext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newp.setVisibility(View.GONE);
                hotp.setVisibility(View.VISIBLE);
                newtext.setTextColor(Color.DKGRAY);
                hottext.setTextColor(Color.WHITE);

            }
        });
        newtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newp.setVisibility(View.VISIBLE);
                hotp.setVisibility(View.GONE);
                newtext.setTextColor(Color.WHITE);
                hottext.setTextColor(Color.DKGRAY);

            }
        });

        return myView;
    }

    private void exhibit1() {
        //View Pager Adapter setup
        intrview = (ViewPager) myView.findViewById(R.id.viewpage);
        adapter = new Home_Adapter(getContext());
        intrview.setAdapter(adapter);
        //Recyclerview Adapter setup
        rv = (RecyclerView) myView.findViewById(R.id.new_p_recycler);
        lm = new GridLayoutManager(getContext(), 2);
        rv.setLayoutManager(lm);
        String[] titles = getResources().getStringArray(R.array.exhibit1);
        for (String Titles : titles) {
            data = new Content(imgresource[position], Titles);
            contents.add(data);
            position++;
        }
        secondAdapter = new SecondAdapter(getContext(), contents);
        rv.setHasFixedSize(false);
        rv.addItemDecoration(new RecyclereviewGridSpacing(2, dpToPx(1), true));
        rv.setAdapter(secondAdapter);
          secondAdapter.setClickListner(this);
    }
    private void exhibit(){
        rv2 = (RecyclerView)myView.findViewById(R.id.hotp_recycler);
        lm = new GridLayoutManager(getContext(),2);
        String[] hottitles = getResources().getStringArray(R.array.exhibit2);
        for (String Tile : hottitles){
            data1 = new Content(exhibit[positi], Tile);
            contents2.add(data1);
            positi++;
        }


        second = new SecondAdapter(getContext(), contents2);
        second.setClickListner(this);
        rv2.setLayoutManager(lm);
        rv2.setHasFixedSize(false);
        rv2.addItemDecoration(new RecyclereviewGridSpacing(2,dpToPx(1),true));
        rv2.setAdapter(second);

    }
    private void layoutfunctions(){
        newp = (LinearLayout)myView.findViewById(R.id.newp_layout);
        hotp = (LinearLayout)myView.findViewById(R.id.hotp_layout);
        hottext = (TextView)myView.findViewById(R.id.hot_p);
        newtext = (TextView)myView.findViewById(R.id.new_p);
        newp.setVisibility(View.VISIBLE);
        hotp.setVisibility(View.GONE);
        newtext.setTextColor(Color.WHITE);
        hottext.setTextColor(Color.DKGRAY);
    }


    @Override
    public void Itemclicked(View view, int position) {

    }

    public class RecyclereviewGridSpacing extends RecyclerView.ItemDecoration{
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public RecyclereviewGridSpacing(int spanCount, int spacing, boolean includeEdge){
            this.spacing = spacing;
            this.spanCount = spanCount;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


    // Converting dp to pixel

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        layoutfunctions();
        exhibit();

    }




}
