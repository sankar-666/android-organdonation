package com.example.organdonation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DonorHome extends AppCompatActivity {
    Button b1,b2,b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_home);

        b1=(Button) findViewById(R.id.donaton);
        b2=(Button) findViewById(R.id.donview);
        b3=(Button) findViewById(R.id.historybtn);

        Button b4=(Button) findViewById(R.id.lg) ;
        Button b5=(Button) findViewById(R.id.changepass) ;

        Button b6=(Button) findViewById(R.id.orgview) ;

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DonChangePass.class));

            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),OrganViewReq.class));

            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Donate Blood.", "Donate Organ."};

                AlertDialog.Builder builder = new AlertDialog.Builder(DonorHome.this);
                // builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Donate Blood.")) {
                            startActivity(new Intent(getApplicationContext(),Blooddonation.class));}


                        else if (items[item].equals("Donate Organ.")) {
                            startActivity(new Intent(getApplicationContext(),Organdonation.class));}
                    }



                });
                builder.show();



            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DonorViewBloodReq.class));
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Donated History.", "remaning donation history"};

                AlertDialog.Builder builder = new AlertDialog.Builder(DonorHome.this);
                // builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Donated History.")) {
                            startActivity(new Intent(getApplicationContext(),DonorDonatedHistory.class));}


                        else if (items[item].equals("remaning donation history")) {
                            startActivity(new Intent(getApplicationContext(),DonorRemaningDonationHistory.class));}
                    }



                });
                builder.show();
            }
        });
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),DonorHome.class);
        startActivity(b);
    }
}