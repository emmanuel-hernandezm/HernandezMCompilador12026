package compilador.modelos;


public class ListaLexemas {
    
    // Operadores Aritmeticos
    public static final int MAS = 03;
    public static final int MENOS = 05;
    public static final int MULT = 06;
    public static final int DIV = 11;
    public static final int IGUAL = 12;

    // Operadores Relacionales 50-100
    public static final int COMPARA = 54;
    public static final int DIF = 551;
    public static final int MENOR_QUE = 56;
    public static final int MAYOR_QUE = 58;
    public static final int MENOR_IGUAL = 60;
    public static final int MAYOR_IGUAL = 66;

    // Simbolos especiales 101-1000
    public static final int COMA = 100;
    public static final int PUNTO_COMA = 200;
    public static final int PUNTO = 276;
    public static final int ABRE_PARENT = 300;
    public static final int CIERRA_PARENT = 475;   
}
