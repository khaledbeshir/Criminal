package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mohamed Amr on 6/20/2018.
 */

public class CrimeFragment extends Fragment {
    Crime mCrime;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private EditText mTitleField;
    private  UUID CrimeId;
    private Button mButtonReport;
    private Button msuspectButton;
    private static String ARG_CRIME_ID = "crime_id";
    private static String DIALOG_DATE = "dialog date";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT = 1;

    public static CrimeFragment newInstance (UUID crimeId){
        Bundle Args =new Bundle();
        Args.putSerializable(ARG_CRIME_ID , crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(Args);
        return fragment;
    }

    private String getCrimeReport(){
        String SolvedString = null;
        if(mCrime.isSolved()){
            SolvedString = getString(R.string.crime_report_solved);
        }else {
            SolvedString = getString(R.string.crime_report_unsolved);
        }

        String dateFormat = "EEE, MMM dd ";
        String dateString = DateFormat.format(dateFormat , mCrime.getDate()).toString();

        String suspect = mCrime.getSuspect();

        if(suspect == null){
            suspect = getString(R.string.crime_report_no_suspect);
        }else{
            suspect = getString(R.string.crime_report_suspect , suspect);
        }

        String report = getString(R.string.crime_report ,
                mCrime.getTitle() , dateString , SolvedString , suspect);
        return report;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime=CrimeLab.get(getActivity()).getCrime(CrimeId);
    }

    @Override
    public void onPause() {
        super.onPause();

        CrimeLab.get(getActivity()).updatecrime(mCrime);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_DATE){
           Date date =(Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
           mCrime.setDate(date);
           mDateButton.setText(mCrime.getDate().toString());

        }else if(requestCode == REQUEST_CONTACT && data != null) {
            Uri ContentUri = data.getData();
            String[] queryFields = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
            Cursor c = getActivity().getContentResolver()
                    .query(ContentUri, queryFields, null, null, null);
            try {
                if (c.getCount() == 0) {
                    return;
                }
                c.moveToFirst();
                String suspect = c.getString(0);
                mCrime.setSuspect(suspect);
                msuspectButton.setText(suspect);
            }finally {
                c.close();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
               View view = inflater.inflate(R.layout.fragment_crime,container,false);


               mTitleField = (EditText) view.findViewById(R.id.crime_title);
               mTitleField.setText(mCrime.getTitle());

               mDateButton = (Button) view.findViewById(R.id.crime_date);
               mDateButton.setText(mCrime.getDate().toString());
               mDateButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       FragmentManager manager = getFragmentManager();
                       DatePickerFragment datePickerFragment = DatePickerFragment.newInstence(mCrime.getDate());
                       datePickerFragment.setTargetFragment(CrimeFragment.this , REQUEST_DATE);
                       datePickerFragment.show(manager , DIALOG_DATE);
                   }
               });

               mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
               mSolvedCheckBox.setChecked(mCrime.isSolved());


                       mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                   @Override
                   public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                       mCrime.setSolved(isChecked);

                             }});


                       mTitleField.addTextChangedListener(new TextWatcher() {
                           @Override
                           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                           }


                           @Override
                           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                             mCrime.setTitle(charSequence.toString());
                           }


                           @Override
                           public void afterTextChanged(Editable editable) {

                           }
                       });


                       mButtonReport = (Button)view.findViewById(R.id.crime_report);
                       mButtonReport.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               Intent i = new Intent(Intent.ACTION_SEND);
                               i.setType("text/plain");
                               i.putExtra(Intent.EXTRA_TEXT ,getCrimeReport());
                               i.putExtra(Intent.EXTRA_SUBJECT , getString(R.string.crime_report_subject));
                               startActivity(i);
                           }
                       });

                       final Intent PickContact = new Intent(Intent.ACTION_PICK ,
                               ContactsContract.Contacts.CONTENT_URI);
                       msuspectButton = (Button)view.findViewById(R.id.crime_suspect);
                       msuspectButton.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               startActivityForResult(PickContact , REQUEST_CONTACT);
                           }
                       });
                       if(mCrime.getSuspect() != null){
                           msuspectButton.setText(mCrime.getSuspect());
                       }

               return view;
    }

}
