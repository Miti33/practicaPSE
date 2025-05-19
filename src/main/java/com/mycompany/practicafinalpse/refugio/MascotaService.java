package com.mycompany.practicafinalpse.refugio;

import com.mycompany.practicafinalpse.entities.Mascota;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class MascotaService {

    @PersistenceContext(unitName = "com.mycompany_PracticaFinalPSE_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public void crearMascota(Mascota mascota) {
        em.persist(mascota);
    }
}

