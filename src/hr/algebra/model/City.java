/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bruno
 */
@Entity
@Table(name = "City")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "City.findAll", query = "SELECT c FROM City c")
    , @NamedQuery(name = "City.findByIDCity", query = "SELECT c FROM City c WHERE c.iDCity = :iDCity")
    , @NamedQuery(name = "City.findByImeGrada", query = "SELECT c FROM City c WHERE c.imeGrada = :imeGrada")})
public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCity")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer iDCity;
    @Basic(optional = false)
    @Column(name = "ImeGrada")
    private String imeGrada;
    @Lob
    @Column(name = "Picture")
    private byte[] picture;
    @JoinColumn(name = "CountryID", referencedColumnName = "IDCountry")
    private Integer countryID;

    public City() {
    }

    public City(City data) {
        updateDetails(data);
    }
    
    public City(Integer iDCity) {
        this.iDCity = iDCity;
    }

    public City(Integer iDCity, String imeGrada,Integer countryID) {
        this.iDCity = iDCity;
        this.imeGrada = imeGrada;
        this.countryID = countryID;
    }

    public Integer getIDCity() {
        return iDCity;
    }

    public void setIDCity(Integer iDCity) {
        this.iDCity = iDCity;
    }

    public String getImeGrada() {
        return imeGrada;
    }

    public void setImeGrada(String imeGrada) {
        this.imeGrada = imeGrada;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Integer getCountryID() {
        return countryID;
    }

    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDCity != null ? iDCity.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof City)) {
            return false;
        }
        City other = (City) object;
        if ((this.iDCity == null && other.iDCity != null) || (this.iDCity != null && !this.iDCity.equals(other.iDCity))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hr.algebra.model.City[ iDCity=" + iDCity + " ]";
    }

    public void updateDetails(City data) {
        this.imeGrada=data.getImeGrada();
        this.picture=data.getPicture();
        this.countryID=data.getCountryID();
    }
    
}
