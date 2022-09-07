package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class CardPage extends AppCompatActivity {
    TextView amount;
    TextInputLayout cardNumberTextLayout,cardExpireTextLayout,cardCvvTextLayout;
    Boolean flag_cardNumber,flag_cardDate,flag_cardCvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_page);
        cardNumberTextLayout = findViewById(R.id.card_number_textLayout);
        cardExpireTextLayout = findViewById(R.id.carddate);
        cardCvvTextLayout = findViewById(R.id.cardcvv);





        amount = findViewById(R.id.card_amount);
        Intent intent = getIntent();
        String amu = intent.getStringExtra(AddMoney.EXTRA_NAME);
        amount.setText("Amount to be added = Rs "+amu);






        cardNumberTextLayout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String pass  = cardNumberTextLayout.getEditText().getText().toString();
                flag_cardNumber = validateCard(pass);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
        cardExpireTextLayout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String pass  = cardExpireTextLayout.getEditText().getText().toString();
                flag_cardDate = validateCardDate(pass);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
        cardCvvTextLayout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String pass  = cardCvvTextLayout.getEditText().getText().toString();
                flag_cardCvv = validateCardCvv(pass);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }
    private boolean validateCard(String pass) {
        if(pass.equals("")){
            cardNumberTextLayout.setError("Card Number can't be left empty!");
            return false;
        }
        else if(pass.length()!=16){
            cardNumberTextLayout.setError("Card number should be 16 digits!");
            return false;
        }
        else{
            cardNumberTextLayout.setError(null);
            return  true;
        }
    }
    private boolean validateCardDate(String pass) {
        if(pass.equals("")){
            cardExpireTextLayout.setError("Card Number can't be left empty!");
            return false;
        }
        else if(pass.length()!=4){
            cardExpireTextLayout.setError("Invalid Date");
            return false;
        }
        else{
            cardExpireTextLayout.setError(null);
            return  true;
        }
    }
    private boolean validateCardCvv(String pass) {
        if(pass.equals("")){
            cardCvvTextLayout.setError("Card Number can't be left empty!");
            return false;
        }
        else if(pass.length()!=3){
            cardCvvTextLayout.setError("Invalid CVV");
            return false;
        }
        else{
            cardCvvTextLayout.setError(null);
            return  true;
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

    public void openhhomepage(View view) {
        Intent intent =new Intent(getApplicationContext(),AddMoney.class);
        startActivity(intent);
    }
    public void openOTP(View view) {
        if(flag_cardNumber && flag_cardDate && flag_cardCvv)
        {
            Intent intent = new Intent(getApplicationContext(),cardOTP.class);
            startActivity(intent);
        }
    }
}