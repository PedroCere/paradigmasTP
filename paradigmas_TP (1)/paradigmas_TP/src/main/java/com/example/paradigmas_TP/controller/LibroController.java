package com.example.paradigmas_TP.controller;


import com.example.paradigmas_TP.dao.LibroDAO;
import com.example.paradigmas_TP.model.Libro;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class LibroController {
    private LibroDAO libroDAO;

    public LibroController() {
        libroDAO = new LibroDAO();
    }

    // Método para registrar un nuevo libro
    public void registrarLibro(Libro libro) {
        libro.setEstado("disponible");  // Inicialmente el libro está disponible
        libroDAO.insertarLibro(libro);
    }

    // Obtener todos los libros
    public List<Libro> obtenerTodosLosLibros() {
        return libroDAO.obtenerTodosLosLibros();
    }

    // Obtener libros por estado
    public List<Libro> obtenerLibrosPorEstado(String estado) {
        return libroDAO.obtenerLibrosPorEstado(estado);
    }

    // Actualizar un libro
    public void actualizarLibro(Libro libro) {
        libroDAO.actualizarLibro(libro);
    }

    // Eliminar un libro
    public void eliminarLibro(int idLibro) {
        libroDAO.eliminarLibro(idLibro);
    }
}