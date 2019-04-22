package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.bignerdranch.android.criminalintent.data.*;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mohamed Amr on 6/22/2018.
 */

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListAdapter.listItemClickListener {



    @Override
    public void OnItemClicked(int crimeposition) {
        CrimeLab crimeLab = new CrimeLab(this);
        List<Crime> crimes = crimeLab.getCrimes();
        UUID crimeId = crimes.get(crimeposition).getId();
        Intent intent = CrimePagerActivity.newIntent( CrimeListActivity.this,crimeId );
        startActivity(intent);
    }

    @Override
    public Fragment CreateFragment() {
        return new CrimeListFragment();
    }
}
