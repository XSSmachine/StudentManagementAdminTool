package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Ova klasa nam implementira MenuBar koji omogućuje otvaranje svih JDialog-a potrebnih za provođenje različitih radnji.
 * @author Karlo Kovačević
 */
public class MenuBar extends JPanel {
    private JMenuBar menubar;
    private JMenu menuFile,menuStudent,menuCourse,menuScore;
    public static JDialog dialog;
    public static JProgressBar progressBar;
    MainScreen mainScreen;
    private Action openAddStudent, openMngStudent,openAddCourse,openMngCourse,openAddScore,openMngScore,openAllScore;
    public MenuBar() {
        dialog = new JDialog(mainScreen, true);
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
    private void initFrameComps() {
        openAddStudent = new OpenAddStAction("Dodaj Studenta", null,
                "Zapisuje novog studenta u bazu",
                new Integer(KeyEvent.VK_S),
                KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        openMngStudent = new OpenMngStAction("Upravljaj Studentima", null,
                "Manipulacija podatcima studenata",
                new Integer(KeyEvent.VK_D),
                KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        openAddCourse = new OpenAddCoAction("Dodaj Kolegij", null,
                "Zapisuje novi kolegij u bazu",
                new Integer(KeyEvent.VK_K),
                KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
        openMngCourse = new OpenMngCoAction("Upravljaj Kolegijma", null,
                "Manipulacija podatcima kolegija",
                new Integer(KeyEvent.VK_L),
                KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        openAddScore = new OpenAddScAction("Dodaj Ocjenu", null,
                "Zapisuje novog ocjenu u bazu",
                new Integer(KeyEvent.VK_O),
                KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openMngScore = new OpenMngScAction("Upravljaj ocjenama", null,
                "Manipulacija podatcima ocjena",
                new Integer(KeyEvent.VK_P),
                KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        openAllScore = new OpenAllScAction("Pregled Svih Ocjena", null,
                "Daje potpun izlist svih ocjena iz sustava",
                new Integer(KeyEvent.VK_A),
                KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        menubar = new JMenuBar();
        menuFile = new JMenu();
        ImageIcon icon = new ImageIcon("./static/logo1.png");
        Image img = icon.getImage();
        Image newImage = img.getScaledInstance(169,50,Image.SCALE_SMOOTH);
        ImageIcon newImgIc = new ImageIcon(newImage);
        menuFile.setIcon(newImgIc);
        menuStudent = new JMenu("Studenti");
        menuCourse = new JMenu("Kolegij");
        menuScore = new JMenu("Ocjene");
    }
    private void activateFrame() {
    }
    private void layoutFrameComps() {
        setLayout(new BorderLayout());
        menubar.add(menuFile);
        menubar.add(menuStudent);
        menuStudent.add(openAddStudent);
        menuStudent.add(openMngStudent);

        menubar.add(menuCourse);
        menuCourse.add(openAddCourse);
        menuCourse.add(openMngCourse);

        menubar.add(menuScore);
        menuScore.add(openAddScore);
        menuScore.add(openMngScore);
        menuScore.add(openAllScore);

        add(menubar, BorderLayout.NORTH);
    }
}
