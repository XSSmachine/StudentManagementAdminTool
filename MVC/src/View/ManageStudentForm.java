package View;

import Controller.Controller;
import Model.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Properties;
/**
 * Ova klasa nam omogućuje uvid i pristup svim studentima iz baze podataka kao i njihovu manipulaciju poput brisanja, dodavanja i ažuriranja.
 * @author Karlo Kovačević
 */

public class ManageStudentForm extends JDialog {
    Controller control;
    private StudentTable table;
    private JTextField nameField, surnameField, sexField, nationalField, addressField, emailField,idField;
    UtilDateModel model;
    Properties p;
    JDatePanelImpl datePanel;
    JDatePickerImpl datePicker;
    private JButton cancel, add,update;
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    private JPanel panel,titlePanel,searchBar;
    private JLabel label;
    private JTextField searchField;

    public ManageStudentForm() {
        setTitle("UPRAVLJAJ STUDENTIMA");
        pack();
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 600);
        setResizable(false);
        setLocationRelativeTo(null);


        initFrameComps();
        activateFrame();
        layoutFrameComps();

        control.loadStTableDataWithSwingWorker(false);
    }

    private void layoutFrameComps() {
        setLayout(null);
        searchBar.setLayout(new BorderLayout());
        searchBar.add(label,BorderLayout.CENTER);
        searchBar.add(searchField,BorderLayout.EAST);
        panel.setBounds(0,100,350,500);
        table.setBounds(365,100,455,450);
        searchBar.setBounds(500,70,250,20);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Insets dfltInst = new Insets(0, 0, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = -1;
        gbc.weighty = 0.1;
        gbc.gridx++;

        JLabel label = new JLabel("Upravljaj Studentima");
        titlePanel.setBounds(300,20,200,40);
        Font font = new Font("Courier", Font.BOLD,20);
        label.setFont(font);
        titlePanel.add(label);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(new JLabel("Id: "), gbc);

        gbc.gridx=1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(new JLabel("Ime: "), gbc);

        gbc.gridx=1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(new JLabel("Prezime: "), gbc);

        gbc.gridx = 1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(surnameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        panel.add(new JLabel("Spol: "), gbc);


        gbc.gridx++;
        panel.add(createRadioPanel(), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(new JLabel("Datum rođenja: "), gbc);

        gbc.gridx = 1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(datePicker, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(new JLabel("Nacionalnost: "), gbc);

        gbc.gridx = 1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(nationalField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(new JLabel("Adresa: "), gbc);

        gbc.gridx = 1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(new JLabel("Email: "), gbc);

        gbc.gridx = 1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(emailField, gbc);

        gbc.gridy++;
        panel.add(createButtonsPanel(), gbc);

        gbc.gridx = 3;
        gbc.gridy=2;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        //add(new StudentTable());

        add(panel);
        add(table);
        add(searchBar);
        add(titlePanel);
    }

    private void activateFrame() {
        /**
         * Ova metoda nam omogućuje filtriranje studenata prema unesenom slovu/riječi.
         */
        searchField.addKeyListener(
                new KeyListener(){
                    public void keyTyped(KeyEvent e){}
                    @Override
                    public void keyPressed(KeyEvent e) {}
                    @Override
                    public void keyReleased(KeyEvent e) {
                        control.fillStudentTable(StudentTable.table,searchField.getText());
                    }
                }
        );
/**
 * Ovaj listener nam omogućava prikazivanje podataka iz tablice u zadanim okvirima za lakšu manipulaciju
 */
        StudentTable.table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int rowIndex = StudentTable.table.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) StudentTable.table.getModel();
                maleRadio.setSelected(false);
                femaleRadio.setSelected(false);
                if (model.getValueAt(rowIndex, 3).toString().equals("Male") ) {
                    maleRadio.setSelected(true);
                }else{
                    femaleRadio.setSelected(true);
                }
                idField.setText(model.getValueAt(rowIndex,0).toString());
                nameField.setText(model.getValueAt(rowIndex,1).toString());
                surnameField.setText(model.getValueAt(rowIndex,2).toString());
                nationalField.setText(model.getValueAt(rowIndex,5).toString());
                addressField.setText(model.getValueAt(rowIndex,6).toString());
                emailField.setText(model.getValueAt(rowIndex,7).toString());
                String bdate;
                try{
                    bdate = model.getValueAt(rowIndex,4).toString();
                    datePicker.getJFormattedTextField().setText(String.valueOf(bdate));
                }catch(Exception ee){
                    ee.printStackTrace();
                }
            }
        });

/**
 * Ovaj listener nam otvara dialog za dodavanje novog studenta u bazu podataka
 */

        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddStudentForm studentForm = new AddStudentForm();
                studentForm.setVisible(true);
                studentForm.setLocationRelativeTo(null);
            }
        });
/**
 * Ovaj listener nam ažurira podatke odabranog studenta.
 */
        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String fname = nameField.getText();
                String lname = surnameField.getText();
                String nationality = nationalField.getText();
                String address = addressField.getText();
                String email = emailField.getText();
                String sex = "Male";
                if(femaleRadio.isSelected()){
                    sex = "Female";
                }
                if(verifyText()){
                    String date = datePicker.getJFormattedTextField().getText();
                    control.addStudent2DB('u',id,fname,lname,sex,date,nationality,address,email);
                }


            }
        });
/**
 * Ovaj listener nam omogućava uklanjanje odabranog studenta iz baze podataka.
 */
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*
                ALTER TABLE score
                add CONSTRAINT fk_score
                FOREIGN KEY(`student_id`)
                REFERENCES student(id)
                on DELETE CASCADE
                 */
                if(idField.getText().equals("")){
                }else{
                    int id = Integer.parseInt(idField.getText());
                    control.addStudent2DB('r',id,null,null,null,null,null,null,null);
                        idField.setText("");
                        nameField.setText("");
                        surnameField.setText("");
                        nationalField.setText("");
                        addressField.setText("");
                        emailField.setText("");
                        maleRadio.setSelected(false);
                        femaleRadio.setSelected(false);
                        datePicker.getJFormattedTextField().setText("");
                }
            }
        });
    }

    private void initFrameComps() {
        control = new Controller();
        label = new JLabel("Traži: ");
        searchField = new JTextField(15);
        searchBar = new JPanel();
        table = new StudentTable();
        titlePanel = new JPanel();
        idField = new JTextField(10);
        idField.setEditable(false);
        nameField = new JTextField(20);
        surnameField = new JTextField(20);
        maleRadio = new JRadioButton("MALE");
        femaleRadio = new JRadioButton("FEMALE");
        sexField = new JTextField(20);
        addressField = new JTextField(20);
        emailField = new JTextField(20);
        nationalField = new JTextField(20);
        add = new JButton("Dodaj");
        update = new JButton("Ažuriraj");
        cancel = new JButton("Briši");
        model = new UtilDateModel();
        p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        panel = new JPanel();
    }

    public boolean verifyText(){
        if(nameField.getText().equals("") || surnameField.getText().equals("")
                || emailField.getText().equals("")|| addressField.getText().equals("")
                || nationalField.getText().equals("")|| idField.getText().equals("") || datePicker.getJFormattedTextField().getText().length() == 0){
            JOptionPane.showMessageDialog(null, "Ispunite sva polja!");
            return false;
        }  else if(Integer.parseInt(datePicker.getJFormattedTextField().getText().substring(6))>2005){
            JOptionPane.showMessageDialog(null, "Dodaj pravi datum rođenja!");
            return  false;
        } else{
            return true;
        }
    }
    private JPanel createRadioPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        maleRadio = new JRadioButton("Male");
        maleRadio.setActionCommand("Male");
        maleRadio.setSelected(true);
        panel.add(maleRadio);
        femaleRadio = new JRadioButton("Female");
        femaleRadio.setActionCommand("Female");
        panel.add(femaleRadio);
        ButtonGroup bgHoles = new ButtonGroup();
        bgHoles.add(maleRadio);
        bgHoles.add(femaleRadio);
        return panel;
    }
    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(add);
        panel.add(cancel);
        panel.add(update);
        return panel;
    }
}
