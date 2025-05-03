/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practicafinalpse.verMascota;

import com.mycompany.practicafinalpse.entities.Mascota;
import com.mycompany.practicafinalpse.rest.MascotaFacadeREST;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Vasil
 */
@Named
@FlowScoped("verMascota")
public class verMascota implements Serializable {

    private int mascotaId;
    private String nombre;
    private int edad;
    private String imagenUrl;
    private String raza;
    private String descripcion;

    @PersistenceContext
    EntityManager em;

    @EJB
    private MascotaFacadeREST mascotaService;

    public List<Mascota> getMascotas() {
        return mascotaService.findAll();
    }
    
    public void setMascotaId(int mascotaId) {
        this.mascotaId = mascotaId;
        Mascota mascota = em.find(Mascota.class, mascotaId);
        if (mascota != null) {
            this.nombre = mascota.getNombre();
            this.edad = mascota.getEdad();
            this.imagenUrl = mascota.getImagenUrl();
            this.raza = mascota.getRaza();
            this.descripcion = mascota.getDescripcion();
        }
    }

    public int getMascotaId() { return mascotaId; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public String getFotoUrl() { return imagenUrl; }
    public String getRaza() { return raza; }
    public String getDescripcion() { return descripcion; }

    public String confirmarAdopcion() {
        return "confirmado";
    }
}

