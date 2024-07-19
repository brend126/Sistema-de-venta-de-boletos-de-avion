/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadevuelos.InicioSesion;

/**
 *
 * @author ACER
 */
public class FlightDetails {
    private static FlightDetails instance;
    private int cantidadPasajes;

    // Constructor privado para evitar instanciación
    private FlightDetails() {}

    // Método para obtener la instancia única de la clase
    public static FlightDetails getInstance() {
        if (instance == null) {
            instance = new FlightDetails();
        }
        return instance;
    }

    // Métodos getter y setter para cantidadPasajes
    public int getCantidadPasajes() {
        return cantidadPasajes;
    }

    public void setCantidadPasajes(int cantidadPasajes) {
        this.cantidadPasajes = cantidadPasajes;
    }

    public void decrementarCantidadPasajes() {
        if (cantidadPasajes > 0) {
            cantidadPasajes--;
        }
    }
}
