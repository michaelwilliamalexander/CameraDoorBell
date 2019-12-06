package com.example.doorbellcamera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText Email, Pass;
    private static final String TAG = "login";
    private FirebaseAuth mAuth;
    private Button loginButton;
    private TextView signup, login, dontHave;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
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
                    dontHave.setVisibility(View.GONE);
                    login.setVisibility(View.GONE);
                    signup.setVisibility(View.GONE);
                    loginButton.setVisibility(View.GONE);
                    Email.setVisibility(View.GONE);
                    Pass.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(emailInp, passInp)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //if login succeed

                                    if (task.isSuccessful()) {
                                        Email.getText().clear();
                                        if (mAuth.getCurrentUser().isEmailVerified()) {
                                            Log.d(TAG, "Sign In With Email : Success");
                                            Intent mainActivity = new Intent(LoginActivity.this,
                                                    MainActivity.class);
                                            startActivity(mainActivity);
                                            finish();
                                        } else {
                                            Toast.makeText(LoginActivity.this,
                                                    "Please verify your Account ", Toast.LENGTH_SHORT).show();
                                            Pass.getText().clear();
                                            dontHave.setVisibility(View.VISIBLE);
                                            login.setVisibility(View.VISIBLE);
                                            signup.setVisibility(View.VISIBLE);
                                            loginButton.setVisibility(View.VISIBLE);
                                            Email.setVisibility(View.VISIBLE);
                                            Pass.setVisibility(View.VISIBLE);
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    } else {
                                        Toast.makeText(LoginActivity.this,
                                                "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                                        Email.getText().clear();
                                        Pass.getText().clear();
                                        dontHave.setVisibility(View.VISIBLE);
                                        login.setVisibility(View.VISIBLE);
                                        signup.setVisibility(View.VISIBLE);
                                        loginButton.setVisibility(View.VISIBLE);
                                        Email.setVisibility(View.VISIBLE);
                                        Pass.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }
        });

        //when the user click sign up, the code goes here
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(LoginActivity.this, SignUp_Activity.class);
                startActivity(signup);
            }

        });
    }
}
