/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practicafinalpse.refugio;

import com.mycompany.practicafinalpse.entities.Refugio;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author david
 */
@Stateless
public class RefugioService {

    @PersistenceContext(unitName = "com.mycompany_PracticaFinalPSE_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public boolean isRefugioAutorizado(String email) {
        TypedQuery<Refugio> query = em.createQuery(
            "SELECT r FROM Refugio r WHERE r.email = :email", Refugio.class);
        query.setParameter("email", email);
        Refugio refugio = query.getSingleResult();
        System.out.println(refugio.getEmail());
        System.out.println(refugio.getTelefono());
        return refugio.getAutorizado();
    }
}