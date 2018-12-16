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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import entity.Student;


public class StudentClass extends DBClass {
    
    @Override
    public void delete(int id) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "GradingSystemPU" );
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction().begin();

        Student student = entitymanager.find( Student.class, id );
        entitymanager.remove( student );
        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }
    
    @Override
    public void update(int id) {
        
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
    
    
    public static void create(int id, int sectionId, String name, String last_name, String email, String program) {
       
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Student student = new Student();
        student.setId(id);
        student.setSectionId(sectionId);
        student.setName(name);
        student.setLastName(last_name);
        student.setEmail(email);
        student.setProgram(program);

        entitymanager.persist( student );
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();
    }
    
    public void uploadStudentsCSV(String location) {
        
        String csvFile = location;
        String line = "";
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            
            while ((line = br.readLine()) != null) {
              
                // use comma as separator
                String[] studentRow = line.split(csvSplitBy);
                
                if(studentRow.length > 0 && !studentRow[0].equals("ID")) {
                    
                    Student student = new Student();
                    create(
                        Integer.parseInt(studentRow[0]),
                        studentRow[1],
                        studentRow[2],
                        studentRow[3],
                        studentRow[4]
                    );
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Student find(int id) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        Student student = entitymanager.find( Student.class, id );
        
        System.out.println(student);
        System.out.println("student ID = " + student.getId());
        System.out.println("student Name = " + student.getName());
        System.out.println("student Last Name = " + student.getLastName());
        System.out.println("student Email = " + student.getEmail());
        
        return student;
    }
    
    public static Collection<Student> findAll() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        
        Query query = entitymanager.createQuery("SELECT e FROM Student e");
        return (Collection<Student>) query.getResultList();
        
    }
   
    
    public void addAssignment(int assignmentId, int studentId) {
        
    }
    
    public void getAssignments(int studentId, int courseId) {
        
    }
    
    public Collection<StudentAssignment> getStudentAssignmentsBySection(int studentId, int sectionId) {
        
        
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        
        String queryString = "SELECT e FROM StudentAssignment e WHERE e.studentId = " + studentId 
                + " AND e.sectionId = " + sectionId;
        
        Query query = entitymanager.createQuery(queryString);
        Collection<StudentAssignment> arr = query.getResultList();
        
        System.out.println(arr);
        
        entitymanager.close();
        emfactory.close();
        
        return arr;
    }
    
    
    public Collection<StudentAssignment> getAllStudentAssignments(int studentId) {
        
        
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        
        String queryString = "SELECT e FROM StudentAssignment e WHERE e.studentId = " + studentId;
        
        Query query = entitymanager.createQuery(queryString);
        Collection<StudentAssignment> arr = query.getResultList();
        
        System.out.println(arr);
        
        entitymanager.close();
        emfactory.close();
        
        return arr;
    }
    
    

}
