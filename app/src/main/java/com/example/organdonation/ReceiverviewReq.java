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

import java.util.Stack;

public class ReceiverviewReq extends AppCompatActivity implements JsonResponse {

    ListView l1;
    SharedPreferences sh;
    String[] statuss,value,date,unit,groupname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiverview_req);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=(ListView) findViewById(R.id.vlreq);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)ReceiverviewReq.this;
        String q="/viewrequsets?log_id="+sh.getString("log_id","");
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
                groupname = new String[ja1.length()];
                unit = new String[ja1.length()];
                date = new String[ja1.length()];
                statuss = new String[ja1.length()];

                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    groupname[i] = ja1.getJSONObject(i).getString("group_name");
                    unit[i] = ja1.getJSONObject(i).getString("unit_request");
                    date[i] = ja1.getJSONObject(i).getString("date_time");
                    statuss[i] = ja1.getJSONObject(i).getString("status");

                    value[i] = "You have requested for "+groupname[i]+" blood of "+unit[i] +" units on  "+date[i]+"\nStatus : "+statuss[i];

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
            }
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}