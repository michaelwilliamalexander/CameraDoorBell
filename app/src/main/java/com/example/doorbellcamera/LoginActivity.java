package com.example.doorbellcamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private EditText Email, Pass;
    private static final String TAG = "login";
    Button loginButton;
    TextView signup, login, dontHave;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.Email);
        Pass = findViewById(R.id.Password);
        loginButton = findViewById(R.id.LoginButton);
        signup = findViewById(R.id.SignUp);
        dontHave = findViewById(R.id.dontHave);
        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailInp = Email.getText().toString();
                final String passInp = Pass.getText().toString();
                //if the field is empty
                if (emailInp.isEmpty()) {
                    Email.setError("Please Enter Your Email");
                    Email.requestFocus();
                } else if (passInp.isEmpty()) {
                    Pass.setError("Please Enter Your Password");
                    Pass.requestFocus();
                }else{
                    Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainActivity);
                    finish();
                }
            }
        });
    }
}
