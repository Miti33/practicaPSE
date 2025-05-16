/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practicafinalpse.jaas;

import com.mycompany.practicafinalpse.entities.Refugio;
import com.mycompany.practicafinalpse.entities.Usuario;
import com.mycompany.practicafinalpse.refugio.RefugioService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vasil
 */

@Named
@SessionScoped
public class LoginView implements Serializable{
    @Inject
    private UserEJB userEJB; 
    private String email;
    private String password;
    private Usuario user;
    @Inject
    private RefugioService refugioService;

    public UserEJB getUserEJB() {
        return userEJB;
    }

    public void setUserEJB(UserEJB userEJB) {
        this.userEJB = userEJB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Usuario getAuthenticatedUser() {
        return user;
    }
    
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(email, password);
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login incorrecto!", null));
            return null;
        }

        this.user = userEJB.findByEmail(request.getUserPrincipal().getName());

        if (request.isUserInRole("users")) {
            return "/users/privatepage?faces-redirect=true";
        } else if (request.isUserInRole("refugio")) {
            System.out.println("Aqui 1");
            if (refugioService.isRefugioAutorizado(user.getEmail())) {
                System.out.println("Aqui 2");
                return "/refugio/privatepage?faces-redirect=true";
            } else {
                System.out.println("Aqui 3");
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Tu refugio aun no esta autorizado", null));
                logout();
                return null;
            }
        } else if (request.isUserInRole("admin")) {
            return "/admin/privatepage?faces-redirect=true";
        } else {
            return "login";
        }
    }

    
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            this.user = null;
            request.logout();
            ((HttpSession) context.getExternalContext().getSession(false)).invalidate();
        } catch (ServletException e) {
            System.out.println("Fallo durante el proceso de logout!");
        }
        return "/index?faces-redirect=true";
    }
    
    public boolean isUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        return request.isUserInRole("users");
    }

    public boolean isRefugio() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        return request.isUserInRole("refugio");
    }

    public boolean isAdmin() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        return request.isUserInRole("admin");
    }
    
    public String deleteAccount() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (user != null) {
            try {
                userEJB.deleteUser(user); 
                logout(); 
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cuenta eliminada correctamente.", null));
                return "/index?faces-redirect=true";
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar la cuenta.", null));
            }
        }
        return null;
    }
    
}
