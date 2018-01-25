package com.example.eileen.smartstorage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class account extends Fragment{
    Button btnBooking;
    Button btnReturn;
    Button btnHistory;
    Button btnReports;

    TextView tName;
    TextView tStudentId;
    TextView tPillar;
    TextView upBook;

    private DatabaseReference databaseName;
    private DatabaseReference databaseID;
    private DatabaseReference databasePillar;
    private DatabaseReference databaseBooking;


    public account() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_account,container,false);

        tName = view.findViewById(R.id.tName);
        tStudentId = view.findViewById(R.id.tStudentId);
        tPillar = view.findViewById(R.id.tPillarYear);
        upBook = view.findViewById(R.id.displaybooking);

        btnBooking = view.findViewById(R.id.btnBooking);
        btnReturn = view.findViewById(R.id.btnReturnTools);
        btnHistory = view.findViewById(R.id.btnHistory);
        btnReports = view.findViewById(R.id.btnReport);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Login.MY_PREF,Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString(Login.KEY,"default");

        databaseName = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Name");
        databaseName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Name = dataSnapshot.getValue().toString();
                tName.setText("Name: " + Name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseID = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("StudentID");
        databaseID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ID = dataSnapshot.getValue().toString();
                tStudentId.setText("Student ID: "+ ID);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databasePillar = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Pillar");
        databasePillar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Pillar = dataSnapshot.getValue().toString();
                tPillar.setText("Pillar/Year: "+Pillar);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseBooking = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Booking");
        databaseBooking.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Booking = dataSnapshot.getValue().toString();
                upBook.setText(Booking);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                Booking booking = new Booking();
                fragmentTransaction.replace(R.id.frame1, booking);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

             }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentIntentIntegrator integrator = new FragmentIntentIntegrator(getFragmentManager().findFragmentById(R.id.frame1));
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan the QR Code");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(),HistoryMain.class);
                getActivity().startActivity(i);
            }
        });

        btnReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),MakeReport.class);
                getActivity().startActivity(intent);
            }
        });

        return view;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(getActivity(),"Cancelled", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getActivity(),result.getContents(),Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                intent.setClass(getActivity(), ReturnTools.class);
                getActivity().startActivity(intent);

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
