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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "INGREDIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingrediente.findAll", query = "SELECT i FROM Ingrediente i")
    , @NamedQuery(name = "Ingrediente.findByIding", query = "SELECT i FROM Ingrediente i WHERE i.iding = :iding")
    , @NamedQuery(name = "Ingrediente.findByNombre", query = "SELECT i FROM Ingrediente i WHERE i.nombre = :nombre")
    , @NamedQuery(name = "Ingrediente.findByCantidad", query = "SELECT i FROM Ingrediente i WHERE i.cantidad = :cantidad")
    , @NamedQuery(name = "Ingrediente.findByUnidad", query = "SELECT i FROM Ingrediente i WHERE i.unidad = :unidad")})
public class Ingrediente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDING")
    private Integer iding;
    @Column(name = "NOMBRE")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CANTIDAD")
    private Double cantidad;
    @Column(name = "UNIDAD")
    private String unidad;
    @JoinColumn(name = "TIPOING", referencedColumnName = "IDING")
    @ManyToOne
    private TipoIngrediente tipoing;
    @OneToMany(mappedBy = "iding")
    private List<Ingpla> ingplaList;

    public Ingrediente() {
    }

    public Ingrediente(Integer iding) {
        this.iding = iding;
    }

    public Integer getIding() {
        return iding;
    }

    public void setIding(Integer iding) {
        this.iding = iding;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public TipoIngrediente getTipoing() {
        return tipoing;
    }

    public void setTipoing(TipoIngrediente tipoing) {
        this.tipoing = tipoing;
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
        hash += (iding != null ? iding.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingrediente)) {
            return false;
        }
        Ingrediente other = (Ingrediente) object;
        if ((this.iding == null && other.iding != null) || (this.iding != null && !this.iding.equals(other.iding))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Ingrediente[ iding=" + iding + " ]";
    }
    
}
