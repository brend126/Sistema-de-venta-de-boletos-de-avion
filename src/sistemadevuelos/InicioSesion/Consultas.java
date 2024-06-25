/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadevuelos.InicioSesion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import sistemadevuelos.conexion.ConexionDB;

public class Consultas {
    
    public void guardarUsuarios(String correo, String nombre, char[] password, char[] confirmacionPassword) {
        if (!String.valueOf(password).equals(String.valueOf(confirmacionPassword))) {
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden. Por favor, inténtalo de nuevo.");
            return;
        }
        
        ConexionDB db = new ConexionDB();
        String sql = "INSERT INTO usuarios(correo, nombre, clave) VALUES (?, ?, ?)";
        
        try (Connection conexion = db.conectar();
             PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, correo);
            pst.setString(2, nombre);
            pst.setString(3, String.valueOf(password)); // Convertir char[] a String para almacenar en la base de datos
            
            int rs = pst.executeUpdate();
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Usuario guardado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar el usuario");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el usuario: " + e.getMessage());
        }
    }
    
public void AccesoUsuario(String correo, char[] pass) {
        ConexionDB db = new ConexionDB();
        
        try (Connection cn = db.conectar();
             PreparedStatement pst = cn.prepareStatement("SELECT correo, clave FROM usuarios WHERE correo = ?")) {
            pst.setString(1, correo);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                String passCorrecto = rs.getString("clave");
                
                if (String.valueOf(pass).equals(passCorrecto)) {
                    JOptionPane.showMessageDialog(null, "Login correcto. Bienvenido " + correo);
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar el usuario: " + e.getMessage());
        }
    }
    
}
