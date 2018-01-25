package com.example.eileen.smartstorage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Booking extends Fragment {
    public static final String BOOKINGDATA = "Booking";
    public static final String KEY = "Time";
    public static final String KEY2 = "Tool";
    DatabaseReference inventoryRef;

    Spinner spinner;

    Button time1;
    Button name1;
    Button time2;
    Button name2;
    Button time3;
    Button name3;
    Button time4;
    Button name4;
    Button time5;
    Button name5;
    Button time6;
    Button name6;
    Button time7;
    Button name7;
    Button time8;
    Button name8;
    Button time9;
    Button name9;

    String chosenTool;

    SharedPreferences sharedPreferences;

    public Booking() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking,container,false);

        time1 = view.findViewById(R.id.book9to10);
        name1 = view.findViewById(R.id.name9to10);
        time2 = view.findViewById(R.id.book10to11);
        name2 = view.findViewById(R.id.name10to11);
        time3 = view.findViewById(R.id.book11to12);
        name3 = view.findViewById(R.id.name11to12);
        time4 = view.findViewById(R.id.book12to13);
        name4 = view.findViewById(R.id.name12to13);
        time5 = view.findViewById(R.id.book13to14);
        name5 = view.findViewById(R.id.name13to14);
        time6 = view.findViewById(R.id.book14to15);
        name6 = view.findViewById(R.id.name14to15);
        time7 = view.findViewById(R.id.book15to16);
        name7 = view.findViewById(R.id.name15to16);
        time8 = view.findViewById(R.id.book16to17);
        name8 = view.findViewById(R.id.name16to17);
        time9 = view.findViewById(R.id.book17to18);
        name9 = view.findViewById(R.id.name17to18);//Buttons init

        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),R.array.Spinner1,android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        Log.i("Eileen","Spinner value "+spinner.getSelectedItem().toString());

        sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA,Context.MODE_PRIVATE);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
                Toast.makeText(getContext(), "Item selected is "+ parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

                chosenTool = spinner.getSelectedItem().toString();
                Log.i("EEEEEEEEEEEEE", "TOOL: "+ chosenTool);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY2,chosenTool);
                editor.apply();

                inventoryRef = FirebaseDatabase.getInstance().getReference().child("Bookings").child(chosenTool).child("0900");
                inventoryRef.child("StudentID").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String data = dataSnapshot.getValue().toString();

//                        Log.i("Eileen","Data is "+data);

                        if (data.equals("Free")){
                            name1.setText(data);

                            time1.setEnabled(true);
                            time1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(KEY,"0900");
                                    editor.apply();

                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), BookTool.class);
                                    getActivity().startActivity(intent);
                                }
                            });
                        } else{
                            name1.setText("Student ID: " + data);
                            time1.setEnabled(false);
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

                inventoryRef = FirebaseDatabase.getInstance().getReference().child("Bookings").child(chosenTool).child("1000");
                inventoryRef.child("StudentID").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String data = dataSnapshot.getValue().toString();
                        if (data.equals("Free")){
                            name2.setText(data);

                            time2.setEnabled(true);
                            time2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(KEY,"1000");
                                    editor.apply();

                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), BookTool.class);
                                    getActivity().startActivity(intent);
                                }
                            });
                        } else{
                            name2.setText("Student ID: " + data);
                            time2.setEnabled(false);
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

                inventoryRef = FirebaseDatabase.getInstance().getReference().child("Bookings").child(chosenTool).child("1100");
                inventoryRef.child("StudentID").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String data = dataSnapshot.getValue().toString();
                        if (data.equals("Free")){
                            name3.setText(data);

                            time3.setEnabled(true);
                            time3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(KEY,"1100");
                                    editor.apply();

                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), BookTool.class);
                                    getActivity().startActivity(intent);
                                }
                            });
                        } else{
                            name3.setText("Student ID: " + data);
                            time3.setEnabled(false);
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

                inventoryRef = FirebaseDatabase.getInstance().getReference().child("Bookings").child(chosenTool).child("1200");
                inventoryRef.child("StudentID").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String data = dataSnapshot.getValue().toString();
                        if (data.equals("Free")){
                            name4.setText(data);

                            time4.setEnabled(true);
                            time4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(KEY,"1200");
                                    editor.apply();

                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), BookTool.class);
                                    getActivity().startActivity(intent);
                                }
                            });
                        } else{
                            name4.setText("Student ID: " + data);
                            time4.setEnabled(false);
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

                inventoryRef = FirebaseDatabase.getInstance().getReference().child("Bookings").child(chosenTool).child("1300");
                inventoryRef.child("StudentID").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String data = dataSnapshot.getValue().toString();
                        if (data.equals("Free")){
                            name5.setText(data);

                            time5.setEnabled(true);
                            time5.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(KEY,"1300");
                                    editor.apply();

                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), BookTool.class);
                                    getActivity().startActivity(intent);
                                }
                            });
                        } else{
                            name5.setText("Student ID: " + data);
                            time5.setEnabled(false);
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
                inventoryRef = FirebaseDatabase.getInstance().getReference().child("Bookings").child(chosenTool).child("1400");
                inventoryRef.child("StudentID").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String data = dataSnapshot.getValue().toString();
                        if (data.equals("Free")){
                            name6.setText(data);

                            time6.setEnabled(true);
                            time6.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(KEY,"1400");
                                    editor.apply();

                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), BookTool.class);
                                    getActivity().startActivity(intent);
                                }
                            });
                        } else{
                            name6.setText("Student ID: " + data);
                            time6.setEnabled(false);
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
                inventoryRef = FirebaseDatabase.getInstance().getReference().child("Bookings").child(chosenTool).child("1500");
                inventoryRef.child("StudentID").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String data = dataSnapshot.getValue().toString();
                        if (data.equals("Free")){
                            name7.setText(data);

                            time7.setEnabled(true);
                            time7.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(KEY,"1500");
                                    editor.apply();

                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), BookTool.class);
                                    getActivity().startActivity(intent);
                                }
                            });
                        } else{
                            name7.setText("Student ID: " + data);
                            time7.setEnabled(false);
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
                inventoryRef = FirebaseDatabase.getInstance().getReference().child("Bookings").child(chosenTool).child("1600");
                inventoryRef.child("StudentID").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String data = dataSnapshot.getValue().toString();
                        if (data.equals("Free")){
                            name8.setText(data);

                            time8.setEnabled(true);
                            time8.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(KEY,"1600");
                                    editor.apply();

                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), BookTool.class);
                                    getActivity().startActivity(intent);
                                }
                            });
                        } else{
                            name8.setText("Student ID: " + data);
                            time8.setEnabled(false);
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
                inventoryRef = FirebaseDatabase.getInstance().getReference().child("Bookings").child(chosenTool).child("1700");
                inventoryRef.child("StudentID").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String data = dataSnapshot.getValue().toString();
                        if (data.equals("Free")){
                            name9.setText(data);

                            time9.setEnabled(true);
                            time9.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(KEY,"1700");
                                    editor.apply();

                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), BookTool.class);
                                    getActivity().startActivity(intent);
                                }
                            });
                        } else{
                            name9.setText("Student ID: " + data);
                            time9.setEnabled(false);
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });//code hidden: updating the names for all timing.


                name1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY,"0900");
                        editor.apply();

                        Intent intent = new Intent();
                        intent.setClass(getActivity(),Profile.class);
                        getActivity().startActivity(intent);
                    }
                });

                name2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY,"1000");
                        editor.apply();

                        Intent intent = new Intent();
                        intent.setClass(getActivity(),Profile.class);
                        getActivity().startActivity(intent);
                    }
                });
                name3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY,"1100");
                        editor.apply();

                        Intent intent = new Intent();
                        intent.setClass(getActivity(),Profile.class);
                        getActivity().startActivity(intent);
                    }
                });
                name4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY,"1200");
                        editor.apply();

                        Intent intent = new Intent();
                        intent.setClass(getActivity(),Profile.class);
                        getActivity().startActivity(intent);
                    }
                });
                name5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY,"1300");
                        editor.apply();

                        Intent intent = new Intent();
                        intent.setClass(getActivity(),Profile.class);
                        getActivity().startActivity(intent);
                    }
                });
                name6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY,"1400");
                        editor.apply();

                        Intent intent = new Intent();
                        intent.setClass(getActivity(),Profile.class);
                        getActivity().startActivity(intent);
                    }
                });
                name7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY,"1500");
                        editor.apply();

                        Intent intent = new Intent();
                        intent.setClass(getActivity(),Profile.class);
                        getActivity().startActivity(intent);
                    }
                });
                name8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY,"1600");
                        editor.apply();

                        Intent intent = new Intent();
                        intent.setClass(getActivity(),Profile.class);
                        getActivity().startActivity(intent);
                    }
                });
                name9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BOOKINGDATA, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY,"1700");
                        editor.apply();

                        Intent intent = new Intent();
                        intent.setClass(getActivity(),Profile.class);
                        getActivity().startActivity(intent);
                    }
                });//NAME BUTTONS
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;

    }

}
