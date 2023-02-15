package View;

import Controller.Controller;
import Model.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Ova klasa nam omogućuje dodavanje novog kolegija u udaljenu bazu podataka
 * @author Karlo Kovačević
 */
public class AddCourseForm  extends JDialog {

    private JPanel panel,titlePanel;
    private JTextField nameCField;
    private JSpinner hours,ects;
    private JButton cancel, add;
    SpinnerModel hourModel,ectsModel;
    private Controller control;

    public AddCourseForm() {

        setTitle("NOVI KOLEGIJ");
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        initFrameComps();
        activateFrame();
        layoutFrameComps();
    }

    private void layoutFrameComps() {
        setLayout(null);
        panel.setLayout(new GridBagLayout());
        panel.setBounds(0,80,380,300);
        titlePanel.setBounds(170,0,200,80);
        titlePanel.setLayout(new BorderLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Insets dfltInst = new Insets(0, 0, 0, 0);

        gbc.weightx = 0;
        gbc.weighty = 0;

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = -1;
        gbc.weighty = 0.1;
        gbc.gridx++;

        JLabel label = new JLabel("Dodaj Novi Kolegij");
        Font font = new Font("Courier", Font.BOLD,20);
        label.setFont(font);
        titlePanel.add(label,BorderLayout.CENTER);

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
        panel.add(hours, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(new JLabel("ECTS: "), gbc);

        gbc.gridx=1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(ects, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(createButtonsPanel(), gbc);
        add(panel);
        add(titlePanel);
    }

    private void activateFrame() {
        /**
         * Ovaj listener omogućava dodavanje podataka o novom kolegiju u bazu podataka.
         */
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!control.doesCourseExist(nameCField.getText())) {
                    String courseN = nameCField.getText();
                    int courseH = (int) hours.getValue();
                    int courseE = (int) ects.getValue();
                    control.manageCourse2DB('i',null,courseN,courseH,courseE);

                }else{
                    JOptionPane.showMessageDialog(null, "Kolegij već postoji!!!");
                }
            }
        });
        /**
         * Ovaj listener nam omogućava izlaz iz dialoga.
         */
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               dispose();
            }
        });
    }

    private void initFrameComps() {
        control = new Controller();
        hourModel = new SpinnerNumberModel(15, //initial value
                        15, //minimum value
                        60, //maximum value
                        1); //step
        ectsModel = new SpinnerNumberModel(1, //initial value
                1, //minimum value
                7, //maximum value
                1); //step
        panel = new JPanel();
        titlePanel = new JPanel();
        nameCField = new JTextField(13);
        hours = new JSpinner(hourModel);
        ects = new JSpinner(ectsModel);
        cancel = new JButton("Izađi");
        add = new JButton("Dodaj");

    }
    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(add);
        panel.add(cancel);

        return panel;
    }
}
