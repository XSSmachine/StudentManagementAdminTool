package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Ova klasa implementira Singleton pattern nam omogućuje povezivanje sa mySql bazom podataka pomoću jdbc priključka,
 * te bih uz to napomenuo kako je zadani atribut u samom url važan za brže dohvaćanje samih podataka.
 * @author Karlo Kovačević
 */

public class SQLConnection {

    private static Connection con = null;
    static{

        try {
            con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/unimngdb?sendStringParametersAsUnicode=false","karloo","karlo123");
            con.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public static Connection getConnection()
    {
        return con;
    }
}
