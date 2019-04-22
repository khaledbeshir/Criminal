package com.bignerdranch.android.criminalintent;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.bignerdranch.android.criminalintent.data.*;

/**
 * Created by Mohamed Amr on 6/22/2018.
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;
    private SQLiteDatabase mDatabase;
    Context mContext;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }


    CrimeLab(Context context) {

    mDatabase = new CrimeBaseHelper(context).getWritableDatabase();
    }

    public void addCrime(Crime c) {

        ContentValues cv = getContentValues(c);
        mDatabase.insert(CrimeIntentContract.CrimeTable.NAME, null, cv);
    }

    public static ContentValues getContentValues(Crime c) {
        ContentValues values = new ContentValues();
        values.put(CrimeIntentContract.CrimeTable.COLUMS.UUID, c.getId().toString());
        values.put(CrimeIntentContract.CrimeTable.COLUMS.Title, c.getTitle());
        values.put(CrimeIntentContract.CrimeTable.COLUMS.Date, c.getDate().toString());
        values.put(CrimeIntentContract.CrimeTable.COLUMS.SOLVED, c.isSolved() ? 1 : 0);
        values.put(CrimeIntentContract.CrimeTable.COLUMS.SUSPECT, c.getSuspect());

        return values;
    }


    public void updatecrime(Crime c) {
        String UUidString = c.getId().toString();
        ContentValues cv = getContentValues(c);
        mDatabase.update(CrimeIntentContract.CrimeTable.NAME, cv
                , CrimeIntentContract.CrimeTable.COLUMS.UUID + "=?", new String[]{UUidString});
    }

    public CrimeCursorWrapper QueryCrimes(String whereClause, String[] whereArgs) {

        Cursor cursor = mDatabase.query(
                CrimeIntentContract.CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CrimeCursorWrapper(cursor);
    }

    public List<Crime> getCrimes() {

        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = QueryCrimes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id) {

        CrimeCursorWrapper cursor = QueryCrimes(CrimeIntentContract.CrimeTable.COLUMS.UUID + "= ?"
                , new String[] {id.toString()});
        try {
            if (cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }
    }
}