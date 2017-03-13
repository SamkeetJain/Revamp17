package com.samkeet.revamp17.events;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.samkeet.revamp17.Constants;
import com.samkeet.revamp17.R;
import com.samkeet.revamp17.myboom.CardStackView;
import com.samkeet.revamp17.myboom.StackAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.spec.ECField;

import dmax.dialog.SpotsDialog;

/**
 * Created by Frost on 11-03-2017.
 */

public class EventsAdapter extends StackAdapter<Integer> {

    private Context context;
    private JSONArray events;
    private Activity activity;

    public EventsAdapter(Context context, JSONArray events, Activity activity) {
        super(context);
        this.context = context;
        this.events = events;
        this.activity = activity;
    }

    @Override
    public void bindView(Integer data, int position, CardStackView.ViewHolder holder) {
        ColorItemViewHolder h = (ColorItemViewHolder) holder;
        h.onBind(data, position);
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.list_card_item, parent, false);
        return new ColorItemViewHolder(view, events, activity);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.list_card_item;
    }

    public static class ColorItemViewHolder extends CardStackView.ViewHolder {

        boolean authenticationError = true;
        String errorMessage = "Data Corrupted";
        SpotsDialog pd;
        Context progressDialogContext;
        JSONArray events;
        View mLayout;
        View mContainerContent;
        Button mRegBtn;
        TextView mEvent_name, mEvent_Prize1, mEvent_Prize2, mEvent_Reg_Fee, mEvent_Rules;

        public ColorItemViewHolder(View view, JSONArray events, Activity activity) {
            super(view);
            this.events = events;
            progressDialogContext = activity;
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mRegBtn = (Button) view.findViewById(R.id.reg_btn);
            mEvent_name = (TextView) view.findViewById(R.id.event_name);
            mEvent_Prize1 = (TextView) view.findViewById(R.id.event_prize1);
            mEvent_Prize2 = (TextView) view.findViewById(R.id.event_prize2);
            mEvent_Reg_Fee = (TextView) view.findViewById(R.id.event_reg_fee);
            mEvent_Rules = (TextView) view.findViewById(R.id.event_rule);

            checkeligibity();
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
                prize1 = " " + "\u20B9" + " " + events.getJSONObject(position).getString("Event_Prize1");
                prize2 = " " + "\u20B9" + " " + events.getJSONObject(position).getString("Event_Prize2");
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
                    try {

                        final JSONObject jsonObject = events.getJSONObject(position);
                        String details = "Event Name: " + jsonObject.getString("Event_Name") + "\n" + "Registration Fee: " + jsonObject.getString("Event_Reg_Fee");
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());

                        builder.setTitle("Confirm");
                        builder.setMessage("Please read complete events rules and regulations before confirming your registration.\n\nYou must do the payment alteast 24hrs before the start of the event to any of our payment co-ordinators\n\n" + details);

                        String positiveText = "Register";
                        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    register(jsonObject.getString("Event_ID"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        String negativeText = "Not now";
                        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        private void checkeligibity() {
            if (Constants.SharedPreferenceData.getTYPE().equals("GUEST")) {
                mRegBtn.setVisibility(View.VISIBLE);
            } else {
                mRegBtn.setVisibility(View.GONE);
            }
        }

        public void register(String Event_ID) {

            Registration registration = new Registration();
            String[] mParams = {Event_ID};
            registration.execute(mParams);
        }

        public void showRegistrationStatus(JSONObject jsonObject) {

            String event_id, event_name, details = "";
            try {
                event_id = jsonObject.getString("event_ID");
                event_name = jsonObject.getString("event_name");
                details = "Event ID: " + event_id + "\n" + "Event Name: " + event_name + "\nPayment Status: " + "PENDING\n" + "Registration Status: " + "PAYMENT_PENDING";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(progressDialogContext);

            builder.setTitle("Status");
            builder.setMessage(details);

            String positiveText = "Done";
            builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }

        private class Registration extends AsyncTask<String, Void, Integer> {

            JSONObject jsonObject;

            protected void onPreExecute() {
                pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
                pd.setTitle("Loading...");
                pd.setCancelable(false);
                pd.show();
            }

            protected Integer doInBackground(String... params) {
                try {
                    String Event_ID = params[0];

                    URL url = new URL(Constants.URLs.BASE + Constants.URLs.REGISTRATION);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    Log.d("POST", "DATA ready to sent");

                    Uri.Builder _data = new Uri.Builder().appendQueryParameter("token", Constants.SharedPreferenceData.getTOKEN())
                            .appendQueryParameter("requestType", "put")
                            .appendQueryParameter("RegE_ID", Event_ID)
                            .appendQueryParameter("U_MobileNo", Constants.SharedPreferenceData.getMobileNo());
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(_data.build().getEncodedQuery());
                    writer.flush();
                    writer.close();
                    Log.d("POST", "DATA SENT");

                    InputStreamReader in = new InputStreamReader(connection.getInputStream());
                    StringBuilder jsonResults = new StringBuilder();
                    // Load the results into a StringBuilder
                    int read;
                    char[] buff = new char[1024];
                    while ((read = in.read(buff)) != -1) {
                        jsonResults.append(buff, 0, read);
                    }
                    connection.disconnect();
                    Log.d("return from server", jsonResults.toString());

                    authenticationError = jsonResults.toString().contains("Authentication Error");

                    if (authenticationError) {
                        errorMessage = jsonResults.toString();
                    } else {
                        jsonObject = new JSONObject(jsonResults.toString());
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            authenticationError = false;
                        } else {
                            authenticationError = true;
                            errorMessage = status;
                        }
                    }
                    return 1;

                } catch (JSONException ex) {
                    ex.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return 1;
            }

            protected void onPostExecute(Integer result) {
                if (pd != null) {
                    pd.dismiss();
                }
                if (authenticationError) {
                    Toast.makeText(getContext().getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                } else {
                    showRegistrationStatus(jsonObject);
                }

            }
        }


    }

}
