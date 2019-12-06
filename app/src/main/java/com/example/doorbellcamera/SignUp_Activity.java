package com.example.doorbellcamera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp_Activity extends AppCompatActivity {
    public EditText emailSignup, passSignup, passCheck;
    public Button button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        emailSignup = findViewById(R.id.emailSign);
        passSignup = findViewById(R.id.passSign);
        passCheck = findViewById(R.id.passCheck);
        button = findViewById(R.id.signupButton);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailSignup.getText().toString();
                String pass = passSignup.getText().toString();
                String repass = passCheck.getText().toString();

                //cek field kosong
                if (email.isEmpty()) {
                    emailSignup.setError("Please Enter Your Email");
                    emailSignup.requestFocus();
                } else if (pass.isEmpty()) {
                    passSignup.setError("Please Create Your Password");
                    passSignup.requestFocus();
                } else if (repass.isEmpty()) {
                    passCheck.setError("Please re-enter Your Password");
                    passCheck.requestFocus();
                }

                //jika reenter pass tidak sesuai dengan pass
                else if (!pass.equals(repass)) {
                    Toast.makeText(SignUp_Activity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    emailSignup.getText().clear();
                    passSignup.getText().clear();
                    passCheck.getText().clear();
                } else if (!(pass.isEmpty() && email.isEmpty()) && pass.equals(repass)) {
                    Toast.makeText(SignUp_Activity.this, "Please check your email for verification", Toast.LENGTH_SHORT).show();
                    Intent Login = new Intent(SignUp_Activity.this, LoginActivity.class);
                    finish();
                    startActivity(Login);
                }

            }
        });
    }
}
