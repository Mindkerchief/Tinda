package com.lspuspcc.tinda.ui.deal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lspuspcc.tinda.R;
import com.lspuspcc.tinda.databinding.ActivityDealBinding;

public class DealActivity extends AppCompatActivity {
    private ActivityDealBinding mDealBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDealBinding = ActivityDealBinding.inflate(getLayoutInflater());
        setContentView(mDealBinding.getRoot());
    }
}