package com.example.app_ecocity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.app_ecocity.databinding.ActivityCrearIncidenciasBinding;

import java.time.LocalDate;

public class CrearIncidenciasActivity extends AppCompatActivity {

    ActivityCrearIncidenciasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_crear_incidencias
        );

        binding.btnGuardarIncidencia.setOnClickListener(v -> {

            DBHelper db = new DBHelper(this);

            String titulo = binding.etTituloIncidencia.getText().toString();
            String descripcion = binding.etDescripcionIncidencia.getText().toString();
            String prioridad = obtenerPrioridad();
            String fecha = LocalDate.now().toString();
            String fotoUrl = "null"; //Null en Hito1
            String ubicacion = "null"; //Null en Hito1

            db.insertarIncidencia(titulo, descripcion, prioridad, fecha, fotoUrl, ubicacion);

            finish(); //Cuando crear una nueva incidencia vuelve a la Activity de incidencias
        });
    }

    private String obtenerPrioridad(){ //Recoger la prioridad seleccionada en el radiogroup
        int id = binding.rgPrioridad.getCheckedRadioButtonId();

        if (id == R.id.rbAlta){
            return "Alta";
        }else if (id == R.id.rbMedia){
            return "Media";}
        else {
            return "Baja";
        }
    }
}