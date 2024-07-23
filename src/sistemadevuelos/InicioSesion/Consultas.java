/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadevuelos.InicioSesion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.JOptionPane;
import sistemadevuelos.conexion.ConexionDB;

public class Consultas {
    
    public int guardarUsuarios(String correo, String nombre, char[] password, char[] confirmacionPassword) {
        if (!String.valueOf(password).equals(String.valueOf(confirmacionPassword))) {
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden. Por favor, inténtalo de nuevo.");
            return -1; // Indica error
        }

        ConexionDB db = new ConexionDB();
        String sql = "INSERT INTO usuarios(correo, nombre, clave) VALUES (?, ?, ?)";
        int idUsuario = -1;

        try (Connection conexion = db.conectar();
             PreparedStatement pst = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, correo);
            pst.setString(2, nombre); // Asegúrate de pasar el nombre
            pst.setString(3, String.valueOf(password)); // Convertir char[] a String para almacenar en la base de datos

            int filasAfectadas = pst.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        idUsuario = rs.getInt(1); // Obtener el ID generado
                        JOptionPane.showMessageDialog(null, "Usuario guardado correctamente. ");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar el usuario");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el usuario: " + e.getMessage());
        }

        return idUsuario; // Retornar el ID del usuario creado
    }
    
public int AccesoUsuario(String correo, char[] pass) {
    ConexionDB db = new ConexionDB();
    int idUsuario = 0;

    try (Connection cn = db.conectar();
         PreparedStatement pst = cn.prepareStatement("SELECT id, clave FROM usuarios WHERE correo = ?")) {
        pst.setString(1, correo);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String passCorrecto = rs.getString("clave");

            // Comparar contraseñas en texto plano
            if (String.valueOf(pass).equals(passCorrecto)) {
                idUsuario = rs.getInt("id");
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

    return idUsuario;
    }
    

 // Método para generar un código de reserva aleatorio
    private String generarCodigoReserva() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder codigo = new StringBuilder();
        Random rnd = new Random();
        while (codigo.length() < 6) { // Puedes ajustar la longitud del código según tus necesidades
            int index = (int) (rnd.nextFloat() * caracteres.length());
            codigo.append(caracteres.charAt(index));
        }
        return codigo.toString();
    }

    public void guardarDatosPasajero(String nombre, String fechaNacimiento, String genero, String nacionalidad, String numPasaporte,
                                     String paisEmisorPas, String correo, String telefono, String direccion,
                                     String contactoEmer, String relacionEmer, String telefonoEmer, int idUsuario) {
        ConexionDB db = new ConexionDB();
        Connection conn = null;
        
        String sql = "INSERT INTO pasajeros (nombre_completo, fecha_nacimiento, genero, nacionalidad, num_pasaporte, " +
                     "pais_emisor_pasaporte, correo, telefono, direccion, contacto_emergencia, relacion_emergencia, " +
                     "telefono_emergencia, codigo_reserva, usuario_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            conn = db.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            // Generar código de reserva
            String codigoReserva = generarCodigoReserva();

            pstmt.setString(1, nombre);
            pstmt.setString(2, fechaNacimiento);
            pstmt.setString(3, genero);
            pstmt.setString(4, nacionalidad);
            pstmt.setString(5, numPasaporte);
            pstmt.setString(6, paisEmisorPas);
            pstmt.setString(7, correo);
            pstmt.setString(8, telefono);
            pstmt.setString(9, direccion);
            pstmt.setString(10, contactoEmer);
            pstmt.setString(11, relacionEmer);
            pstmt.setString(12, telefonoEmer);
            pstmt.setString(13, codigoReserva);
            pstmt.setInt(14, idUsuario);
            
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Datos del pasajero guardados correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo guardar los datos del pasajero.");
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los datos del pasajero: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }
}
