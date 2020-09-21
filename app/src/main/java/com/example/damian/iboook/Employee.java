package com.example.damian.iboook;

public class Employee {
    int id;
    String name, nazwisko,miejscowosc, dept, joiningDate;
    int salary;

    public Employee(int id, String name, String nazwisko,String miejscowosc, String dept, String joiningDate, int salary) {
        this.id = id;
        this.name = name;
        this.nazwisko = nazwisko;
        this.miejscowosc = miejscowosc;
        this.dept = dept;
        this.joiningDate = joiningDate;
        this.salary = salary;
    }



    public int getId() {
        return id;
    }

    public String getName() { return name; }

    public String getNazism() { return nazwisko; }

    public String getMiejscowosc() {return miejscowosc;}

    public String getDept() { return dept; }

    public String getJoiningDate() {
        return joiningDate;
    }

    public int getSalary() {
        return salary;
    }
}
