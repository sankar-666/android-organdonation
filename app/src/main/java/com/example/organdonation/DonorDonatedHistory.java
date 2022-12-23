package com.example.organdonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class DonorDonatedHistory extends AppCompatActivity implements JsonResponse {

    ListView l1;
    SharedPreferences sh;
    String[] type,details,statuss,value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_donated_history);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=(ListView) findViewById(R.id.donatedhistory);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)DonorDonatedHistory.this;
        String q="/donatedhistory?log_id="+sh.getString("log_id","");
        q = q.replace(" ", "%20");
        JR.execute(q);


    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                type = new String[ja1.length()];

                details = new String[ja1.length()];
                statuss = new String[ja1.length()];

                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    type[i] = ja1.getJSONObject(i).getString("donation_type");

                    details[i] = ja1.getJSONObject(i).getString("details");
                    statuss[i] = ja1.getJSONObject(i).getString("status");

                    value[i] = "You , Donated "+type[i]+"\nDetails : "+details[i]+"\nStatus : "+statuss[i];

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
            }
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "You Have No Donated History", Toast.LENGTH_LONG).show();
        }
    }
}