/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadevuelos.InicioSesion;

/**
 *
 * @author ACER
 */
public class SessionManager {
    private static int idUsuarioActual = -1; // Inicialmente ningún usuario conectado

    // Método para establecer el ID del usuario actual
    public static void setIdUsuarioActual(int id) {
        idUsuarioActual = id;
    }

    // Método para obtener el ID del usuario actual
    public static int getIdUsuarioActual() {
        return idUsuarioActual;
    }

    // Método para verificar si hay un usuario conectado
    public static boolean usuarioEstaConectado() {
        return idUsuarioActual != -1;
    }

    // Método para cerrar la sesión del usuario
    public static void cerrarSesion() {
        idUsuarioActual = -1;
        // Aquí podrías agregar lógica adicional para limpiar cualquier otra información de sesión
    }
}
