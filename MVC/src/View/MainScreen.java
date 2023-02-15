package View;

import javax.swing.*;
/**
 * Ovo nam je glavna klasa za prikazivanje svih komponenti ovoga projekta kao i sam dizajn koji je inspiriran našim sveučilištem.
 * @author Karlo Kovačević
 */

public class MainScreen extends JFrame {
    private MenuBar menuBar;
    private SlideShow slideShow;
    JLabel text;
    private WebsiteButtonPanel websiteButtonPanel;
    String[] buttonLabels = { "Facebook", "LinkedIn","YouTube"};
    String[] linkUrls = {"https://www.facebook.com/unizd/", "https://www.linkedin.com/school/university-of-zadar/", "https://www.youtube.com/channel/UC9GQchnwDagn_2jAJJXQ_qw"};
    public MainScreen() {

        setTitle("UNIZD SIT");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setResizable(false);
        setLocationRelativeTo(null);

        initFrameComps();
        activateFrame();
        layoutFrameComps();
    }

    private void layoutFrameComps() {
        menuBar.setBounds(0,0,1000,50);
        slideShow.setBounds(0,50,1000,300);
        text.setBounds(40,350,500,300);
        websiteButtonPanel.setBounds(550,360,400,300);
        add(menuBar);
        add(slideShow);
        add(text);
        add(websiteButtonPanel);
    }
    private void activateFrame() {
    }
    private void initFrameComps() {
        websiteButtonPanel = new WebsiteButtonPanel(buttonLabels, linkUrls);
        menuBar = new MenuBar();
        slideShow = new SlideShow();
        text = new JLabel("<html>Studij Informacijskih Tehnologija na Sveučilištu u Zadru namijenjen je studentima koji žele stjecati znanja i vještine u području informacijskih tehnologija. Program se fokusira na razvoj i primjenu računalnih tehnologija u različitim industrijama, uključujući informacijske sustave, računalno programiranje, mrežne tehnologije i sigurnost informacija.<br>" +
                "<br>" +
                "Studenti će imati priliku stjecati praktično iskustvo kroz različite projekte i laboratorijske vježbe te će biti spremni za rad u IT industriji ili nastavak daljnjeg akademskog usavršavanja.<br>" +
                "<br>" +
                "Učenje na Studiju Informacijskih Tehnologija podržavaju vrhunski stručnjaci i profesori koji su dobri u svom području i koji žele prenijeti svoje znanje i iskustvo studentima.<br>" +
                "<br>" +
                "Ukupno, Studij Informacijskih Tehnologija u Zadru pruža studentima jedinstvenu priliku da steknu široko znanje i vještine u dinamičnom i brzo rastućem području informacijskih tehnologija, što ih čini dobro pripremljenima za uspješnu karijeru u budućnosti.</html>");
    }
}
