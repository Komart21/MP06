package com.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PR115cp {
    
    public static void main(String[] args) {
        // Comprovem que hi ha dues rutes passades com a arguments
        if (args.length != 2) {
            System.out.println("Ús: java PR115cp <ruta/origen.txt> <ruta/desti.txt>");
            return;
        }

        String rutaOrigen = args[0];
        String rutaDestinacio = args[1];

        // Executem el mètode que copia l'arxiu
        copiarArxiu(rutaOrigen, rutaDestinacio);
    }

    public static void copiarArxiu(String rutaOrigen, String rutaDestinacio) {
        File fitxerOrigen = new File(rutaOrigen);
        File fitxerDestinacio = new File(rutaDestinacio);

        // Comprovacions
        if (!fitxerOrigen.exists() || !fitxerOrigen.isFile()) {
            System.out.println("Error: El fitxer d'origen no existeix o no és un fitxer de text.");
            return;
        }

        if (fitxerDestinacio.exists()) {
            System.out.println("Advertència: L'arxiu de destinació ja existeix. Serà sobreescrit.");
        }

        // Fem una copia del contingut
        try (BufferedReader reader = new BufferedReader(new FileReader(fitxerOrigen, StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(new FileWriter(fitxerDestinacio, StandardCharsets.UTF_8))) {

            String linia;
            boolean ultimaLiniaBuida = false;

            while ((linia = reader.readLine()) != null) {
                writer.write(linia);
                writer.newLine();
                ultimaLiniaBuida = linia.isEmpty(); 
            }
            
            if (ultimaLiniaBuida) {
                writer.newLine();
            }

            System.out.println("Còpia realitzada correctament de " + rutaOrigen + " a " + rutaDestinacio);
        } catch (IOException e) {
            System.out.println("Error en la còpia: " + e.getMessage());
        }
    }
}
