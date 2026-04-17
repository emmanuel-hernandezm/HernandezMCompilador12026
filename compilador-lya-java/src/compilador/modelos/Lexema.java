package compilador.modelos;

public class Lexema {
    private String dato;
    private String tipo;
    private int token;

    public Lexema(String dato, String tipo, int token) {
        this.dato = dato;
        this.tipo = tipo;
        this.token = token;
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
    
    

    @Override
    public String toString() {
        return "" + dato + "\t" + tipo + "\t" + token + "\t";
    }
    
    
}
