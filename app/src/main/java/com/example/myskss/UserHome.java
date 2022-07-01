package com.example.myskss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserHome extends AppCompatActivity implements View.OnClickListener {

    private TextView title1;
    private Button searchBook1,seeBook,logOut1,buttonReissue, buttonNilam;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        title1 = (TextView)findViewById(R.id.title1);
        searchBook1 = (Button)findViewById(R.id.searchBook1);
        seeBook = (Button)findViewById(R.id.seeBook);
        logOut1 = (Button)findViewById(R.id.logOut1);
        buttonReissue = (Button)findViewById(R.id.buttonReissue);
        buttonNilam = (Button)findViewById(R.id.buttonNilam);

        firebaseFirestore = FirebaseFirestore.getInstance();
        searchBook1.setOnClickListener(this);
        seeBook.setOnClickListener(this);
        logOut1.setOnClickListener(this);
        buttonReissue.setOnClickListener(this);
        buttonNilam.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == logOut1) {

            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(), SelectUser.class));
            finish();

        }

    }

}