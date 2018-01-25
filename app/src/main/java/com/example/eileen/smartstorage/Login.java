package com.example.eileen.smartstorage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText Username;
    private EditText Password;
    private Button Login;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;

    public static final String MY_PREF = "MyPref";
    public static final String KEY = "Username";

    //private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = (EditText)findViewById(R.id.etUsername);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = (Button)findViewById(R.id.btnLogin);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Username.getText().toString(),Password.getText().toString());
            }
        });

    }

    private void validate(final String username, final String password){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(username.toString()).child("StudentID");
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username.toString()).child("Password");
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dataPassword = dataSnapshot.getValue().toString();
                if (password.equals(dataPassword)){
                    SharedPreferences sharedPreferences = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY,username);
                    editor.apply();

                    if (username.toString().equals("adminuser")){
                        startActivity(new Intent(Login.this,MainAdmin.class));
                    }else {
                        Intent nextscreen = new Intent(Login.this, MainActivity.class);
                        startActivity(nextscreen);
                    }
                }else{
                    Toast.makeText(Login.this, "Wrong User ID/Password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Disable button after 5 failed attempts
        /*else{
            counter--;
            if (counter == 0){
                Login.setEnabled(false);
            }

        }*/

    }
}
