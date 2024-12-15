package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminLoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private final String correctUsername = "Username";
    private final String correctPassword = "Password";
    public void login(View button){
        EditText username = findViewById(R.id.usernameBox1);
        EditText password = findViewById(R.id.passwordBox1);
        String enteredUsername = username.getText().toString();
        String enteredPassword = password.getText().toString();
        if (enteredUsername.equals(correctUsername) && enteredPassword.equals(correctPassword)){
            Intent intent = new Intent(AdminLoginPage.this, AdminMainMenu.class);
            startActivity(intent);
        }
        else{
            TextView textview = findViewById(R.id.LoginError);
            textview.setVisibility(View.VISIBLE);
        }
    }
    public void back(View button){
        Intent intent = new Intent(AdminLoginPage.this, MainActivity.class);
        startActivity(intent);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        button.startAnimation(animation);
    }


}