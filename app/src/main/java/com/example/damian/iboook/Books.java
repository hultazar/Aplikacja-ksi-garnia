package com.example.damian.iboook;

public class Books {

    int id;
    String tytul,autor,kraj,rodzaj, joiningDate;
    int indeks;

    public Books(int id, String tytul, String autor,String kraj, String rodzaj, String joiningDate, int indeks) {
        this.id = id;
        this.tytul = tytul;
        this.autor = autor;
        this.kraj = kraj;
        this.rodzaj = rodzaj;
        this.joiningDate = joiningDate;
        this.indeks = indeks;
    }



    public int getId() {
        return id;
    }

    public String getTytul() { return tytul; }

    public String getAutor() { return autor; }

    public String getKraj() {return kraj;}

    public String getRodzaj() { return rodzaj; }

    public String getJoiningDate() {
        return joiningDate;
    }

    public int getIndeks() {
        return indeks;
    }





}
