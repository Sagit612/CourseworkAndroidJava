package com.example.coursework.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.DatePicker;

import com.example.coursework.R;
import com.example.coursework.database.AppDatabase;
import com.example.coursework.fragments.hike.AddFragment;
import com.example.coursework.fragments.HomeFragment;
import com.example.coursework.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private AppDatabase appDatabase;
    BottomNavigationView bottomNavigationView;
//    ActivityMainBinding binding;

//    EditText nameText, locationText, dothText, lothText, dlText, descriptionText;
//    MaterialButton buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "courework")
//                .allowMainThreadQueries()
//                .build();
        int homeFragId = R.id.homeFragment;
        int addFragId = R.id.addFragment;
        int searchFragId = R.id.searchFragment;
        displayFragment(new HomeFragment());
        bottomNavigationView = findViewById(R.id.btNav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == addFragId){
                displayFragment(new AddFragment());
            } else if (item.getItemId() == homeFragId) {
                displayFragment(new HomeFragment());
            } else if (item.getItemId() == searchFragId) {
                displayFragment(new SearchFragment());
            }
            return true;
        });
    }
    public void displayFragment(@Nullable Fragment fragment){
        if (fragment == null) {
            bottomNavigationView.setSelectedItemId(R.id.homeFragment);
        } else {
            FragmentManager  fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flFragment, fragment);
            fragmentTransaction.commit();
        }
    }

}