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
        String regex = "([a-zA-Z]\\w*)|(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(texto);

        List<String> Tokens = new ArrayList<>();

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                Tokens.add("[I] " + matcher.group(1));
            } else if (matcher.group(2) != null) {
                Tokens.add("[N] " + matcher.group(2));
            }
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("Total encontrados: ").append(Tokens.size()).append("\n\n");
        
        for (int i = 0; i < Tokens.size(); i++) {
            reporte.append((i + 1)).append(". ").append(Tokens.get(i)).append("\n");
        }
        
        return reporte.toString();
    }
}
