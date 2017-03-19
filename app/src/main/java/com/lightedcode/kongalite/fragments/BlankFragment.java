package com.lightedcode.kongalite.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lightedcode.kongalite.R;
import com.lightedcode.kongalite.adapters.Home_Adapter;
import com.lightedcode.kongalite.models.Content;
import com.lightedcode.kongalite.views.Categories;
import com.lightedcode.kongalite.views.TechCategories;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View myView;
    ViewPager intrview;
    Home_Adapter adapter;
    LayoutInflater inflater;
    Content data;
    LinearLayout hotp, newp;
    TextView hottext, newtext;
    ArrayList<Content> contents = new ArrayList<>();
    RecyclerView.LayoutManager lm;
    int [] imgresource = {R.raw.lap1,R.raw.refri1,R.raw.suit3,R.raw.shoe1,R.raw.undi1,R.raw.menundi,
            R.raw.tv1,R.raw.underwear};
    int position = 0;
    ViewGroup contain;
    Intent intent;
    public SecondAdapter secondAdapter;
    RecyclerView rv;

    private OnFragmentInteractionListener mListener;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView  = inflater.inflate(R.layout.fragment_home, container, false);
        layoutfunctions();
        exhibit();
        hottext.setOnClickListener(this);
        newtext.setOnClickListener(this);

        return myView;
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
    private void exhibit(){
        //View Pager Adapter setup
        intrview = (ViewPager)myView.findViewById(R.id.viewpage);
        adapter = new Home_Adapter(getContext());
        intrview.setAdapter(adapter);
        //Recyclerview Adapter setup
        rv = (RecyclerView)myView.findViewById(R.id.new_p_recycler);
        lm = new GridLayoutManager(getContext(),2);
        rv.setLayoutManager(lm);
        String[] titles = getResources().getStringArray(R.array.exhibit2);
        for (String Titles : titles){
            data = new Content(imgresource[position], Titles);
            contents.add(data);
            position++;
        }
        secondAdapter = new SecondAdapter(getContext(),contents);
        rv.setHasFixedSize(false);
        rv.addItemDecoration(new RecyclereviewGridSpacing(2,dpToPx(3),true));
        rv.setAdapter(secondAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view == hottext){
            newp.setVisibility(View.GONE);
            hotp.setVisibility(View.VISIBLE);
            newtext.setTextColor(Color.DKGRAY);
            hottext.setTextColor(Color.WHITE);
        }
        if (view == newtext){
            newp.setVisibility(View.VISIBLE);
            hotp.setVisibility(View.GONE);
            newtext.setTextColor(Color.WHITE);
            hottext.setTextColor(Color.DKGRAY);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.MyviewHolder>{
        Context ctx;
        ArrayList<Content> list = new ArrayList<>();
        public SecondAdapter(Context ctx, ArrayList<Content> list) {
            this.ctx = ctx;
            this.list = list;
        }

        public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            ImageView image;
            TextView title;
            CardView card;
            public MyviewHolder(View itemView) {
                super(itemView);
                image = (ImageView)itemView.findViewById(R.id.dp);
                title = (TextView)itemView.findViewById(R.id.designer2);
                card = (CardView)itemView.findViewById(R.id.card);
            }

            @Override
            public void onClick(View view) {
                final Intent intent;
                switch (getAdapterPosition()){
                    case 0:
                        intent = new Intent(getContext(), TechCategories.class);
                        break;
                    default:
                        intent = new Intent(getContext(), Categories.class);

                }
                ctx.startActivity(intent);
            }
        }

        @Override
        public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.home_intro_content2,parent,false);
          MyviewHolder mvh = new SecondAdapter.MyviewHolder(view);
            return mvh;
        }

        @Override
        public void onBindViewHolder(MyviewHolder holder, int position) {
            data = list.get(position);
            holder.title.setText(data.getDescription());
            holder.image.setImageResource(data.getImg());
            Picasso.with(ctx).load(data.getImg()).into(holder.image);


        }

        @Override
        public int getItemCount() {
            return list.size();
        }
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
}
