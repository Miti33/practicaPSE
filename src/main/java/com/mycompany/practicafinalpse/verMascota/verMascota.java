package com.mycompany.practicafinalpse.verMascota;

import com.mycompany.practicafinalpse.entities.Mascota;
import com.mycompany.practicafinalpse.rest.MascotaFacadeREST;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
@FlowScoped("verMascota")
public class verMascota implements Serializable {

    private int mascotaId;
    private String nombre;
    private int edad;
    private String imagenUrl;
    private String raza;
    private String descripcion;
    
    private String filtroNombre = "";
    private String filtroEspecie = "";
    private List<Mascota> mascotasFiltradas;
    
    @PersistenceContext
    EntityManager em;

    @EJB
    private MascotaFacadeREST mascotaService;

    @PostConstruct
    public void init() {
        aplicarFiltro(); // Inicializa con todos
    }

    public void aplicarFiltro() {
        List<Mascota> todas = mascotaService.findAll();
        mascotasFiltradas = new ArrayList<Mascota>();

        for (Mascota m : todas) {
            boolean coincideNombre = filtroNombre == null || filtroNombre.isEmpty()
                    || m.getNombre().toLowerCase().contains(filtroNombre.toLowerCase());
            boolean coincideEspecie = filtroEspecie == null || filtroEspecie.isEmpty()
                    || m.getEspecie().toLowerCase().contains(filtroEspecie.toLowerCase());

            if (coincideNombre && coincideEspecie) {
                mascotasFiltradas.add(m);
            }
        }
    }

    public void limpiarFiltro() {
        filtroNombre = "";
        filtroEspecie = "";
        aplicarFiltro();
    }
    
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

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public MascotaFacadeREST getMascotaService() {
        return mascotaService;
    }

    public void setMascotaService(MascotaFacadeREST mascotaService) {
        this.mascotaService = mascotaService;
    }
    public String getFiltroNombre() {
        return filtroNombre;
    }

    public void setFiltroNombre(String filtroNombre) {
        this.filtroNombre = filtroNombre;
    }

    public String getFiltroEspecie() {
        return filtroEspecie;
    }

    public void setFiltroEspecie(String filtroEspecie) {
        this.filtroEspecie = filtroEspecie;
    }

    public List<Mascota> getMascotasFiltradas() {
        return mascotasFiltradas;
    }
}

