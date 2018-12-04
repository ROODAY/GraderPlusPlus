/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entity.Assignment;


public class AssignmentClass {
    
    public static void main( String[ ] args ) {
    
    }
    
    public void create(int courseId, String name, int possiblePoints, String type) {

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Assignment assignment = new Assignment();
        assignment.setCourseId(courseId);
        assignment.setPossiblePoints(possiblePoints);
        assignment.setType(type);

        entitymanager.persist( assignment );
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();
    }
    
    public static void update(int id) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        Assignment course = entitymanager.find( Assignment.class, id );

        //before update
        System.out.println( course );
        //course.setName("Luke");
        entitymanager.getTransaction().commit( );

        //after update
        System.out.println( course );
        entitymanager.close();
        emfactory.close();
    }
    
    public static void find(int id) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        Assignment assignment = entitymanager.find( Assignment.class, id );

        System.out.println("course ID = " + assignment.getId());
        System.out.println("course CourseId = " + assignment.getCourseId());
    }
    
    public Collection<Assignment> findAll() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        
        Query query = entitymanager.createQuery("SELECT e FROM Assignment e");
        return (Collection<Assignment>) query.getResultList();
    }
    
    public static void delete(int id) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "GradingSystemPU" );
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction().begin();

        Assignment assignment = entitymanager.find( Assignment.class, id );
        entitymanager.remove( assignment );
        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }
    
}
