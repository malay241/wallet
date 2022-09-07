package com.example.uidemo;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class SendMoney extends AppCompatActivity {

    Button sendMoneyNextButton;
    TextInputLayout phoneLayout, amountLayout;
    boolean amount_flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);
        phoneLayout = findViewById(R.id.phoneTextInputLayout);
        amountLayout = findViewById(R.id.amount_layout);


        phoneLayout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String phone  = phoneLayout.getEditText().getText().toString().trim();
                validatePhone(phone);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        amountLayout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String amount  = amountLayout.getEditText().getText().toString().trim();
                validateAmount(amount);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }

    public void opensendmoneyreview(View view) {
        String phone = phoneLayout.getEditText().getText().toString();
        String amount = amountLayout.getEditText().getText().toString();
        if(validatePhone(phone) && validateAmount(amount)){
            double billAmount =Double.parseDouble(amountLayout.getEditText().getText().toString());
            double walletAmount = 1000;
            if(billAmount > walletAmount){
                Toast.makeText(this, "Add money to continue!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AddMoney.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(getApplicationContext(), SendMoneyReview.class);
                startActivity(intent);
            }
        }
        else{
            Toast.makeText(this, "Some error occurred!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateAmount(String amount) {
        if(amount.trim().equals("")){
            amountLayout.setError("Amount can not be left empty");
            return false;
        }
        else if(Double.parseDouble(amount)>5000) {
            amountLayout.setError("Amount can not be greater than 5000");
            return false;
        }
        else {
            amountLayout.setError(null);
            return true;
        }
    }

    private boolean validatePhone(String phone) {

        if(phone.trim().equals("")){
            phoneLayout.setError("Mobile can't be left empty!");
            return false;
        }
        else if (Pattern.matches("^[6-9][0-9]{9}$",phone) && phone.trim().length()==10 && android.util.Patterns.PHONE.matcher(phone).matches()) {
            phoneLayout.setError(null);
            return true;
        }
        else{
            phoneLayout.setError("Enter valid mobile number!");
            return false;
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
}