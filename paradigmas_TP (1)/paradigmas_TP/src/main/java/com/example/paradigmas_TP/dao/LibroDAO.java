package com.example.paradigmas_TP.dao;

import com.example.paradigmas_TP.model.Libro;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class LibroDAO {
    private Connection connection;

    public LibroDAO() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    // Insertar un nuevo libro en la base de datos
    public void insertarLibro(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, genero, año, estado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getGenero());
            ps.setInt(4, libro.getAño());
            ps.setString(5, libro.getEstado());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Consultar todos los libros
    public List<Libro> obtenerTodosLosLibros() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id_libro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setGenero(rs.getString("genero"));
                libro.setAño(rs.getInt("año"));
                libro.setEstado(rs.getString("estado"));
                libros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    // Consultar libros por estado (disponible o prestado)
    public List<Libro> obtenerLibrosPorEstado(String estado) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE estado = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Libro libro = new Libro();
                    libro.setId(rs.getInt("id_libro"));
                    libro.setTitulo(rs.getString("titulo"));
                    libro.setAutor(rs.getString("autor"));
                    libro.setGenero(rs.getString("genero"));
                    libro.setAño(rs.getInt("año"));
                    libro.setEstado(rs.getString("estado"));
                    libros.add(libro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    // Actualizar un libro
    public void actualizarLibro(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, genero = ?, año = ?, estado = ? WHERE id_libro = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getGenero());
            ps.setInt(4, libro.getAño());
            ps.setString(5, libro.getEstado());
            ps.setInt(6, libro.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar un libro por ID
    public void eliminarLibro(int idLibro) {
        String sql = "DELETE FROM libros WHERE id_libro = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idLibro);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}