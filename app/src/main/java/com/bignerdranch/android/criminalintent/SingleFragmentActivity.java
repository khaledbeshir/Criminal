package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mohamed Amr on 6/22/2018.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    public abstract Fragment CreateFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = CreateFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
    }
}
