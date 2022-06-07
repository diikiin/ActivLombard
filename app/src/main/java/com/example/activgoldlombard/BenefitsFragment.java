package com.example.activgoldlombard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.activgoldlombard.databinding.FragmentBenefitsBinding;

public class BenefitsFragment extends Fragment {

    private FragmentBenefitsBinding binding;

    public BenefitsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentBenefitsBinding.inflate(inflater,container,false);

        binding.backImg.setOnClickListener(view -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerView, new OthersFragment());
            fragmentTransaction.commit();
        });

        return inflater.inflate(R.layout.fragment_benefits, container, false);
    }
}