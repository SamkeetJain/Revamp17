package com.samkeet.revamp17.coordinator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samkeet.revamp17.R;

/**
 * Created by Frost on 14-03-2017.
 */

public class CoRegListAdapter extends RecyclerView.Adapter<CoRegListAdapter.ViewHolder> {

    private String[] mPhone, mAmount, mDates, mNames;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mPhoneView, mAmountView, mDateView, mNameView;

        public ViewHolder(View v) {
            super(v);
            mPhoneView = (TextView) v.findViewById(R.id.phoneno);
            mAmountView = (TextView) v.findViewById(R.id.amount);
            mDateView = (TextView) v.findViewById(R.id.datetext);
            mNameView = (TextView) v.findViewById(R.id.name);
        }
    }

    public CoRegListAdapter(String[] mPhone, String[] mAmount, String[] mDates, String[] mNames) {
        this.mPhone = mPhone;
        this.mAmount = mAmount;
        this.mDates = mDates;
        this.mNames = mNames;
    }

    @Override
    public CoRegListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_passbook, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mPhoneView.setText(mPhone[position]);
        holder.mAmountView.setText(mAmount[position]);
        holder.mDateView.setText(mDates[position]);
        holder.mNameView.setText(mNames[position]);
    }

    @Override
    public int getItemCount() {
        return mPhone.length;
    }
}