/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practicafinalpse.refugio;

import com.mycompany.practicafinalpse.jaas.LoginView;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Vasil
 */
@Named
@ViewScoped
public class RefugioBean implements Serializable {

    @Inject
    private SolicitudEJB solicitudEJB;

    @Inject
    private LoginView loginView;

    private List<SolicitudDTO> solicitudesPendientes;

    @PostConstruct
    public void init() {
        String emailRefugio = loginView.getAuthenticatedUser().getEmail();
        solicitudesPendientes = solicitudEJB.findSolicitudesPendientesPorRefugio(emailRefugio);
    }

    public void aceptarSolicitud(int id, int idMascota) {
        solicitudEJB.actualizarEstado(id,"Aceptada");
        solicitudEJB.rechazarSolicitudesPorMascota(idMascota);
        init();
    }

    public void rechazarSolicitud(int id) {
        solicitudEJB.actualizarEstado(id,"Rechazada");
        init();
    }

    public List<SolicitudDTO> getSolicitudesPendientes() {
    if (solicitudesPendientes == null && loginView.getAuthenticatedUser() != null) {
        String emailRefugio = loginView.getAuthenticatedUser().getEmail();
        solicitudesPendientes = solicitudEJB.findSolicitudesPendientesPorRefugio(emailRefugio);
    }
    return solicitudesPendientes;
}

}

