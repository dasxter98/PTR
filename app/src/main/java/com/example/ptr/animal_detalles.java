package com.example.ptr;

public class animal_detalles {
    String descripcion;
    String veterinario;
    String cita;
    String comentarios;


    public animal_detalles(String descripcion, String veterinario, String cita, String comentarios) {
        this.descripcion = descripcion;
        this.veterinario = veterinario;
        this.cita = cita;
        this.comentarios = comentarios;
    }
    public animal_detalles() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public String getCita() {
        return cita;
    }

    public void setCita(String cita) {
        this.cita = cita;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }


}
