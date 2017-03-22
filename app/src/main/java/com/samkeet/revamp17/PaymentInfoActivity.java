package com.samkeet.revamp17;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

public class PaymentInfoActivity extends AppCompatActivity {

    public Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_info);

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());

                builder.setTitle("Payment Coordinator List");
                builder.setMessage("Organising team\nSmrithi Menon 8197365277\n\nCultural events\nHeba 9738327413\n\nTechnical events\nSachin 7829041181\n\nTechnical support\nSamkeet Jain 8147514179");

                String positiveText = "Okay";
                builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    public void BackButton(View v) {
        finish();
    }
}
