package com.lightedcode.kongalite.views;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lightedcode.kongalite.R;
import com.lightedcode.kongalite.adapters.CartsAdapter;
import com.lightedcode.kongalite.adapters.CategoryAdapter;
import com.lightedcode.kongalite.adapters.PromotionsAdapter;
import com.lightedcode.kongalite.controllers.Connectivity;
import com.lightedcode.kongalite.models.Promo;

import java.util.ArrayList;

/**
 * Created by joebuntu on 3/12/17.
 */

public class Carts  extends AppCompatActivity implements View.OnClickListener {
    Promo promo;
    CartsAdapter adapter;
    RecyclerView rv;
    DatabaseReference dref, orderref;
    ProgressDialog pd;
    RecyclerView.LayoutManager lg;
    Connectivity connectivity;
    ArrayList<Promo> data = new ArrayList<>();
    Toolbar toolbar;
    FirebaseAuth auth;
    EditText qty;
    Button update, order, cancel;
    FirebaseAuth.AuthStateListener authStateListener;
    TextView code, total;
    View itemview;
    Intent intent;
    CartsAdapter.ViewHolderClass viewHolderClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_charts);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cart");
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();
        contentInitialiser();
        checkAuthstate();
        showDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.carts_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void contentInitialiser() {
        qty = (EditText) findViewById(R.id.quantity);
        update = (Button) findViewById(R.id.update);
        code = (TextView) findViewById(R.id.code);
        order = (Button) findViewById(R.id.order_button);
        cancel = (Button) findViewById(R.id.cancel);
        total = (TextView) findViewById(R.id.totalp);
        //update.setOnClickListener(this);
        order.setOnClickListener(this);
        //cancel.setOnClickListener(this);

    }

    private void checkAuthstate() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    showmesg();
                } else {
                    getFirebasedata();
                }
            }
        };
    }

    private void getFirebasedata() {
        connectivity = new Connectivity(this);
        rv = (RecyclerView) findViewById(R.id.cart_recycler);
        lg = new LinearLayoutManager(this);
        String id = auth.getCurrentUser().getUid();
        dref = FirebaseDatabase.getInstance().getReference("Carts").child(id).getRef();
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

                        rv.setLayoutManager(lg);
                        rv.setItemAnimator(new DefaultItemAnimator());
                        adapter = new CartsAdapter(Carts.this, data);
                        rv.setLayoutManager(lg);
                        rv.setAdapter(adapter);

                    } else {
                        Toast.makeText(Carts.this, "No data", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Error reading from database", Toast.LENGTH_LONG).show();
                    adapter.notifyDataSetChanged();

                }
            });
        } else {
            pd.dismiss();
            Toast.makeText(getApplicationContext(), "No Internet Connections, could not load content", Toast.LENGTH_LONG).show();
        }

    }

    private void showmesg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Carts.this);
        builder.setMessage("You don't have any goods in your cart. \n Add Goods now ? ");
        builder.setTitle("Empty Cart");
        builder.setIcon(R.drawable.ic_exit);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                intent = new Intent(Carts.this, Categories.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                intent = new Intent(Carts.this, NewNav.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showDialog() {
        pd = new ProgressDialog(this);
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
    }


    @Override
    public void onClick(View view) {
        if (view == order) {
            placeOrder();
        }
        if (view == cancel) {

        }
        if (view == update) {

        }
    }

    private void placeOrder() {
        if (adapter.getItemCount() > 0) {
            String key = auth.getCurrentUser().getUid();
            orderref = FirebaseDatabase.getInstance().getReference("Orders").child(key);
            final FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                try {
                    int position = 0;
                    Promo list = data.get(position);
                    String name = list.getName();
                    DatabaseReference user1 = orderref.child("Product");
                    user1.setValue(name);

                    String providerid = user.getProviderId();
                    DatabaseReference databaseReference = orderref.child("Product id");
                    databaseReference.setValue(providerid);

                    int un = list.getPrice();
                    DatabaseReference databaseReference1 = dref.child("Price");
                    databaseReference1.setValue(un);
                }catch (Exception ex){
                    ex.printStackTrace();
                }


            }
            AlertDialog.Builder builder = new AlertDialog.Builder(Carts.this);
            builder.setMessage("Track and Monitor your Goods");
            builder.setTitle("Track Goods");
            builder.setIcon(R.drawable.ic_track);
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Uri uri = Uri.parse("https://kongatrack.herokuapp.com");
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("https://kongatrack.herokuapp.com"));
                    startActivity(intent);
                    finish();
                }

            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    intent = new Intent(Carts.this, NewNav.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        }
        else {
                Toast.makeText(Carts.this, "No goods to order", Toast.LENGTH_SHORT).show();
            }


        }
    private void calculator(){
       /** viewHolderClass = new CartsAdapter.ViewHolderClass(itemview);
        name = (TextView)itemView.findViewById(R.id.p_name);
        details = (TextView)itemView.findViewById(R.id.details);
        price = (TextView)itemView.findViewById(R.id.price);
        discount = (TextView)itemView.findViewById(R.id.discount);)**/
    }
    }
