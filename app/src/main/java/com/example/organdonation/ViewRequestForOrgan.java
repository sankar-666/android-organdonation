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

public class ViewRequestForOrgan extends AppCompatActivity implements JsonResponse {
    ListView l1;
    SharedPreferences sh;
    String[] statuss,value,date,unit,organname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request_for_organ);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=(ListView) findViewById(R.id.orgreq);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)ViewRequestForOrgan.this;
        String q="/vieworganrequest?log_id="+sh.getString("log_id","");
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

                date = new String[ja1.length()];
                statuss = new String[ja1.length()];

                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    organname[i] = ja1.getJSONObject(i).getString("organ_name");

                    date[i] = ja1.getJSONObject(i).getString("date_time");
                    statuss[i] = ja1.getJSONObject(i).getString("status");

                    value[i] = "You have requested for "+organname[i]+"  on  "+date[i]+"\nStatus : "+statuss[i];

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