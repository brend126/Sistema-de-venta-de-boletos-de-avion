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

import sistemadevuelos.BusquedaVuelos.Busqueda;



public class SistemaDeVuelos {

   
    public static void main(String[] args) {
        // para el fondo de inicio
        Inicio i = new Inicio();
        
        i.setVisible(true);
        
         i.setLocationRelativeTo(null);
         
        // para el fondo de busqueda
        Busqueda b = new Busqueda();
        
       
        
    }
    
}
