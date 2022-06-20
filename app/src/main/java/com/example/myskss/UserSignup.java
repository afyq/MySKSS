package com.example.myskss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserSignup extends AppCompatActivity implements View.OnClickListener {

    //Defining variables/ view objects
    private EditText editTextFullName, editTextPhoneNumber, editTextCardNumber, editTextEmail, editTextPassword;
    private Button signupBtn, regToLoginBtn;
    private ProgressDialog progressDialog;

    //defining firebaseauth object
    private FirebaseAuth mAuth;

    private FirebaseFirestore firebaseFirestore;
    private String userID;
    public String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        //initializing firebase auth object
        mAuth = FirebaseAuth.getInstance();

        firebaseFirestore = FirebaseFirestore.getInstance();


        //Initializing all xml elements in activity_sign_up.xml
        editTextPhoneNumber = findViewById(R.id.phoneNumberRegister);
        editTextCardNumber = findViewById(R.id.cardnumberRegister);
        editTextFullName = findViewById(R.id.fullnameRegister);
        editTextEmail = findViewById(R.id.emailRegister);
        editTextPassword = findViewById(R.id.passwordRegister);
        signupBtn = findViewById(R.id.buttonSignUp);
        regToLoginBtn = findViewById(R.id.buttonLogin);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        signupBtn.setOnClickListener(this);
        regToLoginBtn.setOnClickListener(this);
    }

    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        String fullName = editTextFullName.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String cardNumber = editTextCardNumber.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(fullName)){
            Toast.makeText(this,"Please enter fullname",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(this,"Please enter phone number",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(cardNumber)){
            Toast.makeText(this,"Please enter card number",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        //creating a new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            Toast.makeText(UserSignup.this,"Registration Success",Toast.LENGTH_LONG).show();
                            finish();

                            //try
                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("Users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("full_name",fullName);
                            user.put("phone_number",phoneNumber);
                            user.put("card_number",cardNumber);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });

                            startActivity(new Intent(getApplicationContext(), UserLogin.class));

                        } else {
                            //display some message here
                            Toast.makeText(UserSignup.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {

        if(view == signupBtn){
            registerUser();
        }

        if(view == regToLoginBtn){
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, UserLogin.class));
        }

    }

}