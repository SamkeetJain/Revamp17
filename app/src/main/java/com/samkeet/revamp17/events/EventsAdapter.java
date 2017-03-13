package com.samkeet.revamp17.events;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.samkeet.revamp17.R;
import com.samkeet.revamp17.myboom.CardStackView;
import com.samkeet.revamp17.myboom.StackAdapter;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Frost on 11-03-2017.
 */

public class EventsAdapter extends StackAdapter<Integer> {

    private Context context;
    private JSONArray events;

    public EventsAdapter(Context context, JSONArray events) {
        super(context);
        this.context = context;
        this.events = events;
    }

    @Override
    public void bindView(Integer data, int position, CardStackView.ViewHolder holder) {
        ColorItemViewHolder h = (ColorItemViewHolder) holder;
        h.onBind(data, position);
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.list_card_item, parent, false);
        return new ColorItemViewHolder(view, events);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.list_card_item;
    }

    public static class ColorItemViewHolder extends CardStackView.ViewHolder {
        JSONArray events;
        View mLayout;
        View mContainerContent;
        Button mRegBtn;
        TextView mEvent_name, mEvent_Prize1, mEvent_Prize2, mEvent_Reg_Fee, mEvent_Rules;

        public ColorItemViewHolder(View view, JSONArray events) {
            super(view);
            this.events = events;
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mRegBtn = (Button) view.findViewById(R.id.reg_btn);
            mEvent_name = (TextView) view.findViewById(R.id.event_name);
            mEvent_Prize1 = (TextView) view.findViewById(R.id.event_prize1);
            mEvent_Prize2 = (TextView) view.findViewById(R.id.event_prize2);
            mEvent_Reg_Fee = (TextView) view.findViewById(R.id.event_reg_fee);
            mEvent_Rules = (TextView) view.findViewById(R.id.event_rule);
        }

        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

        public void onBind(Integer data, final int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);
            try {
                String name, prize1, prize2, reg_fee, rule;
                name = events.getJSONObject(position).getString("Event_Name");
                prize1 = "\u20B9" + " " + events.getJSONObject(position).getString("Event_Prize1");
                prize2 = "\u20B9" + " " + events.getJSONObject(position).getString("Event_Prize2");
                reg_fee = "Reg Fee   " + "\u20B9" + " " + events.getJSONObject(position).getString("Event_Reg_Fee");
                rule = events.getJSONObject(position).getString("Event_Rules");
                mEvent_name.setText(name);
                mEvent_Prize1.setText(prize1);
                mEvent_Prize2.setText(prize2);
                mEvent_Reg_Fee.setText(reg_fee);
                mEvent_Rules.setText(rule);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mRegBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext().getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}
