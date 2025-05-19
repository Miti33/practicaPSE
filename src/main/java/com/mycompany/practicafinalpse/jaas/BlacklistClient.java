package com.mycompany.practicafinalpse.jaas;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

@ApplicationScoped
public class BlacklistClient {

    private final String BASE_URL = "http://serpis.infor.uva.es:80/darklist/api/validar_adoptante/";

    public boolean estaEnListaNegra(String email) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(BASE_URL + email);

        try {
            Response response = target.request(MediaType.APPLICATION_JSON).get();

            if (response.getStatus() == 404) {
                return false; // No está en lista negra
            }

            String json = response.readEntity(String.class);

            try (JsonReader reader = Json.createReader(new StringReader(json))) {
                JsonObject obj = reader.readObject();
                String estado = obj.getString("listaNegra");
                return "si".equalsIgnoreCase(estado);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Por seguridad, permitimos si falla
        } finally {
            client.close();
        }
    }
}
