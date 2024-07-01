/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadevuelos;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author ACER
 */
// Clase para poner el fondo
public class FondoPanel extends JPanel {
    private Image imagen;

    @Override
    public void paintComponent(Graphics g) {
        if (imagen == null) {
            imagen = new ImageIcon(getClass().getResource("/sistemadevuelos/Imagenes/fondo.jpg")).getImage();
        }
        
        int anchoImagen = imagen.getWidth(null);
        int altoImagen = imagen.getHeight(null);
        int anchoPanel = getWidth();
        int altoPanel = getHeight();
        
        double escalaAncho = 1.0 * anchoPanel / anchoImagen;
        double escalaAlto = 1.0 * altoPanel / altoImagen;
        double escala = Math.max(escalaAncho, escalaAlto);
        
        int nuevoAncho = (int) (escala * anchoImagen);
        int nuevoAlto = (int) (escala * altoImagen);
        
        int x = (anchoPanel - nuevoAncho) / 2;
        int y = (altoPanel - nuevoAlto) / 2;
        
        g.drawImage(imagen, x, y, nuevoAncho, nuevoAlto, this);
        
        setOpaque(false);  // Esto asegura que el fondo del panel sea transparente
        
        // Pintar los componentes después de la imagen de fondo
        super.paintComponent(g);
    }
}