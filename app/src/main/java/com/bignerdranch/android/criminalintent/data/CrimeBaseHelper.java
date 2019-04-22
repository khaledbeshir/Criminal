package com.bignerdranch.android.criminalintent.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.bignerdranch.android.criminalintent.data.CrimeIntentContract.*;

/**
 * Created by Mohamed Amr on 4/6/2019.
 */

public class CrimeBaseHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";


    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table " + CrimeTable.NAME + "("+
                        " _id integer primary key autoincrement, "+
                CrimeTable.COLUMS.UUID  + "," +
                CrimeTable.COLUMS.Title + "," +
                CrimeTable.COLUMS.Date + "," +
                CrimeTable.COLUMS.SOLVED +","+
                CrimeTable.COLUMS.SUSPECT +")"


        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
