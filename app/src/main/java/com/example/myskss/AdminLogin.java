package com.example.myskss;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        progressDialog = new ProgressDialog(this);

    }

    public void login(View view) {

        EditText userField = (EditText) findViewById(R.id.username);
        String user = (userField.getText()).toString();

        EditText passField = (EditText) findViewById(R.id.password);
        String pass = (passField.getText()).toString();

        //if the email and password are not empty
        //displaying a progress dialog
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        if (user.equals("afiq") && (pass.equals("123456"))) {

            progressDialog.dismiss();
            //if the task is successfull
            Intent intent = new Intent(AdminLogin.this, AdminHome.class);
            Toast.makeText(AdminLogin.this,"Admin login successful!",Toast.LENGTH_SHORT).show();
            startActivity(intent);

        } else {
            Toast.makeText(this,"Please Enter Correct Credentials",Toast.LENGTH_SHORT).show();
        }

    }

}