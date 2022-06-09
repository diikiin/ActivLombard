package com.example.activgoldlombard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.activgoldlombard.databinding.FragmentOthersBinding;

public class OthersFragment extends Fragment {

    private FragmentOthersBinding binding;

    public OthersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOthersBinding.inflate(inflater,container,false);

        binding.zaim.setOnClickListener(view -> replaceFragment(new OformlenieZaimaFragment()));

        binding.uslovia.setOnClickListener(view -> replaceFragment(new BenefitsFragment()));

        binding.pravila.setOnClickListener(view -> replaceFragment(new RulesFragment()));

        return binding.getRoot();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }
}