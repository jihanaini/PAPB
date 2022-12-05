package com.example.projectpapb;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AddTugas extends AppCompatActivity {
    EditText juduls, deadlines;
    ImageButton addTodoBtn, deleteBtn, doneBtn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtugas);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String judul = bundle.getString("judul");
        String deadline = bundle.getString("deadline");
        String status = bundle.getString("status");

        juduls = findViewById(R.id.judulAdd);
        deadlines = findViewById(R.id.deadlineAdd);

        juduls.setText(judul);
        deadlines.setText(deadline);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
