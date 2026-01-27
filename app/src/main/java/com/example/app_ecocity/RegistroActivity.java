package com.example.app_ecocity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.app_ecocity.databinding.ActivityRegistroBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RegistroActivity extends AppCompatActivity {

    private ActivityRegistroBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_login
        );
    }


    private void registrarUsuario() {
        String email = binding.correo.getText().toString().trim();
        String contrasena = binding.contrasena.getText().toString().trim();

    }
}
