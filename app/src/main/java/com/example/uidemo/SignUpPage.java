package com.example.uidemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpPage extends AppCompatActivity {
    public static final String TAG = "TAG";
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 4 characters
                    "$");

TextInputLayout first_name_layout, last_name_layout, email_layout, phone_layout, password_layout;
    boolean  flag_fname, flag_lname, flag_email, flag_phone, flag_pass;
    Button register;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        first_name_layout = findViewById(R.id.firstnameTextInputLayout);
        last_name_layout = findViewById(R.id.lastnameTextInputLayout);
        email_layout = findViewById(R.id.emailTextInputLayout);
        phone_layout = findViewById(R.id.phoneTextInputLayout);
        password_layout = findViewById(R.id.passwordTextInputLayout);
        register = findViewById(R.id.signUpbutton);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        if(fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),HomePage.class));
            finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_layout.getEditText().getText().toString().trim().toLowerCase(Locale.ROOT);
                String password = password_layout.getEditText().getText().toString().trim();
                String first_name = first_name_layout.getEditText().getText().toString().trim().toUpperCase(Locale.ROOT);
                String last_name = last_name_layout.getEditText().getText().toString().trim().toUpperCase(Locale.ROOT);
                String phone = phone_layout.getEditText().getText().toString().trim();
                String wallet = "0";
                if (flag_fname && flag_lname && flag_email && flag_pass && flag_phone) {
                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                              //  Toast.makeText(SignUpPage.this, "User Created", Toast.LENGTH_SHORT).show();
                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                Map<String,Object> user = new HashMap<>();
                                user.put("first_name",first_name);
                                user.put("last_name",last_name);
                                user.put("email",email);
                                user.put("phone",phone);
                                user.put("wallet",wallet);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                        Toast.makeText(SignUpPage.this, "Sucessfully ", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG,"onFailure: "+e.toString());
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), HomePage.class));
                            } else {
                                Toast.makeText(SignUpPage.this, "Error ! " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        first_name_layout.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    hideKeyboard(first_name_layout.getEditText());
                }
                return false;
            }
        });

        last_name_layout.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    hideKeyboard(last_name_layout.getEditText());
                }
                return false;
            }
        });

        email_layout.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    hideKeyboard(last_name_layout.getEditText());
                }
                return false;
            }
        });
        phone_layout.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    hideKeyboard(phone_layout.getEditText());
                }
                return false;
            }
        });
        password_layout.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    hideKeyboard(password_layout.getEditText());
                }
                return false;
            }
        });


        first_name_layout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String name  = first_name_layout.getEditText().getText().toString().trim();
                flag_fname = validateFirstName(name);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        last_name_layout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String name  = last_name_layout.getEditText().getText().toString().trim();
                flag_lname = validateLastName(name);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        email_layout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String email  = email_layout.getEditText().getText().toString().trim();
                flag_email = validateEmail(email);

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        phone_layout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String phone  = phone_layout.getEditText().getText().toString().trim();
                flag_phone = validatePhone(phone);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        password_layout.getEditText().addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String pass  = password_layout.getEditText().getText().toString();
                flag_pass = validatePassword(pass);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }

    private boolean validateFirstName(String fname) {
        if(fname.trim().equals("")){
            first_name_layout.setError("First Name can't be left empty!");
            return false;
        }
        else if (!Pattern.matches("^[A-Z a-z]\\w{2,50}$", fname)) {
            first_name_layout.setError("First name do not match pattern!");
            return false;
        }
        else{
            first_name_layout.setError(null);
            return  true;
        }
    }

    private boolean validateLastName(String lname) {
        if(lname.trim().equals("")){
            last_name_layout.setError("Last Name can't be left empty!");
            return false;
        }
        else if (!Pattern.matches("^[A-Z a-z]\\w{2,50}$", lname)) {
           last_name_layout.setError("First name do not match pattern!");
            return false;
        }
        else{
            last_name_layout.setError(null);
            return  true;
        }
    }

    private boolean validateEmail(String email) {
        if(email.trim().equals("")){
            email_layout.setError("Email can't be left empty!");
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

    private boolean validatePhone(String phone) {
        if(phone.trim().equals("")){
            phone_layout.setError("Mobile can't be left empty!");
            return false;
        }
        else if (Pattern.matches("^[6-9][0-9]{9}$",phone) && phone.trim().length()==10 && android.util.Patterns.PHONE.matcher(phone).matches()) {
            phone_layout.setError(null);
            return true;
        }
        else{
           phone_layout.setError("Enter valid mobile number!");
            return false;
        }
    }

    private boolean validatePassword(String pass) {
        if(pass.equals("")){
            password_layout.setError("Password can't be left empty!");
            return false;
        }
        else if(!PASSWORD_PATTERN.matcher(pass).matches()){
            password_layout.setError("Password should contain Capital letter, Small letter, Special character, Digit and alteast of 8 digit");
            return false;
        }
        else{
            password_layout.setError(null);
            return  true;
        }
    }

    private void hideKeyboard(EditText edittext){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edittext.getApplicationWindowToken(), 0);
    }
}