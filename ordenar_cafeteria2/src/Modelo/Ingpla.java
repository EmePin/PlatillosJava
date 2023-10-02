/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aimee
 */
@Entity
@Table(name = "INGPLA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingpla.findAll", query = "SELECT i FROM Ingpla i")
    , @NamedQuery(name = "Ingpla.findByIdip", query = "SELECT i FROM Ingpla i WHERE i.idip = :idip")
    , @NamedQuery(name = "Ingpla.findByExistencia", query = "SELECT i FROM Ingpla i WHERE i.existencia = :existencia")})
public class Ingpla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDIP")
    private Integer idip;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "EXISTENCIA")
    private Double existencia;
    @JoinColumn(name = "IDING", referencedColumnName = "IDING")
    @ManyToOne
    private Ingrediente iding;
    @JoinColumn(name = "IDPLA", referencedColumnName = "IDPLATILLO")
    @ManyToOne
    private Platillo idpla;

    public Ingpla() {
    }

    public Ingpla(Integer idip) {
        this.idip = idip;
    }

    public Integer getIdip() {
        return idip;
    }

    public void setIdip(Integer idip) {
        this.idip = idip;
    }

    public Double getExistencia() {
        return existencia;
    }

    public void setExistencia(Double existencia) {
        this.existencia = existencia;
    }

    public Ingrediente getIding() {
        return iding;
    }

    public void setIding(Ingrediente iding) {
        this.iding = iding;
    }

    public Platillo getIdpla() {
        return idpla;
    }

    public void setIdpla(Platillo idpla) {
        this.idpla = idpla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idip != null ? idip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingpla)) {
            return false;
        }
        Ingpla other = (Ingpla) object;
        if ((this.idip == null && other.idip != null) || (this.idip != null && !this.idip.equals(other.idip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Ingpla[ idip=" + idip + " ]";
    }
    
}
