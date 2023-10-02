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
@Table(name = "TIPO_INGREDIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoIngrediente.findAll", query = "SELECT t FROM TipoIngrediente t")
    , @NamedQuery(name = "TipoIngrediente.findByIding", query = "SELECT t FROM TipoIngrediente t WHERE t.iding = :iding")
    , @NamedQuery(name = "TipoIngrediente.findByNombre", query = "SELECT t FROM TipoIngrediente t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "TipoIngrediente.findByGenero", query = "SELECT t FROM TipoIngrediente t WHERE t.genero = :genero")
    , @NamedQuery(name = "TipoIngrediente.findByCaracteristicas", query = "SELECT t FROM TipoIngrediente t WHERE t.caracteristicas = :caracteristicas")})
public class TipoIngrediente implements Serializable {

    @OneToMany(mappedBy = "tipoing")
    private List<Ingrediente> ingredienteList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDING")
    private Integer iding;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "GENERO")
    private String genero;
    @Column(name = "CARACTERISTICAS")
    private String caracteristicas;

    public TipoIngrediente() {
    }

    public TipoIngrediente(Integer iding) {
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
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
        if (!(object instanceof TipoIngrediente)) {
            return false;
        }
        TipoIngrediente other = (TipoIngrediente) object;
        if ((this.iding == null && other.iding != null) || (this.iding != null && !this.iding.equals(other.iding))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.TipoIngrediente[ iding=" + iding + " ]";
    }

    @XmlTransient
    public List<Ingrediente> getIngredienteList() {
        return ingredienteList;
    }

    public void setIngredienteList(List<Ingrediente> ingredienteList) {
        this.ingredienteList = ingredienteList;
    }
    
}
