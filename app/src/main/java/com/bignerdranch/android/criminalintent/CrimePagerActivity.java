package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Mohamed Amr on 8/9/2018.
 */

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";
    private static final String TAG = "CrimePagerActivity";
    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private int number;


    public static Intent newIntent(Context context , UUID crimeId){
        Intent intent = new Intent(context , CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID , crimeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId =(UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID );

        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);
        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        //this loop help me to identify correct postion for item  cause viewpaer identfy first
        //item 0 by default so setCurrentItem function come here to solve this problem
        //see page 232 if you wanna know more

        for(int i =0;i<= mCrimes.size() ; i++){

/*            if(mCrimes.get(i).getId().equals(crimeId)){
             //   Log.d(TAG ,"called");
                mViewPager.setCurrentItem(i);
                break;
            }
*/
        }

    }

}
