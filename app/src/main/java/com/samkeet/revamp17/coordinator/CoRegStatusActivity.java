package com.samkeet.revamp17.coordinator;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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

public class CoRegStatusActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public SwipeRefreshLayout swipeRefreshLayout;

    private ImageView mSendBtn;
    private EditText mMobileNo;
    private TextInputLayout tMobileNo;
    private String mobileno;
    private JSONArray jsonArray;

    private SpotsDialog pd;
    private Context progressDialogContext;
    private boolean authenticationError = true;
    private String errorMessage = "Data Corrupted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co_reg_status);
        progressDialogContext = this;

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        mMobileNo = (EditText) findViewById(R.id.input_mobileno);
        tMobileNo = (TextInputLayout) findViewById(R.id.text_mobileno);

        mSendBtn = (ImageView) findViewById(R.id.send_button);
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.Methods.networkState(getApplicationContext(), (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE))) {
                    mobileno = mMobileNo.getText().toString();
                    GetRegStatus getRegStatus = new GetRegStatus();
                    getRegStatus.execute();
                }
            }
        });

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(CoRegStatusActivity.this);


                    String details = "";
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(temp);
                        details = "Registration ID: " + jsonObject.getString("Registration_ID") + "\n"
                                + "Registration Event ID: " + jsonObject.getString("Registration_Event_ID") + "\n"
                                + "Registration Event Name: " + jsonObject.getString("Registration_Event_Name") + "\n"
                                + "Registration User MobileNo: " + jsonObject.getString("Registration_User_MobileNo") + "\n"
                                + "Registration User Name: " + jsonObject.getString("Registration_User_Name") + "\n"
                                + "Registration Payment to MobileNo: " + jsonObject.getString("Registration_Payment_To_MobileNo") + "\n"
                                + "Registration Payment to Name: " + jsonObject.getString("Registration_Payment_To_Name") + "\n"
                                + "Registration Payment Status: " + jsonObject.getString("Registration_Payment_Status") + "\n"
                                + "Registration Fee: " + jsonObject.getString("Registration_Fee") + "\n"
                                + "Registration Timestamp: " + jsonObject.getString("Registration_Time") + "\n"
                                + "Registration Status: " + jsonObject.getString("Registration_Status");
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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Constants.Methods.networkState(getApplicationContext(), (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE))) {
                    mobileno = mMobileNo.getText().toString();
                    GetRegStatus getRegStatus = new GetRegStatus();
                    getRegStatus.execute();
                }
            }
        });
    }

    public void BackButton(View v) {
        finish();
    }

    private class GetRegStatus extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {


                URL url = new URL(Constants.URLs.BASE + Constants.URLs.REGISTRATION);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                Uri.Builder _data = new Uri.Builder().appendQueryParameter("token", Constants.SharedPreferenceData.getTOKEN())
                        .appendQueryParameter("requestType", "get")
                        .appendQueryParameter("U_MobileNo", mobileno);
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
                    mAdapter = new CoRegStatusAdapter(jsonArray);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        }
    }

}
