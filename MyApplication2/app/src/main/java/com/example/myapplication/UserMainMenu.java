package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserMainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_main_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void back(View button){
        Intent intent = new Intent(UserMainMenu.this, UserLoginPage.class);
        startActivity(intent);
    }
    public void Profile(View button){
        Intent intent = new Intent(UserMainMenu.this, Profile.class);
        startActivity(intent);
    }
    public void HolidayRequests(View button){
        Intent intent = new Intent(UserMainMenu.this, HolidayRequests.class);
        startActivity(intent);
    }
    public void Notifications(View button){
        Intent intent = new Intent(UserMainMenu.this, Notifications.class);
        startActivity(intent);
    }

}

