package com.showupdate.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.showupdate.app.R;


public class MainActivity extends AppCompatActivity {
   // String sr1, sr2, sr3, sr4;
    EditText et1, et2 , et5, et6;
    Button check0, paipal;
    TextView t3,t4, t7;
    FirebaseDatabase database;
    DatabaseReference  check, check2, check3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText)findViewById(R.id.firEditText);
        et2 = (EditText)findViewById(R.id.secEditText);
        t3 = (TextView) findViewById(R.id.Mbal);
        t4 = (TextView) findViewById(R.id.Csbal);
        et5 = (EditText)findViewById(R.id.fifEditText);
        et6 = (EditText)findViewById(R.id.sixEditText);
        t7 = (TextView) findViewById(R.id.Casbill);
        check0 = (Button)findViewById(R.id.secBtn);
        paipal = (Button)findViewById(R.id.pepo);

        database = FirebaseDatabase.getInstance();
        check = database.getReference("BANK").child("OLDINALLY CUSTOMERS");
        check2 = database.getReference("BANK").child("MERCHANTS");
        check3 = database.getReference("MERCHANTS CUSTOMERS");


        check0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = et1.getText().toString();
                String s2 = et2.getText().toString();
                String s3 = et5.getText().toString();
                String s4 = et6.getText().toString();

                if(TextUtils.isEmpty(s1)){
                    et1.setError("enter merchant name");
                    et1.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(s2)){
                    et2.setError("enter customer username");
                    et2.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(s3)){
                    et5.setError("enter merchant name");
                    et5.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(s4)){
                    et6.setError("enter customer reference number");
                    et6.requestFocus();
                    return;
                }
                else {}
                String sr1 = et1.getText().toString();
                check2.child(sr1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String balance = snapshot.child("accountBalance").getValue().toString();
                        t3.setText(balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                String sr2 = et2.getText().toString();
                check.child(sr2).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String balance = snapshot.child("account balance").getValue().toString();
                        t4.setText(balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
               String sr3 = et5.getText().toString();
                String sr4 = et6.getText().toString();
                check3.child(sr3).child(sr4).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       String bill = snapshot.child("customerBill").getValue().toString();
                       t7.setText(bill);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        paipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Paypal.class);
                startActivity(intent);
            }
        });


    }
}