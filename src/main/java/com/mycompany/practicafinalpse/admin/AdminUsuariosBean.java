package com.mycompany.practicafinalpse.admin;

import com.mycompany.practicafinalpse.entities.Usuario;
import com.mycompany.practicafinalpse.jaas.UserEJB;
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
public class AdminUsuariosBean implements Serializable {

    @Inject
    private UserEJB userEJB;

    private List<Usuario> usuarios;

    @PostConstruct
    public void init() {
        usuarios = userEJB.findAllUsuarios();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public String getGrupoDelUsuario(String email) {
        return userEJB.getGrupoPorEmail(email);
    }
    
    public void eliminarUsuario(String email) {
        userEJB.eliminarUsuarioAdmin(email);
        for (Iterator<Usuario> it = usuarios.iterator(); it.hasNext();) {
            Usuario u = it.next();
            if (u.getEmail() != null && u.getEmail().equals(email)) {
                it.remove();
                break;
            }
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario eliminado: " + email, null));
    }
}
