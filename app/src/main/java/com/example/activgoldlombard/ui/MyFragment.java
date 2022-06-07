package com.example.activgoldlombard.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.activgoldlombard.R;
import com.example.activgoldlombard.databinding.FragmentMyBinding;
import com.example.activgoldlombard.model.PiedgeTicket;
import com.example.activgoldlombard.ui.auth.LoginFragment;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyFragment extends Fragment {

    private FirebaseAuth auth;
    private FragmentMyBinding binding;
    private RecyclerView recyclerView;
    PiedgeAdapter adapter;
    DatabaseReference mbase;
    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);
//        binding = FragmentMyBinding.inflate(inflater, container, false);

//        auth = FirebaseAuth.getInstance();
//
//        checkUser();
//
//        binding.signOut.setOnClickListener(view -> {
//            auth.signOut();
//            replaceFragment(new LoginFragment());
//        });
        mbase = FirebaseDatabase.getInstance().getReference().child("PiedgeTicket");

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(
                new LinearLayoutManager(container.getContext()));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<PiedgeTicket> options
                = new FirebaseRecyclerOptions.Builder<PiedgeTicket>()
                .setQuery(mbase, PiedgeTicket.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new PiedgeAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);
        return rootView;
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