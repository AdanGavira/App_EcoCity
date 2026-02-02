package com.example.app_ecocity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SeleccionarUbicacionActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng ubicacionSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_ubicacion);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.mapa);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng inicio = new LatLng(40.416775, -3.703790); // Madrid
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(inicio, 12));

        mMap.setOnMapClickListener(latLng -> {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng));
            ubicacionSeleccionada = latLng;
        });

        mMap.setOnMarkerClickListener(marker -> {
            devolverUbicacion();
            return true;
        });
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
}