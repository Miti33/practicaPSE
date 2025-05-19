package com.mycompany.practicafinalpse.refugio;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("mascotaBean")
@ViewScoped
public class MascotaBean implements Serializable {

    @Inject
    private MascotaService mascotaService;

    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private String estadoSalud;
    private double costeAdopcion;
    private String descripcion;
    private String imagenUrl;
    private String email;

    public MascotaService getMascotaService() {
        return mascotaService;
    }

    public void setMascotaService(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEstadoSalud() {
        return estadoSalud;
    }

    public void setEstadoSalud(String estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    public double getCosteAdopcion() {
        return costeAdopcion;
    }

    public void setCosteAdopcion(double costeAdopcion) {
        this.costeAdopcion = costeAdopcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}
