package com.upv.proyectoupv.proyectofinal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Register extends Activity implements OnClickListener{

    private EditText user, pass;
    private Button  mRegister;
    // Dialogo objeto
    private ProgressDialog pDialog;
    // clase de JsonParser
    JSONParser jsonParser = new JSONParser();
    //obtener url de webservice
    private static final String LOGIN_URL = "http://192.168.1.64/webservice/register.php";
    //ids de json
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        user = (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);

        mRegister = (Button)findViewById(R.id.register);
        mRegister.setOnClickListener(this);

    }
    @Override
    //ejecuta clase createuser.execute
    public void onClick(View v) {
        // TODO Auto-generated method stub

        new CreateUser().execute();
    }

    class CreateUser extends AsyncTask<String, String, String> {
        //en esta clase se extiende de AsyncTast asi que tendra sus respectivos metodos de procesos
        boolean failure = false;
        @Override
        //antes de ejecutar el proceso principal muestra un mensaje de creando usuario
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Creating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        //este es el proceso principal, en donde obtiene lo tecleado en los campos
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            try {
                //los agrega a un arraylist utilizando params, para de esta forma enviarlos con json y el metodo post
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));
                Log.d("request!", "starting");
                //envia la informacion al script php con post
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);
                Log.d("Login attempt", json.toString());
               //si todo salio bien
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("User Created!", json.toString());
                    finish();
                    return json.getString(TAG_MESSAGE);
                }else{
                 //si no salio bien
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(Register.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }

}