package com.example.damian.iboook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class activity_book extends AppCompatActivity {

    List<Books> booksList;
    SQLiteDatabase bDatabase;
    ListView listViewBooks;
    list_layout_book adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_book);


        listViewBooks = (ListView) findViewById(R.id.listViewBooks);
        booksList = new ArrayList<>();

        bDatabase = openOrCreateDatabase(Main_book.DATABASE_NAME, MODE_PRIVATE, null);
        showBooksFrmDbase();

    }

    private void showBooksFrmDbase() {
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorBooks = bDatabase.rawQuery("SELECT * FROM books", null);

        //if the cursor has some data
        if (cursorBooks.moveToFirst()) {
            //petla po wszystkich rekordach
            do {
                //pushing pojedynczego rekordu do book listy
                booksList.add(new Books(
                        cursorBooks.getInt(0),
                        cursorBooks.getString(1),
                        cursorBooks.getString(2),
                        cursorBooks.getString(3),
                        cursorBooks.getString(4),
                        cursorBooks.getString(5),
                        cursorBooks.getInt(6)
                ));
            } while (cursorBooks.moveToNext());
        }
        //zamykanie kursora
        cursorBooks.close();

        //tworzenie adapter obiektu
        adapter = new list_layout_book (this, R.layout.list_layout_book , booksList, bDatabase);

        //dodanie adaptera do listy view
      // listViewBooks.setAdapter((ListAdapter) adapter);
       listViewBooks.setAdapter(adapter);

    }

}
