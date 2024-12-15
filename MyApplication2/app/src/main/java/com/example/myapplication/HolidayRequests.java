package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class HolidayRequests extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday_requests);
        EditText startDateInput = findViewById(R.id.startDateInput);
        EditText numberOfDaysInput = findViewById(R.id.numberOfDaysInput);
        EditText reasonInput = findViewById(R.id.reasonInput);
        Button submitButton = findViewById(R.id.submitHolidayButton);
        TextView statusText = findViewById(R.id.statusText);
        TextView errorText = findViewById(R.id.SubmitError);
        submitButton.setOnClickListener(view -> {
            String startDate = startDateInput.getText().toString().trim();
            String numberOfDaysStr = numberOfDaysInput.getText().toString().trim();
            String reason = reasonInput.getText().toString().trim();
            if (startDate.isEmpty() || numberOfDaysStr.isEmpty() || reason.isEmpty()) {
                errorText.setVisibility(View.VISIBLE);
                return;
            }
            int numberOfDays;
            try {
                numberOfDays = Integer.parseInt(numberOfDaysStr);
            } catch (NumberFormatException e) {
                errorText.setVisibility(View.VISIBLE);
                return;
            }
            if (numberOfDays > 30) {
                errorText.setText("Holiday request cannot exceed 30 days.");
                errorText.setVisibility(View.VISIBLE);
                return;
            }
            Holidays holidayRequest = new Holidays(startDate, numberOfDays, reason);
            statusText.setText("Holiday request submitted successfully!");
            errorText.setVisibility(View.GONE);
            Toast.makeText(HolidayRequests.this, "Holiday Request Submitted", Toast.LENGTH_SHORT).show();
        });
    }

    public void back(View button) {
        onBackPressed();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        button.startAnimation(animation);
    }
}
