package com.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PR112cat {

    public static void main(String[] args) {
        // Comprobar que se ha pasado un argumento
        if (args.length != 1) {
            System.out.println("Cal proporcionar la ruta d'un fitxer de text com a argument.");
            return;
        }

        String path = args[0];
        llegirFitxer(path);
    }

    public static void llegirFitxer(String path) {
        File fitxer = new File(path);

        // Comprobar si el path es un directorio
        if (fitxer.isDirectory()) {
            System.out.println("El path no correspon a un arxiu, sinó a una carpeta.");
            return;
        }

        // Comprobar si el fitxer existeix
        if (!fitxer.exists()) {
            System.out.println("El fitxer no existeix o no és accessible.");
            return;
        }

        // Leer el contenido del fitxer
        try (BufferedReader reader = new BufferedReader(new FileReader(fitxer))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error en llegir el fitxer: " + e.getMessage());
        }
    }
}
