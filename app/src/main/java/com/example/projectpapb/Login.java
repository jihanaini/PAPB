package com.example.projectpapb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    ImageButton loginBtn;
    EditText email, password;
    TextView loginToRegister;

    FirebaseAuth firebaseAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginToRegister = findViewById(R.id.loginToRegister);
        firebaseAuth = firebaseAuth.getInstance();
        loginBtn = findViewById(R.id.loginBtn);

        loginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Login.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}
