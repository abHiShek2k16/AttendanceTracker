package com.android.prince.attendancetracker.javaClass;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by prince on 7/4/18.
 */

public class combine {

    DatabaseReference databaseReference;
    int i;

    public combine(DatabaseReference databaseReference,int i){
        this.databaseReference = databaseReference;
        this.i = i;
    }

    public int getI(){
        return i;
    }

    public DatabaseReference getDatabaseReference(){
        return databaseReference;
    }
}
