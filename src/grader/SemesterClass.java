/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grader;

import java.util.Collection;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entity.Course;
import entity.Semester;
import entity.Student;
import entity.StudentCourse;
import entity.Assignment;

public class SemesterClass {
    
    public static void main( String[ ] args ) {
    
    }
    
    
    public static void create(String name) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Semester semester = new Semester();
        semester.setName(name);
        

        entitymanager.persist( semester );
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();
    }
    
    public Collection<Semester> findAll() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        
        Query query = entitymanager.createQuery("SELECT e FROM Semester e");
        return (Collection<Semester>) query.getResultList();
    }
    
    public Collection<Course> getCourses(int semesterId) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        
        Query query = entitymanager.createQuery("SELECT e FROM Courses e WHERE e.semesterId = " + semesterId);
        Collection<Course> arr = query.getResultList();
        
        return arr;
        
    }
}
