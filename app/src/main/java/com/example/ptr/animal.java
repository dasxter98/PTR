package com.example.ptr;

public class animal {

    String nombre;
    Boolean vacuna;
    Boolean chip;
    Boolean genero;
    Integer edad;
    Integer peso;
    Integer estado;
    Integer filtro;
    String fotoUrl;




    public animal(String nombre, Boolean vacuna, Boolean chip,Boolean genero, Integer edad, Integer peso, Integer estado ,Integer filtro,String fotoUrl ) {
        this.nombre = nombre;
        this.vacuna = vacuna;
        this.chip = chip;
        this.edad = edad;
        this.peso = peso;
        this.estado = estado;
        this.filtro=filtro;
        this.genero=genero;
        this.fotoUrl=fotoUrl;

    }

    public animal() {

    }


    public Boolean getGenero() {
        return genero;
    }

    public void setGenero(Boolean genero) {
        this.genero = genero;
    }


    public Integer getFiltro() {
        return filtro;
    }

    public void setFiltro(Integer filtro) {
        this.filtro = filtro;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getVacuna() {
        return vacuna;
    }

    public void setVacuna(Boolean vacuna) {
        this.vacuna = vacuna;
    }

    public Boolean getChip() {
        return chip;
    }

    public void setChip(Boolean chip) {
        this.chip = chip;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }
    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

}
