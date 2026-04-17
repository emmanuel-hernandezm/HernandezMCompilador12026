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
    private boolean freeWilly = false;
    
     private int obtenerValorReservada(String palabra) {
        switch(palabra.toLowerCase()) {
            case "const": return ListaLexemas.CONST;
            case "var": return ListaLexemas.VAR;
            case "procedure": return ListaLexemas.PROCED;
            case "begin": return ListaLexemas.BEGIN;
            case "end": return ListaLexemas.END;
            case "write": return ListaLexemas.WRITE;
            case "read": return ListaLexemas.READ;
            case "call": return ListaLexemas.CALL;
            case "if": return ListaLexemas.IF;
            case "then": return ListaLexemas.THEN;
            case "while": return ListaLexemas.WHILE;
            case "do": return ListaLexemas.DO;
            case "for": return ListaLexemas.FOR;
            case "to": return ListaLexemas.TO;
            case "downto": return ListaLexemas.DOWN;
            default: return -1;
        }
    }
    
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
    
    public boolean hayCaracteresInvalidos(){
        return freeWilly;
    }

    public String buscaEnTexto(String texto) {
        freeWilly = false;
        
        String regex = "([a-zA-Z]\\w*)|" +
                       "(0|[1-9]\\d*)|" +
                       "(\\+|-|\\*|/)|" +
                       "(==|!=|<=|>=|<|>|=)|" +
                       "(;|,|\\.|\\(|\\))|" +
                       "(\\S)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(texto);

        List<Lexema> tokens = new ArrayList<>();
        
        String[] etiquetas = {"", "[I] ", "[N] ", "[OA] ", "[OR] ", "[SE] ", "[E] "};
        
        int cantidadGrupos = matcher.groupCount();
        int posError = 1;
        
        while (matcher.find()) {
            for (int i = 1; i <= cantidadGrupos; i++) {
                if (matcher.group(i) != null) {
                    String grupo = matcher.group(i);
                    String etiqueta = etiquetas[i];
                    
                    int codigo = -1;
                    
                    if(i == 1){
                        int reservada = obtenerValorReservada(grupo);
                        if(reservada != -1){
                            etiqueta = "[PR]";
                            codigo = reservada;
                        }else{
                            etiqueta = "[I]";
                            codigo = ListaLexemas.ID;
                        }
                    }
                    if(i == 6){
                        freeWilly = true;
                        etiqueta = "[E]";
                        codigo = -1;
                       // tokens.add("Error en la posicion [" + posError + "] - Caracter no reconocido: " + matcher.group(i));
                    } else {
                        //tokens.add(etiquetas[i] + matcher.group(i));
                    }
                    tokens.add(new Lexema(grupo, etiqueta, codigo));
                    posError ++;
                    break;
                }
            }
        }
        
        System.out.println("Tokens encontrados: ");
        for  (Lexema e : tokens){
            System.out.println(e);
        }


        StringBuilder reporte = new StringBuilder();
        /*if (freeWilly) {
            reporte.append("No es posible compilar: \n\n");
            for (String linea : tokens) {
                if (linea.startsWith("Error")) {
                    reporte.append(linea).append("\n");
                }
            }
        } else {
            reporte.append("COMPILACIÓN EXITOSA\n\n");
            for (int i = 0; i < tokens.size(); i++) {
                reporte.append((i + 1)).append(". ").append(tokens.get(i)).append("\n");
            }
        }*/
        
        return reporte.toString();
    }
}
