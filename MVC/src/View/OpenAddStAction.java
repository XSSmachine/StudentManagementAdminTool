package View;

import View.AddStudentForm;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Ova klasa nam implementira command pattern putem AbstractAction klase koja nam omogućuje otvaranje dialoga te dodavanje različitih značajki
 * koje mogu biti dodane velikom broju drugih komponenti.
 * @author Karlo Kovačević
 */
public class OpenAddStAction extends AbstractAction {
    String name;
    public OpenAddStAction(String name, Icon icon, String desc,
                           Integer mnemonic, KeyStroke accelorator) {
        super(name, icon);
        putValue(Action.SHORT_DESCRIPTION, desc);
        putValue(Action.MNEMONIC_KEY, mnemonic);
        putValue(Action.ACCELERATOR_KEY, accelorator);
        this.name = name;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        AddStudentForm addStudentForm = new AddStudentForm();
        addStudentForm.setVisible(true);
    }


}
