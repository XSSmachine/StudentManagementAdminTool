package Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
/**
 * Ova klasa nam predtavlja model za kreiranje i manipulaciju podatcima završnih ocjena pojedinih studenata.
 * @author Karlo Kovačević
 */
public class Score {

    /**
     * Ova metoda prima podatke ocjena iz view-a te ih obrađuje, te ovisno o ulaznom
     * parametru dodaje, briše ili ažurira u komunikacij s udaljenom bazom podataka.
     * @param operation
     * @param studentId
     * @param courseId
     * @param score
     * @param description
     */
    public void insertUpdateDeleteStudent(char operation, Integer studentId, Integer courseId,Integer score,String description){


        Connection con = SQLConnection.getConnection();
        PreparedStatement ps = null;

        // i za dodavanje
        if(operation == 'i'){
            try {
                String query = "INSERT INTO `gradeTable`(`student_id`, `course_id`, `score`,`description`) VALUES (?,?,?,?)";
                ps = con.prepareStatement(query);

                ps.setInt(1,studentId);
                ps.setInt(2,courseId);
                ps.setInt(3,score);
                ps.setString(4,description);
                int count = ps.executeUpdate();
                con.commit();
                if( count > 0){

                    JOptionPane.showMessageDialog(null,"Score Added");
                }else{
                    JOptionPane.showMessageDialog(null,"Error");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        // u za ažuriranje
        else if(operation == 'u'){


       try{
           ps = con.prepareStatement("UPDATE `gradeTable` SET `score` = ?,`description` = ? WHERE `student_id` = ? AND `course_id` = ?");
                ps.setInt(1,score);
                ps.setString(2,description);
                ps.setInt(3,studentId);
                ps.setInt(4,courseId);

                int count = ps.executeUpdate();
                con.commit();
                if( count > 0){

                    JOptionPane.showMessageDialog(null,"Score Updated");
                }else{
                    JOptionPane.showMessageDialog(null,"Error");
                }
            } catch (SQLException eee) {
                throw new RuntimeException(eee);
            }
        }  if(operation == 'r'){
            try {
                ps = con.prepareStatement("DELETE FROM `gradeTable` WHERE `student_id`= ? AND `course_id`= ?");

                ps.setInt(1,studentId);
                ps.setInt(2,courseId);
                int count = ps.executeUpdate();
                con.commit();
                if( count > 0){

                    JOptionPane.showMessageDialog(null,"Score Deleted");
                }else{
                    JOptionPane.showMessageDialog(null,"Error");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }




}

