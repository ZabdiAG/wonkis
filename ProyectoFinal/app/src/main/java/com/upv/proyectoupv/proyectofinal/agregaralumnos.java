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
    ArrayList<String> worldlist;
    ArrayList<WorldPopulation> world;
    String id_grupo;
    String nombre;
    InputStream is=null;
    String result=null;
    String line=null;
    int code;
 String get_grupos;
    ListView list_grupos;
    public EditText nom_estudiante;
    public EditText matricula;
    Button insert;
    JSONObject jsonobject;
    JSONArray jsonarray;
    ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregaralumnos);

        ///////////////////SELECCION DE DATOS///////////
       new Selectgrupos().execute();

        //////////////////////////////////////////////////



       // final EditText e_id=(EditText) findViewById(R.id.editText1);
        nom_estudiante = (EditText)findViewById(R.id.nom_estudiante);
        matricula = (EditText)findViewById(R.id.matricula);
        list_grupos = (ListView)findViewById(R.id.list_grupos);
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

    public class Selectgrupos extends AsyncTask<Void, Void, Void> {
        String id;
        InputStream is = null;

        @Override
        protected Void doInBackground(Void... params) {

            // Create an array to populate the spinner
            world = new ArrayList<WorldPopulation>();
            worldlist = new ArrayList<String>();
            // JSON file URL address
            jsonobject = JSONfunctions
                    .getJSONfromURL("http://192.168.2.9/proyectofinal/selectgrupos.php");
            //jsonobject = JSONfunctions.getJSONfromURL("http://192.168.1.64/proyectofinal/selectgrupos.php");
            //System.out.println(jsonobject);
            //JSONArray gruposarray = new JSONArray();

            try {

                jsonarray = jsonobject.getJSONArray("grupos");
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);

                    WorldPopulation worldpop = new WorldPopulation();

                    //worldpop.setid(jsonobject.optString("id"));
                    worldpop.setgrupo(jsonobject.optString("nombre"));

                    world.add(worldpop);

                    // Populate spinner with country names
                    worldlist.add(jsonobject.optString("nombre"));

                }


            } catch (Exception e) {
                Looper.prepare();
                Log.e("Fallo al cargar informacion", e.toString());
                Toast.makeText(getApplicationContext(), "No cargo informacion",
                        Toast.LENGTH_LONG).show();
                Looper.loop();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // Locate the spinner in activity_main.xml
            Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);

            // Spinner adapter
            mySpinner
                    .setAdapter(new ArrayAdapter<String>(agregaralumnos.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            worldlist));

            // Spinner on item click listener
            mySpinner
                    .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int position, long arg3) {
                            // TODO Auto-generated method stub
                            // Locate the textviews in activity_main.xml
                            //TextView txtrank = (TextView) findViewById(R.id.rank);
                            //TextView txtcountry = (TextView) findViewById(R.id.nombre);
                            //TextView txtpopulation = (TextView) findViewById(R.id.population);

                            // Set the text followed by the position
                            //txtrank.setText("Rank : "  + world.get(position).getid());
                            //txtcountry.setText("Country : "+ world.get(position).getgrupo());

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });
        }

    }


    public class WorldPopulation {
        private int id_grupo;
        private String nombre;


        public Integer getid() {
            return id_grupo;
        }

        public void setid(String rank) {
            this.id_grupo = id_grupo;
        }

        public String getgrupo() {
            return nombre;
        }

        public void setgrupo(String nombre) {
            this.nombre = nombre;
        }


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
                        HttpPost httppost = new HttpPost("http://192.168.2.9/proyectofinal/insertestudiante.php");
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

            }}


