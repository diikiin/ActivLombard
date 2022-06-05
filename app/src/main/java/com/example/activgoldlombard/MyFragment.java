package com.example.activgoldlombard;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.activgoldlombard.databinding.FragmentMyBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MyFragment extends Fragment {

    private FirebaseAuth auth;
    private FragmentMyBinding binding;

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();

        checkUser();

        binding.signOut.setOnClickListener(view -> {
            auth.signOut();
            replaceFragment(new LoginFragment());
        });

        return binding.getRoot();
    }

    @SuppressLint("ResourceType")
    private void checkUser() {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            replaceFragment(new LoginFragment());
        }
    }

    //Перейти на другой фрагмент
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }
}