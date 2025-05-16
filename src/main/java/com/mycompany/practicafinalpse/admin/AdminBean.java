/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practicafinalpse.admin;

import com.mycompany.practicafinalpse.entities.Refugio;
import com.mycompany.practicafinalpse.jaas.UserEJB;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author david
 */
@Named
@ViewScoped
public class AdminBean implements Serializable {

    @Inject
    private UserEJB userEJB;

    private List<Refugio> refugiosPendientes;

    @PostConstruct
    public void init() {
        refugiosPendientes = userEJB.findRefugiosPendientes();
    }

    public void autorizar(String email) {
        userEJB.validateRefugio(email);
        init(); 
    }

    public void rechazar(String email) {
        userEJB.rejectRefugio(email);
        init(); 
    }

    public List<Refugio> getRefugiosPendientes() {
        return refugiosPendientes;
    }
}
