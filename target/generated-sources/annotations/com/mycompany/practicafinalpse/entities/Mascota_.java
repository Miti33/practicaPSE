package com.mycompany.practicafinalpse.entities;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-04-23T11:40:08")
@StaticMetamodel(Mascota.class)
public class Mascota_ { 

    public static volatile SingularAttribute<Mascota, String> especie;
    public static volatile SingularAttribute<Mascota, String> descripcion;
    public static volatile SingularAttribute<Mascota, String> estadoSalud;
    public static volatile SingularAttribute<Mascota, BigDecimal> costeAdopcion;
    public static volatile SingularAttribute<Mascota, String> raza;
    public static volatile SingularAttribute<Mascota, Integer> id;
    public static volatile SingularAttribute<Mascota, String> nombre;
    public static volatile SingularAttribute<Mascota, Integer> edad;

}