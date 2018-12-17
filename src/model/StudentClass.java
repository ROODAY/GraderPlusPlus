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
import entity.StudentAssignment;


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
        student.setFirst_name("Luke");
        entitymanager.getTransaction().commit( );

        //after update
        System.out.println( student );
        entitymanager.close();
        emfactory.close();
    }
    
    
    public static Student create(int bu_id, int sectionId, int courseId, String name, String last_name, String email, String program, String comments, double ec) {
       
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Student student = new Student();
        student.setId(bu_id);
        student.setBUId(bu_id);
        student.setSectionId(sectionId);
        student.setCourseId(courseId);
        student.setFirst_name(name);
        student.setLastName(last_name);
        student.setEmail(email);
        student.setProgram(program);
        student.setComments(comments);
        student.setEc(ec);

        entitymanager.persist( student );
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();

        return student;
    }
    
    public void uploadStudentsCSV(String location, int courseId, int sectionId) {
        
        String csvFile = location;
        String line = "";
        String csvSplitBy = ",";
        
        //bu id, comments, courseid, email, first name, lastname, grade, program

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            
            while ((line = br.readLine()) != null) {
              
                // use comma as separator
                String[] studentRow = line.split(csvSplitBy);
                
                if(studentRow.length > 0 && !studentRow[0].equals("ID")) {
                    
                    Student student = new Student();
                    create(
                        Integer.parseInt(studentRow[0]),
                        sectionId,
                        courseId,
                        studentRow[1],
                        studentRow[2],
                        studentRow[3],
                        studentRow[4],
                        studentRow[5],
                        Double.parseDouble(studentRow[6])
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
        System.out.println("student Name = " + student.getFirst_name());
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
    
    public static Collection<StudentAssignment> getAssignmentsbyStudent(int studentId) {
        
        
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
    
    
    public static Collection<StudentAssignment> getAllStudentAssignments(int studentId) {
        
        
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
    
    
    
    public static void updateStudentAssignment(StudentAssignment sa) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        
        StudentAssignment na = entitymanager.find( StudentAssignment.class, sa.getId());
        
        na.setPoints(sa.getpoints());
        entitymanager.getTransaction().commit( );

        //after update
        System.out.println( na );
        entitymanager.close();
        emfactory.close();
    }
    
    public static void updateStudent(Student sa) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        
        Student na = entitymanager.find( Student.class, sa.getId());
        
        na.setComments(sa.getComments());
        na.setEc(sa.getEc());
        entitymanager.getTransaction().commit( );

        //after update
        System.out.println( na );
        entitymanager.close();
        emfactory.close();
    }
    
    

}
