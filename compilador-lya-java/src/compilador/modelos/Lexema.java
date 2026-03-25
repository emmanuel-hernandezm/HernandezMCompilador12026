package compilador.modelos;

public class Lexema {
    private String dato;
    private String tipo;

    public Lexema(String dato, String tipo) {
        this.dato = dato;
        this.tipo = tipo;
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

    @Override
    public String toString() {
        return "[" + dato + "\t" + tipo + "]";
    }
    
    
}
