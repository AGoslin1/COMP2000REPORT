package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminMainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_main_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void back(View button){
        Intent intent = new Intent(AdminMainMenu.this, AdminLoginPage.class);
        startActivity(intent);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        button.startAnimation(animation);
    }
    public void EmployeeManagement(View button){
        Intent intent = new Intent(AdminMainMenu.this, EmployeeManagement.class);
        startActivity(intent);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        button.startAnimation(animation);
    }
    public void HolidaySubmissions(View button){
        Intent intent = new Intent(AdminMainMenu.this, HolidaySubmissions.class);
        startActivity(intent);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        button.startAnimation(animation);
    }
}