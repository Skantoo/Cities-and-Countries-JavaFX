/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal;

import hr.algebra.model.City;
import hr.algebra.model.Country;
import java.util.List;

/**
 *
 * @author Bruno
 */
public interface Repository {
    
    int addCountry(Country data) throws Exception;
    int addCity(City data) throws Exception;
    void updateCountry(Country data) throws Exception;
    void updateCity(City data) throws Exception;
    void deleteCountry(Country data) throws Exception;
    void deleteCity(City data) throws Exception;
    Country getCountry(int idCountry) throws Exception;
    City getCity(int idCity) throws Exception;
    List<Country> getCountries() throws Exception;
    List<City> getCities() throws Exception;
    
    default void release() throws Exception{};
}
