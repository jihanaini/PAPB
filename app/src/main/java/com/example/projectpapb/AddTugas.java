package com.example.projectpapb;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddTugas extends AppCompatActivity {
    EditText juduls, deadlines;
    ImageButton addTodoBtn, deleteBtn, doneBtn;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;

    String idsubyek, subyek, idtodo;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtugas);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        db = FirebaseFirestore.getInstance();
        firebaseAuth =firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idsubyek = bundle.getString("idsubyek");
        subyek = bundle.getString("subyek");
        idtodo = bundle.getString("idtodo");
        String judul = bundle.getString("judul");
        String deadline = bundle.getString("deadline");
        String status = bundle.getString("status");

        juduls = findViewById(R.id.judulAdd);
        deadlines = findViewById(R.id.deadlineAdd);
        addTodoBtn = findViewById(R.id.addtodobtn);
        deleteBtn = findViewById(R.id.deletebtn);
        doneBtn = findViewById(R.id.donebtn);

        juduls.setText(judul);
        deadlines.setText(deadline);

        addTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(TextUtils.isEmpty(juduls.getText()) || TextUtils.isEmpty(deadlines.getText())){
                   Toast.makeText(AddTugas.this, "Harap isi semua inputan!", Toast.LENGTH_LONG).show();
               }else{
                   if(idtodo == null){
                       Map<String, String> newTodo = new HashMap<>();
                       newTodo.put("judul", juduls.getText().toString());
                       newTodo.put("deadline", deadlines.getText().toString());
                       newTodo.put("status", "Progress");
                       Log.d(TAG, "onClick: "+idsubyek);
                       Log.d(TAG, "onClick: "+idtodo);
                       db.collection("user").document(firebaseUser.getEmail().toString()).collection("subyek").document(idsubyek).collection("todolist")
                               .add(newTodo)
                               .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                   @Override
                                   public void onSuccess(DocumentReference documentReference) {
                                       Intent intent1 = new Intent(AddTugas.this, TodolistActivity.class);
                                       Bundle bundle = new Bundle();
                                       bundle.putString("idsubyek", String.valueOf(idsubyek));
                                       bundle.putString("subyek", String.valueOf(subyek));
                                       intent1.putExtras(bundle);
                                       startActivity(intent1);
                                   }
                               })
                               .addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                       Toast.makeText(AddTugas.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                   }
                               });
                   }else{
                       Map<String, String> newTodo = new HashMap<>();
                       newTodo.put("judul", juduls.getText().toString());
                       newTodo.put("deadline", deadlines.getText().toString());
                       newTodo.put("status", status);
                       db.collection("user").document(firebaseUser.getEmail().toString()).collection("subyek").document(idsubyek).collection("todolist").document(idtodo)
                               .set(newTodo)
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       Intent intent1 = new Intent(AddTugas.this, TodolistActivity.class);
                                       Bundle bundle = new Bundle();
                                       bundle.putString("idsubyek", String.valueOf(idsubyek));
                                       bundle.putString("subyek", String.valueOf(subyek));
                                       intent1.putExtras(bundle);
                                       startActivity(intent1);
                                   }
                               })
                               .addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                       Toast.makeText(AddTugas.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                   }
                               });
                   }
               }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idtodo != null){
                    db.collection("user").document(firebaseUser.getEmail().toString()).collection("subyek").document(idsubyek).collection("todolist").document(idtodo)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent1 = new Intent(AddTugas.this, TodolistActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("idsubyek", String.valueOf(idsubyek));
                                    bundle.putString("subyek", String.valueOf(subyek));
                                    intent1.putExtras(bundle);
                                    startActivity(intent1);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddTugas.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idtodo !=null){
                    db.collection("user").document(firebaseUser.getEmail().toString()).collection("subyek").document(idsubyek).collection("todolist").document(idtodo)
                            .update("status", "Done")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent1 = new Intent(AddTugas.this, TodolistActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("idsubyek", String.valueOf(idsubyek));
                                    bundle.putString("subyek", String.valueOf(subyek));
                                    intent1.putExtras(bundle);
                                    startActivity(intent1);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddTugas.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent1 = new Intent(AddTugas.this, TodolistActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("idsubyek", String.valueOf(idsubyek));
                bundle.putString("subyek", String.valueOf(subyek));
                intent1.putExtras(bundle);
                startActivity(intent1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
