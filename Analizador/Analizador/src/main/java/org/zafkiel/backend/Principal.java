package org.zafkiel.backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Principal {
    public static void main(String[] args) throws Exception {
        String ruta1 = "C:/Users/david/Documents/CUNOC/Ingenieria en Ciencias y Sistemas/2024/01-Senestre/Compiladores 01/Lab - Compi 01/Proyecto01/Analizador/Analizador/src/main/java/org/zafkiel/backend/Lexer.flex";
        String ruta2 = "C:/Users/david/Documents/CUNOC/Ingenieria en Ciencias y Sistemas/2024/01-Senestre/Compiladores 01/Lab - Compi 01/Proyecto01/Analizador/Analizador/src/main/java/org/zafkiel/backend/LexerCup.flex";
        String[] rutaS = {"-parser", "Sintax", "C:/Users/david/Documents/CUNOC/Ingenieria en Ciencias y Sistemas/2024/01-Senestre/Compiladores 01/Lab - Compi 01/Proyecto01/Analizador/Analizador/src/main/java/org/zafkiel/backend/Sintax.cup"};
        generar(ruta1, ruta2, rutaS);
    }
    public static void generar(String ruta1, String ruta2, String[] rutaS) throws IOException, Exception{
        File archivo;
        archivo = new File(ruta1);
        JFlex.Main.generate(archivo);
        archivo = new File(ruta2);
        JFlex.Main.generate(archivo);
        java_cup.Main.main(rutaS);

        Path rutaSym = Paths.get("C:/Users/david/Documents/CUNOC/Ingenieria en Ciencias y Sistemas/2024/01-Senestre/Compiladores 01/Lab - Compi 01/Proyecto01/Analizador/Analizador/src/main/java/org/zafkiel/backend/sym.java");
        if (Files.exists(rutaSym)) {
            Files.delete(rutaSym);
        }
        Files.move(
                Paths.get("C:/Users/david/Documents/CUNOC/Ingenieria en Ciencias y Sistemas/2024/01-Senestre/Compiladores 01/Lab - Compi 01/Proyecto01/Analizador/Analizador/sym.java"),
                Paths.get("C:/Users/david/Documents/CUNOC/Ingenieria en Ciencias y Sistemas/2024/01-Senestre/Compiladores 01/Lab - Compi 01/Proyecto01/Analizador/Analizador/src/main/java/org/zafkiel/backend/sym.java")
        );
        Path rutaSin = Paths.get("C:/Users/david/Documents/CUNOC/Ingenieria en Ciencias y Sistemas/2024/01-Senestre/Compiladores 01/Lab - Compi 01/Proyecto01/Analizador/Analizador/src/main/java/org/zafkiel/backend/Sintax.java");
        if (Files.exists(rutaSin)) {
            Files.delete(rutaSin);
        }
        Files.move(
                Paths.get("C:/Users/david/Documents/CUNOC/Ingenieria en Ciencias y Sistemas/2024/01-Senestre/Compiladores 01/Lab - Compi 01/Proyecto01/Analizador/Analizador/Sintax.java"),
                Paths.get("C:/Users/david/Documents/CUNOC/Ingenieria en Ciencias y Sistemas/2024/01-Senestre/Compiladores 01/Lab - Compi 01/Proyecto01/Analizador/Analizador/src/main/java/org/zafkiel/backend/Sintax.java")
        );
    }

        //String ruta = "C:/Users/david/Documents/CUNOC/Ingenieria en Ciencias y Sistemas/2024/01-Senestre/Compiladores 01/Lab - Compi 01/Proyecto01/Analizador/Analizador/src/main/java/org/zafkiel/backend/Lexer.flex";

}
