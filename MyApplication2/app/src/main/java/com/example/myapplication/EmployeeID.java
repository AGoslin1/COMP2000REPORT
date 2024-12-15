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

public class EmployeeID extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_id);

        EditText employeeIdInput = findViewById(R.id.employeeIdInput);
        Button fetchEmployeeButton = findViewById(R.id.fetchEmployeeButton);
        TextView employeeDetailsText = findViewById(R.id.employeeDetailsText);

        fetchEmployeeButton.setOnClickListener(view -> {
            String employeeId = employeeIdInput.getText().toString().trim();

            if (employeeId.isEmpty()) {
                employeeDetailsText.setText("Employee ID is required.");
            } else {
                fetchEmployeeById(employeeId, employeeDetailsText);
            }
        });
    }

    private void fetchEmployeeById(String employeeId, TextView employeeDetailsText) {

        OkHttpClient client = new OkHttpClient();
        String url = "http://10.224.41.11/comp2000/employees/get/" + employeeId;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        new Thread(() -> {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    Gson gson = new Gson();
                    Employee employee = gson.fromJson(responseData, Employee.class);

                    runOnUiThread(() -> {
                        String employeeInfo = "ID: " + employee.getId() + "\n" +
                                "Name: " + employee.getFirstname() + " " + employee.getLastname() + "\n" +
                                "Email: " + employee.getEmail() + "\n" +
                                "Department: " + employee.getDepartment() + "\n" +
                                "Salary: " + employee.getSalary() + "\n" +
                                "Hire Date: " + employee.getHireDate() + "\n" +
                                "Leaves: " + employee.getLeaves();
                        employeeDetailsText.setText(employeeInfo);
                    });
                } else {
                    runOnUiThread(() -> employeeDetailsText.setText("Employee not found or error fetching data."));
                }
            } catch (Exception e) {
                runOnUiThread(() -> employeeDetailsText.setText("Error: " + e.getMessage()));
                e.printStackTrace();
            }
        }).start();
    }
    public void back(View button){
        Intent intent = new Intent(EmployeeID.this, EmployeeManagement.class);
        startActivity(intent);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        button.startAnimation(animation);
    }
}
