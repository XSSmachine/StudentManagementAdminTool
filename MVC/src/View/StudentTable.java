package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
/**
 * Ova klasa prikazuje JTable s podatcima studenata.
 * @author Karlo Kovačević
 */

public class StudentTable extends JPanel {

    public static JTable table = new JTable();
    public static JScrollPane scrollPane;
    private String[] colHeadings = {"Id", "Ime", "Prezime","Spol","Datum rođenja","Nacionalnost","Adresa", "Email"};
    private final int numRows = 0;
    private DefaultTableModel model;
    Color lightBlue= new Color(0,0,182,155);
    public StudentTable(){
        setSize(200,300);
        initFrameComps();
        activateFrame();
        layoutFrameComps();
    }

    private void initFrameComps() {
        model = new DefaultTableModel(numRows, colHeadings.length);
        model.setColumnIdentifiers(colHeadings);
        table = new JTable(model);
        table.setRowHeight(40);
        table.setShowGrid(true);
        table.setGridColor(Color.lightGray);
        table.setSelectionBackground(lightBlue);
        table.getTableHeader().setReorderingAllowed(false);
        scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    private void activateFrame() {
    }
    private void layoutFrameComps() {
        setLayout(new BorderLayout());
        scrollPane.setBounds(0,0,200,300);
        add(scrollPane, BorderLayout.CENTER);
    }
}

