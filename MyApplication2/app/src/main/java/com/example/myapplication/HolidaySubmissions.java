package com.example.myapplication;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import java.util.List;
import android.app.Notification;
import android.app.NotificationChannel;

public class HolidaySubmissions extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday_submissions);
        ListView holidayListView = findViewById(R.id.holidayListView);
        List<Holidays> holidayRequests = new ArrayList<>();
        holidayRequests.add(new Holidays("2024-12-20", 5, "Vacation"));
        holidayRequests.add(new Holidays("2024-11-15", 3, "Medical Leave"));
        ArrayAdapter<Holidays> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_2, android.R.id.text1, holidayRequests);
        holidayListView.setAdapter(adapter);
        holidayListView.setOnItemClickListener((parent, view, position, id) -> {
            Holidays selectedHoliday = holidayRequests.get(position);
            showActionDialog(selectedHoliday);
        });
    }
    private void showActionDialog(Holidays holidayRequest) {
        new AlertDialog.Builder(this)
                .setTitle("Holiday Request")
                .setMessage("Accept or Decline the request?")
                .setPositiveButton("Accept", (dialog, which) -> {
                    sendNotification("Your holiday request for " + holidayRequest.getStartDate() + " has been accepted.");
                    updateNotification("Holiday Accepted for " + holidayRequest.getStartDate());
                })
                .setNegativeButton("Decline", (dialog, which) -> {
                    sendNotification("Your holiday request for " + holidayRequest.getStartDate() + " has been declined.");
                    updateNotification("Holiday Declined for " + holidayRequest.getStartDate());
                })
                .show();
    }
    private void sendNotification(String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("holiday_channel", "Holiday Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        Notification notification = new NotificationCompat.Builder(this, "holiday_channel")
                .setContentTitle("Holiday Request Status")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notification)
                .build();
        notificationManager.notify(1, notification);
    }
    private void updateNotification(String statusMessage) {
        Intent intent = new Intent(this, NotificationsActivity.class);
        intent.putExtra("statusMessage", statusMessage);
        startActivity(intent);
    }
}
