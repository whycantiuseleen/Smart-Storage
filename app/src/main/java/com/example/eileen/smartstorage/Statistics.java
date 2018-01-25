package com.example.eileen.smartstorage;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.HashMap;

import static com.github.mikephil.charting.utils.ColorTemplate.*;


public class Statistics extends Fragment {

    BarChart graph1;
    PieChart graph2;
    DatabaseReference numRef;

    Spinner spinner3;
    String chosenMonth;

    int augustTotal = 0;
    int septTotal = 0;
    int octTotal = 0;
    int novTotal = 0;
    int decTotal = 0;

    ArrayList<BarEntry> barEntries = new ArrayList<>();

    public Statistics() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_statistics, container, false);

        graph2 = v.findViewById(R.id.graph2);
        //PIECHART
        graph2.setUsePercentValues(true);
        graph2.getDescription().setEnabled(false);
        graph2.setExtraOffsets(5,10,5,5);

        graph2.setDragDecelerationFrictionCoef(0.15f);

        graph2.setDrawHoleEnabled(true);
        graph2.setHoleColor(Color.WHITE);
        graph2.setTransparentCircleRadius(61f);

        spinner3 = v.findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this.getContext(),R.array.spinner2,android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner3.setAdapter(adapter3);
        spinner3.setSelection(0);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenMonth = spinner3.getSelectedItem().toString();
                numRef.child(chosenMonth).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int Drillbox1 = dataSnapshot.child("Drillbox1").getValue(Integer.class);
                        int Drillbox2 = dataSnapshot.child("Drillbox2").getValue(Integer.class);
                        int Toolbox1 = dataSnapshot.child("Toolbox1").getValue(Integer.class);
                        int Toolbox2 = dataSnapshot.child("Toolbox2").getValue(Integer.class);

                        graph2.setCenterText(chosenMonth);
                        graph2.setCenterTextSize(15f);

                        ArrayList<PieEntry> pievalues = new ArrayList<>();
                        pievalues.add(new PieEntry(Drillbox1,"Drillbox1"));
                        pievalues.add(new PieEntry(Drillbox2,"Drillbox2"));
                        pievalues.add(new PieEntry(Toolbox1,"Toolbox1"));
                        pievalues.add(new PieEntry(Toolbox2,"Toolbox2"));

                        PieDataSet dataSet = new PieDataSet(pievalues,"Types of Tools ");
                        dataSet.setSliceSpace(3f);
                        dataSet.setSelectionShift(5f);
                        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

                        PieData data = new PieData(dataSet);
                        data.setValueTextSize(14f);
                        data.setValueTextColor(Color.BLACK);

                        PieChart chart2 = new PieChart(getActivity().getBaseContext());
                        graph2.addView(chart2);
                        chart2.setData(data);
                        graph2.setData(data);

                        chart2.invalidate();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        graph1 = v.findViewById(R.id.graph1);
        //BARCHART
        graph1.setDrawBarShadow(false);
        graph1.setDrawValueAboveBar(true);
        graph1.setMaxVisibleValueCount(200);
        graph1.setNoDataText("");
        graph1.setPinchZoom(true);
        graph1.setDrawGridBackground(true);

        numRef = FirebaseDatabase.getInstance().getReference().child("Statistics");
        numRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    if (key.equals("August17")){
                        for (DataSnapshot ds2 : ds.getChildren()){
                            augustTotal += ds2.getValue(Integer.class);
                        }
                    }else if (key.equals("September17")){
                        for (DataSnapshot ds2 : ds.getChildren()){
                            septTotal += ds2.getValue(Integer.class);
                        }
                    }else if (key.equals("October17")){
                        for (DataSnapshot ds2 : ds.getChildren()){
                            octTotal += ds2.getValue(Integer.class);
                        }
                    }else if(key.equals("November17")){
                        for (DataSnapshot ds2: ds.getChildren()){
                            novTotal += ds2.getValue(Integer.class);
                        }
                    }else{
                        for (DataSnapshot ds2 : ds.getChildren()){
                            decTotal += ds2.getValue(Integer.class);
                        }
                    }

                    barEntries.add(new BarEntry(1,(float)(augustTotal)));
                    barEntries.add(new BarEntry(2,(float)(septTotal)));
                    barEntries.add(new BarEntry(3,(float)(octTotal)));
                    barEntries.add(new BarEntry(4,(float)(novTotal)));
                    barEntries.add(new BarEntry(5,(float)(decTotal)));

                    BarDataSet barDataSet = new BarDataSet(barEntries, "Total Tools");
                    barDataSet.setValueTextSize(14f);
                    barDataSet.setColors(COLORFUL_COLORS);

                    BarData barData = new BarData(barDataSet);
                    barData.setBarWidth(0.8f);

                    String[] months1 = new String[]{"August","September","October","November","December"};

                    BarChart chart = new BarChart(getActivity().getBaseContext());
                    graph1.addView(chart);
                    chart.setData(barData);

                    XAxis xAxis = chart.getXAxis();
                    xAxis.setValueFormatter(new Xaxisformatter(months1));
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularity(1);
                    xAxis.setCenterAxisLabels(true);

                    chart.setExtraBottomOffset(50f);
                    graph1.setExtraBottomOffset(50f);
                    chart.setExtraTopOffset(50f);
                    graph1.setExtraTopOffset(50f);

                    chart.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }

    public class Xaxisformatter implements IAxisValueFormatter{
        String[] months;

        public  Xaxisformatter(String[] values){
            this.months = values;

        }
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            if (value >= 0) {
                if (months.length > (int) value) {
                    return months[(int) value];
                } else return "";
            } else {
                return "";
            }
        }
    }
}
