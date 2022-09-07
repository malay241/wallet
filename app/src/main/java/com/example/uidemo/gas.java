package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class gas extends AppCompatActivity {

    TextInputLayout phone_Layout, amount_Layout;
    Boolean flaggas, flagamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas);

        phone_Layout = findViewById(R.id.phoneTextInputLayout);
        amount_Layout = findViewById(R.id.amount_layout);

        phone_Layout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String phone = phone_Layout.getEditText().getText().toString().trim();
                flaggas = validatePhone(phone);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
        amount_Layout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String amount = amount_Layout.getEditText().getText().toString().trim();
                flagamount = validateAmount(amount);
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
        if (amount.trim().equals("")) {
            amount_Layout.setError("Amount can not be left empty");
            return false;
        } else if (Double.parseDouble(amount) > 5000) {
            amount_Layout.setError("Amount can not be greater than 5000");
            return false;
        } else {
            amount_Layout.setError(null);
            return true;
        }
    }

    private boolean validatePhone(String phone) {

        if (phone.trim().equals("")) {
            phone_Layout.setError("Customer ID can't be left empty!");
            return false;
        } else if (Pattern.matches("^[4-9][0-9]{9}$", phone) && phone.trim().length() == 10 && android.util.Patterns.PHONE.matcher(phone).matches()) {
            phone_Layout.setError(null);
            return true;
        } else {
            phone_Layout.setError("Enter valid customer ID!");
            return false;
        }
    }

    public void openhome(View view) {
        Intent intent = new Intent(getApplicationContext(), BillPaymentsPage.class);
        startActivity(intent);
    }

    public void openlogout(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
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

    public void openbillreview(View view) {
        if (flagamount && flaggas) {
            Intent intent = new Intent(getApplicationContext(), GasBillReview.class);
            startActivity(intent);
        }
    }
}
