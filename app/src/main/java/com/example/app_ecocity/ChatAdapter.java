package com.example.app_ecocity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private static final int TIPO_BOT = 0;
    private static final int TIPO_USUARIO = 1;

    private List<MensajeChat> mensajes;

    public ChatAdapter(List<MensajeChat> mensajes) {
        this.mensajes = mensajes;
    }

    @Override
    public int getItemViewType(int position) { //Comprueba si el mensaje es del bot o el usuario
        if (mensajes.get(position).isUsuario()) return TIPO_USUARIO;
        return TIPO_BOT;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( //Crea el mensaje del bot o usuario
            @NonNull ViewGroup parent,
            int viewType) {

        int layoutId = (viewType == TIPO_USUARIO)
                ? R.layout.mensaje_usuario
                : R.layout.mensaje_bot;

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(layoutId, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( //Establece el texto del mensaje
            @NonNull ViewHolder holder,
            int position) {

        MensajeChat mensaje = mensajes.get(position);
        holder.tvMensaje.setText(mensaje.getTexto());
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    // ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder { //Recoje el textView del mensaje
        TextView tvMensaje;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMensaje = itemView.findViewById(R.id.tvMensaje);
        }
    }
}
