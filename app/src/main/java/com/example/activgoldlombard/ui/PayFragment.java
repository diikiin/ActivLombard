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
import com.example.activgoldlombard.databinding.FragmentPayBinding;
import com.example.activgoldlombard.model.PiedgeTicket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PayFragment extends Fragment {

    private FragmentPayBinding binding;

    private FirebaseAuth auth;
    private DatabaseReference db;
    private PiedgeTicket piedgeTicket;
    private int sum;

    public PayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPayBinding.inflate(inflater, container, false);
        Bundle bundle = this.getArguments();
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        Button button = binding.button2;
        binding.sumTxt.setText(bundle.get("percent").toString());
        button.setOnClickListener(view -> {
            FragmentManager fragmentManager = getFragmentManager();
            assert fragmentManager != null;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            PaidFragment paidFragment =new PaidFragment();
            paidFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragmentContainerView, paidFragment);
            fragmentTransaction.commit();

        });
        return binding.getRoot();
    }
}