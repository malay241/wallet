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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class NetBanking extends AppCompatActivity {
    TextView amount;
    TextInputLayout accountNumberLayout,reAccountNumberLayout,ifscBankingLayout;
    Boolean flag_accountNumber,flag_ifsc, flag_reaccountNumber;
    Button addmoney;
    RadioGroup banktype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_banking);
        accountNumberLayout = findViewById(R.id.accountNumber);
        reAccountNumberLayout = findViewById(R.id.reAccountNumber);
        ifscBankingLayout = findViewById(R.id.ifscNetbanking);
        addmoney = findViewById(R.id.netbanking_addmoney);
        banktype = findViewById(R.id.netbanking_banktype);


        amount = findViewById(R.id.net_amount);
        Intent intent = getIntent();
        String amu = intent.getStringExtra(AddMoney.EXTRA_NAME);
        amount.setText("Amount to be added = Rs "+amu);


        accountNumberLayout.getEditText().addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String pass  = accountNumberLayout.getEditText().getText().toString();
                flag_accountNumber = validateAccount(pass);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
        reAccountNumberLayout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String pass  = reAccountNumberLayout.getEditText().getText().toString();
                flag_reaccountNumber = validateReAccount(pass);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });


        ifscBankingLayout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String pass  = ifscBankingLayout.getEditText().getText().toString();
                flag_ifsc = validateIFSC(pass);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                banktype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {

                    }
                });
                if(flag_accountNumber && flag_reaccountNumber && flag_ifsc)
                {
                    
                }
            }
        });





    }
    private boolean validateAccount(String pass) {
        if(pass.equals("")){
            accountNumberLayout.setError("Account number can't be left empty!");
            return false;
        }
        else if(pass.length()!=15){
            accountNumberLayout.setError("Account number should be 15 digits!");
            return false;
        }
        else{
            accountNumberLayout.setError(null);
            return  true;
        }
    }
    private boolean validateReAccount(String pass) {
        if(pass.equals("")){
            reAccountNumberLayout.setError("Re Account can't be left empty!");
            return false;
        }
        else if(pass.length()!=15){
            reAccountNumberLayout.setError("Invalid Account Number");
            return false;
        }
        else{
            reAccountNumberLayout.setError(null);
            return  true;
        }
    }
    private boolean validateIFSC(String pass) {
        if(pass.equals("")){
            ifscBankingLayout.setError("IFSC can't be left empty!");
            return false;
        }
        else if(pass.length()!=11){
            ifscBankingLayout.setError("Invalid IFSC");
            return false;
        }
        else{
            ifscBankingLayout.setError(null);
            return  true;
        }
    }


    public void openhome(View view) {
        Intent intent = new Intent(getApplicationContext(),AddMoney.class);
        startActivity(intent);
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.netbanking_saving:
                if (checked)
                    Toast.makeText(this, "Saving", Toast.LENGTH_SHORT).show();
                    break;
            case R.id.netbanking_current:
                if (checked)
                    Toast.makeText(this, "Current", Toast.LENGTH_SHORT).show();
                    break;
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