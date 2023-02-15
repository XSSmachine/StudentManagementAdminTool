package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Ova klasa služi za prikazivanje proizvoljnih Jbutton-a koji linkaju korisnika s zadanim web stranicama.
 * @author Karlo Kovačević
 */

public class WebsiteButtonPanel extends JPanel implements ActionListener {

    private String[] buttonLabels;
    private String[] linkUrls;

    public WebsiteButtonPanel(String[] buttonLabels, String[] linkUrls) {
        this.buttonLabels = buttonLabels;
        this.linkUrls = linkUrls;
        setLayout(new GridLayout(buttonLabels.length, 1, 10, 10));
        setBorder(BorderFactory.createTitledBorder("Websites"));

        // Create a button for each label and add it to the panel
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.setPreferredSize(new Dimension(200, 50));
            button.addActionListener(this);
            add(button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the URL of the button that was clicked
        int index = -1;
        for (int i = 0; i < buttonLabels.length; i++) {
            if (e.getActionCommand().equals(buttonLabels[i])) {
                index = i;
                break;
            }
        }

        // Open the URL in the default web browser
        if (index != -1) {
            try {
                URI uri = new URI(linkUrls[index]);
                java.awt.Desktop.getDesktop().browse(uri);
            } catch (Exception ex) {
                JLabel message = new JLabel("Error: Could not open website", SwingConstants.CENTER);
                message.setForeground(Color.RED);
                message.setPreferredSize(new Dimension(300, 50));
                add(message);
            }
        }
    }
}
