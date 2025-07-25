package com.example.MovieInABox.activity.main;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.MovieInABox.R;
import com.example.MovieInABox.R.*;
import com.example.MovieInABox.fragment.HomeFragment;
import com.example.MovieInABox.fragment.UserProfileFragment;
import com.example.MovieInABox.fragment.SearchFragment;
import com.example.MovieInABox.fragment.SettingFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    BadgeDrawable badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new HomeFragment());

        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (id == R.id.search) {
                replaceFragment(new SearchFragment());
            } else if (id == R.id.profile) {
                replaceFragment(new UserProfileFragment());
            } else if (id == R.id.setting) {
                replaceFragment(new SettingFragment());
            }
            return true;
        });



    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,fragment);
        fragmentTransaction.commit();
    }
}