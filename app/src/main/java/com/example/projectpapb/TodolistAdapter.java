package com.example.projectpapb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodolistAdapter extends RecyclerView.Adapter<TodolistAdapter.ViewHolder>{
    // creating variables for our ArrayList and context
    private ArrayList<Todolist> todolistArrayList;
    private Context context;

    // creating constructor for our adapter class
    public TodolistAdapter(ArrayList<Todolist> todolistArrayList, Context context) {
        this.todolistArrayList = todolistArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public TodolistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new TodolistAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.rowdetail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodolistAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        Todolist todolist = todolistArrayList.get(position);
        holder.judulTV.setText(todolist.getJudul());
        holder.deadlineTV.setText(todolist.getDeadline());
        holder.isDoneTV.setText(todolist.isDone() ? "Done" : "Not Done");
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return todolistArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // creating variables for our text views.
        private final TextView judulTV;
        private final TextView deadlineTV;
        private final TextView isDoneTV;
        Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            judulTV = itemView.findViewById(R.id.judul);
            deadlineTV = itemView.findViewById(R.id.deadline);
            isDoneTV = itemView.findViewById(R.id.isdone);
            context = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, TodolistActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("judul", String.valueOf(judulTV.getText()));
            bundle.putString("deadline", String.valueOf(deadlineTV.getText()));
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
}
