package com.example.eileen.smartstorage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BookTool extends AppCompatActivity {

    DatabaseReference updateRef;
    DatabaseReference updateUser;
    DatabaseReference updateHistory;
    DatabaseReference updateStats;
    DatabaseReference updateBookHist;

    String[] monthName = { "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December" };


    public static final String CONTACTDATA = "ContactData";
    public static final String KEY = "Contact";

    Button btnYes;
    Button btnNo;
    EditText etContact;
    String userID;
    String chosenTime;
    int contact;
    String contact1;
    String chosenTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tool);

        setTitle("Booking");

        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        int width = display.widthPixels;
        int height = display.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.8));

        TextView textBook = findViewById(R.id.tBooking1);
        btnYes = findViewById(R.id.btnyes1);
        btnNo = findViewById(R.id.btnNo1);

        etContact = findViewById(R.id.etContact);

        SharedPreferences sharedPreferences = getSharedPreferences(Login.MY_PREF, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(Login.KEY,"default");

        SharedPreferences sharedPreferences1 = getSharedPreferences(Booking.BOOKINGDATA,Context.MODE_PRIVATE);
        chosenTime = sharedPreferences1.getString(Booking.KEY,"default");
        chosenTool = sharedPreferences1.getString(Booking.KEY2, "default");

        Log.d("E124","CHOSEN BEFORE BOOK + "+chosenTool);

        textBook.setText("Book " +chosenTool + " from " + chosenTime + "?");

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookTool.this, "Booking Cancelled", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BookTool.this,MainActivity.class));
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            contact = etContact.getText(Integer.class);
            contact1 = etContact.getText().toString();
            if (contact1.equals("")|| contact1.length() != 8 && (contact1.charAt(0) != '8' || contact1.charAt(0) != '9')){
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Please enter a valid contact number.");
                AlertDialog alertDialog =builder.create();
                alertDialog.show();}

            else {
                contact = Integer.parseInt(contact1);
                updateUser = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Booking");
                updateUser.setValue(chosenTool + " booked at " + chosenTime);

                updateRef = FirebaseDatabase.getInstance().getReference().child("Bookings").child(chosenTool).child(chosenTime);
                updateRef.child("StudentID").setValue(userID);
                updateRef.child("Contact").setValue(contact);

                updateHistory = FirebaseDatabase.getInstance().getReference().child("History").child(userID);

                HashMap<String, Object> data = new HashMap<>();
                String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());

                data.put("Date", currentDateTime);
                data.put("Tools", chosenTool);
                data.put("Status", "Unreturned");

                updateHistory.push().updateChildren(data);

                HashMap<String, Object> data2 = new HashMap<>();

                data2.put("StudentID",userID);
                data2.put("Status","Unreturned");
                data2.put("Slot",chosenTime);
                data2.put("Date",currentDateTime);

                updateBookHist = FirebaseDatabase.getInstance().getReference().child("BookingHistory");
                updateBookHist.child(chosenTool).push().setValue(data2);

                Calendar cal = Calendar.getInstance();
                String month = monthName[cal.get(Calendar.MONTH)];

                Log.i("e","month: "+month);

                updateStats = FirebaseDatabase.getInstance().getReference().child("Statistics").child(month+"17");
                updateStats.child(chosenTool).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int data = dataSnapshot.getValue(Integer.class);
                        updateStats.child(chosenTool).setValue(data+1);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Toast.makeText(BookTool.this, "Booking for " + chosenTool + " at " + chosenTime + " successfully made.", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(BookTool.this, MainActivity.class);
                startActivity(intent2);
            }
            }
        });

    }
}
