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

public class SubCategoryRecyclerViewAdapter extends RecyclerView.Adapter<SubCategoryRecyclerViewAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<SubCategoryModel> mSubCategoryModels;

    public SubCategoryRecyclerViewAdapter(Context mContext, ArrayList<SubCategoryModel> subCategoryModels) {
        this.mContext = mContext;
        this.mSubCategoryModels = subCategoryModels;
    }

    @NonNull
    @Override
    public SubCategoryRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_sub_category, parent, false);
        return new SubCategoryRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryRecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assign values in the views
        holder.imageVCategoryImage.setImageResource(mSubCategoryModels.get(position).getSubCategoryImage());
        holder.textVCategoryName.setText(mSubCategoryModels.get(position).getSubCategoryName());
    }

    @Override
    public int getItemCount() {
        return mSubCategoryModels.size();
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

    public void updateCategoryModel(ArrayList<SubCategoryModel> subCategoryModels) {
        this.mSubCategoryModels = subCategoryModels;
    }
}
