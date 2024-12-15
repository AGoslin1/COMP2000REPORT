package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;
import com.google.gson.Gson;

public class UpdateEmployee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);

        EditText employeeIdInput = findViewById(R.id.updateEmployeeIdInput);
        EditText firstNameInput = findViewById(R.id.updateFirstNameInput);
        EditText lastNameInput = findViewById(R.id.updateLastNameInput);
        EditText emailInput = findViewById(R.id.updateEmailInput);
        EditText departmentInput = findViewById(R.id.updateDepartmentInput);
        EditText salaryInput = findViewById(R.id.updateSalaryInput);
        EditText hireDateInput = findViewById(R.id.updateHireDateInput);
        EditText leavesInput = findViewById(R.id.updateLeavesInput);
        Button updateButton = findViewById(R.id.updateEmployeeButton);
        TextView statusText = findViewById(R.id.updateStatusText);

        updateButton.setOnClickListener(view -> {
            int employeeId = Integer.parseInt(employeeIdInput.getText().toString().trim());
            String firstName = firstNameInput.getText().toString().trim();
            String lastName = lastNameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String department = departmentInput.getText().toString().trim();
            String salaryStr = salaryInput.getText().toString().trim();
            String hireDate = hireDateInput.getText().toString().trim();
            String leavesStr = leavesInput.getText().toString().trim();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || department.isEmpty() || salaryStr.isEmpty() || hireDate.isEmpty() || leavesStr.isEmpty()) {
                statusText.setText("All fields are required!");
                return;
            }
            double salary;
            int leaves;
            try {
                salary = Double.parseDouble(salaryStr);
                leaves = Integer.parseInt(leavesStr);
            } catch (NumberFormatException e) {
                statusText.setText("Invalid salary or leaves format!");
                return;
            }
            if (!hireDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                statusText.setText("Invalid hire date format! Use YYYY-MM-DD.");
                return;
            }
            Employee updatedEmployee = new Employee(
                    employeeId,
                    firstName,
                    lastName,
                    email,
                    department,
                    salary,
                    hireDate,
                    leaves
            );
            Gson gson = new Gson();
            String json = gson.toJson(updatedEmployee);
            OkHttpClient client = new OkHttpClient();
            String url = "http://10.224.41.11/comp2000/employees/edit/" + employeeId;

            RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(url)
                    .put(body)
                    .build();
            new Thread(() -> {
                try (Response response = client.newCall(request).execute()) {
                    runOnUiThread(() -> {
                        if (response.isSuccessful()) {
                            statusText.setText("Employee updated successfully!");
                        } else {
                            statusText.setText("Failed to update employee: " + response.code());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> statusText.setText("Error updating employee. Please try again."));
                }
            }).start();
        });
    }
    public void back(View button){
        Intent intent = new Intent(UpdateEmployee.this, EmployeeManagement.class);
        startActivity(intent);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        button.startAnimation(animation);
    }
}
