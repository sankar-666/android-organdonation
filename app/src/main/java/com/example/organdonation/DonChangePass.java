package com.example.organdonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class DonChangePass extends AppCompatActivity implements JsonResponse {
    String curpas,newpas,confpas;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_change_pass);

        sh= PreferenceManager.getDefaultSharedPreferences((getApplicationContext()));

        Button b1 = (Button) findViewById(R.id.paybtnn);

        EditText e1 = (EditText) findViewById(R.id.currentr);
        EditText e2 = (EditText) findViewById(R.id.newpassr);
        EditText e3 = (EditText) findViewById(R.id.conpassr);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curpas = e1.getText().toString();
                newpas = e2.getText().toString();
                confpas = e3.getText().toString();

                if (curpas.equalsIgnoreCase("")){

                    e1.setError("Enter your Current Password");
                    e1.setFocusable(true);

                }else if(newpas.equalsIgnoreCase("")){

                    e2.setError("Enter your New Password");
                    e2.setFocusable(true);
                }
                else if(confpas.equalsIgnoreCase("")){

                    e3.setError("Conform Your Password");
                    e3.setFocusable(true);
                }else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) DonChangePass.this;
                    String q = "/donchangepass?curpas=" + curpas + "&newpas=" + newpas+"&confpas="+confpas+"&log_id="+sh.getString("log_id","");
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }

            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {



                Toast.makeText(getApplicationContext(), "Your Password was Successfully changed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), DonorHome.class));


            }
            if (status.equalsIgnoreCase("mismatch")) {



                Toast.makeText(getApplicationContext(), "Password Missmatch... TRY AGAIN", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), DonChangePass.class));


            }
            if (status.equalsIgnoreCase("failed")) {



                Toast.makeText(getApplicationContext(), "Your Current Password is Incorrect...TRY AGAIN", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), DonChangePass.class));


            }

        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),DonorHome.class);
        startActivity(b);
    }
}