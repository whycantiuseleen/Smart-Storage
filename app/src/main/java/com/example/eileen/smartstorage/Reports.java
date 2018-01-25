package com.example.eileen.smartstorage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Reports extends Fragment {

    private RecyclerView recyclerView;
    DatabaseReference historyRef;
    String chosenTool;

    public Reports() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_reports, container, false);

        recyclerView =v.findViewById(R.id.recyclerview3);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        historyRef = FirebaseDatabase.getInstance().getReference().child("Reports");
        FirebaseRecyclerAdapter<Report,MyViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<Report,MyViewHolder>(
                Report.class, R.layout.report_list_rows,MyViewHolder.class,historyRef
        ) {
            @Override
            protected void populateViewHolder(MyViewHolder viewHolder,Report model, int position) {
                viewHolder.setStudentID("StudentID: " +model.getStudentID());
                viewHolder.setDate(model.getDate());
                viewHolder.setTool("Reported Tool: " +model.getTool());
                viewHolder.setProblem(model.getProblem());
            }
        };
        recyclerView.setAdapter(recyclerAdapter);

        return v;

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View mview;
        TextView ID2, date1, problem1,tool1;


        public MyViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            ID2 = itemView.findViewById(R.id.IDReport2);
            date1 = itemView.findViewById(R.id.Reportdate2);
            problem1 = itemView.findViewById(R.id.Problem2);
            tool1 = itemView.findViewById(R.id.ToolReport2);
        }

        public void setStudentID(String ID) {
            ID2.setText(ID);
        }
        public void setDate(String date) {
            date1.setText(date);
        }
        public void setTool(String tool) {tool1.setText(tool);}
        public void setProblem(String problem) {problem1.setText(problem);}
    }

}
