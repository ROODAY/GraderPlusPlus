/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradingsystem;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entity.Course;


public class CourseClass {
    
    public static void main( String[ ] args ) {
    
    }
    
    public static void create(String name, int teacherId) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Course course = new Course();
        course.setName(name);
        course.setTeacherId(teacherId);

        entitymanager.persist( course );
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();
    }
    
    public static void update(int id) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        Course course = entitymanager.find( Course.class, id );

        //before update
        System.out.println( course );
        course.setName("Luke");
        entitymanager.getTransaction().commit( );

        //after update
        System.out.println( course );
        entitymanager.close();
        emfactory.close();
    }
    
    public static void find(int id) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        Course course = entitymanager.find( Course.class, id );

        System.out.println("course ID = " + course.getId());
        System.out.println("course Name = " + course.getName());
        System.out.println("course Teacher = " + course.getTeacherId());
    }
    
    public Collection<Course> findAll() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        
        Query query = entitymanager.createQuery("SELECT e FROM Course e");
        return (Collection<Course>) query.getResultList();
    }
    
    public static void delete(int id) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "GradingSystemPU" );
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction().begin();

        Course course = entitymanager.find( Course.class, id );
        entitymanager.remove( course );
        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }
    
}
