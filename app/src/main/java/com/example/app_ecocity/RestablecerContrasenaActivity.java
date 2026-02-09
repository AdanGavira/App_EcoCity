package com.example.app_ecocity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.app_ecocity.databinding.ActivityRestablecerContrasenaBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RestablecerContrasenaActivity extends AppCompatActivity {

    private ActivityRestablecerContrasenaBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_restablecer_contrasena
        );
        UiUtils.aplicarInsets(binding.getRoot());

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.BotonEnviarCorreo.setOnClickListener(v -> enviarCorreo());
        binding.btnBack.setOnClickListener(
                v -> finish()
        );
    }

    private void enviarCorreo() {
        String email = binding.correo.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Introduce tu correo", Toast.LENGTH_SHORT).show();
            return; //Sale del metodo en caso de que el campo sea incorrecto
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(
                                this,
                                "Correo enviado. Revisa tu email",
                                Toast.LENGTH_LONG
                        ).show();
                        finish(); // volver al login
                    } else {
                        Toast.makeText(
                                this,
                                "Error al enviar el correo",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}