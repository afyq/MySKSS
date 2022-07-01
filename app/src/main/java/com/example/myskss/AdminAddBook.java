package com.example.myskss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminAddBook extends AppCompatActivity {

    TextInputLayout BookTitle, BookAuthor, BookPublisher, BookGenre, BookPage, BookISBNNo, Availability;
    Button BtnAddBook;
    DatabaseReference databaseBook;
    ProgressDialog p1;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_book);

        databaseBook = FirebaseDatabase.getInstance().getReference("Book");

        BookTitle = findViewById(R.id.BookTitle);
        BookAuthor = findViewById(R.id.BookAuthor);
        BookPublisher = findViewById(R.id.BookPublisher);
        BookGenre = findViewById(R.id.BookGenre);
        BookPage = findViewById(R.id.BookPage);
        BookISBNNo = findViewById(R.id.BookISBNNo);
        Availability = findViewById(R.id.Availability);
        BtnAddBook = findViewById(R.id.BtnAddBook);

        BtnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });
    }

    private void addBook() {
        String id = databaseBook.push().getKey();
        String title = BookTitle.getEditText().getText().toString().trim();
        String author = BookAuthor.getEditText().getText().toString().trim();
        String publisher = BookPublisher.getEditText().getText().toString().trim();
        String genre = BookGenre.getEditText().getText().toString().trim(); //tuka nilai
        int page = Integer.parseInt(BookPage.getEditText().getText().toString().trim());
        int ISBN = Integer.parseInt(BookISBNNo.getEditText().getText().toString().trim());
        int available = Integer.parseInt(Availability.getEditText().getText().toString().trim());

        if(TextUtils.isEmpty(title)){
            Toast.makeText(this,"Please insert book title",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(author)){
            Toast.makeText(this,"Please insert author name",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(publisher)){
            Toast.makeText(this,"Please insert publisher name",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(genre)){
            Toast.makeText(this,"Please insert book genre",Toast.LENGTH_LONG).show();
            return;
        }
        if(page == 0){
            Toast.makeText(this,"Please insert book page",Toast.LENGTH_LONG).show();
            return;
        }
        if(ISBN == 0){
            Toast.makeText(this,"Please insert book ISBN No.",Toast.LENGTH_LONG).show();
            return;
        }
        if(available == 0){
            Toast.makeText(this,"Please insert no. of units",Toast.LENGTH_LONG).show();
            return;
        }

        Book book = new Book(id, title, author, publisher, genre, page, ISBN, available);

        databaseBook.child(id).setValue(book);

        BookTitle.getEditText().getText().clear();
        BookAuthor.getEditText().getText().clear();
        BookPublisher.getEditText().getText().clear();
        BookGenre.getEditText().getText().clear();
        BookPage.getEditText().getText().clear();
        BookISBNNo.getEditText().getText().clear();
        Availability.getEditText().getText().clear();

        Toast.makeText(this, "Book added", Toast.LENGTH_LONG).show();
    }

}