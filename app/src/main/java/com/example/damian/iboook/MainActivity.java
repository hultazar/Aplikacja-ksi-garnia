package com.example.damian.iboook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String DATABASE_NAME = "myemployeedatabase";

    TextView textViewViewEmployees;
    EditText editTextName, editTextNazwisko, editTextMiejscowosc, editTextSalary;
    Spinner spinnerDepartment;


    SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewViewEmployees = (TextView) findViewById(R.id.textViewViewEmployees);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextNazwisko = (EditText) findViewById(R.id.editTextNazwisko);
        editTextMiejscowosc = (EditText) findViewById(R.id.editTextMiejscowosc);
        editTextSalary = (EditText) findViewById(R.id.editTextSalary);
        spinnerDepartment = (Spinner) findViewById(R.id.spinnerDepartment);

        findViewById(R.id.buttonAddEmployee).setOnClickListener(this);
        findViewById(R.id.button_ksiazki).setOnClickListener(this);
        textViewViewEmployees.setOnClickListener(this);

        //creating a database
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        createEmployeeTable();
    }


    //metoda tworzenia tabeli
    //as we are going to call this method everytime we will launch the application
    //I have added IF NOT EXISTS to the SQL
    //so it will only create the table when the table is not already created
    private void createEmployeeTable() {
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS employees (\n" +
                        "    id INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                        "    name varchar(200) NOT NULL,\n" +
                        "    nazwisko varchar(200) NOT NULL,\n" +
                        "    miejscowosc varchar(200) NOT NULL,\n" +
                        "    department varchar(200) NOT NULL,\n" +
                        "    joiningdate datetime NOT NULL,\n" +
                        "    salary integer NOT NULL\n" +
                        ");"
        );
    }

    //this method will validate the name and salary
    //dept does not need validation as it is a spinner and it cannot be empty
    private boolean inputsAreCorrect(String name,String nazwisko,String miejscowosc, String salary) {
        if (name.isEmpty()) {
            editTextName.setError("Pole nie może byc puste");
            editTextName.requestFocus();
            return false;
        }
        if (nazwisko.isEmpty()) {
            editTextNazwisko.setError("Pole nie może byc puste");
            editTextNazwisko.requestFocus();
            return false;
        }
        if (miejscowosc.isEmpty() ) {
            editTextMiejscowosc.setError("Pole nie może byc puste");
            editTextMiejscowosc.requestFocus();
            return false;
        }
        if (salary.isEmpty()) {
            editTextSalary.setError("Pole nie może byc puste");
            editTextSalary.requestFocus();
            return false;
        }
        return true;
    }

    //In this method we will do the create operation
    private void addEmployee() {

        String name = editTextName.getText().toString().trim();
        String nazwisko = editTextNazwisko.getText().toString().trim();
        String miejscowosc = editTextMiejscowosc.getText().toString().trim();
        String salary = editTextSalary.getText().toString().trim();
        String dept = spinnerDepartment.getSelectedItem().toString();

        //getting the current time for joining date
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joiningDate = sdf.format(cal.getTime());

        //validating the inptus
        if (inputsAreCorrect(name,nazwisko, miejscowosc, salary)) {

            String insertSQL = "INSERT INTO employees \n" +
                    "(name, nazwisko, miejscowosc, department, joiningdate, salary)\n" +
                    "VALUES \n" +
                    "(?, ?, ?, ?, ?,?);";

            //using the same method execsql for inserting values
            //this time it has two parameters
            //first is the sql string and second is the parameters that is to be binded with the query

            mDatabase.execSQL(insertSQL, new String[]{name,nazwisko,miejscowosc,dept,joiningDate,salary});

            Toast.makeText(this, "Klient dodany do bazy", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_ksiazki:
                Intent intent = new Intent(this, Main_book.class);
                startActivity(intent);
            break;

            case R.id.buttonAddEmployee:

                addEmployee();

                break;
            case R.id.textViewViewEmployees:

                startActivity(new Intent(this, EmployeeActivity.class));

                break;
        }
    }
}
