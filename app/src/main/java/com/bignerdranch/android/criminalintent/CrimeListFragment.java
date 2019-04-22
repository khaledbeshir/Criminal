package com.bignerdranch.android.criminalintent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;
import com.bignerdranch.android.criminalintent.data.*;
import java.util.List;

/**
 * Created by Mohamed Amr on 6/22/2018.
 */

public class CrimeListFragment extends Fragment {
    RecyclerView mCrimeRecyclerView;
    CrimeListAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list , container ,false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycle_view);
        updateUi();
        mCrimeRecyclerView.setHasFixedSize(true);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list ,  menu);
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if(mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else
            subtitleItem.setTitle(R.string.show_subtitle);
    }

    private void updateUi(){

        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if(mAdapter == null) {
            mAdapter = new CrimeListAdapter(crimes, getActivity());
            mCrimeRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.SetCrimes(crimes);
        }
        updatesubtitle();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_crime:
                Crime crime =new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity() , crime.getId());
                startActivity(intent);
                return true;

            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updatesubtitle();
                return true;
                default:
                    return super.onOptionsItemSelected(item);

        }
    }

    private void updatesubtitle(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimecount = crimeLab.getCrimes().size();
         String subtitle = getString(R.string.subtitle_format , String.valueOf(crimecount) );

        if(mSubtitleVisible){
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

}
