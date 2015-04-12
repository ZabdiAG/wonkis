package com.upv.proyectoupv.proyectofinal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class nuevogrupo extends Activity {
    private ProgressDialog pDialog;

   // String id;
    InputStream is=null;
    String result=null;
    String line=null;
    int code;
    public EditText nom_grupo;
    Button insert;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevogrupo);

       // final EditText e_id=(EditText) findViewById(R.id.editText1);
        nom_grupo = (EditText)findViewById(R.id.nom_grupo);
        insert=(Button) findViewById(R.id.button1);

        insert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //id = e_id.getText().toString();


                new insert().execute();
                nom_grupo.setText("");
            }
        });
    }

    class insert extends AsyncTask<String, String, String >
        {


        protected String doInBackground(String... args) {


        String nom_grupo1 = nom_grupo.getText().toString();
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        //nameValuePairs.add(new BasicNameValuePair("id",id));
        nameValuePairs.add(new BasicNameValuePair("nom_grupo",nom_grupo1));

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.2.9/proyectofinal/insertgrupo.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("pass 1", "connection success ");
        }
        catch(Exception e)
        {
            Log.e("Fail 1", e.toString());
            Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();
        }

        try
        {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            Log.e("pass 2", "connection success ");

        }
        catch(Exception e)
        {
            Log.e("Fail 2", e.toString());
        }

        try
        {


            JSONObject json = new JSONObject(result);

            code = json.getInt("code");

            if(code==1)
            {
                Looper.prepare();
                Log.d("Guardo exitosamente!", json.toString());
                Toast.makeText(getApplicationContext(), "Guardo exitosamente!",
                        Toast.LENGTH_LONG).show();
                Looper.loop();

            }
            else
            {
                Log.d("No guardo", json.toString());
            }

        }
        catch(Exception e)
        {
            Log.e("Fail 3", e.toString());
        }
            return null;
    }

}}