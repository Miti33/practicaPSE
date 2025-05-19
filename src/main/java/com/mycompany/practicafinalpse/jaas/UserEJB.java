
package com.mycompany.practicafinalpse.jaas;

import com.mycompany.practicafinalpse.entities.Cliente;
import com.mycompany.practicafinalpse.entities.Refugio;
import com.mycompany.practicafinalpse.entities.UserGroups;
import com.mycompany.practicafinalpse.entities.Usuario;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
        em.persist(user);
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

    public List<Refugio> findRefugiosPendientes() {
        return em.createQuery("SELECT r FROM Refugio r WHERE r.autorizado = false", Refugio.class)
                .getResultList();
    }

    public void insertCliente(String email, String apellidos, String nif, String domicilio, String telefono, Date fechaNacimiento) {
        Cliente cliente = new Cliente();
        cliente.setEmail(email);
        cliente.setApellidos(apellidos);
        cliente.setNif(nif);
        cliente.setDomicilio(domicilio);
        cliente.setTelefono(telefono);
        cliente.setFechaNacimiento(fechaNacimiento);
        em.persist(cliente);
    }

    public void insertRefugio(String email, String cif, String domicilio, String telefono) {
        Refugio refugio = new Refugio();
        refugio.setEmail(email);
        refugio.setCif(cif);
        refugio.setDomicilio(domicilio);
        refugio.setTelefono(telefono);
        refugio.setAutorizado(false);
        em.persist(refugio);
    }

    public void addGroup(String email, String groupname) {
        UserGroups group = new UserGroups();
        group.setEmail(email);
        group.setGroupname(groupname);
        em.persist(group);
    }

    public void deleteUser(Usuario user) {
        Usuario managedUser = em.merge(user);

        //Eliminar de user_groups por email
        em.createQuery("DELETE FROM UserGroups ug WHERE ug.email = :email")
                .setParameter("email", managedUser.getEmail())
                .executeUpdate();

        em.createQuery("DELETE FROM Usuario u WHERE u.email = :email")
                .setParameter("email", managedUser.getEmail())
                .executeUpdate();

        //Eliminar de cliente (si existe)
        try {
            Cliente cliente = em.createQuery("SELECT c FROM Cliente c WHERE c.email = :email", Cliente.class)
                    .setParameter("email", managedUser.getEmail())
                    .getSingleResult();
            em.remove(cliente);
        } catch (NoResultException e) {
            // No es cliente
        }

        //Eliminar de refugio (si existe)
        try {
            Refugio refugio = em.createQuery("SELECT r FROM Refugio r WHERE r.email = :email", Refugio.class)
                    .setParameter("email", managedUser.getEmail())
                    .getSingleResult();
            em.remove(refugio);
        } catch (NoResultException e) {
            // No es refugio
        }

        //Eliminar usuario
        em.remove(managedUser);
    }

    public void validateRefugio(String email) {
        Refugio refugio = em.find(Refugio.class, email);
        if (refugio != null) {
            refugio.setAutorizado(true);
            em.merge(refugio);
        }
    }

    public void rejectRefugio(String email) {
        Refugio refugio = em.find(Refugio.class, email);
        Usuario usuario = em.find(Usuario.class, email);
        UserGroups user_groups = em.find(UserGroups.class, email);

        if (refugio != null) {
            em.remove(refugio);
            em.remove(usuario);
            em.remove(user_groups);
        }
    }

    public List<Usuario> findAllUsuarios() {
        return em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
    }

    public void eliminarUsuarioAdmin(String email) {
        em.createQuery("DELETE FROM Solicitud s WHERE s.clienteEmail = :email OR s.refugioEmail = :email")
                .setParameter("email", email)
                .executeUpdate();

        em.createQuery("DELETE FROM Mascota m WHERE m.email = :email")
                .setParameter("email", email)
                .executeUpdate();

        em.createQuery("DELETE FROM Cliente c WHERE c.email = :email")
                .setParameter("email", email)
                .executeUpdate();

        em.createQuery("DELETE FROM Refugio r WHERE r.email = :email")
                .setParameter("email", email)
                .executeUpdate();

        em.createQuery("DELETE FROM UserGroups g WHERE g.email = :email")
                .setParameter("email", email)
                .executeUpdate();

        Usuario u = em.find(Usuario.class, email);
        if (u != null) {
            em.remove(u);
        }
    }

    public String getGrupoPorEmail(String email) {
        try {
            return em.createQuery("SELECT g.groupname FROM UserGroups g WHERE g.email = :email", String.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return "sin grupo";
        }
    }

}
