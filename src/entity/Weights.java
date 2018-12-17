/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author miguelvaldez
 */
@Entity
public class Weights implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private int id;
    private int courseId;
    private int examWeight;
    private int quizWeight;
    private int homeworkWeight;
    private int participationWeight;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    public int getQuizWeight() {
        return quizWeight;
    }

    public void setQuizWeight(int weight) {
        this.quizWeight = weight;
    }
    
    public int hwWeight() {
        return homeworkWeight;
    }

    public void setHwWeight(int weight) {
        this.homeworkWeight = weight;
    }
    
    public int getExamWeight() {
        return examWeight;
    }

    public void setExamWeight(int weight) {
        this.examWeight = weight;
    }
    
    public int getParticipationWeight() {
        return participationWeight;
    }

    public void setParticipationWeight(int weight) {
        this.participationWeight = weight;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Weights)) {
            return false;
        }
        Weights other = (Weights) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Weights[ id=" + id + " ]";
    }
    
}
