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
    private List<Lexema> tokens;
    
    private String[] clasificarGrupo(int grupo, String lexema) {
        switch (grupo) {
            case 1:
                int reservada = obtenerValorReservada(lexema);
                if (reservada != -1) {
                    return new String[]{"[PR]", String.valueOf(reservada)};
                } else {
                    return new String[]{"[I]", String.valueOf(ListaLexemas.ID)};
                }
            case 2:
                return new String[]{"[N]", String.valueOf(ListaLexemas.NUM)};
            case 3:
                int valorOA = obtenerValorOA(lexema);
                return new String[]{"[OA]", String.valueOf(valorOA)};
            case 4:
                int valorOR = obtenerValorOR(lexema);
                if(lexema.equals("=")){
                    return new String[]{"[OAs]", String.valueOf(ListaLexemas.IGUAL)};
                }
                return new String[]{"[OR]", String.valueOf(valorOR)};
            case 5:
                int valorSE = obtenerValorSE(lexema);
                return new String[]{"[SE]", String.valueOf(valorSE)};
            case 6: // Error
                return new String[]{"[E]", "-1"};
            default:
                return new String[]{"[?]", "-1"};
        }
    }
    
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
     
     private int obtenerValorOA(String op) {
        switch(op) {
            case "+": return ListaLexemas.MAS;
            case "-": return ListaLexemas.MENOS;
            case "*": return ListaLexemas.MULT;
            case "/": return ListaLexemas.DIV;
            default: return -1;
        }
    }
     
     private int obtenerValorOR(String op) {
        switch(op) {
            case "==": return ListaLexemas.COMPARA;
            case "!=": return ListaLexemas.DIF;
            case "<": return ListaLexemas.MENOR_QUE;
            case ">": return ListaLexemas.MAYOR_QUE;
            case "<=": return ListaLexemas.MENOR_IGUAL;
            case ">=": return ListaLexemas.MAYOR_IGUAL;
            default: return -1;
        }
    }
     
     private int obtenerValorSE(String sim) {
        switch(sim) {
            case ",": return ListaLexemas.COMA;
            case ";": return ListaLexemas.PUNTO_COMA;
            case ".": return ListaLexemas.PUNTO;
            case "(": return ListaLexemas.ABRE_PARENT;
            case ")": return ListaLexemas.CIERRA_PARENT;
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

        tokens = new ArrayList<>();
        
        int cantidadGrupos = matcher.groupCount();
        int posicion = 0;
        
        while (matcher.find()) {
            for (int i = 1; i <= cantidadGrupos; i++) {
                if (matcher.group(i) != null) {
                    String grupo = matcher.group(i);
                    String[] clasificacion = clasificarGrupo(i, grupo);
                    String tipo = clasificacion[0];
                    
                    int codigo = Integer.parseInt(clasificacion[1]);
                    posicion ++;
                    
                    if(i == 6) freeWilly = true;
                    tokens.add(new Lexema(grupo, tipo, codigo, posicion));
                    break;
                }
            }
        }
        
        System.out.println("Tokens encontrados: ");
        System.out.printf("%-20s %-8s %s\n", "LEXEMA", "TIPO", "TOKEN");
        for  (Lexema e : tokens){
            System.out.println(e);
        }
        System.out.println("\n\n");

        StringBuilder reporte = new StringBuilder();
        if (freeWilly) {
            reporte.append("No es posible compilar: \n\n");
            for (Lexema e : tokens) {
                if (e.getTipo().equals("[E]")) {
                    int posError = e.getPosError();
                    reporte.append("Error en la posicion [").append(posError).append("] - Caracter no reconocido: ").append(e.getDato()).append("\n");
                }
            }
        } else {
            reporte.append("COMPILACIÓN EXITOSA\n\n");
            int linea = 1;
            for (Lexema e : tokens) {
                reporte.append(linea).append(". ").append(e.getTipo() + "    ").append(e.getDato()).append("\n");
                linea++;
            }
        }
        return reporte.toString();
    }
}
