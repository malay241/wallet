package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class AddMoney extends AppCompatActivity {
    public static final String EXTRA_NAME = "com.example.uidemo.extra.Name";
    TextInputLayout amountLayout;
    boolean flag_amount;
    Button netbanking,card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        amountLayout = findViewById(R.id.amount_layout);
        netbanking = findViewById(R.id.addmoney_netbanking);
        card = findViewById(R.id.addmoney_credit);
        amountLayout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String amount  = amountLayout.getEditText().getText().toString().trim();
                flag_amount = validateAmount(amount);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

    }
    private boolean validateAmount(String amount) {
        if(amount.trim().equals("")){
            amountLayout.setError("Amount can not be left empty");
            return false;
        }
        else if(Double.parseDouble(amount)>10000) {
            amountLayout.setError("Amount can not be greater than 10000");
            return false;
        }
        else {
            amountLayout.setError(null);
            return true;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exmplemenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.profile)
        {
            Intent intent = new Intent(getApplicationContext(),profile.class);
            startActivity(intent);
        }
        if(id == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void opencard(View view) {
        if(flag_amount) {
            Intent intent = new Intent(getApplicationContext(), CardPage.class);
            startActivity(intent);
            String amountText = amountLayout.getEditText().getText().toString();
            intent.putExtra(EXTRA_NAME,amountText);
            startActivity(intent);
        }
        else
        {
            amountLayout.setError("Something went wrong");
        }
    }

    public void opennetbanking(View view) {
        if(flag_amount) {
            Intent intent = new Intent(getApplicationContext(), NetBanking.class);
            startActivity(intent);
            String amountText = amountLayout.getEditText().getText().toString();
            intent.putExtra(EXTRA_NAME,amountText);
            startActivity(intent);
        }
        else
        {
            amountLayout.setError("Something went wrong");
        }

    }
}