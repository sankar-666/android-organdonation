package com.example.organdonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class OrganViewReq extends AppCompatActivity implements JsonResponse {
    ListView l1;
    SharedPreferences sh;
    String[] fname,lname,age,type,status,value,orgname,details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organ_view_req);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1 = findViewById(R.id.vvvv);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)OrganViewReq.this;
        String q="/vieworgreqdonor?log_id="+sh.getString("log_id","");
        q = q.replace(" ", "%20");
        JR.execute(q);



    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            Log.d("pearl", method);


            if (method.equalsIgnoreCase("vieworgreqdonor")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                fname = new String[ja1.length()];
                lname= new String[ja1.length()];
                age= new String[ja1.length()];
                type = new String[ja1.length()];

                status = new String[ja1.length()];
                details = new String[ja1.length()];
                orgname = new String[ja1.length()];

                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    fname[i] = ja1.getJSONObject(i).getString("first_name");
                    lname[i] = ja1.getJSONObject(i).getString("last_name");
                    age[i] = ja1.getJSONObject(i).getString("age");
                    type[i] = ja1.getJSONObject(i).getString("donation_type");
                    status[i] = ja1.getJSONObject(i).getString("status");
                    details[i] = ja1.getJSONObject(i).getString("details");
                    orgname[i] = ja1.getJSONObject(i).getString("name");



                    value[i] = "Name :   "+fname[i]+lname[i]+"\nRequest type : "+type[i]+"\nDetails : "+details[i]+"\nStatus : "+status[i]+"\n";


                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
            }
//            if (method.equalsIgnoreCase("donorupdatestatus")) {
//
//                Toast.makeText(getApplicationContext(), "your request noted succesfully...", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getApplicationContext(), DonorHome.class));
//
//
//            }
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"No Requests Currently", Toast.LENGTH_LONG).show();
        }
    }
}