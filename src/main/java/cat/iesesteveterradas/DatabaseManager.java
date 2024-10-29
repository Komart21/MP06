package cat.iesesteveterradas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:forhonor.db";  // Ruta de la base de dades SQLite

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public void initializeDatabase(Connection connection) throws SQLException {
        // Crear les taules si no existeixen
        String createFaccioTable = """
            CREATE TABLE IF NOT EXISTS Faccio (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom VARCHAR(15) NOT NULL,
                resum VARCHAR(500)
            );
        """;

        String createPersonatgeTable = """
            CREATE TABLE IF NOT EXISTS Personatge (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom VARCHAR(15) NOT NULL,
                atac REAL,
                defensa REAL,
                idFaccio INTEGER,
                FOREIGN KEY(idFaccio) REFERENCES Faccio(id)
            );
        """;

        connection.createStatement().execute(createFaccioTable);
        connection.createStatement().execute(createPersonatgeTable);
    }

    public void populateDatabase(Connection connection) throws SQLException {
        // Inserir dades inicials només si les taules estan buides
        String checkFaccioTable = "SELECT COUNT(*) AS count FROM Faccio";
        var rs = connection.createStatement().executeQuery(checkFaccioTable);
        if (rs.next() && rs.getInt("count") == 0) {
            // Dades de faccions
            connection.createStatement().execute("INSERT INTO Faccio (nom, resum) VALUES ('Cavallers', 'Though seen as a single group...')");
            connection.createStatement().execute("INSERT INTO Faccio (nom, resum) VALUES ('Vikings', 'The Vikings are a loose coalition...')");
            connection.createStatement().execute("INSERT INTO Faccio (nom, resum) VALUES ('Samurais', 'The Samurai are the most unified...')");
            
            // Dades de personatges
            connection.createStatement().execute("INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Warden', 1, 3, 1)");
            connection.createStatement().execute("INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Raider', 3, 3, 2)");
            connection.createStatement().execute("INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Kensei', 3, 2, 3)");
        }
    }

    public void showFactions(Connection connection) throws SQLException {
        FaccioDAO faccioDAO = new FaccioDAO();
        List<Faccio> faccions = faccioDAO.getAllFaccions(connection);

        System.out.println("\n--- Faccions ---");
        for (Faccio faccio : faccions) {
            System.out.println("ID: " + faccio.getId() + ", Nom: " + faccio.getNom() + ", Resum: " + faccio.getResum());
        }
    }

    public void showCharacters(Connection connection) throws SQLException {
        PersonatgeDAO personatgeDAO = new PersonatgeDAO();
        List<Personatge> characters = personatgeDAO.getAllCharacters(connection);

        System.out.println("\n--- Personatges ---");
        for (Personatge character : characters) {
            System.out.println("ID: " + character.getId() + ", Nom: " + character.getNom() + 
                               ", Atac: " + character.getAtac() + ", Defensa: " + character.getDefensa() + 
                               ", Faccio ID: " + character.getIdFaccio());
        }
    }

    public void showCharactersByFaction(Connection connection, int idFaccio) throws SQLException {
        PersonatgeDAO personatgeDAO = new PersonatgeDAO();
        List<Personatge> characters = personatgeDAO.getCharactersByFaction(connection, idFaccio);

        System.out.println("\n--- Personatges per Faccio ---");
        for (Personatge character : characters) {
            System.out.println("Nom: " + character.getNom() + ", Atac: " + character.getAtac() + 
                               ", Defensa: " + character.getDefensa());
        }
    }

    public void showBestAttackerByFaction(Connection connection, int idFaccio) throws SQLException {
        PersonatgeDAO personatgeDAO = new PersonatgeDAO();
        Personatge bestAttacker = personatgeDAO.getBestAttackerByFaction(connection, idFaccio);

        if (bestAttacker != null) {
            System.out.println("\n--- Millor Atacant per Faccio ---");
            System.out.println("Nom: " + bestAttacker.getNom() + ", Atac: " + bestAttacker.getAtac());
        } else {
            System.out.println("No s'han trobat personatges per aquesta facció.");
        }
    }

    public void showBestDefenderByFaction(Connection connection, int idFaccio) throws SQLException {
        PersonatgeDAO personatgeDAO = new PersonatgeDAO();
        Personatge bestDefender = personatgeDAO.getBestDefenderByFaction(connection, idFaccio);

        if (bestDefender != null) {
            System.out.println("\n--- Millor Defensor per Faccio ---");
            System.out.println("Nom: " + bestDefender.getNom() + ", Defensa: " + bestDefender.getDefensa());
        } else {
            System.out.println("No s'han trobat personatges per aquesta facció.");
        }
    }
}
