package sistemadevuelos.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionDB {
    private static Connection conn;
    private static final String driver = "com.mysql.jdbc.Driver";  // Driver class for MySQL Connector 5.1.15
    private static final String URL = "jdbc:mysql://localhost:3306/sistemavuelos?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "Basedatos1";

    public Connection conectar() {
        conn = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName(driver);

            // Establish the connection
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (conn != null) {
                JOptionPane.showMessageDialog(null, "Conexión establecida");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e.toString());
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: MySQL JDBC Driver not found " + e.toString());
        }
        return conn;
    }
}


