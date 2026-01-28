package com.example.app_ecocity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_ecocity.databinding.ActivityIncidenciasBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class IncidenciasActivity extends AppCompatActivity {

    private ActivityIncidenciasBinding binding;
    FirebaseAuth mAuth;
    private DBHelper dbHelper;
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

        dbHelper = new DBHelper(this);

        //Lista de incidencias

        lista = new ArrayList<>();
        adapter = new IncidenciaAdapter(lista);

        //RecyclerView

        binding.rvIncidencias.setLayoutManager(new LinearLayoutManager(this));
        binding.rvIncidencias.setAdapter(adapter);


        binding.anadirIncidencia.setOnClickListener(v ->
                startActivity(new Intent(this, CrearIncidenciasActivity.class))
        );


        binding.btnBack.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            mAuth.signOut();
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
        });
    }
        @Override
        protected void onResume() { //Asegurarse de que la información se muestra aunque salgamos del Activity
            super.onResume();
            lista.clear();
            lista.addAll(dbHelper.obtenerIncidencias());
            adapter.notifyDataSetChanged();
        }

    }