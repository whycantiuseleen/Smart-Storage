package com.example.eileen.smartstorage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eileen on 29/11/2017.
 */

public class HistoryMain extends AppCompatActivity{
    private RecyclerView recyclerView;
    DatabaseReference historyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences(Login.MY_PREF,MODE_PRIVATE);
        String userID = sharedPreferences.getString(Login.KEY,"default");

        historyRef = FirebaseDatabase.getInstance().getReference().child("History").child("/"+userID);
        FirebaseRecyclerAdapter<History,MyViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<History,MyViewHolder>(
                History.class, R.layout.history_list_rows,MyViewHolder.class,historyRef
        ) {
            @Override
            protected void populateViewHolder(MyViewHolder viewHolder, History model, int position) {
                viewHolder.setTools(model.getTools());
                viewHolder.setDate(model.getDate());
                viewHolder.setStatus(model.getStatus());
            }
        };
        recyclerView.setAdapter(recyclerAdapter);

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View mview;
        TextView tools1, date1, status1;


        public MyViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            tools1 = itemView.findViewById(R.id.tools2);
            date1 = itemView.findViewById(R.id.date);
            status1 = itemView.findViewById(R.id.status);
        }

        public void setTools(String tools) {
            tools1.setText(tools);
        }

        public void setDate(String date) {
            date1.setText(date);
        }
        public void setStatus (String status){
            status1.setText(status);
        }
    }

}
