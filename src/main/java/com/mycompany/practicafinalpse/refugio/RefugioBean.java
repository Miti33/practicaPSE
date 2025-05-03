/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practicafinalpse.refugio;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Vasil
 */
@Named("refugioBean")
@ViewScoped
public class RefugioBean implements Serializable {

    private List<SolicitudDTO> solicitudesPendientes;

    @PostConstruct
    public void init() {
        //solicitudesPendientes = cargarSolicitudesPendientes();
    }
    /*
    public List<SolicitudDTO> cargarSolicitudesPendientes() {
        // Aquí llamas al servicio REST que devuelve las solicitudes con estado 'pendiente'
        return RestClient.obtenerSolicitudesPorRefugioYEstado("pendiente");
    }

    public void aceptarSolicitud(int solicitudId) {
        RestClient.actualizarEstadoSolicitud(solicitudId, "aceptada");
        solicitudesPendientes = cargarSolicitudesPendientes();
    }

    public void rechazarSolicitud(int solicitudId) {
        RestClient.actualizarEstadoSolicitud(solicitudId, "rechazada");
        solicitudesPendientes = cargarSolicitudesPendientes();
    }

    public List<SolicitudDTO> getSolicitudesPendientes() {
        return solicitudesPendientes;
    }
    */
}

