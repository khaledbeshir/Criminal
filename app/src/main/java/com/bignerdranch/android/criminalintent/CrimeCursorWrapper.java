package com.bignerdranch.android.criminalintent;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.criminalintent.data.CrimeIntentContract;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Mohamed Amr on 4/21/2019.
 */

public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public  Crime getCrime (){

        String UUidString = getString(getColumnIndex(CrimeIntentContract.CrimeTable.COLUMS.UUID));
        String title = getString(getColumnIndex(CrimeIntentContract.CrimeTable.COLUMS.Title));
        long Date = getLong(getColumnIndex(CrimeIntentContract.CrimeTable.COLUMS.Date));
        int isSolved = getInt(getColumnIndex(CrimeIntentContract.CrimeTable.COLUMS.SOLVED));
        String suspect = getString(getColumnIndex(CrimeIntentContract.CrimeTable.COLUMS.SUSPECT));

        Crime crime = new Crime(UUID.fromString(UUidString));
        crime.setTitle(title);
        crime.setDate(new Date(Date));
        crime.setSolved(isSolved != 0);
        crime.setSuspect(suspect);

        return crime;
    }
}
