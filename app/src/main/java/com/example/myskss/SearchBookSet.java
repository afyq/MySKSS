package com.example.myskss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

public class SearchBookSet extends AppCompatActivity {

    private EditText editTitle3;
    private EditText editBid3;
    private Spinner spinner3;
    private Button button3;
    private String type;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book_set);

        FirebaseApp.initializeApp(this);
        editTitle3 = (EditText) findViewById(R.id.editTitle3);
        editBid3 = (EditText) findViewById(R.id.editBid3);
        spinner3 = (Spinner)findViewById(R.id.spinner3);
        button3 = (Button)findViewById(R.id.button3);
        checkBox = (CheckBox)findViewById(R.id.onlyAvailable);


        String A [] = getResources().getStringArray(R.array.list1);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,A);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(verifyCategory()| verifyTitle()| verifyBid())) {
                    Toast.makeText(SearchBookSet.this, "Select at least parameter!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), SearchBook.class);

                if (verifyBid() && checkBox.isChecked()) {

                    intent.putExtra("id",1);
                    intent.putExtra("bid",Integer.parseInt(editBid3.getText().toString().trim()));
                    startActivity(intent);

                } else if (verifyBid() &&! checkBox.isChecked()) {

                    intent.putExtra("id",2);
                    intent.putExtra("bid",Integer.parseInt(editBid3.getText().toString().trim()));
                    startActivity(intent);

                } else if (verifyTitle() && verifyCategory() && checkBox.isChecked()) {

                    intent.putExtra("id",3);
                    intent.putExtra("btitle",editTitle3.getText().toString().trim());
                    intent.putExtra("btype",type);
                    startActivity(intent);

                } else if (verifyTitle() && verifyCategory() && !checkBox.isChecked()) {

                    intent.putExtra("id",4);
                    intent.putExtra("btitle",editTitle3.getText().toString().trim());
                    intent.putExtra("btype",type);
                    startActivity(intent);

                } else if( verifyTitle() &&! verifyCategory() && checkBox.isChecked()) {

                    intent.putExtra("id",5);
                    intent.putExtra("btitle",editTitle3.getText().toString().trim());
                    startActivity(intent);

                } else if (verifyTitle() &&! verifyCategory() &&! checkBox.isChecked()) {

                    intent.putExtra("id",6);
                    intent.putExtra("btitle",editTitle3.getText().toString().trim());
                    startActivity(intent);

                } else if (! verifyTitle() && verifyCategory() && checkBox.isChecked()) {

                    intent.putExtra("id",7);
                    intent.putExtra("btype",type);
                    startActivity(intent);

                } else if (!verifyTitle() && verifyCategory() &&! checkBox.isChecked()) {

                    intent.putExtra("id",8);
                    intent.putExtra("btype",type);
                    startActivity(intent);

                }
            }
        });

    }

    private boolean verifyTitle() {
        String t = editTitle3.getText().toString().trim();
        if (t.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean verifyBid() {
        String b = editBid3.getText().toString().trim();
        if (b.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }


    private boolean verifyCategory() {
        if (type.equals("Select Book Category")) {
            return false;
        } else {
            return true;
        }
    }

    /*@Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }*/

}