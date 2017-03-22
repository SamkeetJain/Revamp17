package com.samkeet.revamp17.admin;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.samkeet.revamp17.Constants;
import com.samkeet.revamp17.R;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import dmax.dialog.SpotsDialog;

public class AddCoActivity extends AppCompatActivity {

    public TextInputLayout tMobileno, tPassword, tEmail, tFullName, tCpassword;
    public String mobileno, password, email, fullname, cpassword;
    public EditText mMobileno, mPassword, mEmail, mFullName, mCpassword;
    public Button mAdd;

    private SpotsDialog pd;
    private Context progressDialogContext;
    public boolean authenticationError = true;
    public String errorMessage = "Data Corrupted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_co);

        progressDialogContext = this;

        mMobileno = (EditText) findViewById(R.id.input_mobileno);
        tMobileno = (TextInputLayout) findViewById(R.id.text_mobileno);
        mPassword = (EditText) findViewById(R.id.input_password);
        tPassword = (TextInputLayout) findViewById(R.id.text_password);
        mCpassword = (EditText) findViewById(R.id.input_cpassword);
        tCpassword = (TextInputLayout) findViewById(R.id.text_cpassword);
        mEmail = (EditText) findViewById(R.id.input_email);
        tEmail = (TextInputLayout) findViewById(R.id.text_email);
        mFullName = (EditText) findViewById(R.id.input_fullname);
        tFullName = (TextInputLayout) findViewById(R.id.text_fullname);

        mAdd = (Button) findViewById(R.id.btn_signup);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valid()) {
                    if (Constants.Methods.networkState(getApplicationContext(), (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE))) {
                        AddCo addCo = new AddCo();
                        addCo.execute();
                    }
                }
            }
        });
    }

    private boolean valid() {
        mobileno = mMobileno.getText().toString().trim();
        email = mEmail.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        fullname = mFullName.getText().toString().trim();
        cpassword = mCpassword.getText().toString().trim();

        if (fullname.isEmpty() || fullname.length() > 64) {
            tFullName.setError("Enter valid name (64 char max)");
            requestFocus(mFullName);
            return false;
        } else {
            tFullName.setErrorEnabled(false);
        }

        if (mobileno.isEmpty() || mobileno.length() != 10) {
            tMobileno.setError("Enter 10 digits Mobile No");
            requestFocus(mMobileno);
            return false;
        } else {
            tMobileno.setErrorEnabled(false);
        }

        if (email.isEmpty() || !isValidEmail(email)) {
            tEmail.setError("Enter valid Email");
            requestFocus(mEmail);
            return false;
        } else {
            tEmail.setErrorEnabled(false);
        }

        if (password.isEmpty() || password.length() > 64) {
            tPassword.setError("Enter valid password (64 char max)");
            requestFocus(mPassword);
            return false;
        } else {
            tPassword.setErrorEnabled(false);
        }
        if (cpassword.isEmpty() || !(cpassword.equals(password))) {
            tCpassword.setError("The Password doesnot match");
            requestFocus(mCpassword);
            return false;
        } else {
            tCpassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class AddCo extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {

                URL url = new URL(Constants.URLs.BASE + Constants.URLs.COSIGNUP);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                Uri.Builder _data = new Uri.Builder().appendQueryParameter("Mobile", mobileno)
                        .appendQueryParameter("Request_Type", "signup")
                        .appendQueryParameter("Password", password)
                        .appendQueryParameter("Email", email)
                        .appendQueryParameter("Name", fullname);
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
                    JSONObject jsonObj = new JSONObject(jsonResults.toString());
                    String status = jsonObj.getString("status");
                    if (status.equals("success")) {
                        authenticationError = false;
                    } else {
                        authenticationError = true;
                        errorMessage = status;
                    }
                }

                return 1;
            } catch (FileNotFoundException | ConnectException | UnknownHostException ex) {
                authenticationError = true;
                errorMessage = "Connection to server terminated.\n Please check internet connection.";
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
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Successful ", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

}
