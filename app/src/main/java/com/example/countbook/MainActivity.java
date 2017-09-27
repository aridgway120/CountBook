package com.example.countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ListView counterListView;

    private ArrayList<Counter> counterArrayList;
    private ArrayAdapter<Counter> counterAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DebugTagForAlex", "Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterListView = (ListView) findViewById(R.id.counterListView);
        Button buttonTest = (Button) findViewById(R.id.buttonTest);
        Button buttonNew = (Button) findViewById(R.id.buttonNew);

        buttonTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Log.d("DebugTagForAlex", "Set result OK");
                Counter newCounter = new Counter("Test Counter", 10, "Countdown.");
                Log.d("DebugTagForAlex", "Made new Counter");
                counterArrayList.add(newCounter);
                Log.d("DebugTagForAlex", "Added to array");
                counterAdapter.notifyDataSetChanged();
                Log.d("DebugTagForAlex", "updated data set");
            }
        });

        buttonNew.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Counter newCounter = new Counter();
                counterArrayList.add(newCounter);
                counterAdapter.notifyDataSetChanged();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        counterArrayList = new ArrayList<Counter>();
        counterAdapter = new ArrayAdapter<Counter>(this, R.layout.item, counterArrayList);
        counterListView.setAdapter(counterAdapter);
    }

    public void addCounter(View view) {
        Intent intent = new Intent(this, NewCounterActivity.class);
        startActivity(intent);
    }
}
