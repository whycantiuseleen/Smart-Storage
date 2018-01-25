package com.example.eileen.smartstorage;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Eileen on 6/12/2017.
 */

public class HistoryAdminHelper {
    DatabaseReference db;
    Boolean saved = null;
    ArrayList<HistoryAdmin> histories = new ArrayList<>();

    public HistoryAdminHelper(DatabaseReference db){
        this.db = db;
    }

    private void fetchData(DataSnapshot dataSnapshot){
        histories.clear();
        for (DataSnapshot ds:dataSnapshot.getChildren()){
            HistoryAdmin history = ds.getValue(HistoryAdmin.class);
            histories.add(history);
        }
    }
    public ArrayList<HistoryAdmin> retrieve(){
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
