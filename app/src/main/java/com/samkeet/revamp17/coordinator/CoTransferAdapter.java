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
 * Created by Sam on 18-Mar-17.
 */

public class CoTransferAdapter extends RecyclerView.Adapter<CoTransferAdapter.ViewHolder> {
    private JSONArray jsonArray;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mPhoneView, mNameView;

        public ViewHolder(View v) {
            super(v);
            mPhoneView = (TextView) v.findViewById(R.id.phoneno);
            mNameView = (TextView) v.findViewById(R.id.name);
        }
    }

    public CoTransferAdapter(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    @Override
    public CoTransferAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_admin, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            holder.mPhoneView.setText(jsonArray.getJSONObject(position).getString("User_MobileNo"));
            holder.mNameView.setText(jsonArray.getJSONObject(position).getString("User_Name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }
}