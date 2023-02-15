package View;

import Controller.Controller;
import Model.DateLabelFormatter;
import Model.Student;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Properties;
/**
 * Ova klasa nam omogućuje dodavanje novog studenta u udaljenu bazu podataka
 * @author Karlo Kovačević
 */
public class AddStudentForm extends JDialog {

    Controller control;
    private JTextField nameField, surnameField, nationalField, addressField, emailField;
    UtilDateModel model;
    Properties p;
    JDatePanelImpl datePanel;
    JDatePickerImpl datePicker;
    private JButton cancel, add;
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    JDialog dialog;
    final JProgressBar progressBar;
    public AddStudentForm() {
        setTitle("NOVI STUDENT");
        pack();
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 400);
        setResizable(false);
        setLocationRelativeTo(null);

        dialog = new JDialog(this, true);
        dialog.setTitle("Loading...");
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        progressBar = new JProgressBar(0, 100);
        dialog.setLayout(new BorderLayout());
        dialog.add(progressBar);
        dialog.setSize(300, 100);
        dialog.setLocationRelativeTo(this);

        initFrameComps();
        activateFrame();
        layoutFrameComps();
    }

    private void layoutFrameComps() {

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Insets dfltInst = new Insets(0, 0, 0, 0);

        gbc.weightx = 0;
        gbc.weighty = 0;

        gbc.gridx = 0;
        gbc.gridy = 0;

        add(Box.createVerticalStrut(5), gbc);

        gbc.weightx = 0;
        gbc.weighty = 0.1;
        gbc.gridx++;

        JLabel label = new JLabel("Dodaj Novog Studenta");
        Font font = new Font("Courier", Font.BOLD,20);
        label.setFont(font);
        add(label, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        add(new JLabel("Ime: "), gbc);

        gbc.gridx = 1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        add(new JLabel("Prezime: "), gbc);

        gbc.gridx = 1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(surnameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        //gbc.insets = new Insets(0, 0, 0, 5);
        add(new JLabel("Spol: "), gbc);


        gbc.gridx++;
        add(createRadioPanel(), gbc);

        gbc.gridx = 0;
        gbc.gridy =6;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        add(new JLabel("Datum rođenja: "), gbc);

        gbc.gridx = 1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(datePicker, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        add(new JLabel("Nacionalnost: "), gbc);

        gbc.gridx = 1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(nationalField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        add(new JLabel("Adresa: "), gbc);

        gbc.gridx = 1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        add(new JLabel("Email: "), gbc);

        gbc.gridx = 1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(emailField, gbc);

        gbc.gridy++;
        add(createButtonsPanel(), gbc);
    }

    private void activateFrame() {
        /**
         * Ovaj listener daje znak da podatci iz polja budu poslani u bazu.
         */
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
                    Student.MyTask task = new Student.MyTask(dialog);
                    task.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if ("progress".equals(evt.getPropertyName())) {
                                progressBar.setValue((Integer)evt.getNewValue());
                            }
                        }
                    });
                    task.execute();
                    String date = datePicker.getJFormattedTextField().getText();
                    control.addStudent2DB('i',null,fname,lname,sex,date,nationality,address,email);
                }
            }
        });
/**
 * Ovaj listener omogućava izlaz iz ovog dialoga.
 */
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void initFrameComps() {
        control = new Controller();
        nameField = new JTextField(20);
        surnameField = new JTextField(20);
        maleRadio = new JRadioButton("MALE");
        femaleRadio = new JRadioButton("FEMALE");
        addressField = new JTextField(20);
        emailField = new JTextField(20);
        nationalField = new JTextField(20);
        add = new JButton("Dodaj");
        cancel = new JButton("Izađi");
        model = new UtilDateModel();
        p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    /**
     * Ova metoda nam provjerava da li je unos podataka zadovoljavajuć.
     * @return
     */
    public boolean verifyText(){
        if(nameField.getText().equals("") || surnameField.getText().equals("")
                || emailField.getText().equals("")|| addressField.getText().equals("")
                || nationalField.getText().equals("")|| datePicker.getJFormattedTextField().getText().length() == 0){
            JOptionPane.showMessageDialog(null, "Ispuni sva polja!");
            return false;
        }  else if(Integer.parseInt(datePicker.getJFormattedTextField().getText().substring(6))>2005){
            JOptionPane.showMessageDialog(null, "Dodaj pravi datum rođenja!");
            return  false;
        } else{
            return true;
        }
    }

    /**
     * Ova metoda nam stvara Radio button group.
     * @return
     */
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

    /**
     * Ova metoda je zadužena za grupiranje i prikaz JButtons-a.
     * @return
     */
    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(add);
        panel.add(cancel);

        return panel;
    }
}
