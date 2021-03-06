package com.example.activgoldlombard.ui;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.example.activgoldlombard.R;
import com.example.activgoldlombard.databinding.FragmentMyBinding;
import com.example.activgoldlombard.model.PiedgeTicket;
import com.example.activgoldlombard.model.SampleType;
import com.example.activgoldlombard.ui.auth.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyFragment extends Fragment {

    private FirebaseAuth auth;
    private FragmentMyBinding binding;
    private RecyclerView recyclerView;

    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<PiedgeTicket> list;

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();

        if (checkUser()){
            recyclerView = binding.recyclerView;
            database = FirebaseDatabase.getInstance().getReference("PiedgeTicket")
                    .child(Objects.requireNonNull(auth.getCurrentUser()).getUid());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            list = new ArrayList<>();

            database.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        PiedgeTicket piedgeTicket = new PiedgeTicket();
                        if (ds.child("amountForHand").getValue(Long.class) != null) {
                            piedgeTicket.setId(ds.getKey());
                            piedgeTicket.setAmountForHand(ds.child("amountForHand").getValue(Long.class));
                            piedgeTicket.setCredit(ds.child("credit").getValue(Double.class));
                            piedgeTicket.setDate(ds.child("date").getValue(String.class));
                            piedgeTicket.setSampleType(ds.child("sampleType").getValue(SampleType.class));

                            Log.d(TAG, "showData: id: " + piedgeTicket.getId());
                            Log.d(TAG, "showData: amount: " + piedgeTicket.getAmountForHand());
                            Log.d(TAG, "showData: credit: " + piedgeTicket.getCredit());
                            Log.d(TAG, "showData: date: " + piedgeTicket.getDate());
                            Log.d(TAG, "showData: sample: " + piedgeTicket.getSampleType());

                            list.add(piedgeTicket);
                        }
                    }
                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            myAdapter = new MyAdapter(container.getContext(), list);
            recyclerView.setAdapter(myAdapter);
        }
        else {
            replaceFragment(new LoginFragment());
        }

        binding.signOut.setOnClickListener(view -> {
            auth.signOut();
            replaceFragment(new LoginFragment());
        });

        search();

        return binding.getRoot();
    }

    @SuppressLint("ResourceType")
    private boolean checkUser() {
        FirebaseUser user = auth.getCurrentUser();
        return user != null;
    }

    //?????????????? ???? ???????????? ????????????????
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }

    private void search(){
        binding.searchView.clearFocus();
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String text) {
        List<PiedgeTicket> filteredList = new ArrayList<>();
        for (PiedgeTicket item : list) {
            if (String.valueOf(item.getId()).contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        } else {
            myAdapter.setFilteredList(filteredList);
        }

    }
}