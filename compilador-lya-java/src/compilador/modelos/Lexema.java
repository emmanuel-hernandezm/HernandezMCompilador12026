package compilador.modelos;

public class Lexema {
    private String dato;
    private String tipo;
    private int token;
    private int linea;
    private int columna;

    public Lexema(String dato, String tipo, int token, int linea, int columna) {
        this.dato = dato;
        this.tipo = tipo;
        this.token = token;
        this.linea = linea;
        this.columna = columna;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }
    
    public int getColumna() {
        return columna;
    }
    
    public void setColumna(int columna) {
        this.columna = columna;
    }

    @Override
    public String toString() {
        return String.format("%-20s %-8s %d [L:%d, C:%d]", dato, tipo, token, linea, columna);
    }
    
    
}
