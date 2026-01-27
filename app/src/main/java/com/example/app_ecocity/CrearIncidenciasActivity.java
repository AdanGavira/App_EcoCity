package com.example.app_ecocity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CrearIncidenciasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Esto asocia el XML activity_incidencias.xml con esta clase
        setContentView(R.layout.activity_incidencias);

        // Inicializar el RecyclerView y la AppBar
        //configurarRecyclerView();
        //configurarToolbar();
    }
}