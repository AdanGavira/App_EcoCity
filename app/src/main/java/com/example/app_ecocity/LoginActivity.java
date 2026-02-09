package com.example.app_ecocity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.app_ecocity.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_login
        );

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build(); //Activar la funcionalidad offline de Firestore en caso de que no exista conexión

        db.setFirestoreSettings(settings);

        binding.registrame.setOnClickListener(v -> { //Activity de registro
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });
        binding.OlvidarContraseA.setOnClickListener(v -> { //Activity de restablecer contraseña
            Intent intent = new Intent(LoginActivity.this, RestablecerContrasenaActivity.class);
            startActivity(intent);
        });

        binding.loginButton.setOnClickListener(v -> {
            loginUsuario();
        });
    }

    private void loginUsuario(){
        String email = binding.correo.getText().toString().trim();
        String contrasena = binding.contrasena.getText().toString().trim();

        //Comprobaciones de campos

        if (email.isEmpty() || contrasena.isEmpty()){
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return; //Sale del metodo en caso de que algun campo sea incorrecto
        }

        //Metodo de Firebase para logearte con email y contraseña

        mAuth.signInWithEmailAndPassword(email, contrasena).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show(); //Mensaje si se loguea de forma correcta

                Intent intent = new Intent(LoginActivity.this, IncidenciasActivity.class); //Se mueve a la Activity de incidencias
                startActivity(intent);
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show(); //Si uno de los campos es incorrecto
            }
        });
    }
}
