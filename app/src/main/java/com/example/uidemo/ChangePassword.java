package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class ChangePassword extends AppCompatActivity {

    TextInputLayout password_layout, confirm_password_layout;
    public void changePasswordClicked(View view){
        String password = password_layout.getEditText().getText().toString();
        String confirm_password = confirm_password_layout.getEditText().getText().toString();
        if(validatePasswords(password, confirm_password)){
            Toast.makeText(getApplicationContext(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Password do not match!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validatePasswords(String pass, String confirm_pass) {
        return pass.equals(confirm_pass);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        password_layout= findViewById(R.id.password_layout);
        confirm_password_layout= findViewById(R.id.confirmpassword_layout);

        password_layout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String pass  = password_layout.getEditText().getText().toString().trim();
                checkPassword(password_layout, pass);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });


        confirm_password_layout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String pass = confirm_password_layout.getEditText().getText().toString().trim();
                checkPassword(confirm_password_layout, pass);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }

    private void checkPassword(TextInputLayout layout, String pass) {
        if(pass.length()<8)
            layout.setError("Password length can't be less than 8");
        else
            layout.setError(null);
    }

    public void openhhomepage(View view) {
        Intent intent = new Intent(getApplicationContext(),ForgotPasswordPage.class);
        startActivity(intent);
    }
}