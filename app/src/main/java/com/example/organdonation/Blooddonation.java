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
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Blooddonation extends AppCompatActivity implements JsonResponse{

    String blooddetails;
    SharedPreferences sh;
    private ProgressBar loadingPB;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blooddonation);

        Button b1=(Button) findViewById(R.id.bloodbtn);
        EditText e1 = (EditText) findViewById(R.id.blooddtls);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        loadingPB = findViewById(R.id.idPBLoading);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                blooddetails = e1.getText().toString();


                if (blooddetails.equalsIgnoreCase("")){

                    e1.setError("Enter Blooddetails.");
                    e1.setFocusable(true);

                }
                else {


                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Blooddonation.this;
                    String q = "/blooddonation?log_id=" + sh.getString("log_id", "") + "&blooddetails=" + blooddetails;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                    loadingPB.setVisibility(View.VISIBLE);
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

                loadingPB.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Request sent succesfully....", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),DonorHome.class));




            }
            else {
                loadingPB.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Somthing Went Wrong...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),DonorHome.class));

            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }



}
}