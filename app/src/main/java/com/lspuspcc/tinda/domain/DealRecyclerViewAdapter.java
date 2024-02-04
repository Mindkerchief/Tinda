package com.lspuspcc.tinda.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lspuspcc.tinda.R;

import java.util.ArrayList;
import java.util.Locale;

public class DealRecyclerViewAdapter extends RecyclerView.Adapter<DealRecyclerViewAdapter.MyViewHolder> {
    private final Context mContext;
    private ArrayList<DealModel> mDealModels;

    public DealRecyclerViewAdapter(Context context, ArrayList<DealModel> dealModels) {
        this.mContext = context;
        this.mDealModels = dealModels;
    }

    @NonNull
    @Override
    public DealRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.card_deal, parent, false);
        return new DealRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.imageVDealImage.setImageResource(mDealModels.get(position).getDealImage());
        holder.textVDealProduct.setText(mDealModels.get(position).getDealProduct());
        holder.textVDealAddress.setText(mDealModels.get(position).getDealAddress());

        String dealPrice = "P" + String.format(Locale.ENGLISH, "%.2f", mDealModels.get(position).getDealPrice());
        holder.textVDealPrice.setText(dealPrice);

        holder.btnDealMessage.setOnClickListener(v -> {
            // TODO: Provide Message Functionality Here
        });
    }

    @Override
    public int getItemCount() {
        return mDealModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageVDealImage;
        TextView textVDealProduct, textVDealAddress, textVDealPrice;
        Button btnDealMessage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageVDealImage = itemView.findViewById(R.id.imageV_dealImage);
            textVDealProduct = itemView.findViewById(R.id.textV_dealProduct);
            textVDealAddress = itemView.findViewById(R.id.textV_dealAddress);
            textVDealPrice = itemView.findViewById(R.id.textV_dealPrice);
            btnDealMessage = itemView.findViewById(R.id.btn_dealMessage);
        }
    }

    public void updateDealModel(ArrayList<DealModel> dealModels) {
        this.mDealModels = dealModels;
    }
}
