package com.lspuspcc.tinda;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.lspuspcc.tinda.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.navHostF_main);
        NavigationUI.setupWithNavController(mBinding.navVMain, navController);

        // Prevents Night Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Hides Action Bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        /*TODO
        * Scrolling in Home using Store Recycler View does not detect by Collapsing Toolbar
        **/
    }
}
