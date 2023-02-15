package View;

import Controller.Controller;
import Model.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Ova klasa nam omogućuje uvid i pristup svim kolegijma iz baze podataka kao i njihovu manipulaciju poput brisanja, dodavanja i ažuriranja.
 * @author Karlo Kovačević
 */
public class ManageCourseForm extends JDialog {

    Controller control;
    SpinnerModel hourModel,ectsModel;
    private CourseTable table;
    private JTextField idField,nameCField;
    private JSpinner hoursField, ectsField;
    private JButton cancel, add,update;
    private JPanel panel,titlePanel,searchBar;
    private JTextField searchField;

    public ManageCourseForm() {
        setTitle("UPRAVLJAJ KOLEGIJMA");
        pack();
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 600);
        setResizable(false);
        setLocationRelativeTo(null);


        initFrameComps();
        activateFrame();
        layoutFrameComps();
        control.loadCoTableDataWithSwingWorker();
    }

    private void layoutFrameComps() {
        setLayout(null);
        panel.setBounds(0,100,350,500);
        table.setBounds(365,100,455,450);
        titlePanel.setBounds(300,20,200,40);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Insets dfltInst = new Insets(0, 0, 0, 0);

        gbc.weightx = 0;
        gbc.weighty = 0;

        gbc.gridx = 0;
        gbc.gridy = 0;


        gbc.weightx = -1;
        gbc.weighty = 0.1;
        gbc.gridx++;

        JLabel label = new JLabel("Upravljaj Kolegijma");
        Font font = new Font("Courier", Font.BOLD,20);
        label.setFont(font);
        titlePanel.add(label,BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(new JLabel("Kolegij ID: "), gbc);

        gbc.gridx=1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(new JLabel("Ime Kolegija: "), gbc);

        gbc.gridx=1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(nameCField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(new JLabel("Sati: "), gbc);

        gbc.gridx=1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(hoursField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(new JLabel("ECTS: "), gbc);

        gbc.gridx=1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(ectsField, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(createButtonsPanel(), gbc);

        add(panel);
        add(table);
        add(searchBar);
        add(titlePanel);
    }

    private void activateFrame() {

/**
 * Ovaj listener nam omogućava prikazivanje podataka iz tablice u zadanim okvirima za lakšu manipulaciju
 */
        CourseTable.table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int rowIndex = CourseTable.table.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) CourseTable.table.getModel();

                idField.setText(model.getValueAt(rowIndex,0).toString());
                nameCField.setText(model.getValueAt(rowIndex,1).toString());
                hoursField.setValue(model.getValueAt(rowIndex,2));
                ectsField.setValue(model.getValueAt(rowIndex,3));
            }
        });


/**
 * Ovaj listener nam otvara dialog za dodavanje novog kolegija u bazu podataka
 */
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddCourseForm courseForm = new AddCourseForm();
                courseForm.setVisible(true);
                courseForm.setLocationRelativeTo(null);


            }
        });
/**
* Ovaj listener nam ažurira odabrani kolokvij.
*/
        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                    int id = Integer.parseInt(idField.getText());
                    String courseN = nameCField.getText();
                    int courseH = (int) hoursField.getValue();
                    int courseE = (int) ectsField.getValue();
                    control.manageCourse2DB('u',id,courseN,courseH,courseE);
                    JOptionPane.showMessageDialog(null, "Kolegij ažuriran!!!");


            }
        });
/**
 * Ovaj listener nam omogućava uklanjanje odabranog kolegija iz baze podataka.
 */
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*
                ALTER TABLE score
                add CONSTRAINT fk_score_course
                FOREIGN KEY (`course_id`)
                REFERENCES course (id)
                on DELETE CASCADE
                 */
                if(!idField.getText().equals("")){
                    int id = Integer.parseInt(idField.getText());
                    control.manageCourse2DB('r',id,null,0,0);
                    idField.setText("");
                    nameCField.setText("");
                    hoursField.setValue(0);
                    ectsField.setValue(0);
                }else{
                    JOptionPane.showMessageDialog(null, "Molim Vas prvo izaberite kolegij!!!");
                }
            }
        });
    }

    private void initFrameComps() {
        control = new Controller();
        hourModel = new SpinnerNumberModel(15, 0, 120, 1);
        ectsModel = new SpinnerNumberModel(1, 0, 8, 1);
        searchField = new JTextField(15);
        searchBar = new JPanel();
        table = new CourseTable();
        titlePanel = new JPanel();
        idField = new JTextField(8);
        idField.setEditable(false);
        nameCField = new JTextField(10);
        ectsField = new JSpinner(ectsModel);
        hoursField = new JSpinner(hourModel);
        add = new JButton("Dodaj");
        update = new JButton("Ažuriraj");
        cancel = new JButton("Briši");
        panel = new JPanel();

    }



    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(add);
        panel.add(cancel);
        panel.add(update);
        return panel;
    }
}
