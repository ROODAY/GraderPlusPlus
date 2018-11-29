/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grader;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entity.Student;


public class StudentClass {
    
    public static void main( String[ ] args ) {
    
    }
    
    public static void create(String name, String email, double gpa) {
       
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Student student = new Student();
        student.setEmail(email);
        student.setName(name);
        student.setGPA(gpa);

        entitymanager.persist( student );
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();
    }
    
    public static void update(int id) {
        
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        Student student = entitymanager.find( Student.class, id );

        //before update
        System.out.println( student );
        student.setName("Luke");
        entitymanager.getTransaction().commit( );

        //after update
        System.out.println( student );
        entitymanager.close();
        emfactory.close();
    }
    
    public Student find(int id) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        Student student = entitymanager.find( Student.class, id );
        
        System.out.println(student);
        System.out.println("student ID = " + student.getId());
        System.out.println("student Name = " + student.getName());
        System.out.println("student Email = " + student.getEmail());
        System.out.println("student GPA = " + student.getGPA());
        
        return student;
    }
    
    public Collection<Student> findAll() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        
        Query query = entitymanager.createQuery("SELECT e FROM Student e");
        return (Collection<Student>) query.getResultList();
        
//        Collection<Student> arr = student.findAll();
//        System.out.println(arr);
//        
//        for (Student o : arr){
//            System.out.println(o.getId());
//        }
    }
    
    public static void delete(int id) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "GradingSystemPU" );
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction().begin();

        Student student = entitymanager.find( Student.class, id );
        entitymanager.remove( student );
        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }
    
    public void addAssignment(int assignmentId, int studentId) {
        
    }
}
