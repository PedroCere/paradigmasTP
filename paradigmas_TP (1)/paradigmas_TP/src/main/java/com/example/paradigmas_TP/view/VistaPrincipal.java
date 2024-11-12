package com.example.paradigmas_TP.view;

import com.example.paradigmas_TP.controller.LibroController;
import com.example.paradigmas_TP.model.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VistaPrincipal extends JFrame {
    private JTextField txtTitulo, txtAutor, txtGenero, txtAño;
    private JButton btnAgregarLibro, btnFiltrarDisponible, btnFiltrarPrestado;
    private JTable tableLibros;
    private LibroController libroController;
    private DefaultTableModel tableModel;

    public VistaPrincipal() {
        libroController = new LibroController();
        setTitle("Sistema de Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Configurar formulario de registro de libros
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panel.add(txtTitulo);

        panel.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panel.add(txtAutor);

        panel.add(new JLabel("Género:"));
        txtGenero = new JTextField();
        panel.add(txtGenero);

        panel.add(new JLabel("Año:"));
        txtAño = new JTextField();
        panel.add(txtAño);

        btnAgregarLibro = new JButton("Agregar Libro");
        btnAgregarLibro.addActionListener(e -> agregarLibro());
        panel.add(btnAgregarLibro);

        btnFiltrarDisponible = new JButton("Mostrar Disponibles");
        btnFiltrarDisponible.addActionListener(e -> mostrarLibros("disponible"));
        panel.add(btnFiltrarDisponible);

        btnFiltrarPrestado = new JButton("Mostrar Prestados");
        btnFiltrarPrestado.addActionListener(e -> mostrarLibros("prestado"));
        panel.add(btnFiltrarPrestado);

        add(panel, BorderLayout.NORTH);

        // Configuración del JTable y modelo de tabla
        tableModel = new DefaultTableModel(new String[] {"ID", "Título", "Autor", "Género", "Año", "Estado"}, 0);
        tableLibros = new JTable(tableModel);
        add(new JScrollPane(tableLibros), BorderLayout.CENTER);

        setVisible(true);
        mostrarLibros("");  // Cargar todos los libros al iniciar la vista
    }

    private void agregarLibro() {
        try {
            Libro libro = new Libro();
            libro.setTitulo(txtTitulo.getText());
            libro.setAutor(txtAutor.getText());
            libro.setGenero(txtGenero.getText());
            libro.setAño(Integer.parseInt(txtAño.getText()));
            libroController.registrarLibro(libro);

            // Limpiar campos y actualizar tabla
            txtTitulo.setText("");
            txtAutor.setText("");
            txtGenero.setText("");
            txtAño.setText("");
            mostrarLibros("");  // Mostrar todos los libros después de agregar uno nuevo
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un año válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarLibros(String estado) {
        List<Libro> libros;
        if (estado.isEmpty()) {
            libros = libroController.obtenerTodosLosLibros();
        } else {
            libros = libroController.obtenerLibrosPorEstado(estado);
        }

        // Limpiar el modelo de la tabla
        tableModel.setRowCount(0);

        // Llenar el modelo de la tabla con los datos de los libros
        for (Libro libro : libros) {
            Object[] rowData = {
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getGenero(),
                    libro.getAño(),
                    libro.getEstado()
            };
            tableModel.addRow(rowData);
        }
    }
}
