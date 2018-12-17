/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connector;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entity.Course;
import entity.Semester;

public class SemesterConnector {
    
    public static int create(String name) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Semester semester = new Semester();
        semester.setName(name);

        entitymanager.persist( semester );
        entitymanager.getTransaction().commit();
        //entitymanager.flush();

        entitymanager.close();
        emfactory.close();

        return semester.getId();
    }
    
    public static Collection<Semester> findAll() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        
        Query query = entitymanager.createQuery("SELECT e FROM Semester e");
        return (Collection<Semester>) query.getResultList();
    }
    
    
    public static Collection<Course> getCourses(int semesterId) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        
        Query query = entitymanager.createQuery("SELECT e FROM Course e WHERE e.semesterId = " + semesterId);
        Collection<Course> arr = query.getResultList();
        
        return arr;
        
    }
}
