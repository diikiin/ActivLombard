package com.example.activgoldlombard.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.activgoldlombard.R;
import com.example.activgoldlombard.databinding.FragmentOthersBinding;


public class OthersFragment extends Fragment {
    private FragmentOthersBinding binding;

    public OthersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOthersBinding.inflate(getLayoutInflater());

        binding.pravila.setOnClickListener(v -> {
            RulesFragment rulesFragment = new RulesFragment();
            replaceFragment(rulesFragment);
        });

        binding.uslovia.setOnClickListener(v -> {
            BenefitsFragment benefitsFragment = new BenefitsFragment();
            replaceFragment(benefitsFragment);
        });

        binding.zaim.setOnClickListener(v -> {
            OformlenieZaimaFragment oformlenieZaimaFragment = new OformlenieZaimaFragment();
            replaceFragment(oformlenieZaimaFragment);
        });

        return binding.getRoot();

    }

    //Перейти на другой фрагмент
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }

}