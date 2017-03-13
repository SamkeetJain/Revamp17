package com.samkeet.revamp17.coordinator;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.samkeet.revamp17.Constants;
import com.samkeet.revamp17.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import dmax.dialog.SpotsDialog;

public class CoTPassbookActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SpotsDialog pd;
    private Context progressDialogContext;
    private boolean authenticationError = true;
    private String errorMessage = "Data Corrupted";
    private JSONArray jsonArray;
    public SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co_tpassbook);

        progressDialogContext = this;

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        final GestureDetector mGestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent motionEvent) {

                View child = mRecyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    int temp = mRecyclerView.getChildPosition(child);
                    AlertDialog.Builder builder = new AlertDialog.Builder(CoTPassbookActivity.this);


                    String details = "";
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(temp);
                        details = "Payment ID: " + jsonObject.getString("Payment_ID") + "\n"
                                + "Payment Admin MobileNo: " + jsonObject.getString("Payment_Admin_MobileNo") + "\n"
                                + "Payment Admin Name: " + jsonObject.getString("Payment_Admin_Name") + "\n"
                                + "Payment Coordinator MobileNo: " + jsonObject.getString("Payment_Coordinator_MobileNo") + "\n"
                                + "Payment Coordinator Name: " + jsonObject.getString("Payment_Coordinator_Name") + "\n"
                                + "Payment Amounts: " + jsonObject.getString("Payment_Amounts") + "\n"
                                + "Payment Timestamp: " + jsonObject.getString("Payment_Timestamp");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    builder.setTitle("Status");
                    builder.setMessage(details);

                    String positiveText = "Okay";
                    builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        if (Constants.Methods.networkState(getApplicationContext(), (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE))) {
            GetPassbook getPassbook = new GetPassbook();
            getPassbook.execute();
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Constants.Methods.networkState(getApplicationContext(), (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE))) {
                    GetPassbook getPassbook = new GetPassbook();
                    getPassbook.execute();
                }
            }
        });
    }

    private class GetPassbook extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {


                URL url = new URL(Constants.URLs.BASE + Constants.URLs.PAYMENT_TRANSFER);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                Log.d("POST", "DATA ready to sent");

                Uri.Builder _data = new Uri.Builder().appendQueryParameter("token", Constants.SharedPreferenceData.getTOKEN())
                        .appendQueryParameter("requestType", "t_payment");
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
                    jsonArray = new JSONArray(jsonResults.toString());
                }
                return 1;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return 1;
        }

        protected void onPostExecute(Integer result) {
            if (pd != null) {
                pd.dismiss();
            }

            swipeRefreshLayout.setRefreshing(false);
            if (authenticationError) {
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                if (jsonArray.length() > 0) {
                    mAdapter = new CoTPassbookAdapter(jsonArray);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        }
    }

    public void BackButton(View v) {
        finish();
    }

}
