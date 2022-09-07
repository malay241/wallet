package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class ForgotPasswordPage extends AppCompatActivity {

    Button verifyOTP, sendOtp;
    TextInputLayout email_layout, otp_layout;
    boolean validEmail_flag, verifiedEmail_flag;
    TextView countDownText, countDownTimerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page);

        sendOtp = findViewById(R.id.send_OTP_button);
        verifyOTP = findViewById(R.id.verify_otp_button);
        verifyOTP.setEnabled(false);
        otp_layout = findViewById(R.id.otp_layout);
        email_layout = findViewById(R.id.email_layout);
        countDownTimerText = findViewById(R.id.countdown_timer_text);
        countDownText = findViewById(R.id.countdown_timer);


        email_layout.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    hideKeyboard(email_layout.getEditText());
                }
                return false;
            }
        });


        otp_layout.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    hideKeyboard(otp_layout.getEditText());
                }
                return false;
            }
        });

        email_layout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                validateEmail();
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }

    public void send_otp_clicked(View view) {

        if (validateEmail() && verifyEmail()) {
            TextView otpText = findViewById(R.id.sent_otp_text);
            otpText.setVisibility(View.VISIBLE);
            verifyOTP.setEnabled(true);
            sendOtp.setText("Resend OTP");
            sendOtp.setEnabled(false);
            otpText.setVisibility(View.VISIBLE);
            countDownTimerText.setVisibility(View.VISIBLE);
            countDownText.setVisibility(View.VISIBLE);

            CountDownTimer countDownTimer = new CountDownTimer(30000,1000) {
                @Override
                public void onTick(long l) {
                    updateTimer(l/1000);
                }

                @Override
                public void onFinish() {
                    otpText.setText("OTP has been re-sent to your email address.");
                    countDownText.setVisibility(View.GONE);
                    countDownTimerText.setVisibility(View.GONE);
                    sendOtp.setEnabled(true);
                }
            }.start();
        }
    }

    public void verifyotpClicked(View view) {
        if (validateOTP()) {
            Intent intent = new Intent(this, ChangePassword.class);
            startActivity(intent);
        } else {
            otp_layout.setError("Invalid OTP");
        }
    }

    private void updateTimer(long l) {
       String secondsLeft;
        if(l<10)
             secondsLeft = "0"+ Long.toString(l);
        else
            secondsLeft = Long.toString(l);
        countDownText.setText("0 : " +secondsLeft+" seconds");
    }



    private boolean validateOTP() {
        String otp_entered = otp_layout.getEditText().getText().toString();
        if (otp_entered.equals("123456")) {
            return true;
        } else {
            otp_layout.setError("Invalid OTP!");
            return false;
        }
    }


    private boolean validateEmail() {
        String email  = email_layout.getEditText().getText().toString().trim();
        if(email.trim().equals("")){
            email_layout.setError("Please enter an email address");
            return false;
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_layout.setError("Invalid Email!");
            return false;
        }
        else{
                email_layout.setError(null);
                return true;
        }
    }

    private boolean verifyEmail() {
        String email  = email_layout.getEditText().getText().toString().trim();
        if(email.equalsIgnoreCase("abc@gmail.com"))             //API Call needed here to check if
            return true;                                                    //email address exists
        else {
            email_layout.setError("Email ID does not exist!");
            return false;
        }
    }


    private void hideKeyboard(EditText edittext){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edittext.getApplicationWindowToken(), 0);
    }

    public void openhhomepage(View view) {
        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}