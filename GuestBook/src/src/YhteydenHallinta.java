package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class YhteydenHallinta {

    public static Connection avaaYhteys(String ajuri, String url, String kayttaja, String salasana) {

        try {
            Class.forName(ajuri).newInstance();
            return DriverManager.getConnection(url, kayttaja, salasana);
        } catch (Exception e) {
            System.out.println("Virhe: " + e);
            return null;
        }
    }

    public static boolean suljeYhteys(Connection yhteys) {
        if (yhteys != null) {
            try {
                yhteys.close();
                return true;
            } catch (Exception sqle) {
                System.out.println("Virhe sulkemisessa");
                return false;
            }
        } else {
            return false;
        }
    }

    public static void suljeLause(Statement lause) {
        if (lause != null) {
            try {
                lause.close();
            } catch (Exception sqle) {
                System.out.println("Virhe lauseen sulkemisessa");
            }
        }
    }

    public static boolean suljeTulosjoukko(ResultSet tulosjoukko) {
        if (tulosjoukko != null) {
            try {
                tulosjoukko.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
