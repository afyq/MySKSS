package com.example.myskss;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdminIssueBook extends AppCompatActivity {

    DatabaseReference databaseBook;
    DatabaseReference databaseIssue;

    EditText ETSearchBook;
    Button BtnSearchBook;

    ListView listViewBook;

    List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_issue_book);

        //getting the reference of book node
        databaseBook = FirebaseDatabase.getInstance().getReference("Book");
        databaseIssue = FirebaseDatabase.getInstance().getReference("Issue");

        //getting listviews
        listViewBook = (ListView) findViewById(R.id.listViewBook);

        ETSearchBook = findViewById(R.id.ETSearchBook);
        BtnSearchBook = (Button) findViewById(R.id.BtnSearchBook);

        //list to store letters
        books = new ArrayList<>();

        listViewBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book book = books.get(i);
                showEditDeleteDialog(book.getBookId(), book.getBookTitle(), book.getBookAuthor(), book.getBookPublisher(), book.getBookGenre(), book.getBookPage(), book.getBookISBNNo(), book.getBookAvailability());
                return;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        BtnSearchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //attaching value event listener
                databaseBook.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String SearchBook = ETSearchBook.getText().toString().trim();

                        //clearing the previous artist list
                        books.clear();

                        if (!TextUtils.isEmpty(SearchBook)) {
                            //iterating through all the nodes
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                //getting letter
                                Book book = postSnapshot.getValue(Book.class);

                                if ((book.getBookTitle() != null && book.getBookTitle().toLowerCase().contains(SearchBook.toLowerCase()))
                                        || (book.getBookAuthor() != null && book.getBookAuthor().toLowerCase().contains(SearchBook.toLowerCase()))
                                        || (book.getBookPublisher() != null && book.getBookPublisher().toLowerCase().contains(SearchBook.toLowerCase()))
                                        || (book.getBookGenre() != null && book.getBookGenre().toLowerCase().contains(SearchBook.toLowerCase()))) {
                                    //adding letter to the list
                                    books.add(book);
                                }
                            }

                            //creating adapter
                            BookList bookAdapter = new BookList(AdminIssueBook.this, books);
                            //attaching adapter to the listview
                            listViewBook.setAdapter(bookAdapter);

                            Toast.makeText(AdminIssueBook.this, "Searching for " + SearchBook, Toast.LENGTH_SHORT).show();
                            ETSearchBook.getText().clear();

                        } else {
                            Toast.makeText(AdminIssueBook.this, "Please enter book detail!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void showEditDeleteDialog(String bookId, String bookTitle, String bookAuthor, String bookPublisher, String bookGenre, int bookPage, int bookISBNNo, int bookAvailability) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.issue_book_dialog, null);
        dialogBuilder.setView(dialogView);
        final TextView TVUnavailable = (TextView) dialogView.findViewById(R.id.TVUnavailable);
        final EditText ETIssueStudent = (EditText) dialogView.findViewById(R.id.ETIssueStudent);
        final Button BtnIssueBook = (Button) dialogView.findViewById(R.id.BtnIssueBook);

        TVUnavailable.setVisibility(View.GONE);
        ETIssueStudent.setVisibility(View.GONE);
        BtnIssueBook.setVisibility(View.GONE);

        if (bookAvailability == 0){
            TVUnavailable.setVisibility(View.VISIBLE);
            dialogBuilder.setTitle("Issue Book");
            final AlertDialog c = dialogBuilder.create();
            c.show();
        }else{

            ETIssueStudent.setVisibility(View.VISIBLE);
            BtnIssueBook.setVisibility(View.VISIBLE);

            dialogBuilder.setTitle("Issue Book");
            final AlertDialog c = dialogBuilder.create();
            c.show();

            //getting current date
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("d MMM yyyy, H:mm a");

            BtnIssueBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = databaseIssue.push().getKey();
                    String name = ETIssueStudent.getText().toString().trim();
                    String date = df.format(calendar.getTime());
                    int availability = bookAvailability - 1;
                    String status = "on going";

                    if (TextUtils.isEmpty(name)) {
                        Toast.makeText(getApplicationContext(), "Please insert student name!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Issue issue = new Issue(id, name, bookId, bookTitle, date, status);
                    databaseIssue.child(id).setValue(issue);

                    //updating book
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Book").child(bookId);
                    Book book = new Book(bookId, bookTitle, bookAuthor, bookPublisher, bookGenre, bookPage, bookISBNNo, availability);
                    dR.setValue(book);

                    c.dismiss();

                    Toast.makeText(AdminIssueBook.this, "Book Issued", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

}