package com.mycompany.practicafinalpse.verMascota;

import com.mycompany.practicafinalpse.jaas.LoginView;
import com.mycompany.practicafinalpse.refugio.SolicitudDTO;
import com.mycompany.practicafinalpse.refugio.SolicitudEJB;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class MisSolicitudesBean implements Serializable {

    @Inject
    private SolicitudEJB solicitudEJB;

    @Inject
    private LoginView loginView;

    private List<SolicitudDTO> solicitudes;

    @PostConstruct
    public void init() {
        String email = loginView.getAuthenticatedUser().getEmail();
        solicitudes = solicitudEJB.findSolicitudesPendientesPorCliente(email);
    }

    public List<SolicitudDTO> getSolicitudes() {
        return solicitudes;
    }

    public void eliminarSolicitud(int id) {
        solicitudEJB.eliminarSolicitudPorId(id);
        for (Iterator<SolicitudDTO> it = solicitudes.iterator(); it.hasNext();) {
            if (it.next().getId() == id) {
                it.remove();
                break;
            }
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Solicitud eliminada correctamente"));
    }
}
