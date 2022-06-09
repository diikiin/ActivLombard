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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.example.activgoldlombard.R;
import com.example.activgoldlombard.model.PiedgeTicket;
import com.example.activgoldlombard.model.SampleType;
import com.example.activgoldlombard.ui.auth.LoginFragment;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyFragment extends Fragment {

    private FirebaseAuth auth;
    //    private FragmentMyBinding binding;
    private RecyclerView recyclerView;
    PiedgeAdapter adapter;
    DatabaseReference mbase;

    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<PiedgeTicket> list;

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);
//        binding = FragmentMyBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();

        checkUser();

        rootView.findViewById(R.id.signOut).setOnClickListener(view -> {
            auth.signOut();
            replaceFragment(new LoginFragment());
        });

//        mbase = FirebaseDatabase.getInstance().getReference().child("PiedgeTicket");
//
//        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
//
//        // To display the Recycler view linearly
//        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
//
//        // It is a class provide by the FirebaseUI to make a
//        // query in the database to fetch appropriate data
//        FirebaseRecyclerOptions<PiedgeTicket> options
//                = new FirebaseRecyclerOptions.Builder<PiedgeTicket>()
//                .setQuery(mbase, PiedgeTicket.class)
//                .build();
//        // Connecting object of required Adapter class to
//        // the Adapter class itself
//        adapter = new PiedgeAdapter(options);
//        // Connecting Adapter class with the Recycler view*/
//        recyclerView.setAdapter(adapter);


        recyclerView = rootView.findViewById(R.id.recyclerView);
        database = FirebaseDatabase.getInstance().getReference("PiedgeTicket");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        list = new ArrayList<>();


        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    PiedgeTicket piedgeTicket = new PiedgeTicket();
                    if (ds.child("amountForHand").getValue(Long.class) != null) {
//                    PiedgeTicket piedgeTicket = dataSnapshot.getValue(PiedgeTicket.class);
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

        SearchView searchView = rootView.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterlist(newText);
                return true;
            }
        });
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

    private void filterlist(String text) {
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