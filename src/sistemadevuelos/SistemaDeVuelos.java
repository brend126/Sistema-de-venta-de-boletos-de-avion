/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistemadevuelos;

import javax.swing.SwingUtilities;
import sistemadevuelos.Home.Inicio;
import sistemadevuelos.conexion.ConexionDB;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author Brenda
 */
public class SistemaDeVuelos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // para el fondo
        Inicio i = new Inicio();
        
        i.setVisible(true);
        
         i.setLocationRelativeTo(null);
        
    }
    
}
