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
       
        if (e.getSource() == vista.getItemAbrir()) {
            File dirInicial = obtenerDirectorio();
            JFileChooser selector = new JFileChooser();
            if(dirInicial.exists() && dirInicial.isDirectory()){
                selector.setCurrentDirectory(dirInicial);
            }
            int resultado = selector.showOpenDialog(vista);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivo = selector.getSelectedFile();
                try {
                    String contenido = modelo.leeContenidoArchivo(archivo);
                    vista.setArchivo(contenido);
                    vista.limpiarResultado();
                    vista.mostrarResultado("Archivo cargado con éxito: " + archivo.getName());
                    
                    vista.getItemLexico().setEnabled(true);
                    vista.getItemSintactico().setEnabled(false);
                    
                } catch (IOException ex) {
                    vista.mostrarError("Error I/O: " + ex.getMessage());
                }
            }
        }
        
        else if (e.getSource() == vista.getItemLexico()) {
            String texto = vista.getArchivo();
            
            if (texto.isEmpty()) {
                vista.mostrarError("No hay texto para analizar");
                return;
            }
            
            String resultadoLexico = modelo.buscaEnTexto(texto); 
            vista.limpiarResultado();
            vista.mostrarResultado(resultadoLexico);

            if (!modelo.hayCaracteresInvalidos()) {
                vista.getItemSintactico().setEnabled(true);
            } else {
                vista.getItemSintactico().setEnabled(false);
            }
        }

        else if (e.getSource() == vista.getItemSintactico()) {
        }
    }   
    private File obtenerDirectorio(){
        String userDir = System.getProperty("user.dir");
        File testDir = new File(userDir, "test");
        return testDir;
        
    } 
}