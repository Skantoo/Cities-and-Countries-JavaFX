/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.viewcontroller;


import hr.algebra.model.City;
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
public class CityViewModel {
    private final City city;

    public CityViewModel(City city) {
        if (city == null) {
            city = new City(0, "",0);
        }
        this.city = city;
    }

    public City getCity() {
        return city;
    }
    
    public IntegerProperty getIDCityProperty() {
        return new SimpleIntegerProperty(city.getIDCity());
    }

    public IntegerProperty getCountryIDProperty() {
        return new SimpleIntegerProperty(city.getCountryID());
    }
    
    public StringProperty getImeGradaProperty() {
        return new SimpleStringProperty(city.getImeGrada());
    }

    public ObjectProperty<byte[]> getPictureProperty() {
        return new SimpleObjectProperty<>(city.getPicture());
    }
    
}
