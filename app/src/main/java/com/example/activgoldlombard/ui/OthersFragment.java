package com.example.activgoldlombard.ui;

import android.os.Bundle;

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
FragmentOthersBinding binding;

    public OthersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOthersBinding.inflate(getLayoutInflater());
        Button btnPr = (Button) binding.pravila;
        Button btnUslov = (Button) binding.uslovia;
        Button btnZaim = (Button) binding.zaim;
        btnPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RulesFragment rulesFragment = new RulesFragment();
                replaceFragment(rulesFragment);
            }
        });
        btnUslov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BenefitsFragment benefitsFragment = new BenefitsFragment();
                replaceFragment(benefitsFragment);
            }
        });
        btnZaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OformlenieZaimaFragment oformlenieZaimaFragment = new OformlenieZaimaFragment();
                replaceFragment(oformlenieZaimaFragment);
            }
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