package com.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.project.excepcions.IOFitxerExcepcio;
import com.project.objectes.PR122persona;

public class PR122main {
    // Ruta per al fitxer de dades serialitzades
    private static String filePath = System.getProperty("user.dir") + "/data/PR122persones.dat";

    public static void main(String[] args) {
        // Creació d'objectes PR122persona
        List<PR122persona> persones = new ArrayList<>();
        persones.add(new PR122persona("Maria", "López", 36));
        persones.add(new PR122persona("Gustavo", "Ponts", 63));
        persones.add(new PR122persona("Irene", "Sales", 54));

        try {
            // Serialitzar les persones a un fitxer
            serialitzarPersones(persones);
            // Deserialitzar les persones del fitxer i mostrar-les
            List<PR122persona> deserialitzades = deserialitzarPersones();
            deserialitzades.forEach(System.out::println);  // Mostra la informació per pantalla
        } catch (IOFitxerExcepcio e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Mètode per serialitzar la llista de persones
    public static void serialitzarPersones(List<PR122persona> persones) throws IOFitxerExcepcio {
        File file = new File(filePath);
        file.getParentFile().mkdirs(); // Crea els directoris si no existeixen
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(persones);  // Serialitza la llista de persones
            System.out.println("Persones serialitzades correctament.");
        } catch (IOException e) {
            throw new IOFitxerExcepcio("Error al serialitzar el fitxer.", e);
        }
    }

    // Mètode per deserialitzar la llista de persones
    public static List<PR122persona> deserialitzarPersones() throws IOFitxerExcepcio {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOFitxerExcepcio("El fitxer no existeix.");
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<PR122persona>) ois.readObject();  // Deserialitza la llista de persones
        } catch (IOException e) {
            throw new IOFitxerExcepcio("Error al llegir el fitxer.", e);
        } catch (ClassNotFoundException e) {
            throw new IOFitxerExcepcio("Classe no trobada durant la deserialització.", e);
        }
    }

    // Getter i Setter per a filePath (opcional)
    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String newFilePath) {
        filePath = newFilePath;
    }
}
