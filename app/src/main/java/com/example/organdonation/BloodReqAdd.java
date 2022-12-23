package com.example.organdonation;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class BloodReqAdd extends AppCompatActivity implements JsonResponse, AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner s1;
    String[] groupid,group_name,group_description,value;
    Button b1;
    SharedPreferences sh;
    String date,id,pincode,quantity;
    EditText e1;
    private ProgressBar loadingPB;
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_req_add);

        loadingPB = findViewById(R.id.idPBLoading);



        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1=(Button) findViewById(R.id.mlbr);
        s1=(Spinner) findViewById(R.id.spinner4);

        s1.setOnItemSelectedListener(this);

        e1=(EditText) findViewById(R.id.datereq);
        EditText e2=(EditText) findViewById(R.id.pinc);
        EditText e3=(EditText) findViewById(R.id.quantity);
        e1.setOnClickListener(this);



        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)BloodReqAdd.this;
        String q="/viewblood";
        q = q.replace(" ", "%20");
        JR.execute(q);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                date=e1.getText().toString();
                pincode=e2.getText().toString();
                quantity=e3.getText().toString();

                if (date.equalsIgnoreCase("")){

                    e1.setError("Select Date");
                    e1.setFocusable(true);

                }else if(pincode.equalsIgnoreCase("") || pincode.length()!=6){


                    e2.setError("Enter your Pincode");
                    e2.setFocusable(true);
                }
                else if(quantity.equalsIgnoreCase("")){

                    e3.setError("Select your Quantity");
                    e3.setFocusable(true);
                }
                else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) BloodReqAdd.this;
                    String q = "/addblood?log_id=" + sh.getString("log_id", "") + "&date=" + date + "&groupid=" + id
                            + "&pincode=" + pincode + "&quantity=" + quantity;
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

            String method = jo.getString("method");
            Log.d("pearl", method);


            if (method.equalsIgnoreCase("viewblood")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                groupid = new String[ja1.length()];
                group_name = new String[ja1.length()];
                group_description = new String[ja1.length()];

                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    groupid[i] = ja1.getJSONObject(i).getString("group_id");
                    group_name[i] = ja1.getJSONObject(i).getString("group_name");
                    group_description[i] = ja1.getJSONObject(i).getString("group_description");

                    value[i] = group_name[i];

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                s1.setAdapter(ar);
            }

            if (method.equalsIgnoreCase("addblood")) {

                loadingPB.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Request added Successfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),ReceiverHome.class));


            }




        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



        id=groupid[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                                e1.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}