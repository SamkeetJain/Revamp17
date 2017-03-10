package com.samkeet.revamp17;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    public AppCompatButton mLoginButton, mSignupButton;
    public TextInputLayout tMobileno, tPassword;
    public String mobileno, password;
    public EditText mMobileno, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mMobileno = (EditText) findViewById(R.id.input_mobileno);
        mPassword = (EditText) findViewById(R.id.input_password);

        tMobileno = (TextInputLayout) findViewById(R.id.text_mobileno);
        tPassword = (TextInputLayout) findViewById(R.id.text_password);

        mLoginButton = (AppCompatButton) findViewById(R.id.btn_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valid()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        mSignupButton = (AppCompatButton) findViewById(R.id.link_signup);
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean valid() {
        mobileno = mMobileno.getText().toString().trim();
        password = mPassword.getText().toString().trim();

        if (mobileno.isEmpty() || mobileno.length() != 10) {
            tMobileno.setError("Enter 10 digits Mobile No");
            requestFocus(mMobileno);
            return false;
        } else {
            tMobileno.setErrorEnabled(false);
        }

        if (password.isEmpty()) {
            tPassword.setError("Enter valid Password");
            requestFocus(mPassword);
            return false;
        } else {
            tPassword.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
