package com.example.myapplication;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        TextView notificationText = findViewById(R.id.notificationText);
        String statusMessage = getIntent().getStringExtra("statusMessage");
        notificationText.setText(statusMessage);
    }
}
