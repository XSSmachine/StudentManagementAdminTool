package View;

import Controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Ova klasa nam omogućuje dodavanje nove završne ocjene kolegija za odabranog studenta u udaljenu bazu podataka.
 * @author Karlo Kovačević
 */
public class AddScoreForm extends JDialog{
    Controller control;
    SpinnerModel scoreModel;
    private StudentTable table;
    private JTextField idField;
    private JTextArea dscrField;
    private JComboBox nameCField;
    private JSpinner scoreField;

    private JButton cancel, add;

    private JPanel panel,titlePanel;



    public AddScoreForm() {
        setTitle("NOVA OCJENA");
        pack();
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        initFrameComps();
        activateFrame();
        layoutFrameComps();
        control.fillCourseComboBox(nameCField);
        control.loadStTableDataWithSwingWorker(true);
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

        JLabel label = new JLabel("Dodaj Ocjenu");
        Font font = new Font("Courier", Font.BOLD,20);
        label.setFont(font);
        titlePanel.add(label,BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(new JLabel("Student ID: "), gbc);

        gbc.gridx=1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(new JLabel("Kolegij ID: "), gbc);

        gbc.gridx=1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(nameCField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(new JLabel("Ocjena: "), gbc);

        gbc.gridx=1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(scoreField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(new JLabel("Opis: "), gbc);

        gbc.gridx=1;
        gbc.insets = dfltInst;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(dscrField, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(createButtonsPanel(), gbc);

        add(panel);
        add(table);
        add(titlePanel);
    }

    private void activateFrame() {
/**
 * Ovaj listener nam omogućava biranje određenog studenta za upis ocjene.
 */
        StudentTable.table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int rowIndex = StudentTable.table.getSelectedRow();
                idField.setText(StudentTable.table.getValueAt(rowIndex,0).toString());
            }
        });

/**
 * Ovaj listener nam omogućava bilježenje ocjene u bazi podataka.
 */

        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int studentId = Integer.parseInt(idField.getText());
                int courseId = control.getCourseId(nameCField.getSelectedItem().toString());
                Integer studentScore = (Integer) scoreField.getValue();
                System.out.println(courseId);
                 control.manageScores2DB('i',studentId,courseId,studentScore,dscrField.getText());
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
        scoreModel = new SpinnerNumberModel(1, 1, 5, 1);
        table = new StudentTable();
        titlePanel = new JPanel();
        idField = new JTextField(8);
        dscrField = new JTextArea(5,15);
        idField.setEditable(false);
        nameCField = new JComboBox();
        scoreField = new JSpinner(scoreModel);
        add = new JButton("Dodaj");
        cancel = new JButton("Izađi");
        panel = new JPanel();

    }

    /**
     * Ova metoda nam grupira i prikazuje JButtons-e:
     * @return
     */
    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(add);
        panel.add(cancel);
        return panel;
    }
}
