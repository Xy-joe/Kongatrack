package com.lightedcode.kongalite.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lightedcode.kongalite.R;
import com.lightedcode.kongalite.views.About;
import com.lightedcode.kongalite.views.User;

/**
 * Created by joebuntu on 3/12/17.
 */

public class Settings extends Fragment implements View.OnClickListener{
    View myView;
    TextView about, history, profile;
    Intent intent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView  = inflater.inflate(R.layout.fragment_setings, container, false);
         initialiseviews();
        return myView;
    }

    @Override
    public void onClick(View view) {
        if (view == about) {
            intent = new Intent(getContext(), About.class);
            startActivity(intent);
        }
            if (view == profile){
                intent = new Intent(getContext(), User.class);
                startActivity(intent);
        }
        if (view == history){
            Toast msg = Toast.makeText(getContext(),"No History Record yet", Toast.LENGTH_LONG);
            msg.show();
        }

    }
    private void initialiseviews(){
        about = (TextView)myView.findViewById(R.id.about_us);
       history = (TextView)myView.findViewById(R.id.history);
        profile = (TextView)myView.findViewById(R.id.user);
        about.setOnClickListener(this);
        profile.setOnClickListener(this);
        history.setOnClickListener(this);
    }
    FragmentManager.OnBackStackChangedListener back = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {

        }
    };

}
