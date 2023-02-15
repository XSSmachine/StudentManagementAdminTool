package View;

import Controller.Controller;
import javax.swing.*;
import java.awt.*;

/**
 *Ova klasa nam nasljeđuje JDialog te prikazuje sve ocjene studenata na jednom mjestu.
 * @author Karlo Kovačević
 */
public class ShowScores extends JDialog {

    private Controller control;
    private AllScoresTable table;
    private JPanel titlePanel;

    public ShowScores(){
        setTitle("SVE OCJENE");
        pack();
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        initFrameComps();
        activateFrame();
        layoutFrameComps();

        control.loadAllScTableDataWithSwingWorker();
    }

    private void layoutFrameComps() {
        setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));

        table.setBounds(365,100,455,450);
        titlePanel.setBounds(300,20,200,40);
        JLabel label = new JLabel("Sve Ocjene");
        Font font = new Font("Courier", Font.BOLD,20);
        label.setFont(font);
        titlePanel.add(label,BorderLayout.CENTER);

        add(titlePanel);
        add(table);
    }

    private void activateFrame() {
    }

    private void initFrameComps() {
        control = new Controller();
        table = new AllScoresTable();
        titlePanel = new JPanel();
    }
}
