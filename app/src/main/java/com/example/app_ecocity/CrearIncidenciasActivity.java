package com.example.app_ecocity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.app_ecocity.databinding.ActivityCrearIncidenciasBinding;

import java.io.InputStream;
import java.time.LocalDate;

public class CrearIncidenciasActivity extends AppCompatActivity {

    ActivityCrearIncidenciasBinding binding;
    private static final int REQUEST_GALERIA = 1;
    private static final int REQUEST_CAMARA = 2;
    private static final int REQUEST_UBICACION = 3;
    private String ubicacionSeleccionada = "null";
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_crear_incidencias
        );

        binding.etAdjuntarFoto.setOnClickListener(v -> mostrarDialogoImagen());
        binding.layoutVerMapa.setOnClickListener(v -> {
            Intent intent = new Intent(this, SeleccionarUbicacionActivity.class);
            startActivityForResult(intent, REQUEST_UBICACION);
        });

        binding.btnGuardarIncidencia.setOnClickListener(v -> {

            DBHelper db = new DBHelper(this);

            String titulo = binding.etTituloIncidencia.getText().toString();
            String descripcion = binding.etDescripcionIncidencia.getText().toString();
            String prioridad = obtenerPrioridad();
            String fecha = LocalDate.now().toString();
            String fotoUrl = (imageUri != null) ? imageUri.toString() : "null"; //Si no hay foto no la guarda
            String ubicacion = ubicacionSeleccionada;

            db.insertarIncidencia(titulo, descripcion, prioridad, fecha, fotoUrl, ubicacion);

            finish(); //Cuando crear una nueva incidencia vuelve a la Activity de incidencias
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_GALERIA && data != null) {
                imageUri = data.getData();
            }

            if (requestCode == REQUEST_UBICACION && data != null) {
                ubicacionSeleccionada = data.getStringExtra("UBICACION");
                binding.ubicacion.setText(ubicacionSeleccionada);
            }

            binding.ivPreviewFoto.setImageURI(imageUri);
            binding.ivPreviewFoto.setVisibility(View.VISIBLE);

        }
    }

    private void mostrarDialogoImagen() {
        String[] opciones = {"Hacer una foto", "Elegir una foto de la galería"};

        new AlertDialog.Builder(this)
                .setTitle("Adjuntar imagen")
                .setItems(opciones, (dialog, which) -> {
                    if (which == 0) {
                        abrirCamara();
                    } else {
                        abrirGaleria();
                    }
                })
                .show();
    }

    private void abrirCamara() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Foto_Incidencia");

        imageUri = getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_CAMARA);
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALERIA);
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