package com.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PR113sobreescriu {
    public static void escriureFrases(String camiFitxer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(camiFitxer))) {
            writer.write("I can only show you the door");
            writer.newLine();
            writer.write("You're the one that has to walk through it");
            writer.newLine();
            writer.newLine(); // Añadir línea en blanco al final
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
