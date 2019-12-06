package com.example.doorbellcamera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp_Activity extends AppCompatActivity {
    private EditText emailSignup, passSignup, passCheck;
    private Button button;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        emailSignup = findViewById(R.id.emailSign);
        passSignup = findViewById(R.id.passSign);
        passCheck = findViewById(R.id.passCheck);
        button = findViewById(R.id.signupButton);
        mAuth =  FirebaseAuth.getInstance();

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
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(SignUp_Activity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignUp_Activity.this, "Please check your email for verification", Toast.LENGTH_SHORT).show();
                                            Intent Login = new Intent(SignUp_Activity.this, LoginActivity.class);
                                            finish();
                                            startActivity(Login);
                                        } else {
                                            Toast.makeText(SignUp_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                            } else
                                Toast.makeText(SignUp_Activity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}
