package com.example.projectpapb;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TodolistActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;

    TextView subyekTodolist;

    RecyclerView rv2;
    FloatingActionButton addTodolist;
    LinearLayoutManager linearLayoutManager;
    ProgressDialog progress;
    ArrayList<Todolist> todoArrayList;
    TodolistAdapter todolistAdapter;

    String subyek, idSubyek;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailtugas);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        subyek = bundle.getString("subyek");
        idSubyek = bundle.getString("idsubyek");
        subyekTodolist = findViewById(R.id.subyekTodoList);

        subyekTodolist.setText(subyek);

        firebaseAuth =firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        rv2 = findViewById(R.id.rv2);
        addTodolist = findViewById(R.id.addTodolist);
        db = FirebaseFirestore.getInstance();

        todoArrayList = new ArrayList<>();
        rv2.setHasFixedSize(true);
        rv2.setLayoutManager(new LinearLayoutManager(this));

        todolistAdapter = new TodolistAdapter(todoArrayList, this, idSubyek, subyek);

        rv2.setAdapter(todolistAdapter);

        getTodoList();

        addTodolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TodolistActivity.this, AddTugas.class);
                Bundle bundle = new Bundle();
                bundle.putString("idsubyek", idSubyek);
                bundle.putString("subyek", subyek);
                bundle.putString("judul", String.valueOf(""));
                bundle.putString("deadline", String.valueOf(""));
                bundle.putString("status", String.valueOf(""));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void getTodoList(){
        progress=new ProgressDialog(TodolistActivity.this);
        progress.setMessage("Memproses");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        db.collection("user").document(firebaseUser.getEmail()).collection("subyek").document(idSubyek).collection("todolist").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progress.hide();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Todolist todo = document.toObject(Todolist.class);
                                todo.setIdTodo(document.getId());
                                todoArrayList.add(todo);
                                todolistAdapter.notifyDataSetChanged();
                            }
                        } else {
                            progress.hide();
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // if we do not get any data or any error we are displaying
                        // a toast message that we do not get any data
                        progress.hide();
                        Toast.makeText(TodolistActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.subyek_menu, menu);
        return true;
    }

    public void deleteSubyek(){
        db.collection("user").document(firebaseUser.getEmail().toString()).collection("subyek").document(idSubyek)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent1 = new Intent(TodolistActivity.this, MainActivity.class);
                        startActivity(intent1);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TodolistActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent i = new Intent(TodolistActivity.this, MainActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.deletesubyek){
            deleteSubyek();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
