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

import entity.StudentCourse;


public class StudentCourseClass {
    
    public static void main( String[ ] args ) {
    
    }
    
    public static void create(int courseId, int studentId) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        StudentCourse course = new StudentCourse();
        course.setCourseId(courseId);
        course.setStudentId(studentId);

        entitymanager.persist( course );
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();
    }
    
    public static void update(int id) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        StudentCourse course = entitymanager.find( StudentCourse.class, id );

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
        StudentCourse course = entitymanager.find( StudentCourse.class, id );

        System.out.println("course ID = " + course.getId());
        System.out.println("course CourseId = " + course.getCourseId());
        System.out.println("course StudentId = " + course.getStudentId());
    }
    
    public Collection<StudentCourse> findAll() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        
        Query query = entitymanager.createQuery("SELECT e FROM StudentCourse e");
        return (Collection<StudentCourse>) query.getResultList();
    }
    
    public static void delete(int id) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "GradingSystemPU" );
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction().begin();

        StudentCourse course = entitymanager.find( StudentCourse.class, id );
        entitymanager.remove( course );
        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }
    
}
