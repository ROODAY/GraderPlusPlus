/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connector;

import entity.Weights;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Collection;

/**
 *
 * @author miguelvaldez
 */
public class WeightsConnector {
    
    public static Weights create(int courseId, int type, int examWeight, int quizWeight, int hwWeight, int participationWeight) {
       
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        entity.Weights w = new entity.Weights();
        w.setCourseId(courseId);
        w.setType(type);
        w.setExamWeight(examWeight);
        w.setQuizWeight(quizWeight);
        w.setHwWeight(hwWeight);
        w.setParticipationWeight(participationWeight);

        entitymanager.persist( w );
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();

        return w;
    }

    public static Collection<Weights> getWeightsForCourse(int courseid) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();

        String queryString = "SELECT e FROM Weights e WHERE e.courseId = " + courseid;

        Query query = entitymanager.createQuery(queryString);
        Collection<Weights> arr = query.getResultList();

        entitymanager.close();
        emfactory.close();

        return arr;
    }
    
    public static void updateWeights(Weights weights) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        
        entity.Weights na = entitymanager.find( entity.Weights.class, weights.getId());
        
        na.setType(weights.getType());
        na.setHwWeight(weights.getHwWeight());
        na.setQuizWeight(weights.getQuizWeight());
        na.setExamWeight(weights.getExamWeight());
        na.setParticipationWeight(weights.getParticipationWeight());
        entitymanager.getTransaction().commit( );

        //after update
        System.out.println( na );
        entitymanager.close();
        emfactory.close();
    }
}
