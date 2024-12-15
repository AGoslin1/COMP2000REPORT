package com.example.myapplication;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import android.widget.TextView;
import android.widget.EditText;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddEmployee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_employee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void addEmployee() {
        OkHttpClient client = new OkHttpClient();
        String url = "http://10.224.41.11/comp2000/employees";

        EditText firstNameInput = findViewById(R.id.firstNameField);
        EditText lastNameInput = findViewById(R.id.lastNameField);
        EditText emailInput = findViewById(R.id.emailField);
        EditText departmentInput = findViewById(R.id.departmentField);
        EditText salaryInput = findViewById(R.id.salaryField);
        EditText hireDateInput = findViewById(R.id.hireDateField);
        String firstName = firstNameInput.getText().toString().trim();
        String lastName = lastNameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String department = departmentInput.getText().toString().trim();
        String salaryStr = salaryInput.getText().toString().trim();
        String hireDate = hireDateInput.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || department.isEmpty() || salaryStr.isEmpty() || hireDate.isEmpty()) {
            TextView statusTextView = findViewById(R.id.textView28);
            statusTextView.setText("All fields are required!");
            return;
        }
        double salary;
        try {
            salary = Double.parseDouble(salaryStr);
        } catch (NumberFormatException e) {
            TextView statusTextView = findViewById(R.id.textView28);
            statusTextView.setText("Invalid salary format!");
            return;
        }
        if (!hireDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            TextView statusTextView = findViewById(R.id.textView28);
            statusTextView.setText("Invalid hire date format! Use YYYY-MM-DD.");
            return;
        }
        getAllEmployees(firstName, lastName, email, department, salary, hireDate);
    }
    public void getAllEmployees(String firstName, String lastName, String email, String department, double salary, String hireDate) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://10.224.41.11/comp2000/employees";

        Request request = new Request.Builder().url(url).get().build();

        new Thread(() -> {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseData = response.body() != null ? response.body().string() : "";
                    Gson gson = new Gson();
                    Employee[] employees = gson.fromJson(responseData, Employee[].class);
                    int maxID = 0;
                    for (Employee emp : employees) {
                        if (emp.getId() > maxID) {
                            maxID = emp.getId();
                        }
                    }
                    int newId = maxID + 1;
                    Employee newEmployee = new Employee(newId, firstName, lastName, email, department, salary, hireDate, 30);
                    String json = gson.toJson(newEmployee);
                    sendNewEmployeeData(json);
                }
            } catch (Exception e) {
                Log.e("API Error", "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
    public void sendNewEmployeeData(String json) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://10.224.41.11/comp2000/employees/add";

        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder().url(url).post(body).build();

        new Thread(() -> {
            try (Response response = client.newCall(request).execute()) {
                String responseString = response.body() != null ? response.body().string() : "No response from server";
                runOnUiThread(() -> {
                    TextView statusTextView = findViewById(R.id.textView28);
                    if (response.isSuccessful()) {
                        statusTextView.setText("Employee added successfully: " + responseString);
                    } else {
                        statusTextView.setText("Failed to add employee: " + responseString);
                    }
                });
            } catch (Exception e) {
                Log.e("API Error", "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
    public void AddEmployee1(View button){
        addEmployee();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        button.startAnimation(animation);
    }

    public void back(View button){
        Intent intent = new Intent(AddEmployee.this, EmployeeManagement.class);
        startActivity(intent);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        button.startAnimation(animation);
    }
}