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

public class SubyekAdapter extends RecyclerView.Adapter<SubyekAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<Subyek> subyekArrayList;
    private Context context;

    // creating constructor for our adapter class
    public SubyekAdapter(ArrayList<Subyek> subyekArrayList, Context context) {
        this.subyekArrayList = subyekArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SubyekAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.rowtugas, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubyekAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        Subyek subyek = subyekArrayList.get(position);
        holder.subyekTV.setText(subyek.getSubyek());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return subyekArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // creating variables for our text views.
        private final TextView subyekTV;
        Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            subyekTV = itemView.findViewById(R.id.subyek);
            context = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, TodolistActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("subyek", String.valueOf(subyekTV.getText()));
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
}