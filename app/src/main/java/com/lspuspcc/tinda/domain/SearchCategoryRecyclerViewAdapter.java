package com.lspuspcc.tinda.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.lspuspcc.tinda.R;

import java.util.ArrayList;

public class SearchCategoryRecyclerViewAdapter extends RecyclerView.Adapter<SearchCategoryRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<SearchCategoryModel> mSearchCategoryModels;

    public SearchCategoryRecyclerViewAdapter(Context context, ArrayList<SearchCategoryModel> searchCategoryModels) {
        this.mContext = context;
        this.mSearchCategoryModels = searchCategoryModels;
    }

    @NonNull
    @Override
    public SearchCategoryRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.button_category, parent, false);
        return new SearchCategoryRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCategoryRecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assign values in the views
        holder.btnSearchCategoryList.setIcon(AppCompatResources.getDrawable(mContext, R.drawable.ic_basket));
        holder.btnSearchCategoryList.setText(mSearchCategoryModels.get(position).getCategoryName());
        holder.btnSearchCategoryList.setTag(mSearchCategoryModels.get(position).getCategoryIndex());
    }

    @Override
    public int getItemCount() {
        return mSearchCategoryModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialButton btnSearchCategoryList;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btnSearchCategoryList = itemView.findViewById(R.id.btn_searchCategory);
        }
    }
}
