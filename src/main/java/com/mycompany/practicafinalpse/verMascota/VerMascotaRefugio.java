/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practicafinalpse.verMascota;

import com.mycompany.practicafinalpse.entities.Mascota;
import com.mycompany.practicafinalpse.jaas.LoginView;
import com.mycompany.practicafinalpse.rest.MascotaFacadeREST;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

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

    private List<Mascota> mascotasRefugio;

    // Método para inicializar las mascotas del refugio
    @PostConstruct
    public void init() {
        System.out.println("Aqui si");
        // Obtén el email del refugio autenticado
        String emailRefugio = loginView.getAuthenticatedUser().getEmail();
        // Obtén las mascotas asociadas a ese refugio
        List<Mascota> todasMascotas = mascotaService.findAll();
        mascotasRefugio = filtrarMascotasPorRefugio(todasMascotas, emailRefugio);

    }

    // Método para obtener la lista de mascotas
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

    // Otros métodos de lógica de negocio si los necesitas (por ejemplo, eliminar mascota, actualizar, etc.)
}

