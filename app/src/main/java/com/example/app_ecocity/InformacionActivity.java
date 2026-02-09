package com.example.app_ecocity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.app_ecocity.databinding.ActivityInformacionBinding;

public class InformacionActivity extends AppCompatActivity {

    ActivityInformacionBinding binding;
    private FirestoreHelper firestoreHelper;
    private String incidenciaId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_informacion
        );

        firestoreHelper = new FirestoreHelper();
        incidenciaId = getIntent().getStringExtra("INCIDENCIA_ID");

        if (incidenciaId != null) { //Muestra los datos de la incidencia
            cargarIncidencia();
        }

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void cargarIncidencia() {

        firestoreHelper.obtenerIncidenciaPorId(incidenciaId, incidencia -> {

            if (incidencia == null) return;

            binding.titulo.setText(incidencia.getTitulo());
            binding.descripcion.setText(incidencia.getDescripcion());
            binding.prioridad.setText(incidencia.getPrioridad());

            if (incidencia.getUbicacion() != null &&
                    !incidencia.getUbicacion().equals("null")) {
                binding.tvInfoUbicacion.setText(incidencia.getUbicacion());
            }

            if (incidencia.getFotoUrl() != null) {
                binding.ivInfoFoto.setImageURI(Uri.parse(incidencia.getFotoUrl()));
                binding.ivInfoFoto.setVisibility(View.VISIBLE);
            }
        });
    }

}