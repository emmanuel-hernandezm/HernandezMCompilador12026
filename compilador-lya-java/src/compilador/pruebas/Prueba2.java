/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.pruebas;

/**
 *
 * @author rober
 */
import compilador.modelos.Lexema;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Prueba2 {

    public static void main(String[] args) {
        String regex = "([A-Za-z]\\w*)|" +//
                "([1-9]\\d*|0)|" +//
                "(==|!=|<=|>=|<|>|=)|" +//
                "(\\+|-|\\*|/)|" +//
                "(\\.|,|;|\\(|\\)|:)|"+//
                "([^\\s])" +//
                "(.)";
        String texto = "const x=100,y=10.21;var a___2,b_c_2_t,059\n" + //
                "a__2<<===!==xy-yx==>=>>100000000066\n" + //
                "if-while+for*then/do%to|downto\n" + //
                "(x_nueva(y_vieja))(b_c_2_t.\n" + //
                "fin_2";
        Pattern patron = Pattern.compile(regex);
        Matcher matcher = patron.matcher(texto);
        ArrayList<Lexema> lexemas = new ArrayList<>();
        while (matcher.find()) {
            String lexemaEncontrado = matcher.group();

            if (matcher.group(1) != null) { // Es un Identificador
                //Agregar a identifcadores
                lexemas.add(new Lexema(lexemaEncontrado,"ID"));
                continue;//hcae el codigo mas elegante
            }
            if (matcher.group(2) != null) { //Es un numero
                lexemas.add(new Lexema(lexemaEncontrado,"NUM"));
                continue;
            }
            

            if (matcher.group(6) != null) {
                lexemas.add(new Lexema(lexemaEncontrado,"Error"));
            }
        }
        for (Lexema e : lexemas) {
            System.out.println(e);

        }

    }
}