package com.example.activgoldlombard.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
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

import java.util.Objects;

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
                String cardTxt = Objects.requireNonNull(binding.cardTxt.getText()).toString().trim();
                String owner = Objects.requireNonNull(binding.ownerTxt.getText()).toString().trim();
                String dateTxt = Objects.requireNonNull(binding.dateTxt.getText()).toString().trim();
                String  email= Objects.requireNonNull(binding.emailTxt.getText()).toString().trim();

                if (cardTxt.isEmpty()) {
                    binding.cardTxt.setError("Пожалуйста введите номер карты");
                    binding.cardTxt.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    binding.emailTxt.setError("Пожалуйста введите электронную почту");
                    binding.emailTxt.requestFocus();
                    return;
                }

                if (owner.isEmpty()) {
                    binding.ownerTxt.setError("Пожалуйста введите данные владельца");
                    binding.ownerTxt.requestFocus();
                    return;
                }

                if (dateTxt.isEmpty()) {
                    binding.dateTxt.setError("Пожалуйста введите дату");
                    binding.dateTxt.requestFocus();
                    return;
                }



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