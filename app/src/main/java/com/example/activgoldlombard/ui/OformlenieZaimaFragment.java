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
import com.example.activgoldlombard.databinding.FragmentOformlenieZaimaBinding;
import com.example.activgoldlombard.ui.reqHouseFragment;

public class OformlenieZaimaFragment extends Fragment {

    private FragmentOformlenieZaimaBinding binding;

    public OformlenieZaimaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOformlenieZaimaBinding.inflate(inflater,container,false);

        binding.zayavkaBtn.setOnClickListener(view -> replaceFragment(new reqHouseFragment()));

        return binding.getRoot();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }
}