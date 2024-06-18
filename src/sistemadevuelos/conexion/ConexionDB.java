/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadevuelos.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private Connection conexion;

    public Connection conectar() throws SQLException {
        // Cambiar los valores según tu configuración de base de datos
        String url = "jdbc:mysql://localhost:3306/sistemavuelos";
        String usuario = "root";
        String contraseña = "Basedatos1";

        conexion = DriverManager.getConnection(url, usuario, contraseña);
        return conexion;
    }

    public void desconectar() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }
}

