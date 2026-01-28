package com.example.app_ecocity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Esta clase sirve para poder gestionar las funciones de cada carta de incidencia
public class IncidenciaAdapter extends RecyclerView.Adapter<IncidenciaAdapter.ViewHolder> {

    private List<Incidencia> lista;

    public IncidenciaAdapter(List<Incidencia> lista) {
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitulo, tvSubtitulo;
        View viewCircle;
        Button btnEditar;

        public ViewHolder(@NonNull View itemView) { //Para llamar a los campos de nuestro carta_incidencia.xml
            super(itemView);

            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvSubtitulo = itemView.findViewById(R.id.tvSubtitulo);
            viewCircle = itemView.findViewById(R.id.viewCircle);
            btnEditar = itemView.findViewById(R.id.btnEditar);
        }
    }

    @NonNull
    @Override
    public IncidenciaAdapter.ViewHolder onCreateViewHolder( //Aquí dentro llamaremos a carta_incidencia.xml para pasarle el inflater
    @NonNull
    ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carta_incidencia, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( //Establecemos los valores de los campos de cada carta
    @NonNull
        IncidenciaAdapter.ViewHolder holder, int position) {

        Incidencia incidencia = lista.get(position);

        holder.tvTitulo.setText(incidencia.getTitulo());

        String desc = incidencia.getDescripcion();
        if (desc.length() > 30) desc = desc.substring(0, 30) + "...";
        holder.tvSubtitulo.setText(desc);

        //Establecer color de prioridad de la carta

        int colorId = 0;

        switch (incidencia.getPrioridad()) {
            case "Alta":
                colorId = R.color.rojo;
                break;
            case "Media":
                colorId = R.color.amarillo;
                break;
            case "Baja":
                colorId = R.color.verde;
                break;
        }

        int color = ContextCompat.getColor(holder.viewCircle.getContext(), colorId);

        ViewCompat.setBackgroundTintList(holder.viewCircle, ColorStateList.valueOf(color));

        //Boton para editar que te lleva al activity de editar incidencias y si clickas la carta te llevará al activity de información

        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditarIncidenciasActivity.class);
            intent.putExtra("INCIDENCIA_ID", incidencia.getId());
            v.getContext().startActivity(intent);
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), InformacionActivity.class);
            intent.putExtra("INCIDENCIA_ID", incidencia.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { //Recoge cuantas cartas de incidencias existen
        return lista.size();
    }

}
