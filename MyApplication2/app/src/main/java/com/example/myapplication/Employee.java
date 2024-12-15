package com.example.myapplication;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Employee {
    private int id;
    private String firstname, lastname, email, department, hireDate;
    private double salary;
    private int leaves;

    public Employee(int id, String firstname, String lastname, String email, String department, double salary, String hireDate, int leaves) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.hireDate = hireDate;
        this.leaves = leaves;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getEmail() {
        return email;
    }
    public String getDepartment() {
        return department;
    }
    public double getSalary() {
        return salary;
    }
    public String getHireDate() {
        return hireDate;
    }
    public int getLeaves() {
        return leaves;
    }
    public void applyAnnualSalaryIncrease() {
        if (hireDate == null || hireDate.isEmpty()) {
            return;
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date hireDateParsed = formatter.parse(hireDate);
            Calendar hireCalendar = Calendar.getInstance();
            hireCalendar.setTime(hireDateParsed);
            Calendar today = Calendar.getInstance();
            int yearsEmployed = today.get(Calendar.YEAR) - hireCalendar.get(Calendar.YEAR);
            if (today.get(Calendar.DAY_OF_YEAR) < hireCalendar.get(Calendar.DAY_OF_YEAR)) {
                yearsEmployed--;
            }
            if (yearsEmployed > 0) {
                salary += salary * 0.05 * yearsEmployed;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}