package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class BillPaymentsReviewPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payments_review_page);
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

    public void opentransaction(View view) {
        Intent intent = new Intent(getApplicationContext(),TransactionID.class);
        startActivity(intent);
    }

    public void openbillpayment(View view) {
        Intent intent = new Intent(getApplicationContext(),BillPaymentsPage.class);
        startActivity(intent);
    }

    public void openhome(View view) {
        Intent intent = new Intent(getApplicationContext(),BillPaymentsPage.class);
        startActivity(intent);
    }
}