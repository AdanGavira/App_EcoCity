package com.example.app_ecocity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_ecocity.databinding.ActivityIncidenciasBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class IncidenciasActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISO_IMAGEN = 100;
    private ActivityIncidenciasBinding binding;
    private FirebaseAuth mAuth;
    private FirestoreHelper firestoreHelper;
    private List<Incidencia> lista;
    private IncidenciaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_incidencias
        );

        mAuth = FirebaseAuth.getInstance();

        firestoreHelper = new FirestoreHelper();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        //Lista de incidencias

        lista = new ArrayList<>();
        adapter = new IncidenciaAdapter(lista);

        //RecyclerView

        binding.rvIncidencias.setLayoutManager(new LinearLayoutManager(this));
        binding.rvIncidencias.setAdapter(adapter);

        if (!tienePermisoImagenes()) { //Se asegura de que la app tiene permisos con las imagenes del dispositivo
            pedirPermisoImagenes();
        }


        binding.anadirIncidencia.setOnClickListener(v ->
                startActivity(new Intent(this, CrearIncidenciasActivity.class))
        );


        binding.btnBack.setOnClickListener(v -> {
            finish();
            mAuth.signOut();
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onResume() { //Asegurarse de que la información se muestra aunque salgamos del Activity
        super.onResume();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firestoreHelper.obtenerIncidenciasUsuario(userId,incidencias -> {
            lista.clear();
            lista.addAll(incidencias);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISO_IMAGEN) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // Ya tiene permiso y refresca la interfaz si es necesario
                onResume();
            }
        }
    }

    private void pedirPermisoImagenes() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_PERMISO_IMAGEN);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISO_IMAGEN);
        }
    }

    private boolean tienePermisoImagenes() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }
}