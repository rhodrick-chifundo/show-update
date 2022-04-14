package com.showupdate.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.showupdate.app.config.config;

import org.json.JSONException;

import java.math.BigDecimal;

public class Paypal extends AppCompatActivity {
    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config1 = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(config.PAYPAL_CLIENT_ID);
    Button paynow;
    EditText ettamount;
    String amount= "";

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config1);
        startService(intent);



        paynow = (Button)findViewById(R.id.pay);
        ettamount = (EditText)findViewById(R.id.edamount);

        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processpayment();
            }
        });

    }

    private void processpayment() {
        amount = ettamount.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD", "my contribution", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config1);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
        //Donate for EDMTDev
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if(requestCode == PAYPAL_REQUEST_CODE){

           if(resultCode == RESULT_OK){
               PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
               if(confirmation != null){
                  try {
                      String paymentDetails = confirmation.toJSONObject().toString(4);
                      startActivity(new Intent(this, paymentDetails.class)

                                               .putExtra("paymentDetails", paymentDetails)
                                               .putExtra("paymentAmount", amount)

                                   );


                  }catch (JSONException e){
                      e.printStackTrace();

                  }
               }
           }
           else if(requestCode == Activity.RESULT_CANCELED)
               Toast.makeText(this,"cancel", Toast.LENGTH_LONG).show();
       }
       else if(requestCode == PaymentActivity.RESULT_EXTRAS_INVALID)
           Toast.makeText(this, "Invalid", Toast.LENGTH_LONG).show();









    }
}