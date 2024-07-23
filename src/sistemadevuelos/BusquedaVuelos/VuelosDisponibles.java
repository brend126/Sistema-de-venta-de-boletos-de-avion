/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sistemadevuelos.BusquedaVuelos;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import sistemadevuelos.FondoPanel;
import sistemadevuelos.conexion.ConexionDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sistemadevuelos.ReservaPasaje.Ticket;

public class VuelosDisponibles extends JFrame {

    private FondoPanel fondo3 = new FondoPanel();
   public VuelosDisponibles(){
       
   }

  // Constructor para inicializar y mostrar los resultados de búsqueda
    public VuelosDisponibles(String origen, String destino, String fechaDeIda, String pasajeros) {
        this.setContentPane(fondo3);
        initComponents(); // No modificamos este método directamente
        this.setLocationRelativeTo(null); // Centrar el formulario en pantalla

        // Obtenemos el modelo existente de resultadosTable
        DefaultTableModel model = (DefaultTableModel) resultadosTable.getModel();

        // Llenar la tabla con los resultados de la búsqueda y verificar disponibilidad
        boolean vuelosDisponibles = false;

        try {
            vuelosDisponibles = llenarTabla(origen, destino, fechaDeIda, pasajeros, model);
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción y mostrar mensaje de error
            JOptionPane.showMessageDialog(this, "Ocurrió un error al buscar vuelos disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Verificar si hay vuelos disponibles
        if (!vuelosDisponibles) {
            // Mostrar un mensaje indicando que no hay vuelos disponibles
            JOptionPane.showMessageDialog(this, "No hay vuelos disponibles para los criterios de búsqueda especificados.", "Vuelos no disponibles", JOptionPane.INFORMATION_MESSAGE);

            // Cerrar la ventana actual
            dispose();
        } else {
            // Añadir un listener para detectar la selección de filas
            resultadosTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        int selectedRow = resultadosTable.getSelectedRow();
                        if (selectedRow != -1) {
                            // Obtener datos de la fila seleccionada
                            Object origen = model.getValueAt(selectedRow, 0);
                            Object destino = model.getValueAt(selectedRow, 1);
                            Object fechaDeIda = model.getValueAt(selectedRow, 2);
                            int cantidadPasajeros = Integer.parseInt(pasajeros);
                            double precio = (double) model.getValueAt(selectedRow, 3);
                            String horarioSalida = (String) model.getValueAt(selectedRow, 4);
                            String horarioLlegada = (String) model.getValueAt(selectedRow, 5);

                            // Actualizar disponibilidad
                            try {
                                actualizarDisponibilidad(origen.toString(), destino.toString(), fechaDeIda.toString(), cantidadPasajeros);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Error al actualizar la disponibilidad del vuelo.", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                            // Abrir la ventana de detalles con los datos del vuelo seleccionado
                            DetalleVuelo detalleVuelo = new DetalleVuelo(origen, destino, fechaDeIda, cantidadPasajeros, precio, horarioSalida, horarioLlegada);
                            detalleVuelo.setVisible(true);

                            // Cerrar la ventana actual
                            dispose();
                        }
                    }
                }
            });
        }
    }

   

    private boolean llenarTabla(String origen, String destino, String fechaDeIda, String pasajeros, DefaultTableModel model) throws SQLException {
        String query = "SELECT Origen, Destino, FechaDeIda, Precio, HorarioSalida, HorarioLlegada " +
                       "FROM vuelosdisponibles " +
                       "WHERE Origen = ? AND Destino = ? AND FechaDeIda = ? AND CantidadDePasajesDisponibles >= ?";

        ConexionDB conexionDB = new ConexionDB();

        try (Connection connection = (Connection) conexionDB.conectar();
             PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query)) {

            statement.setString(1, origen);
            statement.setString(2, destino);
            statement.setString(3, fechaDeIda);
            statement.setInt(4, Integer.parseInt(pasajeros));

            ResultSet resultSet = statement.executeQuery();

            // Verificar si hay resultados en el ResultSet
            boolean hayResultados = false;

            while (resultSet.next()) {
                hayResultados = true; // Si entra aquí, significa que hay resultados
                String origenResult = resultSet.getString("Origen");
                String destinoResult = resultSet.getString("Destino");
                String fechaDeIdaResult = resultSet.getString("FechaDeIda");
                double precioResult = resultSet.getDouble("Precio");
                String horarioSalidaResult = resultSet.getString("HorarioSalida");
                String horarioLlegadaResult = resultSet.getString("HorarioLlegada");

                model.addRow(new Object[]{origenResult, destinoResult, fechaDeIdaResult, precioResult, horarioSalidaResult, horarioLlegadaResult});
            }

            return hayResultados; // Retornar si hay resultados o no

        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Propagar la excepción para ser manejada en el constructor
        }
    }

    private void actualizarDisponibilidad(String origen, String destino, String fechaDeIda, int cantidadPasajeros) throws SQLException {
        String updateQuery = "UPDATE vuelosdisponibles " +
                             "SET CantidadDePasajesDisponibles = CantidadDePasajesDisponibles - ? " +
                             "WHERE Origen = ? AND Destino = ? AND FechaDeIda = ? AND CantidadDePasajesDisponibles >= ?";

        ConexionDB conexionDB = new ConexionDB();

        try (Connection connection = (Connection) conexionDB.conectar();
             PreparedStatement statement = (PreparedStatement) connection.prepareStatement(updateQuery)) {

            statement.setInt(1, cantidadPasajeros);
            statement.setString(2, origen);
            statement.setString(3, destino);
            statement.setString(4, fechaDeIda);
            statement.setInt(5, cantidadPasajeros);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No se pudo actualizar la disponibilidad. Verifique que los criterios de búsqueda sean correctos.");
            }
        }
    }


   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new FondoPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultadosTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(1260, 986));

        jPanel1.setPreferredSize(new java.awt.Dimension(1260, 986));

        resultadosTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Origen", "Destino", "Fecha de Ida", "Precio", "Horario Salida", "Horario Llegada"
            }
        ));
        jScrollPane2.setViewportView(resultadosTable);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 100)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 202, 85));
        jLabel2.setText("←");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setText("Vuelos disponibles:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addGap(383, 383, 383)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(133, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1013, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(125, 125, 125))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(87, 87, 87)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(308, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        //Para volver a busqueda haciendo click en el logo
        Busqueda busqueda = new Busqueda();

        // Hacer visible el JFrame Busqueda
        busqueda.setVisible(true);

        // Cerrar el JFrame actual
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable resultadosTable;
    // End of variables declaration//GEN-END:variables
}
