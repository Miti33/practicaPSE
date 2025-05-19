package com.mycompany.practicafinalpse.refugio;

import com.mycompany.practicafinalpse.entities.Mascota;
import com.mycompany.practicafinalpse.entities.Solicitud;
import com.mycompany.practicafinalpse.jaas.BlacklistClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



@Stateless
public class SolicitudEJB {

    
    @Inject
    private BlacklistClient blacklistClient;
    
    @PersistenceContext
    private EntityManager em;

    public Solicitud crearSolicitud(Solicitud solicitud) {
        em.persist(solicitud);
        return solicitud;
    }

    public Solicitud findById(Integer id) {
        return em.find(Solicitud.class, id);
    }
    
    public List<SolicitudDTO> findSolicitudesPendientesPorRefugio(String refugioEmail) {
        List<Solicitud> solicitudes = em.createQuery(
                "SELECT s FROM Solicitud s WHERE s.refugioEmail = :email AND s.estado = :estado", Solicitud.class)
                .setParameter("email", refugioEmail)
                .setParameter("estado", "Pendiente")
                .getResultList();

        List<SolicitudDTO> dtos = new ArrayList<>();

        for (Solicitud s : solicitudes) {
            Mascota m = em.find(Mascota.class, s.getMascotaId());
            if (m == null) {
                continue;
            }
            SolicitudDTO dto = new SolicitudDTO();
            dto.setId(s.getId());
            dto.setIdMascota(m.getId());
            dto.setMascotaNombre(m.getNombre());
            dto.setEspecie(m.getEspecie());
            dto.setClienteEmail(s.getClienteEmail());
            dto.setFechaSolicitud(s.getFechaSolicitud().toString());
            dto.setEnlistaNegra(blacklistClient.estaEnListaNegra(s.getClienteEmail()));
            dtos.add(dto);
        }

        return dtos;
    }
    
    public List<SolicitudDTO> findSolicitudesPendientesPorCliente(String clienteEmail) {
        List<Solicitud> solicitudes = em.createQuery(
                "SELECT s FROM Solicitud s WHERE s.clienteEmail = :email AND s.estado = :estado", Solicitud.class)
                .setParameter("email", clienteEmail)
                .setParameter("estado", "Pendiente")
                .getResultList();

        List<SolicitudDTO> dtos = new ArrayList<>();

        for (Solicitud s : solicitudes) {
            Mascota m = em.find(Mascota.class, s.getMascotaId());
            if (m == null) {
                continue;
            }
            SolicitudDTO dto = new SolicitudDTO();
            dto.setId(s.getId());
            dto.setIdMascota(m.getId());
            dto.setMascotaNombre(m.getNombre());
            dto.setEspecie(m.getEspecie());
            dto.setClienteEmail(s.getClienteEmail());
            dto.setFechaSolicitud(s.getFechaSolicitud().toString());
            dto.setEstado(s.getEstado());
            dto.setEnlistaNegra(blacklistClient.estaEnListaNegra(s.getClienteEmail()));
            dtos.add(dto);
        }

        return dtos;
    }

    public void actualizarEstado(int id, String nuevoEstado) {
        Solicitud solicitud = em.find(Solicitud.class, id);
        if (solicitud != null) {
            solicitud.setEstado(nuevoEstado);
            em.merge(solicitud);
        }
    }
    public void rechazarSolicitudesPorMascota(int mascotaId) {
        List<Solicitud> solicitudes = em.createQuery(
                "SELECT s FROM Solicitud s WHERE s.mascotaId = :id AND s.estado != :estado", Solicitud.class)
                .setParameter("id", mascotaId)
                .setParameter("estado", "Aceptada")
                .getResultList();

        for (Solicitud s : solicitudes) {
            s.setEstado("Rechazada");
            em.merge(s);
        }
        
        Mascota mascota = em.find(Mascota.class, mascotaId);
        if (mascota != null) {
            em.remove(mascota);
        } 
    }

    public void eliminarSolicitudPorId(int id) {
        Solicitud s = em.find(Solicitud.class, id);
        if (s != null && "Pendiente".equalsIgnoreCase(s.getEstado())) {
            em.remove(s);
        }
    }
}
