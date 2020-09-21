package com.example.damian.iboook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main_book extends AppCompatActivity implements View.OnClickListener{


    public static final String DATABASE_NAME = "mojeksiazki";

    TextView textPodgladiEdycja;

    EditText editTytul,editAutor,editKraj,editIndeks;
    Spinner spinnerRodzaj;


    SQLiteDatabase bDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_book);


        editAutor = (EditText) findViewById(R.id.editAutor);
        editTytul = (EditText) findViewById(R.id.editTytul);
        editKraj = (EditText) findViewById(R.id.editKraj);
        editIndeks = (EditText) findViewById(R.id.editIndeks);
        textPodgladiEdycja = (TextView) findViewById(R.id.textPodgladiEdycja);
        spinnerRodzaj = (Spinner) findViewById(R.id.spinnerRodzaj);


        //implementacja button/textview
        findViewById(R.id.buttoDodajKsiazke).setOnClickListener(this);
        textPodgladiEdycja.setOnClickListener(this);

        //torzenie bazy danych ksiazki
        bDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        createBooksTable();
    }

    private void createBooksTable() {
        bDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS books (\n" +
                        "    id INTEGER NOT NULL CONSTRAINT books_pk PRIMARY KEY AUTOINCREMENT,\n" +
                        "    tytul varchar(200) NOT NULL,\n" +
                        "    autor varchar(200) NOT NULL,\n" +
                        "    kraj varchar(200) NOT NULL,\n" +
                        "    rodzaj varchar(200) NOT NULL,\n" +
                        "    joiningdate datetime NOT NULL,\n" +
                        "    indeks integer NOT NULL\n" +
                        ");"
        );
    }




    private boolean poprawnoscpola (String tytul,String autor,String kraj, String indeks) {
        if (tytul.isEmpty()) {
            editTytul.setError("Pole nie moze byc puste");
            editTytul.requestFocus();
            return false;
        }

        if (autor.isEmpty()) {
            editAutor.setError("Pole nie moze byc puste");
            editAutor.requestFocus();
            return false;
        }

        if (kraj.isEmpty()) {
            editKraj.setError("Pole nie moze byc puste");
            editKraj.requestFocus();
            return false;
        }
        if (indeks.isEmpty()|| Integer.parseInt(indeks) <= 0){
            editIndeks.setError("Pole nie moze byc puste");
            editIndeks.requestFocus();
            return  false;
        }

        return true;
    }


    private void addBooks() {


        String tytul = editTytul.getText().toString().trim();
        String autor = editAutor.getText().toString().trim();
        String kraj = editKraj.getText().toString().trim();
        String indeks = editIndeks.getText().toString().trim();

        String rodzaj = spinnerRodzaj.getSelectedItem().toString();

        //getting the current time for joining date
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joiningDate = sdf.format(cal.getTime());

        //walidacja inputow
        if (poprawnoscpola(tytul,autor, kraj, indeks)) {

            String insertSQL = "INSERT INTO books \n" +
                    "(tytul, autor, kraj, rodzaj, joiningdate, indeks)\n" +
                    "VALUES \n" +
                    "(?, ?, ?, ?, ?,?);";

            //using the same method execsql for inserting values
            //czas ma dwa paramtery
            //first is the sql string and second is the parameters that is to be binded with the query

            bDatabase.execSQL(insertSQL, new String[]{tytul,autor,kraj,rodzaj,joiningDate,indeks});

            Toast.makeText(this, "Książka została dodana do bazy", Toast.LENGTH_SHORT).show();
        }
    }






    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttoDodajKsiazke:

                addBooks();

                break;
            case R.id.textPodgladiEdycja:

                startActivity(new Intent(this, activity_book.class));

                break;
        }

    }
}
