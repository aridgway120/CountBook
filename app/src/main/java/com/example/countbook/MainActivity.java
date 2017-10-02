package com.example.countbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private static final int NEW_COUNTER_REQUEST = 1;
    private static final int EDIT_COUNTER_REQUEST = 2;
    private static final int RESULT_DELETE = 10;
    private ListView counterListView;

    private ArrayList<Counter> counterArrayList;
    private ArrayAdapter<Counter> counterAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("zzz-Main-onCreate", "created");
        setContentView(R.layout.activity_main);

        counterListView = (ListView) findViewById(R.id.counterListView);
        Button buttonTest = (Button) findViewById(R.id.buttonTest);

        loadFromFile();
        counterAdapter = new ArrayAdapter<Counter>(this, R.layout.item, counterArrayList);
        counterListView.setAdapter(counterAdapter);

        buttonTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Counter newCounter = new Counter("Sample Counter", 10, "Countdown.");
                counterArrayList.add(newCounter);
                counterAdapter.notifyDataSetChanged();
                saveToFile();
            }
        });


        counterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                editCounter(view, position, id);
            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();
        counterAdapter.notifyDataSetChanged();
        saveToFile();
        Log.d("zzz-Main-onStart", "started");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d("zzz-Main-onResult", "onActivityResult started");
        if (requestCode == NEW_COUNTER_REQUEST) {
            Log.d("zzz-Main-onResult", "new counter");
            if (resultCode == RESULT_OK) {
                Log.d("zzz-Main-onResult", "result ok");
                Counter newCounter = new Counter();
                Bundle extras = intent.getExtras();

                newCounter.setName( extras.getString("name") );
                newCounter.setInitVal( extras.getInt("initVal") );
                newCounter.setComment( extras.getString("comment") );

                newCounter.resetCounter();
                counterArrayList.add(newCounter);
                counterAdapter.notifyDataSetChanged();
                saveToFile();
            } else {

            }
        } else if (requestCode == EDIT_COUNTER_REQUEST) {
            Log.d("zzz-Main-onResult", "counter edit");
            if (resultCode == RESULT_OK) {
                Log.d("zzz-Main-onResult", "result ok");
                Bundle extras = intent.getExtras();

                int position = extras.getInt("position");
                Log.d("zzz-Main-onResult", "got position");
                Counter selectedCounter = (Counter) counterListView.getItemAtPosition(position);
                Log.d("zzz-Main-onResult", "found counter");

                selectedCounter.setName( extras.getString("name") );
                selectedCounter.setInitVal( extras.getInt("initVal") );
                selectedCounter.setCurrentVal( extras.getInt("currVal") );
                selectedCounter.setComment( extras.getString("comment") );
                Log.d("zzz-Main-onResult", "updated assets");

                selectedCounter.setDate(new Date());
                Log.d("zzz-Main-onResult", "updated date");
                counterAdapter.notifyDataSetChanged();
                saveToFile();
            }
            else if (resultCode == RESULT_DELETE) {
                Bundle extras = intent.getExtras();
                int position = extras.getInt("position");
                counterArrayList.remove(position);
                counterAdapter.notifyDataSetChanged();
                saveToFile();
            }
        }
    }

    public void addCounter(View view) { // buttonNew onClick
        Intent newCounterIntent = new Intent(this, NewCounterActivity.class);
        startActivityForResult(newCounterIntent, NEW_COUNTER_REQUEST);
    }

    public void editCounter(View view, int position, long id) {
        Log.d("zzz-Main-editCounter", "called");
        // Get selected counter
        Counter selectedCounter = (Counter) counterListView.getItemAtPosition(position);
        Log.d("zzz-Main-editCounter", "counter grabbed");
        // Load current data into intent
        Intent editCounterIntent = new Intent(this, EditActivity.class);

        editCounterIntent.putExtra("name", selectedCounter.getName());
        editCounterIntent.putExtra("currVal", selectedCounter.getCurrentVal());
        editCounterIntent.putExtra("initVal", selectedCounter.getInitVal());
        editCounterIntent.putExtra("comment", selectedCounter.getComment());

        editCounterIntent.putExtra("position", position);
        editCounterIntent.putExtra("id", id);

        Log.d("zzz-Main-editCounter", "set intent");

        startActivityForResult(editCounterIntent, EDIT_COUNTER_REQUEST);
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-09-19
            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            counterArrayList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            counterArrayList = new ArrayList<Counter>();
        }
    }

    private void saveToFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(counterArrayList, out);
            out.flush();

            fos.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
