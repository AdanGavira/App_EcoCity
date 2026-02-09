package com.example.app_ecocity;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_ecocity.databinding.ActivityChatBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private ChatAdapter adapter;
    private List<MensajeChat> mensajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_chat
        );

        // Lista de mensajes
        mensajes = new ArrayList<>();

        // Adapter
        adapter = new ChatAdapter(mensajes);

        binding.rvChat.setLayoutManager(new LinearLayoutManager(this));
        binding.rvChat.setAdapter(adapter);

        // Mensaje inicial del bot
        mensajes.add(new MensajeChat(
                "Hola 👋 Soy el asistente de incidencias.\n" +
                        "Puedes preguntarme sobre prioridades, fotos, ubicación o conexión.",
                false
        ));
        adapter.notifyItemInserted(mensajes.size() - 1);
        scrollAbajo();


        binding.btnEnviar.setOnClickListener(v -> enviarMensaje());

        // Enviar mensaje
        binding.etMensaje.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                enviarMensaje();
                return true;
            }
            return false;
        });

        if (binding.btnBack != null) {
            binding.btnBack.setOnClickListener(v -> finish());
        }
    }

    private void enviarMensaje() {

        String texto = binding.etMensaje.getText().toString().trim();

        if (texto.isEmpty()) {
            Toast.makeText(this, "Escribe un mensaje", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mensaje del usuario
        mensajes.add(new MensajeChat(texto, true));
        adapter.notifyItemInserted(mensajes.size() - 1);
        scrollAbajo();

        // Respuesta del bot
        String respuesta = responder(texto);

        mensajes.add(new MensajeChat(respuesta, false));
        adapter.notifyItemInserted(mensajes.size() - 1);
        scrollAbajo();

        binding.etMensaje.setText("");
    }

    //Scroll al último mensaje
    private void scrollAbajo() {
        binding.rvChat.scrollToPosition(mensajes.size() - 1);
    }

    // Chatbot
    private String responder(String mensaje) {

        mensaje = mensaje.toLowerCase();

        if (mensaje.contains("prioridad")) {
            return "Usa prioridad ALTA si hay peligro para personas, MEDIA si afecta al servicio y BAJA si es un problema menor.";
        }

        if (mensaje.contains("foto") || mensaje.contains("imagen")) {
            return "Puedes adjuntar una foto desde la galería o usando la cámara al crear o editar una incidencia.";
        }

        if (mensaje.contains("ubicacion") || mensaje.contains("mapa")) {
            return "Puedes añadir una ubicación usando el mapa para indicar exactamente dónde ocurre la incidencia.";
        }

        if (mensaje.contains("internet") || mensaje.contains("conexión")) {
            return "Puedes crear incidencias sin conexión. Se sincronizan automáticamente cuando vuelve Internet.";
        }

        if (mensaje.contains("editar")) {
            return "Para editar una incidencia, pulsa el botón de editar en la lista de incidencias.";
        }

        if (mensaje.contains("borrar") || mensaje.contains("eliminar")) {
            return "Puedes borrar una incidencia desde la pantalla de edición usando el botón de eliminar.";
        }

        return "No he entendido tu pregunta 🤔\n" +
                "Puedes preguntarme sobre prioridades, fotos, ubicación o conexión.";
    }
}