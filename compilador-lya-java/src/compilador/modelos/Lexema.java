package compilador.modelos;

public class Lexema {
    private String dato;
    private String tipo;
    private int token;
    private int posError;

    public Lexema(String dato, String tipo, int token, int posicion) {
        this.dato = dato;
        this.tipo = tipo;
        this.token = token;
        this.posError = posicion;
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

    public int getPosError() {
        return posError;
    }

    public void setPosError(int posError) {
        this.posError = posError;
    }

    @Override
    public String toString() {
        return String.format("%-20s %-8s %d", dato, tipo, token);
    }
    
    
}
