package com.mycompany.practicafinalpse.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "mascota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mascota.findAll", query = "SELECT m FROM Mascota m"),
    @NamedQuery(name = "Mascota.findById", query = "SELECT m FROM Mascota m WHERE m.id = :id"),
    @NamedQuery(name = "Mascota.findByNombre", query = "SELECT m FROM Mascota m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Mascota.findByEspecie", query = "SELECT m FROM Mascota m WHERE m.especie = :especie"),
    @NamedQuery(name = "Mascota.findByRaza", query = "SELECT m FROM Mascota m WHERE m.raza = :raza"),
    @NamedQuery(name = "Mascota.findByEdad", query = "SELECT m FROM Mascota m WHERE m.edad = :edad"),
    @NamedQuery(name = "Mascota.findByEstadoSalud", query = "SELECT m FROM Mascota m WHERE m.estadoSalud = :estadoSalud"),
    @NamedQuery(name = "Mascota.findByCosteAdopcion", query = "SELECT m FROM Mascota m WHERE m.costeAdopcion = :costeAdopcion"),
    @NamedQuery(name = "Mascota.findByDescripcion", query = "SELECT m FROM Mascota m WHERE m.descripcion = :descripcion"),
    @NamedQuery(name = "Mascota.findByImagen_url", query = "SELECT m FROM Mascota m WHERE m.imagenUrl = :imagenUrl"),
    @NamedQuery(name = "Mascota.findByEmail", query = "SELECT m FROM Mascota m WHERE m.email = :email")})
public class Mascota implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "especie")
    private String especie;
    @Size(max = 50)
    @Column(name = "raza")
    private String raza;
    @Column(name = "edad")
    private Integer edad;
    @Size(max = 2147483647)
    @Column(name = "estado_salud")
    private String estadoSalud;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "coste_adopcion")
    private BigDecimal costeAdopcion;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "imagen_url")
    private String imagenUrl;
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    

    public Mascota() {
    }

    public Mascota(Integer id) {
        this.id = id;
    }

    public Mascota(Integer id, String nombre, String especie) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getEstadoSalud() {
        return estadoSalud;
    }

    public void setEstadoSalud(String estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    public BigDecimal getCosteAdopcion() {
        return costeAdopcion;
    }

    public void setCosteAdopcion(BigDecimal costeAdopcion) {
        this.costeAdopcion = costeAdopcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mascota)) {
            return false;
        }
        Mascota other = (Mascota) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.practicafinalpse.entities.Mascota[ id=" + id + " ]";
    }

    public void setCosteAdopcion(double costeAdopcion) {
        this.costeAdopcion = BigDecimal.valueOf(costeAdopcion);
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
