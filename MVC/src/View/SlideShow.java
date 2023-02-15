package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
/**
 *Ova klasa prikazuje slideShow odabranih slika s vremenskim razmakom.
 * @author Karlo Kovačević
 */
public class SlideShow extends JPanel {
    private JLabel pic;
    private Timer tm;
    private int x = 0;
    String[] list = {"./static/faks1.jpg","./static/faks2.jpg","./static/faks3.jpg","./static/faks4.jpg","./static/faks5.jpg"};
    public SlideShow() {
        pic = new JLabel();
        pic.setBounds(0,0,1000,380);
        setImageSIze(4);
        tm = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setImageSIze(x);
                x+=1;
                if(x>=list.length){
                    x = 0;
                }

            }
        });
        add(pic);
        tm.start();
        setLayout(null);
        setVisible(true);
    }
    public void setImageSIze(int imageSize) {
        ImageIcon icon = new ImageIcon(list[imageSize]);
        Image img = icon.getImage();
        Image newImage = img.getScaledInstance(pic.getWidth(),pic.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon newImgIc = new ImageIcon(newImage);
        pic.setIcon(newImgIc);
    }

}
