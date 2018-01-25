package com.example.eileen.smartstorage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ReturnTools extends AppCompatActivity {

    Button btnyes;
    Button btnno;
    DatabaseReference databaseInventory;
    DatabaseReference databaseUser;
    DatabaseReference historyRef;

    AlertDialog alertDialog;

    String chosenTool;
    String userID;
    String input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_tools);

        setTitle("CheckOut");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.8));

        SharedPreferences sharedPreferences = getSharedPreferences(Login.MY_PREF, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(Login.KEY,"default");

        btnyes = findViewById(R.id.btnYes);
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yes(userID);
            }
        });

        btnno = findViewById(R.id.btnNo);
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReturnTools.this,MainActivity.class));
            }
        });
    }
    private void yes(final String userID){
        databaseInventory = FirebaseDatabase.getInstance().getReference().child("Return");
        databaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        historyRef = FirebaseDatabase.getInstance().getReference().child("History").child("/"+userID);

        databaseUser.child("Booking").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chosenTool = dataSnapshot.getValue().toString();
                String inputSub = chosenTool.substring(7,8);

                if (chosenTool != "No Upcoming Booking"){
                    input = String.valueOf(Integer.valueOf(inputSub)+1);
                    Log.i("Eileen", "lastchar is "+inputSub);

                    databaseInventory.child("1").child(input).setValue(userID);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ReturnTools.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        databaseUser.child("Booking").setValue("No Upcoming Booking");
        historyRef.child("Status").setValue("Returned");

        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Please place the item on the lift within 10 seconds");
        alertDialog.setMessage("00:10");
        alertDialog.show();   //

        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                alertDialog.setMessage("00:"+ (millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                alertDialog.setMessage("End");
                startActivity(new Intent(ReturnTools.this,MainActivity.class));
            }
        }.start();
    }

}
