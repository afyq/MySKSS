package com.example.myskss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
    }

    public void Admin (View view) {
        Intent intent = new Intent(SelectUser.this,AdminLogin.class);
        startActivity(intent);
    }

    public void Student (View view) {
        Intent intent = new Intent(SelectUser.this, UserLogin.class);
        startActivity(intent);
    }

}