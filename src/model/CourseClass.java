/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Collection;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entity.Course;
import entity.Student;
import entity.StudentSection;
import entity.Assignment;


public class CourseClass {
    
    public static void main( String[ ] args ) {
    
    }
    
    public static void create(String name, int teacherId, int semesterId) {
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
    
    public void find(int id) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        Course course = entitymanager.find( Course.class, id );

        System.out.println("course ID = " + course.getId());
        System.out.println("course Name = " + course.getName());
        System.out.println("course Teacher = " + course.getTeacherId());
        System.out.println("course Semester = " + course.getSemesterId());
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
    
    
    public Collection<Assignment> getAssignments(int courseID) {
        
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        
        String queryString = "SELECT e FROM Assigment e WHERE e.courseId = " + courseID;
        
        Query query = entitymanager.createQuery(queryString);
        Collection<Assignment> arr = query.getResultList();
        
        System.out.println(arr);
        
        entitymanager.close();
        emfactory.close();
        
        return arr;
    }
}
