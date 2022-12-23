package com.example.organdonation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class OrganView extends AppCompatActivity implements JsonResponse {

    String[] value,organname,organdetails;
    ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organ_view);

        l1=(ListView) findViewById(R.id.vieworgans);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)OrganView.this;
        String q="/vieworgans";
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
                organname = new String[ja1.length()];
                organdetails = new String[ja1.length()];

                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    organname[i] = ja1.getJSONObject(i).getString("organ_name");
                    organdetails[i] = ja1.getJSONObject(i).getString("organ_details");

                    value[i] = "Organ Name : " + organname[i] + "\nDetails : " + organdetails[i];

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