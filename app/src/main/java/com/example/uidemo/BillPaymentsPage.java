package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BillPaymentsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payments_page);

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
    public void openhome(View view) {
        Intent intent = new Intent(getApplicationContext(),HomePage.class);
        startActivity(intent);
    }
    public void openlogout(View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void openmobile(View view) {
        Intent intent = new Intent(getApplicationContext(),mobile.class);
        startActivity(intent);
    }
    public void openelectric(View view) {
        Intent intent = new Intent(getApplicationContext(),electricity.class);
        startActivity(intent);
    }
    public void opengas(View view) {
        Intent intent = new Intent(getApplicationContext(),gas.class);
        startActivity(intent);
    }
    public void opentv(View view) {
        Intent intent = new Intent(getApplicationContext(),dth.class);
        startActivity(intent);
    }
}
