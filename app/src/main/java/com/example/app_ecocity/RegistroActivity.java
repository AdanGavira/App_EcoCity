package com.example.app_ecocity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.example.app_ecocity.databinding.ActivityRegistroBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RegistroActivity extends AppCompatActivity {

    private ActivityRegistroBinding binding;
    private FirebaseAuth mAuth;
    private boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_registro
        );
        mAuth = FirebaseAuth.getInstance();

        binding.BotonRegistro.setOnClickListener(v -> {
            registrarUsuario();
        });
    }


    private void registrarUsuario() { //Metodo para registrar usuarios

        //Valores necesarios
        String email = binding.correo.getText().toString().trim();
        String contrasena = binding.contrasena.getText().toString().trim();
        String confcontrasena = binding.confirmarcontrasena.getText().toString().trim();

        //Validacion de contraseña
        isValid = isValidPass(contrasena, confcontrasena);

        if (isValid){
            if (binding.terminos.isChecked()){ //Validacion checkbox
                mAuth.createUserWithEmailAndPassword(email, contrasena).addOnCompleteListener(this, task -> { //Metodo de Firebase para registrar usuarios
                    if (task.isSuccessful()){
                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(
                                this,
                                "Error al registrar el usuario: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
            } else {
                Toast.makeText(
                        this,
                        "Debe aceptar los términos y condiciones ",
                        Toast.LENGTH_LONG
                ).show();
                binding.terminos.setTextColor(ContextCompat.getColor(this, R.color.rojo));
            }
            } else {
            Toast.makeText(
                    this,
                    "Las contraseñas no coinciden",
                    Toast.LENGTH_SHORT
            ).show();
            }


    }

    private boolean isValidPass(String contrasena1, String contrasena2){ //Metodo para validar las contraseñas
        if (contrasena2.equals(contrasena1)){
            return true;
        } else return false;

    }
}
