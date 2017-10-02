package com.example.countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NewCounterActivity extends AppCompatActivity {

    private EditText nameText;
    private EditText initValText;
    private EditText commentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DebugTag-NewCounter", "test");
        setContentView(R.layout.activity_new_counter);

        nameText = (EditText) findViewById(R.id.editTextName);
        initValText = (EditText) findViewById(R.id.editTextInitVal);
        commentText = (EditText) findViewById(R.id.editTextComment);
    }


    /**
     * Collects new counter data from interface, loads it into Intent returnData,  and returns it
     * to MainActivity
     *
     *
     */
    public void saveCounter(View view) {
        Log.d("DebugTag-NewCounter", "started data fetch");

        // pull counter data
        String name = nameText.getText().toString();
        int initVal = Integer.parseInt(initValText.getText().toString());
        String comment = commentText.getText().toString();

        // add data to intent
        Intent returnData = new Intent();
        returnData.putExtra("name", name);
        returnData.putExtra("initVal", initVal);
        returnData.putExtra("comment", comment);

        setResult(RESULT_OK, returnData);
        Log.d("DebugTag-NewCounter", "finished - result set");
        finish();

    }
}
