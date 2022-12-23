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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements JsonResponse{
    EditText e1,e2;
    Button b1;
    TextView t1;
    String username,password,logid,usertype;
    SharedPreferences sh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.etuser);
        e2=(EditText)findViewById(R.id.etpassw);
        t1=(TextView)findViewById(R.id.txtreg);

        b1=(Button)findViewById(R.id.btlogin);



        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Registration for Donor.", "Registration for Receiver."};

                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                // builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Registration for Donor.")) {
                            startActivity(new Intent(getApplicationContext(),Donorreg.class));}


                        else if (items[item].equals("Registration for Receiver.")) {
                            startActivity(new Intent(getApplicationContext(),Receiverreg.class));}
                        }



                });
                builder.show();


            }
        });




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                username=e1.getText().toString();
                password=e2.getText().toString();

                if (username.equalsIgnoreCase("")){

                    e1.setError("Enter your Username");
                    e1.setFocusable(true);

                }else if(password.equalsIgnoreCase("")){

                    e2.setError("Enter your Password");
                    e2.setFocusable(true);
                }else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Login.this;
                    String q = "/login?username=" + username + "&password=" + password;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }


//              Toast.makeText(getApplicationContext(),"username : "+username+"\npassword : "+password, Toast.LENGTH_LONG).show();

            }
        });


    }

    @Override
    public void response(JSONObject jo) {

        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                logid = ja1.getJSONObject(0).getString("loginid");
                usertype = ja1.getJSONObject(0).getString("usertype");

                SharedPreferences.Editor e = sh.edit();
                e.putString("log_id", logid);
                e.commit();

                if(usertype.equals("donor"))
                {
                    Toast.makeText(getApplicationContext(),"Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DonorHome.class));
                }
                if(usertype.equals("receiver"))
                {
                    Toast.makeText(getApplicationContext(),"Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ReceiverHome.class));
                }



            }
            else {
                Toast.makeText(getApplicationContext(), "Login failed invalid username and password", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),IpSetting.class);
        startActivity(b);
    }


}