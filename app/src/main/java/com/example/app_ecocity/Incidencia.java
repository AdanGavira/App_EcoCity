package com.example.app_ecocity;

public class Incidencia {

    private int id;
    private String titulo;
    private String descripcion;
    private String prioridad;
    private String fecha;
    private String fotoUrl;
    private String ubicacion;

    //Constructor
    public Incidencia(int id, String titulo, String descripcion, String prioridad, String fecha, String fotoUrl, String ubicacion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.fecha = fecha;
        this.fotoUrl = fotoUrl;
        this.ubicacion = ubicacion;
    }
    //Getters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public String getFecha() {
        return fecha;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public String getUbicacion() {
        return ubicacion;
    }

}
