package com.example.app_ecocity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {

    // Declaración de variables para los componentes del XML
    private EditText etNombre, etApellidos, etEmail, etPassword;
    private Button btnRegistrar;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Asociar el XML de Registro a esta clase Java

        setContentView(R.layout.activity_registro);

        // 2. Vincular las variables con los IDs definidos en el XML

        //vincularComponentes();

        // 3. Configurar la flecha de la AppBar para volver atrás

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra esta actividad y vuelve a la anterior
            }
        });

        // 4. Configurar la lógica del botón de Registro

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    /*
    private void vincularComponentes() {
        btnBack = findViewById(R.id.btnBackRegistro);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
    }

     */

    private void registrarUsuario() {
        String nombre = etNombre.getText().toString();

        if (nombre.isEmpty()) {
            Toast.makeText(this, "Por favor, rellena los campos", Toast.LENGTH_SHORT).show();
        } else {
            // Aquí iría la lógica para guardar en la base de datos
            Toast.makeText(this, "Usuario " + nombre + " registrado", Toast.LENGTH_LONG).show();
        }
    }
}
