package com.mycompany.practicafinalpse.verMascotaRefugio;

import com.mycompany.practicafinalpse.entities.Mascota;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
public class MascotaClientService {

    private final Client client;
    private final WebTarget baseTarget;

    public MascotaClientService() {
        client = ClientBuilder.newClient();
        baseTarget = client.target("http://localhost:8080/PracticaFinalPSE/webresources/com.mycompany.practicafinalpse.entities.mascota");
    }

    public void editarMascota(Mascota mascota) {
        baseTarget.path(String.valueOf(mascota.getId()))
                  .request()
                  .put(Entity.entity(mascota, MediaType.APPLICATION_JSON));
    }

    public void eliminarMascota(int id) {
        baseTarget.path(String.valueOf(id))
                  .request()
                  .delete();
    }

    public void close() {
        client.close();
    }
}

