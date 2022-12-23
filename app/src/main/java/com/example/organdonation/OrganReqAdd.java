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

public class OrganReqAdd extends AppCompatActivity implements JsonResponse, AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner s1;
    String[] orgid,organ_name,organ_details,value;
    Button b1;
    SharedPreferences sh;
    EditText e1;
    String date,id;
    private ProgressBar loadingPB;
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organ_req_add);



        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        loadingPB = findViewById(R.id.idPBLoading);
        b1=(Button) findViewById(R.id.btndon);
        s1=(Spinner) findViewById(R.id.spinner2);

        s1.setOnItemSelectedListener(this);

        e1=(EditText) findViewById(R.id.date);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)OrganReqAdd.this;
        String q="/vieworgan";
        q = q.replace(" ", "%20");
        JR.execute(q);

        e1.setOnClickListener(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                date=e1.getText().toString();
                if (date.equalsIgnoreCase("")){

                    e1.setError("Select Date");
                    e1.setFocusable(true);

                }
                else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) OrganReqAdd.this;
                    String q = "/addorgan?log_id=" + sh.getString("log_id", "") + "&date=" + date + "&orgid=" + id;
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


            if (method.equalsIgnoreCase("vieworgan")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                orgid = new String[ja1.length()];
                organ_name = new String[ja1.length()];
                organ_details = new String[ja1.length()];

                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    orgid[i] = ja1.getJSONObject(i).getString("organ_id");
                    organ_name[i] = ja1.getJSONObject(i).getString("organ_name");
                    organ_details[i] = ja1.getJSONObject(i).getString("organ_details");

                    value[i] = organ_name[i] ;

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                s1.setAdapter(ar);
            }

            if (method.equalsIgnoreCase("addorgan")) {

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

            id=orgid[i];

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