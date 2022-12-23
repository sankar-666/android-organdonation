package com.example.organdonation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class DonorViewBloodReq extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView l1;
    SharedPreferences sh;
    String[] orgname,orgphone,orglicense,bloodgroup,unit,desc,statuss,value,mid;
 String message_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_view_blood_req);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=(ListView) findViewById(R.id.bloodview);
        l1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)DonorViewBloodReq.this;
        String q="/donorreqmessage";
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            Log.d("pearl", method);


            if (method.equalsIgnoreCase("donorreqmessage")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                orgname = new String[ja1.length()];
                mid = new String[ja1.length()];
                orgphone = new String[ja1.length()];
                orglicense = new String[ja1.length()];

                bloodgroup = new String[ja1.length()];
                unit = new String[ja1.length()];
                desc = new String[ja1.length()];
                statuss = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    mid[i] = ja1.getJSONObject(i).getString("message_id");
                    orgname[i] = ja1.getJSONObject(i).getString("name");
                    orgphone[i] = ja1.getJSONObject(i).getString("phone");
                    orglicense[i] = ja1.getJSONObject(i).getString("license_number");
                    bloodgroup[i] = ja1.getJSONObject(i).getString("group_name");
                    unit[i] = ja1.getJSONObject(i).getString("unit_required");
                    desc[i] = ja1.getJSONObject(i).getString("description");
                    statuss[i] = ja1.getJSONObject(i).getString("status");

                    value[i] = "Request for  "+bloodgroup[i]+"\nUnit Required : "+unit[i]+"\nDetails : "+desc[i]+"\nOrganisation Name : "+orgname[i]+"\nPhone No : "+orgphone[i]+"\nLisence No : "+orglicense[i]+"\nStatus : "+statuss[i];


                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
            }
            if (method.equalsIgnoreCase("donorupdatestatus")) {

                    Toast.makeText(getApplicationContext(), "your request noted succesfully...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DonorHome.class));


            }
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"No Requests Currently", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        message_id =mid[i];
        final CharSequence[] items = {"Donate Blood", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(DonorViewBloodReq.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Donate Blood")) {

                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse)DonorViewBloodReq.this;
                    String q="/donorupdatestatus?log_id="+sh.getString("log_id","")+"&mid="+message_id;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                }


                else if (items[item].equals("cancel")) {
                    dialog.dismiss();
                }
            }



        });
        builder.show();
    }
}