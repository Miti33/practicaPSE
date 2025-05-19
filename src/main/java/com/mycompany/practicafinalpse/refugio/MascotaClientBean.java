package com.mycompany.practicafinalpse.refugio;

import com.mycompany.practicafinalpse.entities.Mascota;
import com.mycompany.practicafinalpse.jaas.LoginView;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@Named
@RequestScoped
public class MascotaClientBean {

    @Inject
    MascotaBean bean;
    @Inject
    private LoginView loginView;


    Client client;
    WebTarget target;
    

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/PracticaFinalPSE/webresources/com.mycompany.practicafinalpse.entities.mascota");
    }

    @PreDestroy
    public void destroy() {
        client.close();
    }

    public void addMascota() {
        Mascota mascota = new Mascota();
        mascota.setNombre(bean.getNombre());
        mascota.setEspecie(bean.getEspecie());
        mascota.setRaza(bean.getRaza());
        mascota.setEdad(bean.getEdad());
        mascota.setEstadoSalud(bean.getEstadoSalud());
        mascota.setCosteAdopcion(bean.getCosteAdopcion());
        mascota.setDescripcion(bean.getDescripcion());
        mascota.setImagenUrl(bean.getImagenUrl());
        mascota.setEmail(loginView.getAuthenticatedUser().getEmail());
        target.request().post(Entity.entity(mascota, MediaType.APPLICATION_JSON));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Mascota añadida correctamente"));
    }
}
