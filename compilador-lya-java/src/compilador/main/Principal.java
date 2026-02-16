package compilador.main;

import compilador.controles.Controlador;
import compilador.modelos.ModeloCompilador;
import compilador.vistas.VistaCompilador;
import javax.swing.SwingUtilities;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModeloCompilador modelo = new ModeloCompilador();
            VistaCompilador vista = new VistaCompilador();
            Controlador controlador = new Controlador(vista, modelo);
            
            controlador.iniciar();
        });
    }
}
