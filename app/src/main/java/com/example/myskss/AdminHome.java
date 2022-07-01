package com.example.myskss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminHome extends AppCompatActivity {

    private Button BtnAddBook, BtnSearchBook, BtnIssueBook, BtnReturnBook, BtnStudent,BtnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        BtnAddBook = findViewById(R.id.BtnAddBook);
        BtnSearchBook = findViewById(R.id.BtnSearchBook);
        BtnIssueBook = findViewById(R.id.BtnIssueBook);
        BtnReturnBook = findViewById(R.id.BtnReturnBook);
        BtnLogOut = findViewById(R.id.BtnLogOut);

        BtnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, AdminAddBook.class));
            }
        });

        BtnSearchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, SearchBook.class));
            }
        });

        BtnIssueBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, AdminIssueBook.class));
            }
        });

        BtnReturnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, AdminReturnBook.class));
            }
        });

        BtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, SelectUser.class));
            }
        });
    }

}