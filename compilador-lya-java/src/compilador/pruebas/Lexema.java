/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.pruebas;

/**
 *
 * @author rober
 */
public class Lexema {
    
    private int token;
    private String dato;
    private String tipo;
    //constructor 

    public Lexema(String dato, String tipo) {
        this.dato = dato;
        this.tipo = tipo;
        if(tipo.equals("id")){
            this.token = Estaticos.esReservada(dato);
            this.tipo = (token==100)?tipo:"pr";//ternario 
        }else {
            this.token=0;
        }
       // this.token = token;
    }
    
    //getts y setts
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
    
    //to String
    
    @Override
    public String toString() {
        return "{" + dato + "\t" + tipo + "\t" + token +"\t" + '}';
    }
    
    
}
