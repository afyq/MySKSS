package com.example.myskss;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchBook extends AppCompatActivity {

    DatabaseReference databaseBook;

    EditText ETSearchBook;
    Button BtnSearchBook;

    ListView listViewBook;

    List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);

        //getting the reference of letters node
        databaseBook = FirebaseDatabase.getInstance().getReference("Book");

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
                            BookList bookAdapter = new BookList(SearchBook.this, books);
                            //attaching adapter to the listview
                            listViewBook.setAdapter(bookAdapter);

                            Toast.makeText(SearchBook.this, "Searching for " + SearchBook, Toast.LENGTH_SHORT).show();
                            ETSearchBook.getText().clear();

                        } else {
                            Toast.makeText(SearchBook.this, "Please enter book detail!", Toast.LENGTH_SHORT).show();
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
        final View dialogView = inflater.inflate(R.layout.edit_book_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText EditBookTitle = (EditText) dialogView.findViewById(R.id.EditBookTitle);
        final EditText EditBookAuthor = (EditText) dialogView.findViewById(R.id.EditBookAuthor);
        final EditText EditBookPublisher = (EditText) dialogView.findViewById(R.id.EditBookPublisher);
        final EditText EditBookGenre = (EditText) dialogView.findViewById(R.id.EditBookGenre);
        final EditText EditBookPage = (EditText) dialogView.findViewById(R.id.EditBookPage);
        final EditText EditBookISBNNo = (EditText) dialogView.findViewById(R.id.EditBookISBNNo);
        final EditText EditAvailability = (EditText) dialogView.findViewById(R.id.EditAvailability);
        final Button BtnEditBook = (Button) dialogView.findViewById(R.id.BtnEditBook);
        final Button BtnDeleteBook = (Button) dialogView.findViewById(R.id.BtnDeleteBook);

        EditBookTitle.setText(bookTitle);
        EditBookAuthor.setText(bookAuthor);
        EditBookPublisher.setText(bookPublisher);
        EditBookGenre.setText(bookGenre);
        EditBookPage.setText(String.valueOf(bookPage));
        EditBookISBNNo.setText(String.valueOf(bookISBNNo));
        EditAvailability.setText(String.valueOf(bookAvailability));

        dialogBuilder.setTitle("Edit Book");
        final AlertDialog c = dialogBuilder.create();
        c.show();

        BtnEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = EditBookTitle.getText().toString().trim();
                String author = EditBookAuthor.getText().toString().trim();
                String publisher = EditBookPublisher.getText().toString().trim();
                String genre = EditBookGenre.getText().toString().trim();
                int page = Integer.parseInt(EditBookPage.getText().toString().trim());
                int ISBN = Integer.parseInt(EditBookISBNNo.getText().toString().trim());
                int availability = Integer.parseInt(EditAvailability.getText().toString().trim());

                if(TextUtils.isEmpty(title)){
                    Toast.makeText(getApplicationContext(),"Please insert book title",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(author)){
                    Toast.makeText(getApplicationContext(),"Please insert author name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(publisher)){
                    Toast.makeText(getApplicationContext(),"Please insert publisher name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(genre)){
                    Toast.makeText(getApplicationContext(),"Please insert book genre",Toast.LENGTH_LONG).show();
                    return;
                }
                if(page == 0){
                    Toast.makeText(getApplicationContext(),"Please insert book page",Toast.LENGTH_LONG).show();
                    return;
                }
                if(ISBN == 0){
                    Toast.makeText(getApplicationContext(),"Please insert book ISBN No.",Toast.LENGTH_LONG).show();
                    return;
                }
                if(availability == 0){
                    Toast.makeText(getApplicationContext(),"Please insert no. of units",Toast.LENGTH_LONG).show();
                    return;
                }

                updateLetter(bookId, title, author, publisher, genre, page, ISBN, availability);
                c.dismiss();
            }
        });

        BtnDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteLetter(bookId);
                c.dismiss();
            }
        });
    }

    private boolean updateLetter(String id, String title, String author, String publisher, String genre, int page, int ISBN, int availability) {
        //getting the specified letter reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Book").child(id);

        //updating letter
        Book book = new Book(id, title, author, publisher, genre, page, ISBN, availability);
        dR.setValue(book);
        Toast.makeText(getApplicationContext(), "Book Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteLetter(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Book").child(id);

        //removing artist
        dR.removeValue();

        Toast.makeText(getApplicationContext(), "Book Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

}