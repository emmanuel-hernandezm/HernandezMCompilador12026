/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.pruebas;

/**
 *
 * @author rober
 */
public class PruebaEstaticos {
    
    public static void main (String[] args){
        System.out.println(Estaticos.mensaje());    
        Estaticos e= new Estaticos();
        System.out.println(e.mensajeNS());
        
        if(80>Estaticos.MIN_CALIF ){
            System.out.println("Hay una calificacionmayor a 0");
        }
        
        int calif=Math.max(Estaticos.MIN_CALIF,Estaticos.MAX_CALIF);{
        calif=(int)(Math.random()*100);
        System.out.println("La calificacion:"+calif);
        if(calif>Estaticos.MIN_APROB){
            System.out.println("Hay una calificacion aprobatoria");
        }
        if(calif < Estaticos.MAX_CALIF){
            System.out.println("Hay una calificacion no aprobatoria");
        }
        System.out.println(Estaticos.MAYOR_QUE);
        System.out.println(Estaticos.MENOR_QUE);
        //Una clase nueva que se llame lexemas en la cual pondremos los numeros que pusimos en los lexemas (mediante los static) exceptuando las palabreas reservadas 
        System.out.println(Estaticos.palabrasReservadas[0]); //los metodos variables e instancias se escriben con minusculas
        System.out.println(Estaticos.palabrasReservadas[3]);
        
        if(Estaticos.palabrasReservadas[0].equals("CONST")){
         System.out.println("Son iguales");   //en el proyecto hay que definir se se hara diferencia en mayusculas/minusculas o no 
        }
        
        Estaticos.llenaPalRes();
        
        if(Estaticos.palabraRes.contains("do")){
          System.out.println("Si lo contiene");  
        }//fin if contains
        
       // Lexema l1= new Lexema("Computadora", "Portatil");
        //System.out.println(l1);
        
    }

    }
    
}
