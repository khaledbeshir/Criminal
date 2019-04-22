package com.bignerdranch.android.criminalintent;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import java.util.List;
import java.util.UUID;


import android.os.Bundle;
import android.support.annotation.Nullable;


/**
 * Created by Mohamed Amr on 6/25/2018.
 */

public class CrimeListAdapter extends RecyclerView.Adapter<CrimeListAdapter.CrimeListView> {
    private List<Crime> mCrimes;
    private Context mContext;

    final private listItemClickListener mClickListener;
    public interface listItemClickListener{
        void OnItemClicked (int itempostion);
    }

    public CrimeListAdapter(List<Crime> crimes ,Context context ) {
        mCrimes = crimes;
        mContext = context;
        mClickListener = (listItemClickListener) context;
    }

    @Override
    public CrimeListView onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.crime_list_item, parent, false);
        return new CrimeListView(view);
    }

    @Override
    public void onBindViewHolder(CrimeListView holder, int position) {

        Crime crime = mCrimes.get(position);
        holder.mTitleTextView.setText(crime.getTitle());
        holder.mDateTextView.setText(crime.getDate().toString());
        holder.mSolvedCheckBox.setChecked(crime.isSolved());


    }

    @Override
    public int getItemCount() {
        return mCrimes.size();
    }

    public void SetCrimes(List<Crime> crimes){

        mCrimes = crimes;
        this.notifyDataSetChanged();
    }

    public class CrimeListView extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitleTextView;
        TextView mDateTextView;
        CheckBox mSolvedCheckBox;

        public CrimeListView(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox =(CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

        @Override
        public void onClick(View view) {

             int ClickedPosition = getAdapterPosition();
             mClickListener.OnItemClicked(ClickedPosition);

        }

    }

}
