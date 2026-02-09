package com.example.app_ecocity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.app_ecocity.databinding.ActivityCrearIncidenciasBinding;
import com.google.firebase.auth.FirebaseAuth;

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
        UiUtils.aplicarInsets(binding.getRoot());

        binding.etAdjuntarFoto.setOnClickListener(v -> mostrarDialogoImagen()); //Abre el dialógo para adjuntar una imagen

        binding.layoutVerMapa.setOnClickListener(v -> { //Abre la activity con Google Maps
            Toast.makeText(this, "Abriendo mapa", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SeleccionarUbicacionActivity.class);
            startActivityForResult(intent, REQUEST_UBICACION);
        });

        binding.btnGuardarIncidencia.setOnClickListener(v -> { //Guarda la incidencia en FireStore

            FirestoreHelper firestore = new FirestoreHelper();

            String titulo = binding.etTituloIncidencia.getText().toString();
            String descripcion = binding.etDescripcionIncidencia.getText().toString();
            String prioridad = obtenerPrioridad();
            String fecha = LocalDate.now().toString();
            String fotoUrl = (imageUri != null) ? imageUri.toString() : "null"; //Si no hay foto no la guarda
            String ubicacion = ubicacionSeleccionada;
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            Incidencia incidencia = new Incidencia(userId, titulo, descripcion, prioridad, fecha, fotoUrl, ubicacion);

            firestore.insertarIncidencia(incidencia);

            finish(); //Cuando crea una nueva incidencia vuelve a la Activity de incidencias
        });

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_GALERIA && data != null) { //Comprueba si existe una imagen guardada y la recoge
                imageUri = data.getData();
            }

            if (requestCode == REQUEST_UBICACION && data != null) { //Comprueba si existe una ubicación guardada y la muestra
                ubicacionSeleccionada = data.getStringExtra("UBICACION");
                binding.ubicacion.setText(ubicacionSeleccionada);
            }
            if (imageUri != null) { //Si hay una imagen la muestra
                binding.ivPreviewFoto.setImageURI(imageUri);
                binding.ivPreviewFoto.setVisibility(View.VISIBLE);
            }
        }
    }

    private void mostrarDialogoImagen() { //Muestra el dialogo para adjuntar una imagen
        String[] opciones = {"Hacer una foto", "Elegir una foto de la galería"};

        new AlertDialog.Builder(this).setTitle("Adjuntar imagen").setItems(opciones, (dialog, which) -> {
                    if (which == 0) {
                        abrirCamara();
                    } else {
                        abrirGaleria();
                    }
        }).show();
    }

    private void abrirCamara() { //Abre la camara
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Foto_Incidencia");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_CAMARA);
    }

    private void abrirGaleria() { //Abre la galeria
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