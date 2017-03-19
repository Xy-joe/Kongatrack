package com.lightedcode.kongalite.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.lightedcode.kongalite.R;
import com.lightedcode.kongalite.controllers.Connectivity;
import com.lightedcode.kongalite.fragments.Promotion;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity implements View.OnClickListener{
EditText em, pass;
    TextView forgottenpass;
    Button signup, login;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    Intent intent;
    ProgressDialog pd;
    String email, password;
    Connectivity connectivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        showDialog();
        connectivity = new Connectivity(this);
        clicklistners();


    }
    private void login() {
        email = em.getText().toString();
        password = pass.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(Login.this, "Please enter you email",Toast.LENGTH_LONG);
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(Login.this, "Please tyoe in your password",Toast.LENGTH_LONG);
            return;
        }
        if (connectivity.isConnected()) {
            pd.show();
           auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if (task.isSuccessful()){
                       pd.dismiss();
                       Intent intent = new Intent(Login.this, Carts.class);
                       startActivity(intent);
                   }else {
                       pd.dismiss();
                       Toast.makeText(Login.this, "Network error", Toast.LENGTH_LONG).show();
                   }
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   pd.dismiss();
                   Toast.makeText(Login.this, "Incorrect email or password", Toast.LENGTH_LONG).show();

               }
           });
        }else {
            Toast.makeText(Login.this, "Please enable Internet connection",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == forgottenpass){
            intent = new Intent(Login.this, Forgotpassword.class);
            startActivity(intent);
        }
        if(view == signup){
            intent = new Intent(Login.this, Signup.class);
            startActivity(intent);
        }
        if (view == login){
            login();
        }
    }
    public void showDialog() {
        pd = new ProgressDialog(this);
        pd.setTitle("Logging in");
        pd.setMessage("Please wait...");
        pd.setIcon(R.drawable.ic_loading);
        pd.setCancelable(false);
    }
    private void clicklistners(){
        em = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        forgottenpass = (TextView) findViewById(R.id.forgot_password);
        signup = (Button)findViewById(R.id.signup);
        login = (Button)findViewById(R.id.login);
        forgottenpass.setOnClickListener(this);
        signup.setOnClickListener(this);
        login.setOnClickListener(this);
    }

}
