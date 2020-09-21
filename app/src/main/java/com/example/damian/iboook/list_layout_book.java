package com.example.damian.iboook;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class list_layout_book  extends ArrayAdapter<Books> {

    Context bcontx;
    int listLR;
    List<Books> booklist;
    SQLiteDatabase bDatabase;

    public list_layout_book(Context bcontx, int listLR, List<Books> booklist, SQLiteDatabase bDatabase) {
        super(bcontx, listLR, booklist);

        this.bcontx = bcontx;
        this.listLR = listLR;
        this.booklist = booklist;
        this.bDatabase = bDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(bcontx);
        View view = inflater.inflate(listLR, null);

        final Books books = booklist.get(position);


        TextView textViewTytul = view.findViewById(R.id.TextTytulKsiazki);
        TextView textViewAutor = view.findViewById(R.id.TextAutroKsiazki);
        TextView textViewIndeks = view.findViewById(R.id.TextViewIndeks);
        TextView textViewJoiningDate = view.findViewById(R.id.TextJoinBookDate);
        TextView textViewKraj = view.findViewById(R.id.TextKrajPochodzenia);
        TextView textViewRodzaj = view.findViewById(R.id.TextViewRodzaj);

      textViewRodzaj.setText(books.getRodzaj());
      textViewAutor.setText(books.getAutor());
      textViewJoiningDate.setText(books.getJoiningDate());
      textViewTytul.setText(books.getTytul());
      textViewKraj.setText(books.getKraj());
      textViewIndeks.setText(String.valueOf(books.getIndeks()));


        Button buttonDeleteBook = view.findViewById(R.id.buttonDeleteBooks);
        Button buttonEditBook = view.findViewById(R.id.buttonEditBooks);

        buttonEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
               updatebook(books); }
        });


        //przycisk usuwania / operacja usuwania
        buttonDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(bcontx);
                builder.setTitle("Czy jestes pewny?");
                builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM books WHERE id = ?";
                        bDatabase.execSQL(sql, new Integer[]{books.getId()});
                        reloadBooks();
                    }
                });
                builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    private void updatebook(final Books books) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(bcontx);

        LayoutInflater inflater = LayoutInflater.from(bcontx);
        View view = inflater.inflate(R.layout.update_book, null);
        builder.setView(view);

        final EditText editTextTytul = view.findViewById(R.id.editTytul);
        final EditText editTextAutor = view.findViewById(R.id.editAutor);
        final EditText editTextKraj = view.findViewById(R.id.editKraj);
        final EditText editTextIndeks = view.findViewById(R.id.editIndeks);
        final Spinner spinnerRodzaj = view.findViewById(R.id.spinnerRodzaj);


        editTextTytul.setText(books.getTytul());
        editTextAutor.setText(books.getAutor());
        editTextIndeks.setText(String.valueOf(books.getIndeks()));
        editTextKraj.setText(books.getKraj());



        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.buttonUpdateBooks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tytul = editTextTytul.getText().toString().trim();
                String autor = editTextAutor.getText().toString().trim();
                String kraj = editTextKraj.getText().toString().trim();
                String indeks = editTextIndeks.getText().toString().trim();
                String rodzaj = spinnerRodzaj.getSelectedItem().toString();

                if (tytul.isEmpty()) {
                    editTextTytul.setError("pole nie może byc puste");
                    editTextTytul.requestFocus();
                    return;
                }

                if (autor.isEmpty()) {
                    editTextAutor.setError("pole nie może byc puste");
                    editTextAutor.requestFocus();
                    return;
                }

                if(kraj.isEmpty()){
                    editTextKraj.setError("pole nie może byc puste");
                    editTextKraj.requestFocus();
                    return;
                }

                if (indeks.isEmpty()) {
                    editTextIndeks.setError("pole nie może byc puste");
                    editTextIndeks.requestFocus();
                    return;
                }

                String sql = "UPDATE books \n" +
                        "SET tytul = ?, \n" +
                        "autor = ?, \n" +
                        "kraj = ?, \n" +
                        "rodzaj = ?, \n" +
                        "indeks = ? \n" +
                        "WHERE id = ?;\n";

                bDatabase.execSQL(sql,new String[]{tytul,autor,kraj,rodzaj,indeks,String.valueOf(books.getId())});

                Toast.makeText(bcontx, "Ksiązka uaktualniona", Toast.LENGTH_SHORT).show();
                reloadBooks();

                dialog.dismiss();
            }
        });
    }

    private void reloadBooks() {
        Cursor cursorBooks = bDatabase.rawQuery("SELECT * FROM books", null);
        if (cursorBooks.moveToFirst()) {
            booklist.clear();
            do {
                booklist.add(new Books(
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
        cursorBooks.close();
        notifyDataSetChanged();
    }



}


