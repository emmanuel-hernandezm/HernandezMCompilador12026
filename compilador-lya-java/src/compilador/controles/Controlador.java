package compilador.controles;


import compilador.modelos.ModeloCompilador;
import compilador.vistas.VistaCompilador;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Controlador implements ActionListener {

    private final VistaCompilador vista;
    private final ModeloCompilador modelo;

    public Controlador(VistaCompilador vista, ModeloCompilador modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.vista.setListeners(this); 
    }

    public void iniciar() {
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        // 1. Lógica para Abrir Archivo
        if (e.getSource() == vista.getItemAbrir()) {
            JFileChooser selector = new JFileChooser();
            int resultado = selector.showOpenDialog(vista);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivo = selector.getSelectedFile();
                try {
                    String contenido = modelo.leeContenidoArchivo(archivo);
                    vista.setArchivo(contenido);
                    vista.limpiarResultado();
                    vista.mostrarResultado("Archivo cargado con éxito: " + archivo.getName());
                    
                    // Al abrir el archivo, se libera el botón de Léxico
                    vista.getItemLexico().setEnabled(true);
                    // Aseguramos que el sintáctico siga bloqueado hasta que pase el léxico
                    vista.getItemSintactico().setEnabled(false);
                    
                } catch (IOException ex) {
                    vista.mostrarError("Error I/O: " + ex.getMessage());
                }
            }
        }
        
        // 2. Lógica para Análisis Léxico (Reemplaza a itemEncontrar)
        else if (e.getSource() == vista.getItemLexico()) {
            String texto = vista.getArchivo();
            
            if (texto.isEmpty()) {
                vista.mostrarError("No hay texto para analizar");
                return;
            }
            
            // Aquí llamarías a tu lógica de tokens (ej. números hexadecimales, ciclos for, etc.)
            String resultadoLexico = modelo.buscaEnTexto(texto); 
            vista.limpiarResultado();
            vista.mostrarResultado("--- Resultado Análisis Léxico ---\n" + resultadoLexico);

            // Si no hubo errores (asumiendo que tu modelo lo valida), se libera el Sintáctico
            // Por ahora lo activamos directamente tras el clic
            vista.getItemSintactico().setEnabled(true);
        }

        // 3. Lógica para Análisis Sintáctico
        else if (e.getSource() == vista.getItemSintactico()) {
            vista.mostrarResultado("\nIniciando Análisis Sintáctico...");
            // Aquí iría la lógica de estructura formal que estás preparando
        }
    }
}