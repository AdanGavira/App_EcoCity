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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.example.app_ecocity.databinding.ActivityEditarIncidenciasBinding;

import java.time.LocalDate;

public class EditarIncidenciasActivity extends AppCompatActivity {

    private ActivityEditarIncidenciasBinding binding;
    private static final int REQUEST_PERMISO_IMAGEN = 100;
    private DBHelper dbHelper;
    private int incidenciaId = -1;
    private String fotoUrl;
    private String ubicacion;
    private Uri imageUri;
    private static final int REQUEST_GALERIA = 1;
    private static final int REQUEST_CAMARA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_editar_incidencias
        );

        dbHelper = new DBHelper(this);

        if (!tienePermisoImagenes()) {
            pedirPermisoImagenes();
            return; // ⛔ NO intentes cargar la imagen aún
        }

        incidenciaId = getIntent().getIntExtra("INCIDENCIA_ID", -1); //Recogemos el id de la incidencia que queremos editar

        if (incidenciaId != -1) {
            cargarIncidencia();
        }

        binding.etAdjuntarFoto.setOnClickListener(v -> mostrarDialogoImagen());

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

    private void cargarIncidencia() { //Cargamos desde la base de datos la informacion de la incidencia

        Incidencia i = dbHelper.obtenerIncidenciaPorId(incidenciaId);
        if (i == null) {
            return; //Si el id es nulo sale del metodo
        }

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

        if (i.getFotoUrl() != null) { //En el Hito1 la foto será la default
            fotoUrl = i.getFotoUrl();
            binding.ivPreviewFoto.setImageURI(Uri.parse(fotoUrl));
            binding.ivPreviewFoto.setVisibility(View.VISIBLE);
        }

        ubicacion = i.getUbicacion(); //En el Hito1 no hay ubicacion
    }

    private void guardarCambios() {

        String titulo = binding.etTituloIncidencia.getText().toString();
        String descripcion = binding.etDescripcionIncidencia.getText().toString();
        String urgencia = obtenerPrioridad();
        String fecha = LocalDate.now().toString();

        dbHelper.actualizarIncidencia(
                incidenciaId,
                titulo,
                descripcion,
                urgencia,
                fecha,
                fotoUrl,
                ubicacion
        );

        finish();
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

    private void borrarIncidencia() {

        new AlertDialog.Builder(this)
                .setTitle("Eliminar incidencia")
                .setMessage("¿Seguro que quieres borrar esta incidencia?")
                .setPositiveButton("Sí", (d, w) -> {
                    dbHelper.borrarIncidencia(incidenciaId);
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
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
