package Controller;

import Model.Course;
import Model.SQLConnection;
import Model.Score;
import Model.Student;
import View.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Ova klasa nam predstavlja kntroller u ovom MVC dizajnu te time ima zadaću povezivanja
 * modela sa samim prikazom GUI-a korištenjem različitih metoda.
 * @author Karlo Kovačević
 */

public class Controller {

    /**
     * Kompozicijski uzimamo elemente iz modela pomoću kojih ćemo zvati različite metode
     */
    private Student st;
    private Course c;
    private Score sc;
    private StudentTableTask exampleTask;
    private CourseTableTask exampleTask1;
    private Course.fillComboTask fillComboTask;
    private ScoreTableTask exampleTask2;
    private AllScoresTableTask allScoresTableTask;
    public Controller(){
        st=new Student();
        c=new Course();
        sc=new Score();
    }

    /**
     * Ovom metodom kontroler prima podatke studenta iz view-a te ih obrađuje, te ovisno o ulaznom
     * parametru dodaje, briše ili ažurira u komunikacij s udaljenom bazom podataka koja mu vraća promjenjene podatke
     * kojima kontroler ažurira sam izgled aplikacije.
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
    public void addStudent2DB(char operation,
                              Integer id, String fname, String lname,
                              String sex, String bdate, String nationality,
                              String address, String email){
        st.insertUpdateDeleteStudent(operation,id,fname,lname,sex,bdate,nationality,address,email);
        StudentTable.table.setModel(new DefaultTableModel(null,new Object[]{"Id", "Ime", "Prezime","Spol","Datum rođenja","Nacionalnost","Adresa", "Email"}));
        st.fillStudentTable(StudentTable.table,"");
    }

    /**
     * Ova metoda kontroleru omogućuje ažuriranje i punjenje tablice studenata pri samoj inicijalizacij ili ručno nakon promjene podataka u bazi. Također
     * nam omogućava filtriranje podataka studenata prema traženom pojmu.
     * @param table
     * @param valueToSearch
     */
    public void fillStudentTable(JTable table, String valueToSearch){
        table.setModel(new DefaultTableModel(null,new Object[]{"Id", "Ime", "Prezime","Spol","Datum rođenja","Nacionalnost","Adresa", "Email"}));
        st.fillStudentTable(table,valueToSearch);

        }

    /**
     * Ova metoda nam omgućava dohvaćanje podataka studenata iz baze uz zadržavanje responzivnosti GUI-a
     * @param toHide
     */
    public void loadStTableDataWithSwingWorker(boolean toHide) {
        (exampleTask = new StudentTableTask()).execute();
        if (toHide) {
            for (int i = 6; i > 2; i--) {
                TableColumnModel tcm = StudentTable.table.getColumnModel();
                tcm.removeColumn(tcm.getColumn(i));
            }
        }
    }
    /**
     * Ova metoda nam omgućanje dohvaćanje podataka kolegija iz baze uz zadržavanje responzivnosti GUI-a
     */
    public void loadCoTableDataWithSwingWorker(){
        (exampleTask1 = new CourseTableTask()).execute();
    }
    /**
     * Ova metoda nam omgućanje dohvaćanje podataka ocjena iz baze uz zadržavanje responzivnosti GUI-a
     */
    public void loadScTableDataWithSwingWorker(){
        (exampleTask2 = new ScoreTableTask()).execute();
    }
    /**
     * Ova metoda nam omgućanje dohvaćanje podataka svih ocjena iz baze uz zadržavanje responzivnosti GUI-a
     */
    public void loadAllScTableDataWithSwingWorker(){
        (allScoresTableTask = new AllScoresTableTask()).execute();
    }

    /**
     * Ova metoda omogućava provjeru duplikata pojedinog kolegija.
     * @param courseName
     * @return
     */
    public boolean doesCourseExist(String courseName){
        return c.courseExists(courseName);
    }

    /**
     * Ovom metodom kontroler prima podatke kolegija iz view-a te ih obrađuje, te ovisno o ulaznom
     * parametru dodaje, briše ili ažurira u komunikacij s udaljenom bazom podataka koja mu vraća promjenjene podatke
     * kojima kontroler ažurira sam izgled aplikacije.
     * @param operation
     * @param id
     * @param courseName
     * @param hours
     * @param ects
     */
    public void manageCourse2DB(char operation, Integer id, String courseName, int hours,int ects){
        c.insertUpdateDeleteStudent(operation,id,courseName,hours, ects);
        fillCourseTable();
    }

    /**
     * Ova metoda kontroleru omogućuje ažuriranje i punjenje tablice kolegija pri samoj inicijalizacij ili ručno nakon promjene podataka u bazi.
     */
    public void fillCourseTable(){
        CourseTable.table.setModel(new DefaultTableModel(null,new Object[]{"Id", "Ime Kolegija", "Sati", "ECTS"}));
        c.fillCourseTable(CourseTable.table);
    }
    /**
     * Ova metoda kontroleru omogućuje punjenje comboBoxa pri samoj inicijalizacij pristupanjem podataka u udaljenoj bazi.
     */
    public void fillCourseComboBox(JComboBox comboBox){
        fillComboTask = new Course.fillComboTask(comboBox);
        fillComboTask.execute();
    }

    /**
     * Ova metoda omogućuje vraćanje id-a koji je pridružen ulaznom parametru.
     * @param courseName
     * @return
     */
    public int getCourseId(String courseName){
        return c.getCourseId(courseName);
    }

    /**
     * Ovom metodom kontroler prima podatke ocjena iz view-a te ih obrađuje, te ovisno o ulaznom
     * parametru dodaje, briše ili ažurira u komunikacij s udaljenom bazom podataka koja mu vraća promjenjene podatke
     * kojima kontroler ažurira sam izgled aplikacije.
     * @param operation
     * @param studentId
     * @param courseId
     * @param score
     * @param description
     */
    public void manageScores2DB(char operation, Integer studentId, Integer courseId,Integer score,String description){
        sc.insertUpdateDeleteStudent(operation,studentId,courseId,score,description);
        ScoreTable.table.setModel(new DefaultTableModel(null,new Object[]{"Student Id", "Kolegij Id", "Ime Kolegija","Ocjena","Opis"}));

    }
    /**
     * Ova klasa nam omogućuje rad s SwingWorkerom koji je u ovom slučaju zadužen za dohvaćanje
     * podataka studenta iz udaljene baze te inicijalizaciju glavnog framea uz što veću responzivnost same aplikacije.
     * @author Karlo Kovačević
     */
    public static class StudentTableTask extends SwingWorker<Void, DefaultTableModel> {
        @Override
        protected Void doInBackground() {
            DefaultTableModel model;
            Connection con = SQLConnection.getConnection();
            PreparedStatement ps;
            String query1 = "SELECT *\n" +
                    "FROM `student`\n";
            try {
                ps = con.prepareStatement(query1);
                ResultSet rs = ps.executeQuery();
                model = (DefaultTableModel) StudentTable.table.getModel();
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
            publish(model);

            return null;
        }

        @Override
        protected void process(List<DefaultTableModel> pairs) {
            StudentTable.table.setModel(pairs.get(pairs.size()-1));
        }
    }

    /**
     * Ova metoda nam omogućuje punjenje samih podataka u tablicu ocjena te
     * uz mali delay pristupa udaljenoj bazi podataka no zadržavajući responzivnost same aplikacije.
     * @author Karlo Kovačević
     */
    public static class ScoreTableTask extends SwingWorker<Void, DefaultTableModel> {
        @Override
        protected Void doInBackground() {
            DefaultTableModel model;
            Connection con = SQLConnection.getConnection();
            PreparedStatement ps;

            String query ="SELECT `student_id`,course_id,c_name, score, description FROM `gradeTable` INNER JOIN student as stab on stab.id = `student_id` INNER JOIN course as ctab on ctab.id = `course_id`";
            try {
                ps = con.prepareStatement(query);

                ResultSet rs = ps.executeQuery();
                model = (DefaultTableModel) ScoreTable.table.getModel();
                Object[] row;
                int i = 0;
                while(rs.next()){
                    row = new Object[5];
                    row[0] = rs.getInt(1);
                    row[1] =rs.getInt(2);
                    row[2] = rs.getString(3);
                    row[3] = rs.getInt(4);
                    row[4] = rs.getString(5);

                    model.addRow(row);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            publish(model);

            return null;
        }

        @Override
        protected void process(List<DefaultTableModel> pairs) {
            ScoreTable.table.setModel(pairs.get(pairs.size()-1));
        }
    }

    /**
     * Ova klasa uz pomoć SwingWorkera pristupa podatcima iz baze zadržavajući responzivnost ostalih komponenti GUI-a.
     * @author Karlo Kovačević
     */

    public static class AllScoresTableTask extends SwingWorker<Void, DefaultTableModel> {
        @Override
        protected Void doInBackground() {
            Connection con = SQLConnection.getConnection();
            PreparedStatement ps;
            DefaultTableModel model;

            String query ="SELECT `student_id`,name,surname,c_name, score FROM `gradeTable` INNER JOIN student as stab on stab.id = `student_id` INNER JOIN course as ctab on ctab.id = `course_id`";
            try {
                ps = con.prepareStatement(query);

                ResultSet rs = ps.executeQuery();
                model = (DefaultTableModel) AllScoresTable.table.getModel();
                Object[] row;
                int i = 0;
                while(rs.next()){
                    row = new Object[5];
                    row[0] = rs.getInt(1);
                    row[1] = rs.getString(2);
                    row[2] = rs.getString(3);
                    row[3] = rs.getString(4);
                    row[4] = rs.getInt(5);

                    model.addRow(row);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            publish(model);

            return null;
        }

        @Override
        protected void process(List<DefaultTableModel> pairs) {
            AllScoresTable.table.setModel(pairs.get(pairs.size()-1));
        }
    }

    /**
     * Ova metoda nam omogućuje punjenje samih podataka u tablicu kolegija te
     * uz mali delay pristupa udaljenoj bazi podataka no zadržavajući responzivnost same aplikacije.
     * @author Karlo Kovačević
     */
    public static class CourseTableTask extends SwingWorker<Void, DefaultTableModel> {
        @Override
        protected Void doInBackground() {
            DefaultTableModel model;
            Connection con = SQLConnection.getConnection();
            PreparedStatement ps;

            String query ="SELECT * FROM `course`";
            try {
                ps = con.prepareStatement(query);

                ResultSet rs = ps.executeQuery();
                model = (DefaultTableModel) CourseTable.table.getModel();
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

            publish(model);

            return null;
        }

        @Override
        protected void process(List<DefaultTableModel> pairs) {
            CourseTable.table.setModel(pairs.get(pairs.size()-1));
        }
    }
}
