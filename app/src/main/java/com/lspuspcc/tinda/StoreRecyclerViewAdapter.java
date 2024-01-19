package com.lspuspcc.tinda;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoreRecyclerViewAdapter extends RecyclerView.Adapter<StoreRecyclerViewAdapter.MyViewHolder> {

    @NonNull
    @Override
    public StoreRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreRecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assign values in the views
    }

    @Override
    public int getItemCount() {
        // counts number of items to be displayed
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // gets the views from recycler view rows
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
