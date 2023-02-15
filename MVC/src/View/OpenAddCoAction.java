package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
/**
 * Ova klasa nam implementira command pattern putem AbstractAction klase koja nam omogućuje otvaranje dialoga te dodavanje različitih značajki
 * koje mogu biti dodane velikom broju drugih komponenti.
 * @author Karlo Kovačević
 */
public class OpenAddCoAction extends AbstractAction{
    String name;
    public OpenAddCoAction(String name, Icon icon, String desc,
                           Integer mnemonic, KeyStroke accelorator) {
        super(name, icon);
        putValue(Action.SHORT_DESCRIPTION, desc);
        putValue(Action.MNEMONIC_KEY, mnemonic);
        putValue(Action.ACCELERATOR_KEY, accelorator);
        this.name = name;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        AddCourseForm addCourseForm =new AddCourseForm();
        addCourseForm.setVisible(true);
    }
}
