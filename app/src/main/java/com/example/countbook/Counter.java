package com.example.countbook;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aridgway120 on 2017-09-26.
 */

public class Counter {
    private String name;
    private Date date;
    private int currentVal;
    private int initVal;
    private String comment;

    private static final SimpleDateFormat printFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Counter() {
        name = "";
        date = new Date();
        initVal = 0;
        currentVal = initVal;
        comment = "";
    }

    public Counter(String name, int initVal, String comment) {
        this.name = name;
        this.date = new Date();
        this.initVal = initVal;
        this.comment = comment;

        this.currentVal = initVal;
    }

    @Override
    public String toString() {
        return name + " | " + currentVal + "\n" + printFormat.format(date);
    }
}
