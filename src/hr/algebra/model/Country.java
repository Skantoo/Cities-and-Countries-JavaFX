/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bruno
 */
@Entity
@Table(name = "Country")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c")
    , @NamedQuery(name = "Country.findByIDCountry", query = "SELECT c FROM Country c WHERE c.iDCountry = :iDCountry")
    , @NamedQuery(name = "Country.findByImeDrzave", query = "SELECT c FROM Country c WHERE c.imeDrzave = :imeDrzave")})
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCountry")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer iDCountry;
    @Basic(optional = false)
    @Column(name = "ImeDrzave")
    private String imeDrzave;
    @Lob
    @Column(name = "Picture")
    private byte[] picture;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "countryID")
    private Collection<City> cityCollection;

    public Country() {
    }
    
    public Country(Country data) {
        updateDetails(data);
    }

    public Country(Integer iDCountry) {
        this.iDCountry = iDCountry;
    }

    public Country(Integer iDCountry, String imeDrzave) {
        this.iDCountry = iDCountry;
        this.imeDrzave = imeDrzave;
    }

    public Integer getIDCountry() {
        return iDCountry;
    }

    public void setIDCountry(Integer iDCountry) {
        this.iDCountry = iDCountry;
    }

    public String getImeDrzave() {
        return imeDrzave;
    }

    public void setImeDrzave(String imeDrzave) {
        this.imeDrzave = imeDrzave;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @XmlTransient
    public Collection<City> getCityCollection() {
        return cityCollection;
    }

    public void setCityCollection(Collection<City> cityCollection) {
        this.cityCollection = cityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDCountry != null ? iDCountry.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Country)) {
            return false;
        }
        Country other = (Country) object;
        if ((this.iDCountry == null && other.iDCountry != null) || (this.iDCountry != null && !this.iDCountry.equals(other.iDCountry))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return imeDrzave;
    }

    public void updateDetails(Country data) {
        this.imeDrzave=data.getImeDrzave();
        this.picture=data.getPicture();
    }
    
}
