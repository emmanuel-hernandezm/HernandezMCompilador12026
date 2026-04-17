/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.pruebas;

import java.util.ArrayList;

/**
 *
 * @author rober
 */
public class Estaticos {
    
    public static final int MAX_CALIF=70;
    public static final int MIN_CALIF=0;
    public static final int MIN_APROB= 70;
    public static final int MENOR_QUE=16;
    public static final int MAYOR_QUE=33;
    public static String[] palabrasReservadas={"const", "begin","for","while"};   
    public static ArrayList <String> palabraRes= new ArrayList <String>();
    
    public static void llenaPalRes(){
        palabraRes.add("do");
        palabraRes.add("then");
    }
    
    public static String mensaje(){
        String mensaje ="Hola, soy un metodo estatico";
        return mensaje;
    }
    
    public String mensajeNS(){
        String mensaje= "Hola, soy un metoo no estatico";
        return mensaje;
    }
    
    public static int esReservada(String palabra){
        int res=-1;
        String[] reservadas={"const", "if", "var", "while"};
        palabra=palabra.toLowerCase();
        for (int i = 0; i < reservadas.length; i++) {
            if(palabra.equals(reservadas[i])){
                return i+1;
            }
            
        }
        return res;
    }
    
}
