package com.example.countbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by aridgway120 on 2017-10-01.
 */

public class EditActivity extends AppCompatActivity {

    private static final int RESULT_DELETE = 10;
    private EditText nameText;
    private EditText initValText;
    private EditText commentText;
    private TextView currValText;
    private int currVal;
    private int initVal;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Log.d("zzz-Edit-onCreate", "started");

        Bundle extras = getIntent().getExtras();

        nameText = (EditText) findViewById(R.id.editTextName);
        initValText = (EditText) findViewById(R.id.editTextInitVal);
        commentText = (EditText) findViewById(R.id.editTextComment);

        currValText = (TextView) findViewById(R.id.textViewCurrVal2);
        Log.d("zzz-Edit-onCreate", "set EditText listeners");

        nameText.setText(extras.getString("name"));
        initValText.setText(Integer.toString(extras.getInt("initVal")));
        commentText.setText(extras.getString("comment"));
        Log.d("zzz-Edit-onCreate", "text set 1");


        currVal = extras.getInt("currVal");
        Log.d("zzz-Edit-onCreate", "flag1");
        currValText.setText(Integer.toString(currVal));
        Log.d("zzz-Edit-onCreate", "currVal set");

        position = extras.getInt("position");
        Log.d("zzz-Edit-onCreate", "got position");
    }

    public void saveEdits(View view) {

        // pull counter data
        String name = nameText.getText().toString();
        initVal = Integer.parseInt(initValText.getText().toString());
        String comment = commentText.getText().toString();

        Intent returnData = new Intent();
        returnData.putExtra("name", name);
        returnData.putExtra("initVal", initVal);
        returnData.putExtra("currVal", currVal);
        returnData.putExtra("comment", comment);
        returnData.putExtra("position", position);

        setResult(RESULT_OK, returnData);
        finish();
    }

    public void decrement(View view) {
        if (currVal > 0) {
            currVal--;
        } else {
            currVal = 0;
        }
        currValText.setText(Integer.toString(currVal));
    }

    public void increment(View view) {
        currVal++;
        currValText.setText(Integer.toString(currVal));
    }

    public void reset(View view) {
        initVal = Integer.parseInt(initValText.getText().toString());
        currVal = initVal;
        currValText.setText(Integer.toString(currVal));
    }

    public void deleteCounter(View view) {
        Intent returnData = new Intent();
        returnData.putExtra("position", position);
        setResult(RESULT_DELETE, returnData);
        finish();
    }
}
