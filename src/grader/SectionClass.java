/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grader;

import entity.Course;
import entity.Student;
import entity.StudentSection;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author miguelvaldez
 */
public class SectionClass {


    public Collection<Student> getStudents(int sectionID) {
        
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        
        String queryString = "SELECT e FROM StudentSection e WHERE e.sectionId = " + sectionID;
        
        Query query = entitymanager.createQuery(queryString);
        Collection<StudentSection> arr = query.getResultList();
        
        Collection<Student> students = new ArrayList<Student>();
        
        for (StudentSection o : arr){
            
            System.out.println(o.getStudentId());
            StudentClass student = new StudentClass();
            Student ind = student.find(o.getStudentId());
            System.out.println(ind);
           
            students.add(ind);
          
        }
        
        entitymanager.close();
        emfactory.close();
        
        System.out.println(students);
        
        return students;
    }
    
    public void addStudent(int sectionId, int studentId) {
        
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        StudentSection studentSection = new StudentSection();
        
        studentSection.setSectionId(sectionId);
        studentSection.setStudentId(studentId);

        entitymanager.persist( studentSection );
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();
                
    }

    public void addStudentsToSection(Collection<Student> students, int sectionId) {

        for (Student o : students){

            addStudent(sectionId, o.getId());
        }
    }    
    
}

