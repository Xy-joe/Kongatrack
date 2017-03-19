package com.lightedcode.kongalite.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.lightedcode.kongalite.R;
import com.lightedcode.kongalite.controllers.Connectivity;

public class MainActivity extends Activity {
    Connectivity connect;
    Thread load;
    boolean doubleBackToExitPressedOnce  = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkConnections();
    }
    private void checkConnections() {
        connect = new Connectivity(this);

            load = new Thread() {
                public void run() {
                    try {
                        sleep(2500);
                    } catch (InterruptedException es) {
                        es.printStackTrace();

                    }
                    finally {
                        if (connect.isConnected()) {
                            Intent intent = new Intent(MainActivity.this, NewNav.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(),"Please enable internet connection to gain full access",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, NewNav.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                }
            }

        };
        load.start();
    }

    @Override
    public void onBackPressed() {
        //if (auth.getCurrentUser() != null){


        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
