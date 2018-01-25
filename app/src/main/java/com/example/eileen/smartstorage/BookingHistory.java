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

public class BookingHistory extends Fragment {
    private RecyclerView recyclerView;
    DatabaseReference historyRef;
    Spinner spinner4;

    String chosenTool;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_booking_history, container, false);

        spinner4 = v.findViewById(R.id.spinner4);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.Spinner1,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner4.setAdapter(adapter);
        spinner4.setSelection(0);

        recyclerView = v.findViewById(R.id.recyclerview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner4.setSelection(position);
                chosenTool = spinner4.getSelectedItem().toString();

                Log.d("eadf", "TOOL: "+chosenTool);

                historyRef = FirebaseDatabase.getInstance().getReference().child("BookingHistory").child(chosenTool);
                FirebaseRecyclerAdapter<HistoryAdmin,MyViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<HistoryAdmin,MyViewHolder>(
                        HistoryAdmin.class, R.layout.booking_list_rows,MyViewHolder.class,historyRef
                ) {
                    @Override
                    protected void populateViewHolder(MyViewHolder viewHolder, HistoryAdmin model, int position) {
                        viewHolder.setStudentID("StudentID: "+model.getStudentID());
                        viewHolder.setDate(model.getDate());
                        viewHolder.setStatus(model.getStatus());
                        viewHolder.setSlot("Booked Slot: "+model.getSlot());
                    }
                };
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return v;

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View mview;
        TextView ID2, date1, status1,slot1;


        public MyViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            ID2 = itemView.findViewById(R.id.userhistory);
            date1 = itemView.findViewById(R.id.Bookinghistorydate);
            status1 = itemView.findViewById(R.id.statushistory);
            slot1 = itemView.findViewById(R.id.slots);
        }

        public void setStudentID(String ID) {
            ID2.setText(ID);
        }

        public void setDate(String date) {
            date1.setText(date);
        }
        public void setStatus (String status){
            status1.setText(status);
        }

        public void setSlot(String slot) {
            slot1.setText(slot);
        }
    }

}
