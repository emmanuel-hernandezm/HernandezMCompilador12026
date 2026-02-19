package compilador.pruebas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcesoEncontrar{

    public static void main(String[] args) {

        
         System.out.println("3n (un) mUnD0 {d0nd3} l45-p4l4br45 n0s13mpr3 + 50n [l0] qu3 p4r3c3n,\n" +
                              "l45_l3tr45 {s3} m3zcl4n-c0n num3r05 y cr34n (un) c0d1g0+c4s1_53cr3t0.");
        String textoEjemplo = "3n (un) mUnD0 {d0nd3} l45-p4l4br45 n0s13mpr3 + 50n [l0] qu3 p4r3c3n,\n" +
                              "l45_l3tr45 {s3} m3zcl4n-c0n num3r05 y cr34n (un) c0d1g0+c4s1_53cr3t0.";
        analizarTexto(textoEjemplo);
       
    }
    
public static void analizarTexto(String texto) {

        String regex = "\\b[a-zA-Z]\\w*";
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(texto);

        List<String> identificadores = new ArrayList<>();

        while (matcher.find()) {
            identificadores.add(matcher.group());
        }

        System.out.println("Total encontrados: " + identificadores.size());
        
        for (int i = 0; i < identificadores.size(); i++) {
            System.out.println((i + 1) + ". " + identificadores.get(i));
        }
    }
}


