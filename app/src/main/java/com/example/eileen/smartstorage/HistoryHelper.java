package com.example.eileen.smartstorage;

import android.widget.ArrayAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Eileen on 29/11/2017.
 */

// 1. Receive DB Ref
// 2. Save Data
// 3. Retrieve Data
// 4. Return ArrayList into Main
public class HistoryHelper {
    DatabaseReference db;
    Boolean saved = null;
    ArrayList<History> histories = new ArrayList<>();

    public HistoryHelper(DatabaseReference db){
        this.db = db;
    }

    private void fetchData(DataSnapshot dataSnapshot){
        histories.clear();
        for (DataSnapshot ds:dataSnapshot.getChildren()){
            History history = ds.getValue(History.class);
            histories.add(history);
        }
    }
    public ArrayList<History> retrieve(){
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return histories;
    }

}
