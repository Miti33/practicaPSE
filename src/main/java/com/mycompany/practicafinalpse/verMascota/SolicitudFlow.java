package com.mycompany.practicafinalpse.verMascota;

import com.mycompany.practicafinalpse.entities.Mascota;
import com.mycompany.practicafinalpse.entities.Solicitud;
import com.mycompany.practicafinalpse.jaas.LoginView;
import com.mycompany.practicafinalpse.refugio.SolicitudEJB;
import javax.annotation.PostConstruct;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Date;

@Named
@FlowScoped("verMascota")
public class SolicitudFlow implements Serializable {

    private Solicitud solicitud;
    
    private Mascota mascota;

    @Inject
    private SolicitudEJB solicitudEJB;

    @Inject
    private LoginView loginView;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {
        solicitud = new Solicitud();
    }

    public String confirmar(int mascotaId) {
        mascota = em.find(Mascota.class, mascotaId);
        if (mascota == null) return "error";

        solicitud.setMascotaId(mascotaId);
        solicitud.setClienteEmail(loginView.getAuthenticatedUser().getEmail());
        solicitud.setRefugioEmail(mascota.getEmail());
        solicitud.setFechaSolicitud(new Date());
        solicitud.setEstado("Pendiente");

        solicitudEJB.crearSolicitud(solicitud);
        return "print";
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }
    
    public Mascota getMascota(){
        return mascota;
    } 

    public String getQrText() {
        return "ID Solicitud: " + solicitud.getId();
    }
}
