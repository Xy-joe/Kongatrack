package com.lightedcode.kongalite.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lightedcode.kongalite.R;
import com.lightedcode.kongalite.adapters.PromotionsAdapter;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProductDetails extends AppCompatActivity implements PromotionsAdapter.ClickListner {
Toolbar toolbar;
    LinearLayout info,comment;
    TextView comment_text, info_text, name,price,discount,details, det_head, cotext,user,datetext;
    Button buy, comment_btn;
    Intent intent;
    Date date;
    DatabaseReference dref;
    FirebaseAuth auth;
    DateFormat df;
    Bundle bundle;
    ImageView dp;
    StorageReference sref;
    String doe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Product Details");
        actionBar.setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getExtras();
        layoutsetup();
        productsetup();
        auth = FirebaseAuth.getInstance();


    }
    private void layoutsetup(){
        buy = (Button)findViewById(R.id.buy_button);
        info = (LinearLayout)findViewById(R.id.info_layout);
        comment = (LinearLayout)findViewById(R.id.comment_layout);
        info_text = (TextView)findViewById(R.id.info_text);
        comment_text = (TextView)findViewById(R.id.comm_text);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auth.getCurrentUser() == null) {
                   showmesg();
                }else {
                    if (addproductocart()){
                        Toast.makeText(getApplicationContext(), "Transactions Successful", Toast.LENGTH_LONG).show();
                              trackgoods();
                    }else {
                        Toast.makeText(getApplicationContext(), "Error completing transactions", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
        info.setVisibility(View.VISIBLE);
        comment.setVisibility(View.GONE);
        comment_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info.setVisibility(View.GONE);
                comment.setVisibility(View.VISIBLE);
                comment_text.setTextColor(Color.WHITE);
                info_text.setTextColor(Color.BLACK);
            }
        });
        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info.setVisibility(View.VISIBLE);
                comment.setVisibility(View.GONE);
                comment_text.setTextColor(Color.BLACK);
                info_text.setTextColor(Color.WHITE);
            }
        });

    }


    private void productsetup(){
       df = DateFormat.getDateInstance();
       date = new Date();
        name = (TextView)findViewById(R.id.p_name);
        details = (TextView)findViewById(R.id.details);
        discount = (TextView)findViewById(R.id.discount);
        price = (TextView)findViewById(R.id.price);
        dp = (ImageView)findViewById(R.id.dp);
        det_head = (TextView)findViewById(R.id.details_head);
        cotext = (TextView)findViewById(R.id.comment);
        user = (TextView)findViewById(R.id.comment_user);
        datetext = (TextView)findViewById(R.id.date);
       String url = bundle.getString("image_id");
        Picasso.with(getApplicationContext()).load(url).into(dp);
        name.setText(getIntent().getStringExtra("name_id"));
        price.setText(getIntent().getStringExtra("price_id"));
        discount.setText(getIntent().getStringExtra("discount_id"));
        det_head.setText(getIntent().getStringExtra("name_id"));
        details.setText(getIntent().getStringExtra("details_id"));
        cotext.setText("hey this is a nice product");
        user.setText("Robot");
        String dat = df.format(date);
        datetext.setText(dat);

    }

    @Override
    public void Itemclicked(View view, int position) {

    }
    private boolean addproductocart(){
        if (auth.getCurrentUser() != null) {
            String id = auth.getCurrentUser().getUid();
            DateFormat df = DateFormat.getDateInstance();
            Date date = new Date();
            String data1 = df.format(date);
            dref = FirebaseDatabase.getInstance().getReference("Carts").child(id).child(data1);
            if (loadimage()) {

                String product = name.getText().toString();
                DatabaseReference databaseReference = dref.child("Product name");
                databaseReference.setValue(product);
// price
                String amount = price.getText().toString();
                DatabaseReference databaseReference1 = dref.push().child("Price");
                databaseReference1.setValue(amount);
                // discount
                String dis = discount.getText().toString();
                DatabaseReference databaseReference2 = dref.push().child("discount");
                databaseReference2.setValue(product);
                //Description
                String details = name.getText().toString();
                DatabaseReference databaseReference3 = dref.push().child("desription");
                databaseReference3.setValue(details);

                DatabaseReference databaseReference4 = dref.push().child("image");
                databaseReference4.setValue(doe);
  
            }
        }

  return true;
    }//.getReferenceFromUrl("gs://Kongalite-544f1.appspot.com");

    private boolean loadimage(){
        sref = FirebaseStorage.getInstance().getReference();
         df = DateFormat.getDateInstance();
        Date dateobj = new Date();
        final String ImagePath= df.format(dateobj) +".jpg";
        StorageReference storageReference = sref.child("user/"+ ImagePath);
        dp.setDrawingCacheEnabled(true);

        dp.buildDrawingCache();
        // Bitmap bitmap = imageView.getDrawingCache();
        BitmapDrawable drawable=(BitmapDrawable)dp.getDrawable();
        Bitmap bitmap =drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageReference.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadurl = taskSnapshot.getDownloadUrl();
                doe = downloadurl.toString();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
                return true;
}
    private void trackgoods(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetails.this);
        builder.setMessage("Monitor and track your goods");
        builder.setTitle("Kongatrack");
        builder.setIcon(R.drawable.ic_track);
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
     String trackurl;
                //NewNav.this.finishAffinity();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                intent = new Intent(ProductDetails.this, Carts.class);
                startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void showmesg(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetails.this);
        builder.setMessage("You don't have an account please sign in  to make a transaction");
        builder.setTitle("Login ?");
        builder.setIcon(R.drawable.ic_exit);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                intent = new Intent(ProductDetails.this, Login.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
/**      if (task.isSuccessful()){
 try {
 String downloadurl =  task.getResult().getDownloadUrl().toString();
 downloadurl= java.net.URLEncoder.encode(downloadurl , "UTF-8");
 DatabaseReference databaseReference = dref.child("image");
 databaseReference.setValue(downloadurl);
 } catch (Exception e) {
 e.printStackTrace();
 }
 }**/