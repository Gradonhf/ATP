/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atp;

/**
 *
 * @author csembree
 */
public class Person implements IPerson{

    private String Name;
    private int ID;
    private int studentID;
    private boolean canTutor;

    public Person(String Name, int ID, int studentID, int canTutor) {
        this.Name=Name;
        this.ID=ID;
        this.studentID=studentID;
        
        if (canTutor==1){
            this.canTutor=true;
        }
        else {
            this.canTutor=false;
        }
    }

    public String getName() {
        return this.Name;
    }

    public int getStudentID() {
        return this.studentID;
    }

    public int getID() {
        return this.ID;
    }

    public boolean getCanTutor() {
        return this.canTutor;
    }
    
    public void setName(String Name) {
        this.Name=Name;
    }

    public void setStudentID(int studentID) {
        this.studentID=studentID;
    }

    public void setID(int ID) {
        this.ID=ID;
    }

    public void setCanTutor(int canTutor) {
        if (canTutor==1){ //1=that person can tutor
            this.canTutor=true;
        }
        else {
            this.canTutor=false;
        }
    }
    
    public void setCanTutor(boolean canTutor) {
        this.canTutor=canTutor;
    }
}
