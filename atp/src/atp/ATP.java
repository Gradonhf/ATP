/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

/**
 *
 * @author csembree
 */
public class ATP {

    private Connection connection;
    private LinkedList<Person> checkedInList;

    /**
     * @param args the command line arguments
     *
     * public static void main(String[] args) { // TODO code application logic
     * here }
     */
    public ATP() {
        connectToDatabase();
        checkedInList = new LinkedList<Person>();
    }

    private void connectToDatabase() {
        try {
            String driverName = "org.gjt.mm.mysql.Driver";
            Class.forName(driverName);

            String serverName = "75.82.56.169:3306";
            String mydatabase = "tutordb";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "tutorclient";
            String password = "tutor123#";

            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.printf("ERROR: issue connecting to database\n");
        }
    }

    public boolean checkIn(String userInfo) {
        boolean found = false;
        String name;
        int ID;
        int studentID;
        int canTutor;

        try {
            Statement stmt = (Statement) connection.createStatement();
            ResultSet r = stmt.executeQuery("SELECT * FROM users");

            while (r.next() && found == false) {
                if (userInfo.equals(r.getString("studentid")) == true || userInfo.toLowerCase().equals(r.getString("username")) == true) {
                    System.out.println("FOUND");

                    name = r.getString("username");
                    ID = Integer.parseInt(r.getString("userid"));
                    studentID = Integer.parseInt(r.getString("studentid"));
                    canTutor = Integer.parseInt(r.getString("canTutor"));
                    Person newPerson = new Person(name, ID, studentID, canTutor);
                    
                    checkedInList.add(newPerson);

                    found = true;
                }
            }

            if (found == false) {
                System.out.println("NOT FOUND");
            }

        } catch (Exception e) {
            System.out.printf("ERROR: issue checking in to database\n");

        }


        return found;
    }
}
