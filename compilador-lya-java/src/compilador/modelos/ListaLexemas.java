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
    
    // Palabras reservadas 1001 - 1150
    public static final int CONST = 1001;
    public static final int VAR = 1010;
    public static final int PROCED = 1020;
    public static final int BEGIN = 1030;
    public static final int END = 1040;
    public static final int WRITE = 1050;
    public static final int READ = 1060;
    public static final int CALL = 1070;
    public static final int IF = 1080;
    public static final int THEN = 1090;
    public static final int WHILE = 1100;
    public static final int DO = 1110;
    public static final int FOR = 1120;
    public static final int TO = 1130;
    public static final int DOWN = 1140;
    
    // Genericos 1151 - 1200
    public static final int ID = 1160;
    public static final int NUM = 1170;





}
