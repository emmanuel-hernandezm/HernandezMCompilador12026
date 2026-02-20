package compilador.modelos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModeloCompilador {


    public String leeContenidoArchivo(File archivo) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        }
        return contenido.toString();
    }

    public String buscaEnTexto(String texto) {
        String regex = "[a-zA_Z]\\w*";
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(texto);
        
        List<String> identificadores = new ArrayList<>();
        
        while (matcher.find()){
            identificadores.add(matcher.group());
        }
        
        StringBuilder reporte = new StringBuilder();
        reporte.append("Total de identificadores: ").append(identificadores.size()).append("\n");
        
        for (int i = 0; i <  identificadores.size(); i++) {
            reporte.append((i + 1)).append(". ").append(identificadores.get(i)).append("\n");
        }
        return reporte.toString();
    }
}
