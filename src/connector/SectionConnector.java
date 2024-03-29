/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connector;

import entity.Section;
import entity.Student;
import entity.StudentSection;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author miguelvaldez
 */
public class SectionConnector {

    public static int create(String name, int courseId) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Section section = new Section();
        section.setName(name);
        section.setCourseId(courseId);

        entitymanager.persist( section );
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();

        return section.getId();
    }
    

    public static Collection<Student> getStudents(int sectionID) {
        
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        
        String queryString = "SELECT e FROM Student e WHERE e.sectionId = " + sectionID;
        
        Query query = entitymanager.createQuery(queryString);
        Collection<Student> arr = query.getResultList();
        
//        Collection<Student> students = new ArrayList<Student>();
//        
//        for (StudentSection o : arr){
//            
//            System.out.println(o.getStudentId());
//            StudentConnector student = new StudentConnector();
//            Student ind = student.find(o.getStudentId());
//            System.out.println(ind);
//           
//            students.add(ind);
//          
//        }
        
        entitymanager.close();
        emfactory.close();
        
        System.out.println(arr);
        
        return arr;
    }
    
    
    public static void addStudent(int sectionId, int studentId) {
        
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

    public static void addStudentsToSection(Collection<Student> students, int sectionId) {

        for (Student o : students){

            addStudent(sectionId, o.getId());
        }
    }    
    
    
    public void handleSectionStudents(int sectionId, Collection<Student> newStudentRoster) {
        
        Collection<Student> studentsInSection = getStudents(sectionId);
        
        Collection<Student> newStudents = null;
        
        for (Student o : newStudentRoster){
            
            if(studentsInSection.contains(o)) {
                
                studentsInSection.remove(o);
            } else {
                
                newStudents.add(o);
            }
        }
        
        //delete students that are not longer on list
        for(Student o: studentsInSection) {
               
            EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "GradingSystemPU" );
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction().begin();
            
            
            StudentSection ss = findStudentSection(sectionId, o.getId());
            
            //also delete all studentassignments from this student
            //delete all student assignments that have this studentSectionId
            //deleteStudentsAssignments(ss.getId());
            
            
            
            entitymanager.remove( ss );
            entitymanager.getTransaction().commit();   
            entitymanager.close();
            emfactory.close();
        }
        
        //add new students
        for(Student o: newStudents) {     
            
            addStudent(sectionId, o.getId());
        }
    }


    public StudentSection findStudentSection(int sectionId, int studentId) {
        
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        
        Query query = entitymanager.createQuery("SELECT e FROM StudentSection e "
                + "WHERE e.sectionId = " + sectionId + " AND e.studentId = " + studentId);
        
        Collection<StudentSection> list = query.getResultList();
        
        return list.iterator().next();
    }
    
}