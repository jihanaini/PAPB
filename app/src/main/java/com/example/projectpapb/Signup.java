package com.example.projectpapb;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    Button registerBtn;
    EditText email, username, password;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    TextView registerToLogin;
    ProgressDialog progress;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.signup_layout);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseApp.initializeApp(this);
        email = findViewById(R.id.emailRegister);
        username = findViewById(R.id.username);
        password = findViewById(R.id.passwordRegister);
        registerBtn = findViewById(R.id.registerBtn);
        registerToLogin = findViewById(R.id.registerToLogin);

        registerToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(username.getText())){
                    Toast.makeText(Signup.this, "Harap isi semua inputan!", Toast.LENGTH_LONG).show();
                }
                else{
                    progress = new ProgressDialog(Signup.this);
                    progress.setMessage("Memproses");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.show();
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                progress.hide();
                                Map<String, String> user = new HashMap<>();
                                user.put("username", username.getText().toString());
                                db.collection("user").document(email.getText().toString())
                                        .set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(Signup.this, "Berhasil Daftar!", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(Signup.this, Login.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }else{
                                Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
