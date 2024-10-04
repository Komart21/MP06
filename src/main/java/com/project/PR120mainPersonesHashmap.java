package com.project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.project.excepcions.IOFitxerExcepcio;

public class PR120mainPersonesHashmap {
    private static String filePath = System.getProperty("user.dir") + "/data/PR120persones.dat";

    public static void main(String[] args) {
        HashMap<String, Integer> persones = new HashMap<>();
        persones.put("Anna", 25);
        persones.put("Bernat", 30);
        persones.put("Carla", 22);
        persones.put("David", 35);
        persones.put("Elena", 28);

        try {
            escriurePersones(persones);
            llegirPersones();
        } catch (IOFitxerExcepcio e) {
            System.err.println("Error en treballar amb el fitxer: " + e.getMessage());
        }
    }

    // Getter per a filePath
    public static String getFilePath() {
        return filePath;
    }

    // Setter per a filePath
    public static void setFilePath(String newFilePath) {
        filePath = newFilePath;
    }

    // Mètode per escriure les persones al fitxer
    public static void escriurePersones(HashMap<String, Integer> persones) throws IOFitxerExcepcio {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filePath))) {
            for (Map.Entry<String, Integer> entry : persones.entrySet()) {
                dos.writeUTF(entry.getKey());  // Escribir el nombre
                dos.writeInt(entry.getValue()); // Escribir la edad
            }
        } catch (FileNotFoundException e) {
            throw new IOFitxerExcepcio("El fitxer no s'ha pogut crear o no existeix.", e);
        } catch (IOException e) {
            throw new IOFitxerExcepcio("Error en escriure al fitxer.", e);
        }
    }

    // Mètode per llegir les persones des del fitxer
    public static void llegirPersones() throws IOFitxerExcepcio {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filePath))) {
            while (true) {
                String nom = dis.readUTF();    // Llegir el nom
                int edat = dis.readInt();      // Llegir la edat
                System.out.println(nom + ": " + edat + " anys"); // Mostrar per pantalla
            }
        } catch (EOFException e) {
            // Final del fitxer assolit, no es fa res
        } catch (FileNotFoundException e) {
            throw new IOFitxerExcepcio("El fitxer no existeix.", e);
        } catch (IOException e) {
            throw new IOFitxerExcepcio("Error en llegir del fitxer.", e);
        }
    }
}
