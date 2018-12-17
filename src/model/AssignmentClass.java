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
import entity.StudentAssignment;
import entity.Student;


public class AssignmentClass {
    
    public static void main( String[ ] args ) {
    
    }
    
    public Assignment create(int courseId, String name, int totalPoints , String type, String dateAssigned) {

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Assignment assignment = new Assignment();
        assignment.setCourseId(courseId);
        assignment.setTotalPoints(totalPoints);
        assignment.setType(type);
        assignment.setDateAssigned(dateAssigned);

        entitymanager.persist( assignment );
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();
        return assignment;
    }
    
    
    public void addAssignmentToCourse(Assignment assignment) {
        //get students in course sections
        //create the student assignments
        
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        
        Query query = entitymanager.createQuery("SELECT e FROM Student e WHERE e.courseId = " +
                assignment.getCourseId());
        
        Collection<Student> arr = query.getResultList();
            
        for (Student o : arr){
            
            entitymanager.getTransaction().begin();
            
            StudentAssignment sa = new StudentAssignment();
            sa.setName(assignment.getName());
            
            sa.setCourseId(assignment.getCourseId());
            sa.setSectionId(o.getSectionId());
            sa.setStudentId(o.getId());
            sa.setAssignmentId(assignment.getId());
            sa.setPoints(0);
            
            entitymanager.persist( sa );
            entitymanager.getTransaction().commit();
            entitymanager.close();
          
        }
        
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

        System.out.println("assignment ID = " + assignment.getId());
        System.out.println("course Id = " + assignment.getCourseId());
        System.out.println("weight = " + assignment.getWeight());
    }
    
    public static Collection<Assignment> findAll() {
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
    
    
    public Collection<StudentAssignment> getAllStudentwithAssignment(int assignmentId) {
        
        
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        
        String queryString = "SELECT e FROM StudentAssignment e WHERE e.assignmentId = " + assignmentId;
        
        Query query = entitymanager.createQuery(queryString);
        Collection<StudentAssignment> arr = query.getResultList();
        
        System.out.println(arr);
        
        entitymanager.close();
        emfactory.close();
        
        return arr;
    }
    
    
    
    
}
