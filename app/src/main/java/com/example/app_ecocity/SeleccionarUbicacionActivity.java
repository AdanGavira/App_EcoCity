package com.example.app_ecocity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.app_ecocity.databinding.ActivitySeleccionarUbicacionBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SeleccionarUbicacionActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_LOCATION_PERMISSION = 200;
    private GoogleMap mMap;
    private LatLng ubicacionSeleccionada = null;
    private FusedLocationProviderClient fusedLocationClient;
    private ActivitySeleccionarUbicacionBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_seleccionar_ubicacion
        );

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.mapa);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        binding.btnConfirmarUbicacion.setOnClickListener(v -> {
            if (ubicacionSeleccionada != null) {
                devolverUbicacion();
            } else {
                Toast.makeText(this,
                        "Selecciona una ubicación en el mapa",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (tienePermisoUbicacion()) {
            activarUbicacionUsuario();
        } else {
            pedirPermisoUbicacion();
        }

        // Permitir seleccionar ubicación manualmente
        mMap.setOnMapClickListener(latLng -> {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng));
            ubicacionSeleccionada = latLng; // Guarda la ubicación seleccionada
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                activarUbicacionUsuario();
            } else {
                Toast.makeText(
                        this,
                        "Se necesita permiso de ubicación",
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    }


    private void devolverUbicacion() {
        if (ubicacionSeleccionada != null) {
            Intent result = new Intent();
            result.putExtra(
                    "UBICACION",
                    ubicacionSeleccionada.latitude + "," +
                            ubicacionSeleccionada.longitude
            );
            setResult(RESULT_OK, result);
            finish();
        }
    }

    private boolean tienePermisoUbicacion() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void pedirPermisoUbicacion() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_LOCATION_PERMISSION
        );
    }

    private void activarUbicacionUsuario() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        LatLng userLatLng = new LatLng(
                                location.getLatitude(),
                                location.getLongitude()
                        );

                        mMap.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(userLatLng, 15f)
                        );

                        mMap.addMarker(
                                new MarkerOptions()
                                        .position(userLatLng)
                                        .title("Tu ubicación")
                        );
                    }
                });
    }

}