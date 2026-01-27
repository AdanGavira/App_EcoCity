package com.example.app_ecocity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    // Declarar las variables para los componentes
    private EditText etUsuario;
    private Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Asociar el XML a esta clase Java

        setContentView(R.layout.activity_login);

        // 2. Encontrar los componentes por su ID

        //etUsuario = findViewById(R.id.etUsuario);
        //btnEntrar = findViewById(R.id.btnEntrar);

        // 3. Programar la lógica del botón

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etUsuario.getText().toString();
                // Aquí podrías saltar a la pantalla de Incidencias
                irAIncidencias();
            }
        });
    }

    private void irAIncidencias() {
        Intent intent = new Intent(this, IncidenciasActivity.class);
        startActivity(intent);
    }
}
