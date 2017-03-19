package com.lightedcode.kongalite.controllers;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by joebuntu on 3/13/17.
 */

public class Connectivity {
    Context ctx;

    public Connectivity(Context ctx) {
        this.ctx = ctx;
    }


    public  boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager)
                ctx.getSystemService(Service.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork= cm.getActiveNetworkInfo();
            if (activeNetwork != null) {
                if (activeNetwork.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
