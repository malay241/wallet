package com.example.uidemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    EditText emailEditText, passwordEditText;
    TextInputLayout emailTextLayout, passwordTextLayout;
    TextView forgetpassword;
    FirebaseAuth fAuth;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        passwordTextLayout = findViewById(R.id.login_password_layout);
        emailTextLayout = findViewById(R.id.login_email_layout);
        emailEditText = findViewById(R.id.login_email);
        passwordEditText = findViewById(R.id.login_password);
        forgetpassword = findViewById(R.id.login_forget);
        login = findViewById(R.id.login_login);
        fAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =emailEditText.getText().toString().trim().toLowerCase(Locale.ROOT);
                String password = passwordEditText.getText().toString().trim();
                if(TextUtils.isEmpty(email))
                {
                    emailEditText.setError("Email is required.");
                    return;
                }
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    emailEditText.setError("Invalid Email");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    passwordEditText.setError("Password is required.");
                    return;
                }
                if(password.length()<8)
                {
                    passwordEditText.setError("Password must be of 8 charachter ");
                    return;
                }
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this,"Logged in Succesfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomePage.class));
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Error! "+ task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset password?");
                passwordResetDialog.setTitle("Enter email to receive reset link. ");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //extract the email and send reset link
                        String mail = resetMail.getText().toString().trim().toLowerCase(Locale.ROOT);
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MainActivity.this,"Reset link sent to email",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,"Error! Reset link is not sent "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //close the dialog
                    }
                });

                passwordResetDialog.create().show();
            }
        });
    }
    public void opensignup(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpPage.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        myAlert(MainActivity.this);
    }

    public void myAlert(Context mContext) {
        new AlertDialog.Builder(mContext)
                .setTitle("Exits?")
                .setMessage("Do you want to exit Application?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


}