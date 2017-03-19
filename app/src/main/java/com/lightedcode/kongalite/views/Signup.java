package com.lightedcode.kongalite.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lightedcode.kongalite.R;
import com.lightedcode.kongalite.controllers.Connectivity;

public class Signup extends AppCompatActivity implements View.OnClickListener{
EditText fname, ln, uname, pass, confpass, address, gma, phon,state, country;
    Button login, signup;
    DatabaseReference dref;
    Connectivity connectivity;
    FirebaseAuth auth;
    Intent intent;
    String username,password, firstname, lastname, addre, sta, count, email, phone,confirm;
    FirebaseAuth.AuthStateListener authStateListener;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        signup = (Button)findViewById(R.id.signup_btn);
        login = (Button) findViewById(R.id.login_btn);
         connectivity = new Connectivity(this);
        auth = FirebaseAuth.getInstance();

        signup.setOnClickListener(this);
        login.setOnClickListener(this);
        showDialog();
    }
    private void setSignup(){

    }

    @Override
    public void onClick(View view) {
        if (view == signup){
             userInfo();
        }
        if (view == login){
            intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }

    }
    private boolean profilesetup() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null){
            String userid = user.getUid();
            dref = FirebaseDatabase.getInstance().getReference("Users").child(userid);

            String id = user.getUid();
            DatabaseReference user1 = dref.child("user id");
            user1.setValue(id);

            String providerid = user.getProviderId();
            DatabaseReference databaseReference = dref.child("provider id");
            databaseReference.setValue(providerid);

            String un = "username";
            DatabaseReference databaseReference1 = dref.child(un);
            databaseReference1.setValue(username);

            String scl = "firstname";
            DatabaseReference databaseReference3 = dref.child(scl);
            databaseReference3.setValue(firstname);

            String gmail = "email";
            DatabaseReference databaseReference4 = dref.child(gmail);
            databaseReference4.setValue(email);

            String pass = "password";
            DatabaseReference databaseReference5 = dref.child(pass);
            databaseReference5.setValue(password);

            String ins = "address";
            DatabaseReference databaseReference2 = dref.child("ins");
            databaseReference2.setValue(addre);
            String ln = "lastname";
            DatabaseReference databaseReference6 = dref.child(ln);
            databaseReference6.setValue(lastname);

            String fone = "phone";
            DatabaseReference databaseReference7 = dref.child(fone);
            databaseReference7.setValue(phone);

            String st = "state";
            DatabaseReference databaseReference8 = dref.child(st);
            databaseReference8.setValue(sta);

            String cc = "country";
            DatabaseReference databaseReference9 = dref.child(cc);
            databaseReference9.setValue(count);
        }
        return true;
    }

    private void userInfo() {
        fname = (EditText) findViewById(R.id.first_name);
        firstname = fname.getText().toString();
        ln = (EditText) findViewById(R.id.last_name);
        lastname = ln.getText().toString();
        uname = (EditText) findViewById(R.id.user_name);
        username = uname.getText().toString();
        phon = (EditText) findViewById(R.id.number);


        phone = phon.getText().toString();
        address = (EditText) findViewById(R.id.address);
        addre = address.getText().toString();
        state = (EditText) findViewById(R.id.state);
        sta = state.getText().toString();
        country = (EditText) findViewById(R.id.country);
        count = country.getText().toString();
        gma = (EditText) findViewById(R.id.email);
        email = gma.getText().toString();
        pass = (EditText) findViewById(R.id.editText2);
        password = pass.getText().toString();
        confpass = (EditText) findViewById(R.id.confirm);
        confirm = confpass.getText().toString();

        if (TextUtils.isEmpty(firstname)) {
            Toast.makeText(Signup.this, "Please enter your First name ", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(lastname)) {
            Toast.makeText(Signup.this, "Please enter your last name ", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(addre)) {
            Toast.makeText(Signup.this, "Please enter your address ", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(sta)) {
            Toast.makeText(Signup.this, "Please enter your state ", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(count)) {
            Toast.makeText(Signup.this, "Please enter your country", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(Signup.this, "Please enter your email ", Toast.LENGTH_LONG).show();
            return;
        }
        boolean validemail = isValidEmail(email);
        if (!validemail) {
            Toast.makeText(Signup.this, "Please enter a valid email address", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(Signup.this, "Please enter your User name !", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(Signup.this, "A password is seriously needed !", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(Signup.this, "Please create a password containing at least 6 characters", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(confirm)) {
            Toast.makeText(Signup.this, "Please confirm your password ", Toast.LENGTH_LONG).show();
            return;
        }
        if (!confirm.equals(password)) {
            Toast.makeText(this, "Password do not match", Toast.LENGTH_LONG).show();
            return;
        }else {
            if (connectivity.isConnected()) {
                pd.show();
                if (auth.getCurrentUser() != null){
                    pd.dismiss();
                    Toast.makeText(Signup.this, "Account already exist ", Toast.LENGTH_LONG).show();

                }else {
                    auth.createUserWithEmailAndPassword(email, password).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    pd.dismiss();
                                    if (task.isSuccessful()) {
                                        if (profilesetup()) {
                                            Toast.makeText(Signup.this, "Account succesfully created", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), Login.class);
               startActivity(intent);
                                        }
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
                        }
                    }
    }
    private boolean isValidEmail(String email) {
        boolean isGoodEmail =
                (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            Toast.makeText(Signup.this,"Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        return isGoodEmail;
    }
    private void showDialog() {
        pd = new ProgressDialog(this);
        pd.setTitle("Creating account");
        pd.setMessage("Please wait...");
        pd.setIcon(R.drawable.ic_loading);
        pd.setCancelable(false);
    }

}
