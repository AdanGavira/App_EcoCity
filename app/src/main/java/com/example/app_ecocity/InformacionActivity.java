package com.example.app_ecocity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

        if (incidencia != null) {
            binding.titulo.setText(incidencia.getTitulo());
            binding.descripcion.setText(incidencia.getDescripcion());
            binding.prioridad.setText(incidencia.getPrioridad());
            // Añadir foto e ubicación
        }

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }
}