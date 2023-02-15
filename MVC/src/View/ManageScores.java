package View;

import Controller.Controller;
import Model.Course;
import Model.Score;
import Model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;
/**
 * Ova klasa nam omogućuje uvid i pristup svim ocjenama iz baze podataka kao i njihovu manipulaciju poput brisanja, dodavanja i ažuriranja.
 * @author Karlo Kovačević
 */
public class ManageScores extends JDialog {

    Controller control;
    private ScoreTable table;
    private JTextField idField,courseIdField,scoreField;
    private JTextArea dscrField;


    private JButton cancel,edit, add;

    private JPanel panel,titlePanel,searchBar;
    private JTextField searchField;



    public ManageScores() {
        setTitle("UPRAVLJAJ OCJENAMA");
        pack();
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        initFrameComps();
        activateFrame();
        layoutFrameComps();
        control.loadScTableDataWithSwingWorker();
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

        JLabel label = new JLabel("Upravljaj Ocjenama");
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
        panel.add(courseIdField, gbc);

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
        add(searchBar);
        add(titlePanel);

    }

    private void activateFrame() {
        /**
         * Ovaj listener nam omogućava prikazivanje podataka iz tablice u zadanim okvirima za lakšu manipulaciju
         */
        ScoreTable.table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int rowIndex = ScoreTable.table.getSelectedRow();
                idField.setText(ScoreTable.table.getValueAt(rowIndex,0).toString());
                courseIdField.setText(ScoreTable.table.getValueAt(rowIndex,1).toString());
                scoreField.setText(ScoreTable.table.getValueAt(rowIndex,3).toString());
                dscrField.setText(ScoreTable.table.getValueAt(rowIndex,4).toString());
            }
        });
/**
 * Ovaj listener nam otvara dialog za dodavanje nove ocjene u bazu podataka
 */
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddScoreForm scoreForm = new AddScoreForm();
                scoreForm.setVisible(true);
                scoreForm.setLocationRelativeTo(null);
            }
        });
/**
 * Ovaj listener nam ažurira odabranu ocjenu.
 */

        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int studId = Integer.parseInt(idField.getText());
                int courseId = Integer.parseInt(courseIdField.getText());
                int score = Integer.parseInt(scoreField.getText());
                String descr = dscrField.getText();
                control.manageScores2DB('u',studId,courseId,score,descr);
                control.loadScTableDataWithSwingWorker();
            }
        });
/**
 * Ovaj listener nam omogućava uklanjanje odabrane ocjene iz baze podataka.
 */
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int studId = Integer.parseInt(idField.getText());
                int courseId = Integer.parseInt(courseIdField.getText());
                control.manageScores2DB('r',studId,courseId,null,null);
                control.loadScTableDataWithSwingWorker();
            }
        });
    }

    private void initFrameComps() {
        control = new Controller();
        titlePanel = new JPanel();
        searchField = new JTextField(15);
        searchBar = new JPanel();
        table = new ScoreTable();
        idField = new JTextField(8);
        dscrField = new JTextArea(5,15);
        idField.setEditable(false);
        courseIdField = new JTextField(15);
        scoreField = new JTextField(15);
        add = new JButton("Dodaj");
        edit = new JButton("Ažuriraj");
        cancel = new JButton("Briši");
        panel = new JPanel();
    }
    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(add);
        panel.add(edit);
        panel.add(cancel);
        return panel;
    }
}
