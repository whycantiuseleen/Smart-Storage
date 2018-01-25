package com.example.eileen.smartstorage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MakeReport extends AppCompatActivity {

    Spinner spinner;
    EditText etProb;
    Button btnSubmit;

    String chosenTool;
    String currentDateTime;
    String prob;
    String studentid;

    DatabaseReference reportRef;

    HashMap<String,Object> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_report);

        spinner = findViewById(R.id.spinner2);
        etProb = findViewById(R.id.etProb);
        btnSubmit = findViewById(R.id.btnSubmit);

        SharedPreferences sharedPreferences = getSharedPreferences(Login.MY_PREF,MODE_PRIVATE);
        studentid = sharedPreferences.getString(Login.KEY,"default");

        reportRef = FirebaseDatabase.getInstance().getReference();
        data = new HashMap<>();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Spinner1,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenTool = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        prob = etProb.getText().toString();
        currentDateTime = DateFormat.getDateTimeInstance().format(new Date());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.put("Date",currentDateTime);
                data.put("Tool",chosenTool);
                data.put("Problem",etProb.getText().toString());
                data.put("StudentID",studentid);
                reportRef.child("Reports").push().setValue(data);

                Toast.makeText(MakeReport.this, "Report Successfully Submitted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MakeReport.this,MainActivity.class));

            }
        });

    }
}
