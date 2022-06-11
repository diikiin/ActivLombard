package com.example.activgoldlombard.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.activgoldlombard.R;
import com.example.activgoldlombard.databinding.FragmentHelpBinding;
import com.example.activgoldlombard.ui.LicenseFragment;
import com.example.activgoldlombard.ui.RulesFragment;

public class HelpFragment extends Fragment {

    private FragmentHelpBinding binding;

    public HelpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHelpBinding.inflate(inflater, container, false);

        binding.imageView6.setOnClickListener(view -> replaceFragment(new RulesFragment()));

        binding.imageView8.setOnClickListener(view -> replaceFragment(new LicenseFragment()));

        return binding.getRoot();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }
}