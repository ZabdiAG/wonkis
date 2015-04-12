package com.upv.proyectoupv.proyectofinal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class agregaralumnos extends Activity {
    private ProgressDialog pDialog;
    int code;
    public EditText nom_estudiante;
    public EditText matricula;
    Button insert;
    InputStream is ;
    String line ;
    String result;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregaralumnos);



       // final EditText e_id=(EditText) findViewById(R.id.editText1);
        nom_estudiante = (EditText)findViewById(R.id.nom_estudiante);
        matricula = (EditText)findViewById(R.id.matricula);
        insert=(Button) findViewById(R.id.button1);
        //new select().execute();
        insert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //id = e_id.getText().toString();


                new insert().execute();
                nom_estudiante.setText("");
                matricula.setText("");
            }
        });


    }




    class insert extends AsyncTask<String, String, String> {


                protected String doInBackground(String... args) {


                    String nom_estudiante1 = nom_estudiante.getText().toString();
                    String matricula1 = matricula.getText().toString();
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                    //nameValuePairs.add(new BasicNameValuePair("id",id));
                    nameValuePairs.add(new BasicNameValuePair("nom_estudiante", nom_estudiante1));
                    nameValuePairs.add(new BasicNameValuePair("matricula", matricula1));

                    try {
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost( MainActivity.URL+ "/proyectofinal/insertestudiante.php");
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                        Log.e("pass 1", "connection success ");
                    } catch (Exception e) {
                        Log.e("Fail 1", e.toString());
                        Toast.makeText(getApplicationContext(), "Invalid IP Address",
                                Toast.LENGTH_LONG).show();
                    }

                    try {
                        BufferedReader reader = new BufferedReader
                                (new InputStreamReader(is, "iso-8859-1"), 8);
                        StringBuilder sb = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        is.close();
                        result = sb.toString();
                        Log.e("pass 2", "connection success ");

                    } catch (Exception e) {
                        Log.e("Fail 2", e.toString());
                    }

                    try {


                        JSONObject json = new JSONObject(result);

                        code = json.getInt("code");

                        if (code == 1) {
                            Looper.prepare();
                            Log.d("Guardo exitosamente!", json.toString());
                            Toast.makeText(getApplicationContext(), "Guardo exitosamente!",
                                    Toast.LENGTH_LONG).show();
                            Looper.loop();

                        } else {
                            Log.d("No guardo", json.toString());
                        }

                    } catch (Exception e) {
                        Log.e("Fail 3", e.toString());
                    }
                    return null;
                }

            }
}


