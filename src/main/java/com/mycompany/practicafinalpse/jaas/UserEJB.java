/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practicafinalpse.jaas;

import com.mycompany.practicafinalpse.entities.UserGroups;
import com.mycompany.practicafinalpse.entities.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Vasil
 */
@Stateless
public class UserEJB {

    @PersistenceContext
    private EntityManager em;

    public Usuario createUser(Usuario user) {
        try {
            user.setPassword(AuthenticationUtils.encodeSHA256(user.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException("Error codificando la contraseña", e);
        }
        UserGroups group = new UserGroups();
        group.setEmail(user.getEmail());
        group.setGroupname("users");
        em.persist(user);
        em.persist(group);
        return user;
    }

    public Usuario findByEmail(String email) {
        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.findByEmail",
                Usuario.class);
        query.setParameter("email", email);
        Usuario user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
        }
        return user;
    }
}
