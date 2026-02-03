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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_informacion
        );

        int incidenciaId = getIntent().getIntExtra("INCIDENCIA_ID", -1);

        DBHelper dbHelper = new DBHelper(this);
        Incidencia incidencia = dbHelper.obtenerIncidenciaPorId(incidenciaId);



        if (incidencia != null) { //Muestra los datos de la incidencia
            binding.titulo.setText(incidencia.getTitulo());
            binding.descripcion.setText(incidencia.getDescripcion());
            binding.prioridad.setText(incidencia.getPrioridad());
            if (incidencia.getFotoUrl() != null && !incidencia.getFotoUrl().equals("null")) {
                Uri uriImagen = Uri.parse(incidencia.getFotoUrl());
                binding.ivInfoFoto.setImageURI(uriImagen);
            } else {
                binding.ivInfoFoto.setVisibility(View.GONE);
            }
            binding.tvInfoUbicacion.setText(incidencia.getUbicacion());
        }

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }


}