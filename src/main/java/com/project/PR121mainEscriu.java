package com.project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.project.excepcions.IOFitxerExcepcio;
import com.project.objectes.PR121hashmap;

public class PR121mainEscriu {
    private static String filePath = System.getProperty("user.dir") + "/data/PR121HashMapData.ser";

    public static void main(String[] args) {
        PR121hashmap hashMap = new PR121hashmap();
        hashMap.getPersones().put("Anna", 25);
        hashMap.getPersones().put("Bernat", 30);
        hashMap.getPersones().put("Carla", 22);

        try {
            serialitzarHashMap(hashMap);
            System.out.println("Dades desades correctament al fitxer.");
        } catch (IOFitxerExcepcio e) {
            System.err.println("Error al desar l'arxiu: " + e.getMessage());
        }
    }

    public static void serialitzarHashMap(PR121hashmap hashMap) throws IOFitxerExcepcio {
        // Verificar si el directorio "data" existe, si no, crearlo
        File directory = new File(System.getProperty("user.dir") + "/data");
        if (!directory.exists()) {
            directory.mkdirs(); // Crear el directorio si no existe
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(hashMap);
        } catch (IOException e) {
            throw new IOFitxerExcepcio("Error al serialitzar el fitxer.", e);
        }
    }
}
