package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.bignerdranch.android.criminalintent.data.*;

/**
 * Created by Mohamed Amr on 4/9/2019.
 */

public class test {

    SQLiteDatabase mDatabase;

    test(Context context){

        mDatabase = new CrimeBaseHelper(context).getWritableDatabase();
        Toast.makeText(context , "ahmed" , Toast.LENGTH_SHORT).show();
    }

    public static void khaled (Context context){
        Toast.makeText(context , "a" , Toast.LENGTH_SHORT).show();
    }
}
