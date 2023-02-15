package Model;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Ova klasa nam predtavlja model za kreiranje i manipulaciju podatcima studenata.
 * @author Karlo Kovačević
 */

public class Student {

    /**
     * Ova metoda prima podatke studenta iz view-a te ih obrađuje, te ovisno o ulaznom
     * parametru dodaje, briše ili ažurira u komunikacij s udaljenom bazom podataka.
     * @param operation
     * @param id
     * @param fname
     * @param lname
     * @param sex
     * @param bdate
     * @param nationality
     * @param address
     * @param email
     */
    public void insertUpdateDeleteStudent(char operation,
                                          Integer id, String fname, String lname,
                                          String sex, String bdate, String nationality, String address, String email){

        Connection con = SQLConnection.getConnection();
        PreparedStatement ps = null;

        // i za dodavanje
        if(operation == 'i'){

            try {

                String query = "insert into student( name, surname, sex, birthdate, nationality, adress, email) values (?,?,?,?,?,?,?)";
                ps = con.prepareStatement(query);

                ps.setString(1,fname);
                ps.setString(2,lname);
                ps.setString(3,sex);
                ps.setString(4,bdate);
                ps.setString(5,nationality);
                ps.setString(6,address);
                ps.setString(7,email);
                int count = ps.executeUpdate();
                con.commit();
                if( count > 0){

                    JOptionPane.showMessageDialog(null,"Dodan novi student");
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
                ps = con.prepareStatement("UPDATE `student` SET `name`= ?,`surname`= ?,`sex`= ?,`birthdate`= ?,`nationality`= ?,`adress`= ?,`email`= ? WHERE `id`= ?");

                ps.setString(1,fname);
                ps.setString(2,lname);
                ps.setString(3,sex);
                ps.setString(4,bdate);
                ps.setString(5,nationality);
                ps.setString(6,address);
                ps.setString(7,email);
                ps.setInt(8,id);
                int count = ps.executeUpdate();
                con.commit();
                if( count > 0){

                    JOptionPane.showMessageDialog(null,"Student ažuriran");
                }else{
                    JOptionPane.showMessageDialog(null,"Error");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        // r za brisanje
        if(operation == 'r'){

            int confirm = JOptionPane.showConfirmDialog(null,"Ocjene će isto biti izbrisane!!!","WARNING",JOptionPane.OK_CANCEL_OPTION);
            if(confirm == JOptionPane.OK_OPTION){
                try {
                    ps = con.prepareStatement("DELETE FROM `student` WHERE `id`= ?");

                    ps.setInt(1,id);
                    int count = ps.executeUpdate();
                    con.commit();
                    if( count > 0){

                        JOptionPane.showMessageDialog(null,"Student Izbrisan");
                    }else{
                        JOptionPane.showMessageDialog(null,"Error");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            }

        }


    /**
     * Ova metoda nam omogućuje punjenje samih podataka u odabranu tablicu te također imamo mogućnost
     * filtriranja tih podataka pomoću zadanog parametra 'valueToSearch'.
     * @param table
     * @param valueToSearch
     */
    public void fillStudentTable(JTable table, String valueToSearch){

        Connection con = SQLConnection.getConnection();
        PreparedStatement ps;
        String query1 = "SELECT *\n" +
                "FROM `student`\n" +
                "WHERE CONCAT(name,'#',surname,'#',sex,'#',birthdate,'#',nationality,'#',adress,'#',email) LIKE ?";
        try {
            ps = con.prepareStatement(query1);
            ps.setString(1, "%"+valueToSearch+"%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while(rs.next()){
                row = new Object[8];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);

                model.addRow(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ova klasa nam omogućuje rad s SwingWorkerom koji je u ovom slučaju primjer kako ga
     * možemo iskoristiti za prikaz "loading bar-a".
     * @author Karlo Kovačević
     */

    public static class MyTask extends SwingWorker<Void, Void> {

        private final JDialog dialog;

        public MyTask(JDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        protected Void doInBackground() throws Exception {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    dialog.setVisible(true);
                }
            });

            int progress = 0;
            for (int i = 0; i < 5; i++) {
                Thread.sleep(150);
                setProgress(progress += 20);

            }
            return null;
        }
        @Override
        protected void done() {
            dialog.setVisible(false);
            dialog.dispose();
        }
    }



    }
















//    private int id;
//    private String name;
//    private int age;
//    private String address;
//
//    public Student(int id, String name, int age, String address) {
//        this.id = id;
//        this.name = name;
//        this.age = age;
//        this.address = address;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }

