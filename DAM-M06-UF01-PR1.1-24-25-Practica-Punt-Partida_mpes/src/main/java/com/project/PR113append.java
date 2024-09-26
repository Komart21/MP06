package com.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PR113append {

    // Método para agregar frases al archivo
    public static void afegirFrases(String camiFitxer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(camiFitxer, true))) {
            writer.write("I can only show you the door");
            writer.newLine();
            writer.write("You're the one that has to walk through it");
            writer.newLine();
            writer.newLine(); // Añadir línea en blanco al final
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método principal para ejecutar la clase
    public static void main(String[] args) {
        // Definir la ruta del archivo
        String camiFitxer = System.getProperty("user.dir") + "/data/frasesMatrix.txt";

        // Llamar al método para agregar frases
        afegirFrases(camiFitxer);

        System.out.println("Frases afegides correctament a " + camiFitxer);
    }
}
