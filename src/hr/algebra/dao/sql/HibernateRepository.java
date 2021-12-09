/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dao.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.City;
import hr.algebra.model.Country;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Bruno
 */
public class HibernateRepository implements Repository {

    @Override
    public int addCountry(Country data) throws Exception {
        try(EntityManagerWrapper wrapper= HibernateFactory.getEntityManger()){
            EntityManager em = wrapper.get();
            em.getTransaction().begin();
            
            Country country = new Country(data);
            em.persist(country);
            
            em.getTransaction().commit();
            return country.getIDCountry();
        }
    }

    @Override
    public int addCity(City data) throws Exception {
        try(EntityManagerWrapper wrapper= HibernateFactory.getEntityManger()){
            EntityManager em = wrapper.get();
            em.getTransaction().begin();
            
            City city = new City(data);
            em.persist(city);
            
            em.getTransaction().commit();
            return city.getIDCity();
        }
    }

    @Override
    public void updateCountry(Country data) throws Exception {
        try(EntityManagerWrapper wrapper=HibernateFactory.getEntityManger()){
            EntityManager em= wrapper.get();
            em.getTransaction().begin();
            
            em.find(Country.class,data.getIDCountry()).updateDetails(data);
            em.getTransaction().commit();
        }
    }

    @Override
    public void updateCity(City data) throws Exception {
        try(EntityManagerWrapper wrapper=HibernateFactory.getEntityManger()){
            EntityManager em= wrapper.get();
            em.getTransaction().begin();
            
            em.find(City.class,data.getIDCity()).updateDetails(data);
            em.getTransaction().commit();
        }
    }

    @Override
    public void deleteCountry(Country data) throws Exception {
        try(EntityManagerWrapper wrapper=HibernateFactory.getEntityManger()){
            EntityManager em=wrapper.get();
            em.getTransaction().begin();
            
            em.remove(em.contains(data) ? data :em.merge(data));
            
            em.getTransaction().commit();
        }
    }

    @Override
    public void deleteCity(City data) throws Exception {
        try(EntityManagerWrapper wrapper=HibernateFactory.getEntityManger()){
            EntityManager em=wrapper.get();
            em.getTransaction().begin();
            
            em.remove(em.contains(data) ? data :em.merge(data));
            
            em.getTransaction().commit();
        }
    }

    @Override
    public Country getCountry(int idCountry) throws Exception {
                try(EntityManagerWrapper wrapper= HibernateFactory.getEntityManger()){
            EntityManager em= wrapper.get();
            
            
            return em.find(Country.class, idCountry);
            
            
            
        }
    }

    @Override
    public City getCity(int idCity) throws Exception {
                try(EntityManagerWrapper wrapper= HibernateFactory.getEntityManger()){
            EntityManager em= wrapper.get();
            
            
            return em.find(City.class, idCity);
            
            
            
        }
    }

    @Override
    public List<Country> getCountries() throws Exception {
        try(EntityManagerWrapper wrapper= HibernateFactory.getEntityManger()){
            EntityManager em= wrapper.get();
            
            
            return em.createNamedQuery(HibernateFactory.SELECT_ALLC).getResultList();
            
            
            
        }
    }

    @Override
    public List<City> getCities() throws Exception {
        try(EntityManagerWrapper wrapper= HibernateFactory.getEntityManger()){
            EntityManager em= wrapper.get();
            
            
            return em.createNamedQuery(HibernateFactory.SELECT_ALL).getResultList();
            
            
            
        }
    }

    @Override
    public void release() throws Exception {
       HibernateFactory.release();
    }
    
    
}
