/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.model;

/**
 *
 * @author ABDUL
 */
public class CurriculumSubjects {

    private Curriculum curriculum;
    private Subject subject;

    public CurriculumSubjects(Curriculum curriculum, Subject subject) {
        this.curriculum = curriculum;
        this.subject = subject;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    
}
