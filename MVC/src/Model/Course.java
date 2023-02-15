package Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
/**
 * Ova klasa nam predtavlja model za kreiranje i manipulaciju podatcima kolegija.
 * @author Karlo Kovačević
 */
public class Course {
    /**
     * Ova metoda prima podatke kolegija iz view-a te ih obrađuje, te ovisno o ulaznom
     * parametru dodaje, briše ili ažurira u komunikacij s udaljenom bazom podataka.
     * @param operation
     * @param id
     * @param courseName
     * @param hours
     * @param ects
     */
    public void insertUpdateDeleteStudent(char operation, Integer id, String courseName, int hours,int ects){

        Connection con = SQLConnection.getConnection();
        PreparedStatement ps = null;

        // i za dodavanje
        if(operation == 'i'){
            try {
                String query = "INSERT INTO `course`(`c_name`, `c_hours`, `c_ects`) VALUES (?,?,?)";
                ps = con.prepareStatement(query);

                ps.setString(1,courseName);
                ps.setInt(2,hours);
                ps.setInt(3,ects);
                int count = ps.executeUpdate();
                con.commit();
                if( count > 0){

                    JOptionPane.showMessageDialog(null,"Dodan Novi Kolegij");
                }else{
                    JOptionPane.showMessageDialog(null,"Error");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        // u za ažuriranje
        else if(operation == 'u'){
            try {
                ps = con.prepareStatement("UPDATE `course` SET `c_name` = ?,`c_hours` = ?,`c_ects` = ? WHERE `id` = ?");

                ps.setString(1,courseName);
                ps.setInt(2,hours);
                ps.setInt(3,ects);
                ps.setInt(4,id);

                int count = ps.executeUpdate();
                con.commit();
                if( count > 0){

                    JOptionPane.showMessageDialog(null,"Kolegij Ažuriran");
                }else{
                    JOptionPane.showMessageDialog(null,"Error");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
//        // r za brisanje
        if(operation == 'r') {
            int confirm = JOptionPane.showConfirmDialog(null, "Ocjene će isto biti izbrisane!!!", "WARNING", JOptionPane.OK_CANCEL_OPTION);
            if (confirm == JOptionPane.OK_OPTION) {
                try {
                    ps = con.prepareStatement("DELETE FROM `course` WHERE `id`= ?");

                    ps.setInt(1, id);
                    int count = ps.executeUpdate();
                    con.commit();
                    if (count > 0) {

                        JOptionPane.showMessageDialog(null, "Kolegij Izbrisan");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Ova metoda provjerava da li u zadanoj strukturi već postoji isti podatak.
     * @param courseName
     * @return
     */
    public boolean courseExists(String courseName){
        Connection con = SQLConnection.getConnection();
        PreparedStatement ps;
        String query ="SELECT * FROM `course` WHERE `c_name`= ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, courseName);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Ova metoda služi za punjenje tablice s podatcima kolegija.
     * @param table
     */
    public void fillCourseTable(JTable table){

        Connection con = SQLConnection.getConnection();
        PreparedStatement ps;

        String query ="SELECT * FROM `course`";
        try {
            ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while(rs.next()){
                row = new Object[4];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getInt(4);


                model.addRow(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ova metoda pristupa bazi podataka i vraća id od traženog kolegija.
     * @param courseName
     * @return
     */
    public int getCourseId(String courseName){
        int courseId = 0;

        Connection con = SQLConnection.getConnection();
        PreparedStatement ps;

        String query ="SELECT * FROM `course` WHERE `c_name`= ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, courseName);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                courseId = rs.getInt(1);
            }else{

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return courseId;
    }


    /**
     * Ova metoda nam omogućuje punjenje samih podataka u comboBox te
     * uz mali delay pristupa udaljenoj bazi podataka no zadržavajući responzivnost same aplikacije.
     * @author Karlo Kovačević
     */
    public static class fillComboTask extends SwingWorker<JComboBox, String> {
        JComboBox comboBox;
        public fillComboTask(JComboBox comboBox){

            this.comboBox = comboBox;
        }
        @Override
        protected JComboBox doInBackground() {
            Connection con = SQLConnection.getConnection();
            PreparedStatement ps;

            String query ="SELECT * FROM `course`";
            try {
                ps = con.prepareStatement(query);

                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    publish(rs.getString(2));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
        @Override
        protected void process(List<String> pairs) {
            for (String s : pairs) {
                comboBox.addItem(s);
            }


        }
    }
}
