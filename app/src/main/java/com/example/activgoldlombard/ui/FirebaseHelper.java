package com.example.activgoldlombard.ui;

import androidx.annotation.NonNull;

import com.example.activgoldlombard.model.PiedgeTicket;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

/*
 * 1. RECEIVE DB REFERENCE
 * 2. SAVE
 * 3. RETRIEVE
 * 4. RETURN ARRAYLIST
 */
public class FirebaseHelper {
    DatabaseReference db;
    Boolean saved = null;
    ArrayList<PiedgeTicket> piedgeTickets = new ArrayList<>();

    /*
     PASS DATABASE REFRENCE
     */
    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    //WRITE IF NOT NULL
    public Boolean save(PiedgeTicket piedgeTicket) {
        if (piedgeTicket == null) {
            saved = false;

        } else {
            try {
                db.child("PiedgeTicket").push().setValue(piedgeTicket);
                saved = true;

            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }

        return saved;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot) {
        piedgeTickets.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            PiedgeTicket piedgeTicket = ds.getValue(PiedgeTicket.class);
            piedgeTickets.add(piedgeTicket);
        }
    }

    //READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
    public ArrayList<PiedgeTicket> retrieve() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return piedgeTickets;
    }
}