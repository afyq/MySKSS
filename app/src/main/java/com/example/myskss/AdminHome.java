package com.example.myskss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminHome extends AppCompatActivity implements View.OnClickListener {

    private Button searchBook,addBook,removeBook,updateBook,issueBook,returnBook,logOut,reissueButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        FirebaseApp.initializeApp(this);
        mAuth =FirebaseAuth.getInstance();
        searchBook=(Button)findViewById(R.id.searchBook);
        addBook=(Button)findViewById(R.id.addBook);
        removeBook=(Button)findViewById(R.id.removeBook);
        updateBook=(Button)findViewById(R.id.updateBook);
        issueBook=(Button)findViewById(R.id.issueBook);
        returnBook=(Button)findViewById(R.id.returnBook);
        logOut=(Button)findViewById(R.id.logOut);
        reissueButton=(Button)findViewById(R.id.reissueBook);
        firebaseFirestore =FirebaseFirestore.getInstance();

        searchBook.setOnClickListener(this);
        addBook.setOnClickListener(this);
        removeBook.setOnClickListener(this);
        updateBook.setOnClickListener(this);
        issueBook.setOnClickListener(this);
        returnBook.setOnClickListener(this);
        logOut.setOnClickListener(this);
        reissueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == logOut) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(AdminHome.this, SelectUser.class));
        }

        if(v == searchBook){
            startActivity(new Intent(getApplicationContext(), SearchBookSet.class));
        }

        if(v == addBook) {
            startActivity(new Intent(getApplicationContext(), AdminAddBook.class));
        }

        if(v == removeBook) {
            startActivity(new Intent(getApplicationContext(), AdminRemoveBook.class));
        }

        if(v == issueBook) {
            startActivity(new Intent(getApplicationContext(), AdminIssueBook.class));
        }

        if(v == returnBook) {
            startActivity(new Intent(getApplicationContext(), AdminReturnBook.class));
        }

        if(v == updateBook) {
            startActivity(new Intent(getApplicationContext(), AdminUpdateBook.class));
        }

        if(v == reissueButton) {
            startActivity(new Intent(getApplicationContext(), AdminReissueBook.class));
        }

    }

}