package com.mycompany.practicafinalpse.rest;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.mycompany.practicafinalpse.rest.ClienteFacadeREST.class);
        resources.add(com.mycompany.practicafinalpse.rest.MascotaFacadeREST.class);
        resources.add(com.mycompany.practicafinalpse.rest.RefugioFacadeREST.class);
        resources.add(com.mycompany.practicafinalpse.rest.SolicitudFacadeREST.class);
        resources.add(com.mycompany.practicafinalpse.rest.UsuarioFacadeREST.class);
    }
    
}
