
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 */
package com.mycompany.holamundo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 *
 * @author Mi PC
 */
public class HolaMundo {

    public static void main(String[] args) throws SQLException {
        System.out.println("===Ejecutando operaciones en tabla Alumnos ===");
        // Obtener la conexión
        Connection cn = ConexionDB.getConexion();
        if (cn == null) {
            System.out.println("No se puede establecer la conexión");
            return;
        }

        // Declarar los objetos SQL
        PreparedStatement psInsertar = null;
        PreparedStatement psSeleccionar = null;
        PreparedStatement psActualizar = null;
        PreparedStatement psEliminar = null;
        ResultSet rs = null;

        try {

            // ==============================
            // 1. INSERTAR
            // ==============================
            System.out.println("\n1. Intentando insertar nuevo Alumno...");
            String psInsert =
                    "INSERT INTO Alumnos(nombre, correo) VALUES(?,?)";
            psInsertar = cn.prepareStatement(psInsert);
            psInsertar.setString(1, "Luka Modric");
            psInsertar.setString(2, "modric.luka@uth.hn");
            int filasAfectadas = psInsertar.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Alumno insertado con exito");
            }

            // ==============================
            // 2. SELECCIONAR
            // ==============================
            System.out.println("\n2. Consulta de lista de Alumnos en la Base de Datos");
            String sqlSelect =
                    "SELECT id, nombre, correo FROM Alumnos";
            psSeleccionar = cn.prepareStatement(sqlSelect);
            rs = psSeleccionar.executeQuery();
            System.out.println("\n=======================================================");
            System.out.println("   ID       | Nombre              | Correo");
            System.out.println("=======================================================");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                System.out.printf(
                        "%-3d | %-20s | %s%n",
                        id,
                        nombre,
                        correo
                );
            }
            System.out.println("=======================================================");
            rs.close();
            rs = null;

            // ==============================
            // 3. ACTUALIZAR
            // ==============================
            System.out.println("\n3. Intentando actualizar un Alumno...");
            String sqlActualizar =
                    "UPDATE Alumnos SET nombre = ?, correo = ? WHERE id = ?";
            psActualizar = cn.prepareStatement(sqlActualizar);
            psActualizar.setString(1, "Pirlo");
            psActualizar.setString(2, "Ronaldo.jr@uth.hn");
            psActualizar.setInt(3, 13);
            int filasActualizadas =
                    psActualizar.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Alumno actualizado con exito");
            } else {
                System.out.println("No se encontro el Alumno para actualizar.");
            }

            // ==============================
            // 4. ELIMINAR
            // ==============================
            System.out.println("\n4. Intentando eliminar un Alumno...");
            String sqlEliminar =
                    "DELETE FROM Alumnos WHERE id = ?";
            psEliminar = cn.prepareStatement(sqlEliminar);
            psEliminar.setInt(1, 10);
            int filasEliminadas =
                    psEliminar.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Alumno eliminado con exito");
            } else {
                System.out.println("No se encontro el Alumno para eliminar.");
            }
        } catch (SQLException e) {
            System.err.println(
                    "Ocurrio un error con la base de datos: "
                    + e.getMessage()
            );

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (psInsertar != null) {
                psInsertar.close();
            }
            if (psSeleccionar != null) {
                psSeleccionar.close();
            }
            if (psActualizar != null) {
                psActualizar.close();
            }
            if (psEliminar != null) {
                psEliminar.close();
            }
            ConexionDB.cerrarConexion();
        }
    }
}

