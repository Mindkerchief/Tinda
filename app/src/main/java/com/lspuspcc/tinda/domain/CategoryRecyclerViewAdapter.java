package com.lspuspcc.tinda.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lspuspcc.tinda.R;

import java.util.ArrayList;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<CategoryModel> mCategoryModels;

    public CategoryRecyclerViewAdapter(Context mContext, ArrayList<CategoryModel> categoryModels) {
        this.mContext = mContext;
        this.mCategoryModels = categoryModels;
    }

    @NonNull
    @Override
    public CategoryRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_sub_category, parent, false);
        return new CategoryRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assign values in the views
        holder.imageVCategoryImage.setImageResource(mCategoryModels.get(position).getmCategoryImage());
        holder.textVCategoryName.setText(mCategoryModels.get(position).getmCategoryName());
    }

    @Override
    public int getItemCount() {
        return mCategoryModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Gets the views from recycler view rows
        ImageView imageVCategoryImage;
        TextView textVCategoryName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageVCategoryImage = itemView.findViewById(R.id.imageV_categoryImage);
            textVCategoryName = itemView.findViewById(R.id.textV_categoryName);
        }
    }
}
