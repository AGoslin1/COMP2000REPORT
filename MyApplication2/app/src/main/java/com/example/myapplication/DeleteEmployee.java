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

public class DeleteEmployee extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employee);

        EditText employeeIdField = findViewById(R.id.employeeIdField);
        Button deleteEmployeeButton = findViewById(R.id.deleteEmployeeButton);
        TextView statusTextView = findViewById(R.id.statusTextView);

        deleteEmployeeButton.setOnClickListener(view -> {


            String employeeId = employeeIdField.getText().toString().trim();

            if (employeeId.isEmpty()) {
                statusTextView.setText("Employee ID is required.");
            } else {
                deleteEmployeeById(employeeId, statusTextView);
            }

        });
    }

    private void deleteEmployeeById(String employeeId, TextView statusTextView) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://10.224.41.11/comp2000/employees/delete/" + employeeId;

        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        new Thread(() -> {
            try (Response response = client.newCall(request).execute()) {
                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        statusTextView.setText("Employee deleted successfully.");
                    } else {
                        statusTextView.setText("Failed to delete employee. Error code: " + response.code());
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> statusTextView.setText("Error: " + e.getMessage()));
                e.printStackTrace();
            }
        }).start();
    }
    public void back(View button){
        Intent intent = new Intent(DeleteEmployee.this, EmployeeManagement.class);
        startActivity(intent);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        button.startAnimation(animation);
    }
}
