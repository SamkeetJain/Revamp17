package com.samkeet.revamp17.coordinator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samkeet.revamp17.R;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Frost on 14-03-2017.
 */

public class CoRegListAdapters extends RecyclerView.Adapter<CoRegListAdapters.ViewHolder> {

    private JSONArray jsonArray;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mPhoneView, mStatusView, mENameView, mNameView;

        public ViewHolder(View v) {
            super(v);
            mPhoneView = (TextView) v.findViewById(R.id.phoneno);
            mStatusView = (TextView) v.findViewById(R.id.amount);
            mENameView = (TextView) v.findViewById(R.id.datetext);
            mNameView = (TextView) v.findViewById(R.id.name);
        }
    }

    public CoRegListAdapters(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    @Override
    public CoRegListAdapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_passbook, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            holder.mPhoneView.setText(jsonArray.getJSONObject(position).getString("Registration_User_MobileNo"));
            holder.mStatusView.setText(jsonArray.getJSONObject(position).getString("Registration_Status"));
            holder.mENameView.setText(jsonArray.getJSONObject(position).getString("Registration_Event_Name"));
            holder.mNameView.setText(jsonArray.getJSONObject(position).getString("Registration_User_Name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }
}