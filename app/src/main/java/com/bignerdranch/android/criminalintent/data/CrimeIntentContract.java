package com.bignerdranch.android.criminalintent.data;

import android.provider.BaseColumns;

/**
 * Created by Mohamed Amr on 4/6/2019.
 */

public class CrimeIntentContract {

    public static final class CrimeTable {
        public static final String NAME = "crimes";

        public static final class COLUMS {
            public static final String UUID = "uuid";
            public static final String Title = "title";
            public static final String Date = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
        }
    }
}
