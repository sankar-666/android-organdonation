package com.example.organdonation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReceiverHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_home);

        Button b1=(Button) findViewById(R.id.reqbtn);
        Button b2=(Button) findViewById(R.id.reqq);

        Button b3=(Button) findViewById(R.id.log);
        Button b4=(Button) findViewById(R.id.changerecpass);

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RecChangePass.class));
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });



            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final CharSequence[] items = {"Request for Blood.", "Request for Organs."};

                    AlertDialog.Builder builder = new AlertDialog.Builder(ReceiverHome.this);
                    // builder.setTitle("Add Photo!");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {

                            if (items[item].equals("Request for Blood.")) {
                                startActivity(new Intent(getApplicationContext(),ReceiverviewReq.class));}


                            else if (items[item].equals("Request for Organs.")) {
                                startActivity(new Intent(getApplicationContext(),ViewRequestForOrgan.class));}
                        }



                    });
                    builder.show();

                }
            });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Request for Blood.", "Request for Organs."};

                AlertDialog.Builder builder = new AlertDialog.Builder(ReceiverHome.this);
                // builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Request for Blood.")) {
                            startActivity(new Intent(getApplicationContext(),BloodReqAdd.class));}


                        else if (items[item].equals("Request for Organs.")) {
                            startActivity(new Intent(getApplicationContext(),OrganReqAdd.class));}
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
        Intent b=new Intent(getApplicationContext(),ReceiverHome.class);
        startActivity(b);
    }
}