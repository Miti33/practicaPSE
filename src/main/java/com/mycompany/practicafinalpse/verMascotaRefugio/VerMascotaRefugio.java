/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practicafinalpse.verMascotaRefugio;

import com.mycompany.practicafinalpse.entities.Mascota;
import com.mycompany.practicafinalpse.jaas.LoginView;
import com.mycompany.practicafinalpse.rest.MascotaFacadeREST;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author david
 */
@Named("verMascotaRefugio")
@ViewScoped
public class VerMascotaRefugio implements Serializable {

    @Inject
    private MascotaFacadeREST mascotaService;

    @Inject
    private LoginView loginView;
    
    @Inject
    private MascotaClientService mascotaClient;

    private List<Mascota> mascotasRefugio;

    @PostConstruct
    public void init() {
        System.out.println("Aqui si");
        String emailRefugio = loginView.getAuthenticatedUser().getEmail();
        List<Mascota> todasMascotas = mascotaService.findAll();
        mascotasRefugio = filtrarMascotasPorRefugio(todasMascotas, emailRefugio);

    }

    public List<Mascota> getMascotasRefugio() {
        System.out.println("Aqui");
        if (mascotasRefugio == null && loginView.getAuthenticatedUser() != null) {
            String emailRefugio = loginView.getAuthenticatedUser().getEmail();
            List<Mascota> todasMascotas = mascotaService.findAll();
            mascotasRefugio = filtrarMascotasPorRefugio(todasMascotas, emailRefugio);
        }
        return mascotasRefugio;
    }
    
    private List<Mascota> filtrarMascotasPorRefugio(List<Mascota> todasMascotas, String emailRefugio) {
        List<Mascota> mascotasRefugio = new ArrayList<>();
        for (Mascota m : todasMascotas) {
            if (m.getEmail() != null && m.getEmail().equals(emailRefugio)) {
                mascotasRefugio.add(m);
            }
        }
        return mascotasRefugio;
    }
    
    public void guardarCambios(Mascota mascota) {
        mascotaClient.editarMascota(mascota);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Mascota actualizada correctamente", null));
    }

    public void eliminarMascota(int id) {
        mascotaClient.eliminarMascota(id);
        
        for (Iterator<Mascota> it = mascotasRefugio.iterator(); it.hasNext();) {
            Mascota m = it.next();
            if (m.getId() != null && m.getId() == id) {
                it.remove();
                break;
            }
        }
        
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Mascota eliminada", null));
    }
}

