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
    private static final int REQUEST_PERMISO_IMAGEN = 100;


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

        if (!tienePermisoImagenes()) {
            pedirPermisoImagenes();
            return; //  Vuelve si no consigue la imagen
        }

        if (incidencia != null) {
            binding.titulo.setText(incidencia.getTitulo());
            binding.descripcion.setText(incidencia.getDescripcion());
            binding.prioridad.setText(incidencia.getPrioridad());
            if (incidencia.getFotoUrl() != null && !incidencia.getFotoUrl().equals("null")) {
                Uri uriImagen = Uri.parse(incidencia.getFotoUrl());
                binding.ivInfoFoto.setImageURI(uriImagen);
            } else {
                binding.ivInfoFoto.setVisibility(View.GONE);
            }

            // Añadir foto e ubicación
        }

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void pedirPermisoImagenes() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                    REQUEST_PERMISO_IMAGEN
            );
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISO_IMAGEN
            );
        }
    }

    private boolean tienePermisoImagenes() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED;
        }
    }
}