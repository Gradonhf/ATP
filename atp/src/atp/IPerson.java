/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atp;

/**
 *
 * @author csembree
 */
public interface IPerson {
    //public Person(String Name, int ID, int studentID, int canTutor);
    public String getName();
    public int getStudentID();
    public int getID();
    public boolean getCanTutor();
    public void setName(String Name);
    public void setStudentID(int studentID);
    public void setID(int ID);
    public void setCanTutor(int canTutor);
    public void setCanTutor(boolean canTutor);
}
