package com.showupdate.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class paymentDetails extends AppCompatActivity {
TextView textId, textStatus, texAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        textId = (TextView)findViewById(R.id.txtId);
        textStatus = (TextView)findViewById(R.id.txtStatus);
        texAmount = (TextView)findViewById(R.id.txtAmount);

        Intent intent = getIntent();
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("paymentDetails"));
            showDetails(jsonObject.getJSONObject("response"), intent.getStringExtra("paymentAmount"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }

    private void showDetails(JSONObject response, String paymentAmount) {
        try {
            textId.setText(response.getString("id"));
            textStatus.setText(response.getString("state"));
            texAmount.setText("$" + paymentAmount);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}