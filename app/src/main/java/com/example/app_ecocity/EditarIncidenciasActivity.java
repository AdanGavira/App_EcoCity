package com.example.app_ecocity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.example.app_ecocity.databinding.ActivityEditarIncidenciasBinding;
import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDate;

public class EditarIncidenciasActivity extends AppCompatActivity {

    private ActivityEditarIncidenciasBinding binding;
    private static final int REQUEST_PERMISO_IMAGEN = 100;
    private FirestoreHelper firestoreHelper;
    private String incidenciaId;
    private Incidencia incidencia;
    private String fotoUrl;
    private String ubicacion;
    private Uri imageUri;
    private static final int REQUEST_GALERIA = 1;
    private static final int REQUEST_CAMARA = 2;
    private static final int REQUEST_UBICACION = 3;
    private String ubicacionSeleccionada = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_editar_incidencias
        );
        UiUtils.aplicarInsets(binding.getRoot());

        // Firebase
        firestoreHelper = new FirestoreHelper();
        incidenciaId = getIntent().getStringExtra("INCIDENCIA_ID");

        if (incidenciaId != null) {
            cargarIncidencia();
        }

        binding.etAdjuntarFoto.setOnClickListener(v -> mostrarDialogoImagen()); //Abre el dialógo para adjuntar una imagen

        binding.layoutVerMapa.setOnClickListener(v -> { //Abre la activity con Google Maps
            Toast.makeText(this, "Abriendo mapa", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SeleccionarUbicacionActivity.class);
            if (ubicacion != null && !ubicacion.equals("null")) {
                intent.putExtra("UBICACION_ACTUAL", ubicacion);
            }
            startActivityForResult(intent, REQUEST_UBICACION);
        });

        binding.btnGuardar.setOnClickListener(v -> {
            guardarCambios();
        });
        binding.btnBorrar.setOnClickListener(v -> {
            borrarIncidencia();
        });

        binding.btnBack.setOnClickListener(
                v -> finish()
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {

            if (requestCode == REQUEST_UBICACION) { //Comprueba si existe una ubicación guardada
                ubicacionSeleccionada = data.getStringExtra("UBICACION");

                if (ubicacionSeleccionada != null) { //Muestra la ubicacion si existe
                    ubicacion = ubicacionSeleccionada;
                    binding.ubicacion.setText(ubicacionSeleccionada);
                }
            }

            if (requestCode == REQUEST_GALERIA) { //Comprueba si existe una imagen guardada y la muestra si existe
                imageUri = data.getData();
                fotoUrl = imageUri.toString();
                binding.ivPreviewFoto.setImageURI(imageUri);
                binding.ivPreviewFoto.setVisibility(View.VISIBLE);
            }
        }
    }


    private void cargarIncidencia() { //Cargamos desde la base de datos la informacion de la incidencia

        firestoreHelper.obtenerIncidenciaPorId(incidenciaId, i -> {

            if (i == null) {
                return; //Si el id es nulo sale del metodo
            }
            incidencia = i;

            binding.etTituloIncidencia.setText(i.getTitulo());
            binding.etDescripcionIncidencia.setText(i.getDescripcion());

            switch (i.getPrioridad()) {
                case "Alta":
                    binding.rbAlta.setChecked(true);
                    break;
                case "Media":
                    binding.rbMedia.setChecked(true);
                    break;
                case "Baja":
                    binding.rbBaja.setChecked(true);
                    break;
            }

            if (i.getFotoUrl() != null) { //Carga la foto guardada si existe
                fotoUrl = i.getFotoUrl();
                binding.ivPreviewFoto.setImageURI(Uri.parse(fotoUrl));
                binding.ivPreviewFoto.setVisibility(View.VISIBLE);
            }

            if (i.getUbicacion() != null && !i.getUbicacion().equals("null")) { //Carga la ubicación guardada si existe
                binding.ubicacion.setText(i.getUbicacion());
            }
        });
    }

    private void guardarCambios() {
        incidencia.setTitulo(binding.etTituloIncidencia.getText().toString());
        incidencia.setDescripcion(binding.etDescripcionIncidencia.getText().toString());
        incidencia.setPrioridad(obtenerPrioridad());
        incidencia.setFecha(LocalDate.now().toString());
        incidencia.setFotoUrl(fotoUrl);
        incidencia.setUbicacion(ubicacion);

        firestoreHelper.actualizarIncidencia(incidencia);

        finish();
    }

    private void mostrarDialogoImagen() { //Muestra el dialogo para adjuntar una imagen
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

    private void borrarIncidencia() {

        new AlertDialog.Builder(this)
                .setTitle("Eliminar incidencia")
                .setMessage("¿Seguro que quieres borrar esta incidencia?")
                .setPositiveButton("Sí", (d, w) -> {
                    firestoreHelper.borrarIncidencia(incidenciaId);
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
