package com.example.organdonation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Bloodview extends AppCompatActivity implements JsonResponse {
    String[] value,bloodname,blooddetails;
    ListView l1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodview);

        l1=(ListView) findViewById(R.id.viewblood);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Bloodview.this;
        String q="/viewblood";
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
                blooddetails = new String[ja1.length()];
                bloodname = new String[ja1.length()];

                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    bloodname[i] = ja1.getJSONObject(i).getString("group_name");
                    blooddetails[i] = ja1.getJSONObject(i).getString("group_description");

                    value[i] = "BloodGroup Name : " + bloodname[i] + "\nDetails : " + blooddetails[i];

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