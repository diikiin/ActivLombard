package com.example.activgoldlombard.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.activgoldlombard.R;
import com.example.activgoldlombard.databinding.ActivityMainBinding;
import com.example.activgoldlombard.ui.auth.LoginFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new MainFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.mainFragment:
                    replaceFragment(new MainFragment());
                    break;
                case R.id.mapFragment:
                    replaceFragment(new MapFragment());
                    break;
                case R.id.myFragment:
                    replaceFragment(new MyFragment());
                    break;
                case R.id.helpFragment:
                    replaceFragment(new HelpFragment());
                    break;
                case R.id.othersFragment:
                    replaceFragment(new OthersFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }
}