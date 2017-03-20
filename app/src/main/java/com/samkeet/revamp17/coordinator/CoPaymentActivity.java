package com.samkeet.revamp17.coordinator;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class CoPaymentActivity extends AppCompatActivity {

    public TextView myAccountTextView, myRegTextView;
    public Context progressDialogContext;
    public SpotsDialog pd;
    public boolean authenticationError = true;
    public String errorMessage = "Data Corrupted";
    public String account, reg;
    public Button mCPassbook, mTPassbook, mCollectPayment,mTransferPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co_payment);
        progressDialogContext = this;

        myAccountTextView = (TextView) findViewById(R.id.myAccountTextView);
        myRegTextView = (TextView) findViewById(R.id.myRegTextView);

        mCPassbook = (Button) findViewById(R.id.cpassbook);
        mCPassbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CoCPassbookActivity.class);
                startActivity(intent);
            }
        });

        mCollectPayment = (Button) findViewById(R.id.collectButton);
        mCollectPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CoCollectPaymentActivity.class);
                startActivity(intent);
            }
        });

        mTransferPayment = (Button) findViewById(R.id.transferbutton);
        mTransferPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CoTransferActivity.class);
                startActivity(intent);
            }
        });

        mTPassbook = (Button) findViewById(R.id.tpassbook);
        mTPassbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CoTPassbookActivity.class);
                startActivity(intent);
            }
        });
        if (Constants.Methods.networkState(getApplicationContext(), (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE))) {
            GetDetails getDetails = new GetDetails();
            getDetails.execute();
        }
    }

    public void BackButton(View v) {
        finish();
    }

    private class GetDetails extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {


                URL url = new URL(Constants.URLs.BASE + Constants.URLs.PAYMENT_REG);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                Uri.Builder _data = new Uri.Builder().appendQueryParameter("token", Constants.SharedPreferenceData.getTOKEN())
                        .appendQueryParameter("requestType", "getdetails");
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
                    JSONObject jsonObject = new JSONObject(jsonResults.toString());
                    account = jsonObject.getString("Balance");
                    reg = jsonObject.getString("count");
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

            if (authenticationError) {
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                myAccountTextView.setText(account);
                myRegTextView.setText(reg);
            }
        }
    }

}
