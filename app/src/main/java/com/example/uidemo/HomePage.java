package com.example.uidemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class HomePage extends AppCompatActivity {
    ImageView sendMoney, billpayment;
    TextView userNameTextview, walletAmountTextview;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        userNameTextview = findViewById(R.id.home_name);
        walletAmountTextview = findViewById(R.id.home_wallet);
        sendMoney = findViewById(R.id.login_send);
        billpayment = findViewById(R.id.login_bill);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                userNameTextview.setText(value.getString("first_name") + " " + value.getString("last_name"));
                walletAmountTextview.setText(value.getString("wallet"));
            }
        });
//        sendMoney.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getdata();
//            }
//        });
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
            fAuth.getInstance().signOut();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void openwallet(View view) {
        Intent intent = new Intent(getApplicationContext(), AddMoney.class);
        startActivity(intent);
    }

    public void opensendMoney(View view) {
        Intent intent = new Intent(getApplicationContext(),SendMoney.class);
        startActivity(intent);
    }

    public void openbillpayment(View view) {
        Intent intent = new Intent(getApplicationContext(),BillPaymentsPage.class);
        startActivity(intent);
    }


    public void opensplitpay(View view) {
    Intent intent = new Intent(getApplicationContext(),SplitPayPage.class);
    startActivity(intent);

    }
    @Override
    public void onBackPressed() { myAlert(HomePage.this);}
    public void myAlert(Context mContext)
    {
        new AlertDialog.Builder(mContext)
                .setTitle("Logout?")
                .setMessage("Do you want to logout?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                        fAuth.getInstance().signOut();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }
//    private void getdata() {
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                String value = snapshot.getValue(String.class);
//                if (value.equals("0")) {
//                    Intent intent = new Intent(getApplicationContext(), AddMoney.class);
//                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(getApplicationContext(), SendMoney.class);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}