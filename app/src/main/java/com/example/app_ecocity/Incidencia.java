package com.example.app_ecocity;

public class Incidencia {

    private String id;
    private String userId;
    private String titulo;
    private String descripcion;
    private String prioridad;
    private String fecha;
    private String fotoUrl;
    private String ubicacion;

    //Constructores
    public Incidencia(String userId,String titulo, String descripcion, String prioridad, String fecha, String fotoUrl, String ubicacion) {
        this.userId = userId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.fecha = fecha;
        this.fotoUrl = fotoUrl;
        this.ubicacion = ubicacion;
    }

    public Incidencia(){}

    //Getters
    public String getUserId() {
        return userId;
    }

    public String getId() {
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

    //Setters
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

}
