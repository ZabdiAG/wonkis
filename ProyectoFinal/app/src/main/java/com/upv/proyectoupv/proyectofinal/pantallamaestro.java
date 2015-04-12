package com.upv.proyectoupv.proyectofinal;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * Created by Alejandro Flores on 04/03/2015.
 */
public class pantallamaestro extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TabHost mTabHost = getTabHost();

        mTabHost.addTab(mTabHost.newTabSpec("Nuevo grupo").setIndicator("Nuevo grupo").setContent(new Intent(this  ,nuevogrupo.class )));
        mTabHost.addTab(mTabHost.newTabSpec("Agregar Alumnos").setIndicator("Agregar Alumnos").setContent(new Intent(this , agregaralumnos.class )));
       mTabHost.addTab(mTabHost.newTabSpec("Ver grupo").setIndicator("Ver grupo").setContent(new Intent(this , vergrupo.class )));
        mTabHost.addTab(mTabHost.newTabSpec("Pase de lista").setIndicator("Pase de lista").setContent(new Intent(this , pasedelista.class )));
       mTabHost.addTab(mTabHost.newTabSpec("Asistencia").setIndicator("Asistencia").setContent(new Intent(this , listadeasistencia.class )));
       // mTabHost.addTab(mTabHost.newTabSpec("Vista de asistencia").setIndicator("Vista de asistencia").setContent(new Intent(this , vistadeasistencia.class )));
        mTabHost.setCurrentTab(0);
    }

}


