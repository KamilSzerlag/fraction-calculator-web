/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polsl.jium.kszerlag.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * Data Access Object performing access and operation on database.
 *
 * @author Kamil Szerląg
 * @version 1.0
 */
public class FractionCalculatorDao {

    /**
     * Factory for future EntityManager object initialization.
     */
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("fraction-calculator-web-db");

    /**
     * Persisting single History object in database. 
     * 
     * @param entity
     * @throws PersistenceException 
     */
    public void persist(History entity) throws PersistenceException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    /**
     * Retriving all History objects from database.
     * 
     * @return list contains all elements from History table.
     */
    public List<History> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("Select e From History e");   
            return query.getResultList();
        } catch (PersistenceException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            return null;
        } finally {
            em.close();
        }
    }
    
    
}
