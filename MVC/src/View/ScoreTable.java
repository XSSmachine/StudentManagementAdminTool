package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
/**
 * Ova klasa prikazuje JTable s podatcima završnih ocjena kolegija pojedinih studenata.
 * @author Karlo Kovačević
 */
public class ScoreTable extends JPanel {
    public static JTable table = new JTable();
    public static JScrollPane scrollPane;
    Color lightBlue= new Color(0,0,182,155);
    private String[] colHeadings = {"Student Id", "Kolegij Id", "Ime Kolegija","Ocjena","Opis"};
    private final int numRows = 0;
    private DefaultTableModel model;

    public ScoreTable(){
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
