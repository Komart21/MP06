package com.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class PR114linies {

    public static void main(String[] args) {
        String camiFitxer = System.getProperty("user.dir") + "/data/numeros.txt";
        
        // Llama al método que genera y escribe números aleatorios
        generarNumerosAleatoris(camiFitxer);
    }

    public static void generarNumerosAleatoris(String camiFitxer) {
        // Crea un objeto Random para generar números aleatorios
        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(camiFitxer, StandardCharsets.UTF_8))) {
            for (int i = 0; i < 10; i++) {
                // Número aleatorio entre 0 y 99
                int numeroAleatorio = random.nextInt(100); 
                writer.write(Integer.toString(numeroAleatorio));
                
                if (i < 9) { 
                    writer.newLine();
                }
            }
            System.out.println("10 números aleatorios han sido escritos en " + camiFitxer);
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo: " + e.getMessage());
        }
    }
}
