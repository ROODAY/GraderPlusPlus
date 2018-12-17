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
    
    public static void create(int courseId, int examWeight, int quizWeight, int hwWeight, int participationWeight) {
       
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GradingSystemPU");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        entity.Weights w = new entity.Weights();
        w.setExamWeight(hwWeight);
        w.setQuizWeight(quizWeight);
        w.setHwWeight(hwWeight);
        w.setParticipationWeight(participationWeight);

        entitymanager.persist( w );
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();
    }
}
