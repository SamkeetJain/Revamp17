package com.samkeet.revamp17.coordinator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samkeet.revamp17.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Frost on 13-03-2017.
 */

public class CoCPassbookAdapter extends RecyclerView.Adapter<CoCPassbookAdapter.ViewHolder> {

    private String[] mPhoneno, mAmount, mDates, mNames;
    private JSONObject[] object;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mPhonenoView, mAmountView, mDateView, mNameView;

        public ViewHolder(View v) {
            super(v);
            mPhonenoView = (TextView) v.findViewById(R.id.phoneno);
            mAmountView = (TextView) v.findViewById(R.id.amount);
            mDateView = (TextView) v.findViewById(R.id.datetext);
            mNameView = (TextView) v.findViewById(R.id.name);
        }
    }

    public CoCPassbookAdapter(JSONArray jsonArray) {
        try {
            mPhoneno = new String[jsonArray.length()];
            mAmount = new String[jsonArray.length()];
            mDates = new String[jsonArray.length()];
            mNames = new String[jsonArray.length()];
            object = new JSONObject[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                object[i] = jsonObject;
                mPhoneno[i] = jsonObject.getString("Payment_Guest_MobileNo");
                mAmount[i] = jsonObject.getString("Payment_Amounts");
                mDates[i] = jsonObject.getString("Payment_Timestamp");
                mNames[i] = jsonObject.getString("Payment_Guest_Name");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CoCPassbookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_passbook, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String a = "Amount: " + mAmount[position];
        String t = "Timestamp: " + mDates[position];
        holder.mPhonenoView.setText(mPhoneno[position]);
        holder.mAmountView.setText(a);
        holder.mDateView.setText(t);
        holder.mNameView.setText(mNames[position]);
    }

    @Override
    public int getItemCount() {
        return mPhoneno.length;
    }


}
