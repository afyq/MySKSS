package com.example.myskss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.List;

public class AdminReturnBook extends AppCompatActivity {

    DatabaseReference databaseBook;

    EditText ETSearchIssue;
    Button BtnSearchIssue;

    ListView listViewIssueSearch;

    List<Issue> issues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_return_book);

        //getting the reference of letters node
        databaseBook = FirebaseDatabase.getInstance().getReference("Book");
        databaseBook = FirebaseDatabase.getInstance().getReference("Issue");

        //getting listviews
        listViewIssueSearch = (ListView) findViewById(R.id.listViewIssueSearch);

        ETSearchIssue = findViewById(R.id.ETSearchIssue);
        BtnSearchIssue = (Button) findViewById(R.id.BtnSearchIssue);

        //list to store letters
        issues = new ArrayList<>();

        listViewIssueSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Issue issue = issues.get(i);
                showReturnDialog(issue.getIssueId(), issue.getIssueName(), issue.getBookId(), issue.getBookTitle(), issue.getIssueDate(), issue.getIssueStatus());
                return;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //attaching value event listener
        databaseBook.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting letter
                    Issue issue = postSnapshot.getValue(Issue.class);

                    if (issue.getIssueStatus() != null && issue.getIssueStatus().equals("on going")) {
                        //adding letter to the list
                        issues.add(issue);
                    }
                }

                //creating adapter
                IssueList bookAdapter = new IssueList(AdminReturnBook.this, issues);
                //attaching adapter to the listview
                listViewIssueSearch.setAdapter(bookAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        BtnSearchIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //attaching value event listener
                databaseBook.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String SearchIssue = ETSearchIssue.getText().toString().trim();

                        //clearing the previous list
                        issues.clear();

                        if (!TextUtils.isEmpty(SearchIssue)) {
                            //iterating through all the nodes
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                //getting letter
                                Issue issue = postSnapshot.getValue(Issue.class);

                                if ((issue.getBookTitle() != null && issue.getBookTitle().toLowerCase().contains(SearchIssue.toLowerCase()))
                                        && issue.getIssueStatus() != null && issue.getIssueStatus().equals("on going")
                                        || (issue.getIssueName() != null && issue.getIssueName().toLowerCase().contains(SearchIssue.toLowerCase()))
                                        && issue.getIssueStatus() != null && issue.getIssueStatus().equals("on going")) {
                                    //adding letter to the list
                                    issues.add(issue);
                                }
                            }

                            //creating adapter
                            IssueList bookAdapter = new IssueList(AdminReturnBook.this, issues);
                            //attaching adapter to the listview
                            listViewIssueSearch.setAdapter(bookAdapter);

                            Toast.makeText(AdminReturnBook.this, "Searching for " + SearchIssue, Toast.LENGTH_SHORT).show();
                            ETSearchIssue.getText().clear();

                        } else {
                            Toast.makeText(AdminReturnBook.this, "Please enter book detail!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void showReturnDialog(String issueId, String issueName, String bookId, String bookTitle, String issueDate, String issueStatus) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.return_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button BtnReissueBook = (Button) dialogView.findViewById(R.id.BtnReissueBook);
        final Button BtnReturnBook = (Button) dialogView.findViewById(R.id.BtnReturnBook);

        dialogBuilder.setTitle("Return Book");
        final AlertDialog c = dialogBuilder.create();
        c.show();

        BtnReissueBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateIssue(issueId, issueName, bookId, bookTitle, issueStatus);
                c.dismiss();

                //clearing the previous list
                issues.clear();
            }
        });

        BtnReturnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                returnBook(issueId, issueName, bookId, bookTitle, issueDate);
                c.dismiss();

                //clearing the previous list
                issues.clear();
            }
        });
    }

    private boolean updateIssue(String issueId, String issueName, String bookId, String bookTitle, String issueStatus) {
        //getting the specified letter reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Issue").child(issueId);

        //getting current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("d MMM yyyy, H:mm a");
        String date = df.format(calendar.getTime());

        //updating issue
        Issue issue = new Issue(issueId, issueName, bookId, bookTitle, date, issueStatus);
        dR.setValue(issue);
        Toast.makeText(getApplicationContext(), "Reissue completed!", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean returnBook(String issueId, String issueName, String bookId, String bookTitle, String issueDate) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Issue").child(issueId);
        DatabaseReference databaseBook = FirebaseDatabase.getInstance().getReference("Book").child(bookId);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Book book = new Book();
                book = dataSnapshot.getValue(Book.class);
                int availability = book.getBookAvailability();
                //updating book
                databaseBook.child("bookAvailability").setValue(availability + 1);
                stopLockTask();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        databaseBook.addListenerForSingleValueEvent(listener);


        //int availability = Integer.parseInt(databaseBook.child("bookAvailability").getKey());

        String status = "completed";

        //updating issue
        Issue issue = new Issue(issueId, issueName, bookId, bookTitle, issueDate, status);
        dR.setValue(issue);

        Toast.makeText(getApplicationContext(), "Book Returned", Toast.LENGTH_LONG).show();
        return true;
    }

}