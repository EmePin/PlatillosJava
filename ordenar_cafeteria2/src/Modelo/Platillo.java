/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aimee
 */
@Entity
@Table(name = "PLATILLO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Platillo.findAll", query = "SELECT p FROM Platillo p")
    , @NamedQuery(name = "Platillo.findByIdplatillo", query = "SELECT p FROM Platillo p WHERE p.idplatillo = :idplatillo")
    , @NamedQuery(name = "Platillo.findByNombre", query = "SELECT p FROM Platillo p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Platillo.findByTiempo", query = "SELECT p FROM Platillo p WHERE p.tiempo = :tiempo")
    , @NamedQuery(name = "Platillo.findByPrecio", query = "SELECT p FROM Platillo p WHERE p.precio = :precio")
    , @NamedQuery(name = "Platillo.findByPorcion", query = "SELECT p FROM Platillo p WHERE p.porcion = :porcion")
    , @NamedQuery(name = "Platillo.findByUnidaddm", query = "SELECT p FROM Platillo p WHERE p.unidaddm = :unidaddm")
    , @NamedQuery(name = "Platillo.findByExistencia", query = "SELECT p FROM Platillo p WHERE p.existencia = :existencia")})
public class Platillo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDPLATILLO")
    private Integer idplatillo;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "TIEMPO")
    private Integer tiempo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO")
    private Double precio;
    @Column(name = "PORCION")
    private Integer porcion;
    @Column(name = "UNIDADDM")
    private String unidaddm;
    @Column(name = "EXISTENCIA")
    private Integer existencia;
    @OneToMany(mappedBy = "idpla")
    private List<Ingpla> ingplaList;

    public Platillo() {
    }

    public Platillo(Integer idplatillo) {
        this.idplatillo = idplatillo;
    }

    public Integer getIdplatillo() {
        return idplatillo;
    }

    public void setIdplatillo(Integer idplatillo) {
        this.idplatillo = idplatillo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getPorcion() {
        return porcion;
    }

    public void setPorcion(Integer porcion) {
        this.porcion = porcion;
    }

    public String getUnidaddm() {
        return unidaddm;
    }

    public void setUnidaddm(String unidaddm) {
        this.unidaddm = unidaddm;
    }

    public Integer getExistencia() {
        return existencia;
    }

    public void setExistencia(Integer existencia) {
        this.existencia = existencia;
    }

    @XmlTransient
    public List<Ingpla> getIngplaList() {
        return ingplaList;
    }

    public void setIngplaList(List<Ingpla> ingplaList) {
        this.ingplaList = ingplaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idplatillo != null ? idplatillo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Platillo)) {
            return false;
        }
        Platillo other = (Platillo) object;
        if ((this.idplatillo == null && other.idplatillo != null) || (this.idplatillo != null && !this.idplatillo.equals(other.idplatillo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Platillo[ idplatillo=" + idplatillo + " ]";
    }
    
}
