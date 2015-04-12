package com.upv.proyectoupv.proyectofinal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class pasedelista extends Activity {
    private ProgressDialog pDialog;
    private ArrayList<String> worldlist;
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
    JSONObject jsonarray;
    ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pasedelista);
        //new Selectgrupos().execute();

        //////////////////////////////////////////////////
        final List<String> list = new ArrayList<String>();
        list.add("grupo1");
        list.add("grupo2");

        Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);
        mySpinner.setAdapter(new ArrayAdapter<String>(pasedelista.this,
                android.R.layout.simple_spinner_dropdown_item,
                list));

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // Locate the textviews in activity_main.xml
                get_grupos = list.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        // final EditText e_id=(EditText) findViewById(R.id.editText1);
        // nom_estudiante = (EditText)findViewById(R.id.nom_estudiante);
        // matricula = (EditText)findViewById(R.id.matricula);
        // list_grupos = (ListView)findViewById(R.id.list_grupos);
        insert = (Button) findViewById(R.id.button1);
        //new select().execute();
        insert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //id = e_id.getText().toString();


                //new insert().execute();

            }
        });


    }
}

