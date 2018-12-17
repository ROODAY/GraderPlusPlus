/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author miguelvaldez
 */
public class WeightClass {
    
    public static void create(int courseId, int type, int examWeight, int quizWeight, int hwWeight, int participationWeight) {
       
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        entity.Weights w = new entity.Weights();
        w.setType(type);
        w.setExamWeight(hwWeight);
        w.setQuizWeight(quizWeight);
        w.setHwWeight(hwWeight);
        w.setParticipationWeight(participationWeight);

        entitymanager.persist( w );
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();
    }
    
    public void updateWeights(entity.Weights sa) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        
        entity.Weights na = entitymanager.find( entity.Weights.class, sa.getId());
        
        na.setType(sa.getType());
        na.setHwWeight(sa.getHwWeight());
        na.setQuizWeight(sa.getQuizWeight());
        na.setExamWeight(sa.getExamWeight());
        na.setParticipationWeight(sa.getParticipationWeight());
        entitymanager.getTransaction().commit( );

        //after update
        System.out.println( na );
        entitymanager.close();
        emfactory.close();
    }
}
