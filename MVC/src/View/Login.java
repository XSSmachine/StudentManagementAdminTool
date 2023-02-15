package View;

import Model.SQLConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Ova klasa nam omogućuje ulaz u samu aplikaciju, provjerom podudaranja korisničkog imena i lozinke iz baze podataka.
 * @author Karlo Kovačević
 */
public class Login extends JFrame {
    JLabel header,username,password;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton login;
    JDialog dialog;
    final JProgressBar progressBar;
    MyTask task;

    public Login() {
        setTitle("LOGIRANJE");
        pack();
        setVisible(true);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 320);
        setResizable(false);
        setLocationRelativeTo(null);

        dialog = new JDialog(this, true);
        dialog.setTitle("Loading...");
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        progressBar = new JProgressBar(0, 100);
        dialog.setLayout(new BorderLayout());
        dialog.add(progressBar);
        dialog.setSize(400, 70);
        dialog.setLocationRelativeTo(this);

        initFrameComps();
        activateFrame();
        layoutFrameComps();


    }

    private  void layoutFrameComps() {
        setLayout(null);
        this.getRootPane().setDefaultButton(login);
        header.setBounds(150,50,100,30);
        username.setBounds(100,100,80,30);
        usernameField.setBounds(180,100,100,30);
        password.setBounds(100,200,100,30);
        passwordField.setBounds(180,200,100,30);
        login.setBounds(150,250,100,30);

        usernameField.setBackground(Color.red);
        usernameField.setBackground(Color.white);
        add(header);
        add(username);
        add(usernameField);
        add(password);
        add(passwordField);
        add(login);


    }

    private void activateFrame() {
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(usernameField.getText().equals("") && passwordField.getText().equals("")) {
                    usernameField.setBackground(Color.RED);
                    passwordField.setBackground(Color.RED);
                }else if(usernameField.getText().equals("")){
                    usernameField.setBackground(Color.RED);
                    passwordField.setBackground(Color.WHITE);
                } else if (passwordField.getText().equals("")) {
                    passwordField.setBackground(Color.RED);
                    usernameField.setBackground(Color.WHITE);
                }else{
                    usernameField.setBackground(Color.WHITE);
                    passwordField.setBackground(Color.WHITE);
                    task = new MyTask(dialog);
                    task.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if ("progress".equals(evt.getPropertyName())) {
                                progressBar.setValue((Integer)evt.getNewValue());
                            }
                        }
                    });
                    task.execute();


                }
            }
        });
    }

    /**
     * Ova klasa nam omogućuje rad s SwingWorkerom koji je u ovom slučaju zadužen za dohvaćanje
     * podataka iz udaljene baze te inicijalizaciju glavnog framea uz što veću responzivnost same aplikacije.
     * @author Karlo Kovačević
     */
    private class MyTask extends SwingWorker<Void, Void> {

        private final JDialog dialog;
        private MainScreen mainScreen;
        public MyTask(JDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        protected Void doInBackground() throws Exception {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    dialog.setVisible(true);
                }
            });

            int progress = 0;
            for (int i = 0; i < 5; i++) {
                Thread.sleep(150);
                setProgress(progress += 20);
                if (i == 2){
                    Connection con = SQLConnection.getConnection();
                    PreparedStatement ps;

                    try {
                        ps = con.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
                        ps.setString(1,usernameField.getText());
                        ps.setString(2,passwordField.getText());

                        ResultSet rs = ps.executeQuery();
                        if(rs.next()){
                            System.out.println("YES");

                            mainScreen = new MainScreen();
                            mainScreen.setVisible(false);
                            dispose();
                        }else{
                            JOptionPane.showMessageDialog(null,"Incorrect username or password! \n                Try again!","Login failed",3);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
            return null;
        }
        @Override
        protected void done() {
            dialog.setVisible(false);
            if(mainScreen != null){
                mainScreen.setVisible(true);
            }
            dialog.dispose();
        }
    }
    private void initFrameComps() {

        header = new JLabel("DOBRODOŠLI");
        username = new JLabel("Korisnik: ");
        password = new JLabel("Lozinka: ");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        login = new JButton("UĐI");

    }
}
