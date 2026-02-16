package compilador.vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaCompilador extends JFrame {

    private JTextArea areaArchivo; 
    private JTextArea areaResultado;  
    private JMenuItem itemAbrir;
    private JMenuItem itemEncontrar;    

    public VistaCompilador() {
        super("Compilador - Lenguajes y Autómatas");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuArchivo = new JMenu("Archivo");
        itemAbrir = new JMenuItem("Abrir");
        menuArchivo.add(itemAbrir);
        
        JMenu menuProcesos = new JMenu("Procesos");
        itemEncontrar = new JMenuItem("Encontrar");
        menuProcesos.add(itemEncontrar);

        menuBar.add(menuArchivo);
        menuBar.add(menuProcesos);
        setJMenuBar(menuBar);

        
        areaArchivo = new JTextArea();
        areaArchivo.setFont(new Font("Consolas", Font.PLAIN, 14));
        areaArchivo.setEditable(false);
        JScrollPane scrollAreaArch = new JScrollPane(areaArchivo);
        scrollAreaArch.setBorder(BorderFactory.createTitledBorder("Contenido del Archivo"));
        add(scrollAreaArch, BorderLayout.CENTER);

        areaResultado = new JTextArea();
        areaResultado.setFont(new Font("Consolas", Font.PLAIN, 12));
        areaResultado.setForeground(Color.BLUE); 
        areaResultado.setEditable(false);
        JScrollPane scrollResultados = new JScrollPane(areaResultado);
        scrollResultados.setBorder(BorderFactory.createTitledBorder("Resultado"));
        scrollResultados.setPreferredSize(new Dimension(900,150));
        add(scrollResultados, BorderLayout.SOUTH);

    }


    public String getArchivo() {
        return areaArchivo.getText();
    }

    public void setArchivo(String texto) {
        areaArchivo.setText(texto);
    }

    public void mostrarResultado(String texto) {
        areaResultado.append(texto + "\n"); 
        areaResultado.setCaretPosition(areaResultado.getDocument().getLength());
    }
    
    public void limpiarResultado() {
        areaResultado.setText("");
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Compilación", JOptionPane.ERROR_MESSAGE);
    }

    public void setListeners(ActionListener listener) {
        itemAbrir.addActionListener(listener);
        itemEncontrar.addActionListener(listener);
    }
    
    public JMenuItem getItemAbrir() { return itemAbrir; }
    public JMenuItem getItemEncontrar() { return itemEncontrar; }
}
