package com.project;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class PR124main {

    // Constants que defineixen l'estructura d'un registre
    private static final int ID_SIZE = 4; // Número de registre: 4 bytes
    private static final int NAME_MAX_BYTES = 40; // Nom: màxim 20 caràcters (40 bytes en UTF-8)
    private static final int GRADE_SIZE = 4; // Nota: 4 bytes (float)
    private static final int RECORD_SIZE = ID_SIZE + NAME_MAX_BYTES + GRADE_SIZE; // Longitud total d'un registre

    // Atribut per al path del fitxer
    private String filePath;

    private Scanner scanner = new Scanner(System.in);

    // Constructor per inicialitzar el path del fitxer
    public PR124main() {
        this.filePath = System.getProperty("user.dir") + "/data/PR124estudiants.dat"; // Valor per defecte
    }

    // Getter per al filePath
    public String getFilePath() {
        return filePath;
    }

    // Setter per al filePath
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public static void main(String[] args) {
        PR124main gestor = new PR124main();
        boolean sortir = false;

        while (!sortir) {
            try {
                gestor.mostrarMenu();
                int opcio = gestor.getOpcioMenu();

                switch (opcio) {
                    case 1 -> gestor.llistarEstudiants();
                    case 2 -> gestor.afegirEstudiant();
                    case 3 -> gestor.consultarNota();
                    case 4 -> gestor.actualitzarNota();
                    case 5 -> sortir = true;
                    default -> System.out.println("Opció no vàlida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Si us plau, introdueix un número vàlid.");
            } catch (IOException e) {
                System.out.println("Error en la manipulació del fitxer: " + e.getMessage());
            }
        }
    }

    // Mostrar menú d'opcions
    private void mostrarMenu() {
        System.out.println("\nMenú de Gestió d'Estudiants");
        System.out.println("1. Llistar estudiants");
        System.out.println("2. Afegir nou estudiant");
        System.out.println("3. Consultar nota d'un estudiant");
        System.out.println("4. Actualitzar nota d'un estudiant");
        System.out.println("5. Sortir");
        System.out.print("Selecciona una opció: ");
    }

    // Obtenir la selecció del menú
    private int getOpcioMenu() {
        return Integer.parseInt(scanner.nextLine());
    }

    // Mètode per llistar tots els estudiants
    public void llistarEstudiants() throws IOException {
        llistarEstudiantsFitxer();
    }

    // Mètode per afegir un nou estudiant
    public void afegirEstudiant() throws IOException {
        int registre = demanarRegistre();
        String nom = demanarNom();
        float nota = demanarNota();
        afegirEstudiantFitxer(registre, nom, nota);
    }

    // Mètode per consultar la nota
    public void consultarNota() throws IOException {
        int registre = demanarRegistre();
        consultarNotaFitxer(registre);
    }

    // Mètode per actualitzar la nota
    public void actualitzarNota() throws IOException {
        int registre = demanarRegistre();
        float novaNota = demanarNota();
        actualitzarNotaFitxer(registre, novaNota);
    }

    // Funcions per obtenir input de l'usuari
    private int demanarRegistre() {
        System.out.print("Introdueix el número de registre (enter positiu): ");
        int registre = Integer.parseInt(scanner.nextLine());
        if (registre < 0) {
            throw new IllegalArgumentException("El número de registre ha de ser positiu.");
        }
        return registre;
    }

    private String demanarNom() {
        System.out.print("Introdueix el nom (màxim 20 caràcters, depenent dels bytes UTF-8): ");
        return scanner.nextLine();
    }

    private float demanarNota() {
        System.out.print("Introdueix la nota (valor entre 0 i 10): ");
        float nota = Float.parseFloat(scanner.nextLine());
        if (nota < 0 || nota > 10) {
            throw new IllegalArgumentException("La nota ha de ser un valor entre 0 i 10.");
        }
        return nota;
    }

    // Mètode per trobar la posició d'un estudiant al fitxer segons el número de registre
    private long trobarPosicioRegistre(RandomAccessFile raf, int registreBuscat) throws IOException {
        long posicio = 0;
        raf.seek(0); // Anar al principi del fitxer

        while (raf.getFilePointer() < raf.length()) {
            int registre = raf.readInt();
            if (registre == registreBuscat) {
                return posicio;
            }
            posicio += RECORD_SIZE; // Avançar al següent registre
            raf.seek(posicio); // Posicionar al següent registre
        }

        return -1; // Si no es troba el registre
    }

    // Operacions amb fitxers
    // Mètode que manipula el fitxer i llista tots els estudiants (independent per al test)
    public void llistarEstudiantsFitxer() throws IOException {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            System.out.println("No hi ha estudiants registrats.");
            return;
        }

        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
            raf.seek(0);
            while (raf.getFilePointer() < raf.length()) {
                int registre = raf.readInt();
                String nom = llegirNom(raf);
                float nota = raf.readFloat();
                System.out.println("Registre: " + registre + ", Nom: " + nom + ", Nota: " + nota);
            }
        }
    }

    // Mètode que manipula el fitxer i afegeix l'estudiant
    public void afegirEstudiantFitxer(int registre, String nom, float nota) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "rw")) {
            raf.seek(raf.length()); // Posicionar al final del fitxer per afegir un nou registre
            raf.writeInt(registre); // Escriure el número de registre
            escriureNom(raf, nom); // Escriure el nom
            raf.writeFloat(nota); // Escriure la nota
            System.out.println("Estudiant afegit correctament.");
        }
    }

    // Mètode que manipula el fitxer i consulta la nota d'un estudiant
    public void consultarNotaFitxer(int registre) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
            long posicio = trobarPosicioRegistre(raf, registre);
            if (posicio == -1) {
                System.out.println("No s'ha trobat l'estudiant amb registre: " + registre);
                return;
            }

            raf.seek(posicio);
            raf.readInt(); // Llegir el registre (ja conegut)
            String nom = llegirNom(raf); // Llegir el nom
            float nota = raf.readFloat(); // Llegir la nota
            System.out.println("Registre: " + registre + ", Nom: " + nom + ", Nota: " + nota);
        }
    }

    // Mètode que manipula el fitxer i actualitza la nota d'un estudiant
    public void actualitzarNotaFitxer(int registre, float novaNota) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "rw")) {
            long posicio = trobarPosicioRegistre(raf, registre);
            if (posicio == -1) {
                System.out.println("No s'ha trobat l'estudiant amb registre: " + registre);
                return;
            }

            raf.seek(posicio + ID_SIZE + NAME_MAX_BYTES); // Posicionar per escriure la nova nota
            raf.writeFloat(novaNota); // Actualitzar la nota
            System.out.println("Nota actualitzada correctament.");
        }
    }

    // Funcions auxiliars per a la lectura i escriptura del nom amb UTF-8
    private String llegirNom(RandomAccessFile raf) throws IOException {
        byte[] nomBytes = new byte[NAME_MAX_BYTES];
        raf.readFully(nomBytes);
        return new String(nomBytes, StandardCharsets.UTF_8).trim(); // Convertir bytes a string i eliminar espais
    }

    private void escriureNom(RandomAccessFile raf, String nom) throws IOException {
        byte[] nomBytes = nom.getBytes(StandardCharsets.UTF_8);
        if (nomBytes.length > NAME_MAX_BYTES) {
            throw new IOException("El nom és massa llarg.");
        }

        raf.write(nomBytes); // Escriure el nom
        raf.write(new byte[NAME_MAX_BYTES - nomBytes.length]); // Completar amb espais si és més curt
    }
}
