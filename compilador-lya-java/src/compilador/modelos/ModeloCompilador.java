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
        String regex = "([a-zA-Z]\\w*)|(0|[1-9]\\d*)|(\\+|-|\\*|/)|(==|!=|<=|>=|<|>|=)|(;|,|\\.|\\(|\\))|(\\S)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(texto);

        List<String> tokens = new ArrayList<>();
        String[] etiquetas = {"", "[I] ", "[N] ", "[OA] ", "[OR] ", "[SE] ", "[E] "};
        int cantidadGrupos = matcher.groupCount();
        
        while (matcher.find()) {
            for (int i = 1; i <= cantidadGrupos; i++) {
                if (matcher.group(i) != null) {
                    tokens.add(etiquetas[i] + matcher.group(i));
                    break;
                }
            }
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("Total encontrados: ").append(tokens.size()).append("\n\n");
        
        for (int i = 0; i < tokens.size(); i++) {
            reporte.append((i + 1)).append(". ").append(tokens.get(i)).append("\n");
        }
        
        return reporte.toString();
    }
}
