package com.samkeet.revamp17.events;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.samkeet.revamp17.Constants;
import com.samkeet.revamp17.R;
import com.samkeet.revamp17.myboom.AllMoveDownAnimatorAdapter;
import com.samkeet.revamp17.myboom.CardStackView;
import com.samkeet.revamp17.myboom.UpDownAnimatorAdapter;
import com.samkeet.revamp17.myboom.UpDownStackAnimatorAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;


import dmax.dialog.SpotsDialog;

/**
 * Created by Frost on 11-03-2017.
 */

public class FirstFragment extends Fragment {

    private CardStackView mStackView;
    private EventsAdapter mEventsAdapter;
    private SpotsDialog pd;
    private Context progressDialogContext;
    private JSONArray culruralEvents;
    private boolean errordata = true;

    private static Integer[] TEST_DATAS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first_frag, container, false);

        progressDialogContext = getContext();

        mStackView = (CardStackView) v.findViewById(R.id.stackview_main);

        if (!Constants.Events.isCulturalAvalible) {
            GetEvents getEvents = new GetEvents();
            getEvents.execute();
        } else {
            loadData();
        }
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void loadData() {

        if (Constants.Events.isCulturalAvalible) {
            int count = Constants.Events.culturalEvents.length();
            TEST_DATAS = new Integer[count];
            TEST_DATAS = Constants.Methods.getColors(count);

            mEventsAdapter = new EventsAdapter(getActivity().getApplicationContext(), Constants.Events.culturalEvents, getActivity(), true);
            mStackView.setAdapter(mEventsAdapter);
            mEventsAdapter.updateData(Arrays.asList(TEST_DATAS));
            mStackView.setAnimatorAdapter(new UpDownAnimatorAdapter(mStackView));
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Error in loading data", Toast.LENGTH_SHORT).show();
        }
    }

    private class GetEvents extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {


                URL url = new URL(Constants.URLs.BASE + Constants.URLs.GET_EVENTS);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                Uri.Builder _data = new Uri.Builder().appendQueryParameter("Etype", "cultural");
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(_data.build().getEncodedQuery());
                writer.flush();
                writer.close();

                InputStreamReader in = new InputStreamReader(connection.getInputStream());
                StringBuilder jsonResults = new StringBuilder();
                // Load the results into a StringBuilder
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
                connection.disconnect();

                culruralEvents = new JSONArray(jsonResults.toString());
                errordata = false;

                return 1;

            } catch (JSONException ex) {
                ex.printStackTrace();
                errordata = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 1;
        }

        protected void onPostExecute(Integer result) {
            if (pd != null) {
                pd.dismiss();
            }

            if (errordata) {
                Toast.makeText(getActivity().getApplicationContext(), "Error in loading data", Toast.LENGTH_SHORT).show();
            } else {
                Constants.Events.culturalEvents = culruralEvents;
                Constants.Events.isCulturalAvalible = true;
                loadData();
            }

        }
    }
}
