package cat.iesesteveterradas;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class PR210Honor {

    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        Scanner scanner = new Scanner(System.in);

        try {
            Connection connection = dbManager.connect();
            dbManager.initializeDatabase(connection);
            dbManager.populateDatabase(connection);

            // Menu para interactuar con el usuario
            while (true) {
                System.out.println("\n--- Menú ---");
                System.out.println("1. Mostrar taula Faccio");
                System.out.println("2. Mostrar taula Personatge");
                System.out.println("3. Mostrar personatges per facció");
                System.out.println("4. Mostrar el millor atacant per facció");
                System.out.println("5. Mostrar el millor defensor per facció");
                System.out.println("6. Sortir");
                System.out.print("Selecciona una opció: ");

                int opcio = scanner.nextInt();
                scanner.nextLine();  // Consumir el salto de línea

                switch (opcio) {
                    case 1:
                        dbManager.showFactions(connection);
                        break;
                    case 2:
                        dbManager.showCharacters(connection);
                        break;
                    case 3:
                        System.out.print("Introdueix l'ID de la facció: ");
                        int idFaccio = scanner.nextInt();
                        dbManager.showCharactersByFaction(connection, idFaccio);
                        break;
                    case 4:
                        System.out.print("Introdueix l'ID de la facció: ");
                        idFaccio = scanner.nextInt();
                        dbManager.showBestAttackerByFaction(connection, idFaccio);
                        break;
                    case 5:
                        System.out.print("Introdueix l'ID de la facció: ");
                        idFaccio = scanner.nextInt();
                        dbManager.showBestDefenderByFaction(connection, idFaccio);
                        break;
                    case 6:
                        System.out.println("Sortint del programa...");
                        connection.close();
                        return;  // Termina el programa
                    default:
                        System.out.println("Opció no vàlida, torna a intentar.");
                        break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Path obtenirPathFitxer() {
        return Paths.get(System.getProperty("user.dir"), "data", "bones_practiques_programacio.txt");
    }

    public static List<String> readFileContent(Path filePath) throws IOException {
        return Files.readAllLines(filePath);
    }
}
