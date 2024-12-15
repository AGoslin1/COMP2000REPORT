package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.gson.Gson;
import okhttp3.*;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.LinearLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class EmployeeManagement extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getAllEmployees();
    }
    public void getAllEmployees() {
        OkHttpClient client = new OkHttpClient();
        String url = "http://10.224.41.11/comp2000/employees";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        new Thread(() -> {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseData = response.body() != null ? response.body().string() : "";
                    Log.d("Employee Data", responseData);
                    Gson gson = new Gson();
                    Employee[] employees = gson.fromJson(responseData, Employee[].class);
                    runOnUiThread(() -> displayEmployees(employees));
                } else {
                    Log.e("API Error", "Request failed with code: " + response.code());
                }
            } catch (Exception e) {
                Log.e("API Error", "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
    private void displayEmployees(Employee[] employees) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar today = Calendar.getInstance();

        for (Employee employee : employees) {
            double salary = employee.getSalary();
            String hireDate = employee.getHireDate();
            if (hireDate != null && !hireDate.isEmpty()) {
                try {
                    Date hireDateParsed = dateFormat.parse(hireDate);
                    Calendar hireDateCalendar = Calendar.getInstance();
                    hireDateCalendar.setTime(hireDateParsed);
                    int yearsOfEmployment = today.get(Calendar.YEAR) - hireDateCalendar.get(Calendar.YEAR);
                    if (today.get(Calendar.DAY_OF_YEAR) < hireDateCalendar.get(Calendar.DAY_OF_YEAR)) {
                        yearsOfEmployment--;
                    }
                    salary += salary * 0.05 * yearsOfEmployment;
                } catch (ParseException e) {
                    Log.e("Date Parsing Error", "Invalid hire date: " + hireDate, e);
                }
            }
            TextView employeeView = new TextView(this);
            String employeeInfo = "ID: " + employee.getId() + "\n" +
                    "Name: " + employee.getFirstname() + " " + employee.getLastname() + "\n" +
                    "Email: " + employee.getEmail() + "\n" +
                    "Department: " + employee.getDepartment() + "\n" +
                    "Salary: " + salary + "\n" +
                    "Hire Date: " + employee.getHireDate() + "\n" +
                    "Leaves: " + employee.getLeaves();
            employeeView.setText(employeeInfo);
            employeeView.setPadding(16, 16, 16, 16);
            employeeView.setTextSize(16);
            LinearLayout EmployeeLayout = findViewById(R.id.EmployeeLayout);
            EmployeeLayout.addView(employeeView);
        }
    }


    public void back(View button){
        Intent intent = new Intent(EmployeeManagement.this, AdminMainMenu.class);
        startActivity(intent);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        button.startAnimation(animation);
    }
    public void AddEmployee(View button){
        Intent intent = new Intent(EmployeeManagement.this, AddEmployee.class);
        startActivity(intent);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        button.startAnimation(animation);
    }
    public void DeleteEmployee(View button) {
        Intent intent = new Intent(EmployeeManagement.this, DeleteEmployee.class);
        startActivity(intent);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        button.startAnimation(animation);
    }
    public void SearchEmployee(View button) {
        Intent intent = new Intent(EmployeeManagement.this, EmployeeID.class);
        startActivity(intent);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        button.startAnimation(animation);
    }
    public void UpdateEmployee(View button) {
        Intent intent = new Intent(EmployeeManagement.this, UpdateEmployee.class);
        startActivity(intent);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        button.startAnimation(animation);
    }
}