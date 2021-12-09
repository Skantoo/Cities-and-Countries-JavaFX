/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.viewcontroller;

import hr.algebra.model.Country;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Bruno
 */
public class CountryViewModel {
    private final Country country;

    public CountryViewModel(Country country) {
        if (country == null) {
            country = new Country(0, "");
        }
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }
    
    public IntegerProperty getIDCountryProperty() {
        return new SimpleIntegerProperty(country.getIDCountry());
    }

    public StringProperty getImeDrzaveProperty() {
        return new SimpleStringProperty(country.getImeDrzave());
    }

    public ObjectProperty<byte[]> getPictureProperty() {
        return new SimpleObjectProperty<>(country.getPicture());
    }
    
}
