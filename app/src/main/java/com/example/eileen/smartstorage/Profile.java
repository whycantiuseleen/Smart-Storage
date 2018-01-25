package com.example.eileen.smartstorage;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    DatabaseReference dRef;
    DatabaseReference dRef1;
    DatabaseReference dRef2;
    TextView tName;
    TextView tStudentId;
    TextView tContact;

    String chosenTool;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("Booked Slot Details");

        SharedPreferences sharedPreferences1 = getSharedPreferences(Booking.BOOKINGDATA,MODE_PRIVATE);
        time = sharedPreferences1.getString(Booking.KEY,"default");
        chosenTool = sharedPreferences1.getString(Booking.KEY2,"default");

        tName = findViewById(R.id.tName1);
        tContact = findViewById(R.id.tContact1);
        tStudentId = findViewById(R.id.tStudentId1);
        TextView tTool = findViewById(R.id.tTool1);
        TextView tTime = findViewById(R.id.tTimeSlot);

        tTool.setText("Booked Item: " +chosenTool);
        tTime.setText("Timeslot: " +time);

        dRef2 = FirebaseDatabase.getInstance().getReference().child("Bookings").child(chosenTool).child(time).child("Contact");
        dRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String contactnum = dataSnapshot.getValue().toString();
                tContact.setText("Contact Number: "+contactnum);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        dRef = FirebaseDatabase.getInstance().getReference().child("Bookings").child(chosenTool).child(time).child("StudentID");
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String bookedID = dataSnapshot.getValue().toString();
                tStudentId.setText("Student ID: " + bookedID);

                dRef1 = FirebaseDatabase.getInstance().getReference().child("Users").child(bookedID).child("Name");
                dRef1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.getValue().toString();
                        tName.setText("Name: " + name);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
