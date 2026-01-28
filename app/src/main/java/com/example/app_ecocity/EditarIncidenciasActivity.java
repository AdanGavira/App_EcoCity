package com.example.app_ecocity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.app_ecocity.databinding.ActivityEditarIncidenciasBinding;

import java.time.LocalDate;

public class EditarIncidenciasActivity extends AppCompatActivity {

    private ActivityEditarIncidenciasBinding binding;
    private DBHelper dbHelper;
    private int incidenciaId = -1;
    private String fotoUrl;
    private String ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_editar_incidencias
        );

        dbHelper = new DBHelper(this);



        incidenciaId = getIntent().getIntExtra("INCIDENCIA_ID", -1); //Recogemos el id de la incidencia que queremos editar

        if (incidenciaId != -1) {
            cargarIncidencia();
        }

        binding.btnGuardar.setOnClickListener(v -> {
            guardarCambios();
        });
        binding.btnBorrar.setOnClickListener(v -> {
            borrarIncidencia();
        });

        binding.toolbarEditarIncidencia.setNavigationOnClickListener(
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




}
