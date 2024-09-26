package com.project;

import java.io.File;
import java.io.IOException;

public class PR111FileManagement {

    public static void main(String[] args) {
        // Definir la ruta base
        String rutaCarpeta = System.getProperty("user.dir") + "/data/pr111/myFiles";
        gestionarArxius(rutaCarpeta);
    }

    /**
     * Mètode que gestiona la creació, renombrament, llistat i eliminació d'arxius.
     *
     * @param rutaCarpeta La ruta base on es crearan els directoris i arxius.
     */
    public static void gestionarArxius(String rutaCarpeta) {
        // 1. Creem carpeta myFiles
        File carpeta = new File(rutaCarpeta);
        if (!carpeta.exists()) {
            if (carpeta.mkdirs()) {
                System.out.println("Carpeta creada correctament: " + rutaCarpeta);
            } else {
                System.out.println("Error en crear la carpeta.");
                return;
            }
        } else {
            System.out.println("La carpeta ja existeix: " + rutaCarpeta);
        }

        // 2. Crear els arxius file1.txt i file2.txt
        File file1 = new File(rutaCarpeta + "/file1.txt");
        File file2 = new File(rutaCarpeta + "/file2.txt");
        try {
            if (file1.createNewFile()) {
                System.out.println("file1.txt creat correctament.");
            } else {
                System.out.println("file1.txt ja existeix.");
            }
            if (file2.createNewFile()) {
                System.out.println("file2.txt creat correctament.");
            } else {
                System.out.println("file2.txt ja existeix.");
            }
        } catch (IOException e) {
            System.out.println("Error en crear els arxius: " + e.getMessage());
        }

        // 3. Renombrar file2.txt a renamedFile.txt
        File renamedFile = new File(rutaCarpeta + "/renamedFile.txt");
        if (file2.renameTo(renamedFile)) {
            System.out.println("file2.txt renombrat correctament a renamedFile.txt.");
        } else {
            System.out.println("Error en renombrar file2.txt.");
        }

        // 4. Mostrar els arxius dins de la carpeta myFiles
        mostrarArxius(rutaCarpeta);

        // 5. Eliminar l'arxiu file1.txt
        if (file1.exists()) {
            if (file1.delete()) {
                System.out.println("file1.txt eliminat correctament.");
            } else {
                System.out.println("Error en eliminar file1.txt.");
            }
        } else {
            System.out.println("file1.txt no existeix per eliminar.");
        }

        // 6. Tornar a mostrar els arxius dins de la carpeta myFiles
        mostrarArxius(rutaCarpeta);
    }

    /**
     * Funció per mostrar el llistat dels arxius d'una carpeta.
     *
     * @param rutaCarpeta 
     */
    public static void mostrarArxius(String rutaCarpeta) {
        File carpeta = new File(rutaCarpeta);
        File[] arxius = carpeta.listFiles();
        if (arxius != null && arxius.length > 0) {
            System.out.println("Els arxius de la carpeta són:");
            for (File arxiu : arxius) {
                System.out.println("- " + arxiu.getName());
            }
        } else {
            System.out.println("No hi ha arxius a la carpeta.");
        }
    }
}
