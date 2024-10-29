package cat.iesesteveterradas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonatgeDAO {

    // Método para obtener todos los personajes
    public List<Personatge> getAllCharacters(Connection connection) throws SQLException {
        List<Personatge> characters = new ArrayList<>();
        String query = "SELECT * FROM Personatge";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Personatge character = new Personatge();
                character.setId(rs.getInt("id"));
                character.setNom(rs.getString("nom"));
                character.setAtac(rs.getFloat("atac"));
                character.setDefensa(rs.getFloat("defensa"));
                character.setIdFaccio(rs.getInt("idFaccio"));
                characters.add(character);
            }
        }
        return characters;
    }

    // Método ya existente para obtener personajes por facción
    public List<Personatge> getCharactersByFaction(Connection connection, int idFaccio) throws SQLException {
        List<Personatge> characters = new ArrayList<>();
        String query = "SELECT * FROM Personatge WHERE idFaccio = " + idFaccio;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Personatge character = new Personatge();
                character.setId(rs.getInt("id"));
                character.setNom(rs.getString("nom"));
                character.setAtac(rs.getFloat("atac"));
                character.setDefensa(rs.getFloat("defensa"));
                character.setIdFaccio(rs.getInt("idFaccio"));
                characters.add(character);
            }
        }
        return characters;
    }

    // Métodos ya existentes para obtener el mejor atacante y defensor por facción
    public Personatge getBestAttackerByFaction(Connection connection, int idFaccio) throws SQLException {
        String query = "SELECT * FROM Personatge WHERE idFaccio = " + idFaccio + " ORDER BY atac DESC LIMIT 1";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                Personatge character = new Personatge();
                character.setId(rs.getInt("id"));
                character.setNom(rs.getString("nom"));
                character.setAtac(rs.getFloat("atac"));
                character.setDefensa(rs.getFloat("defensa"));
                character.setIdFaccio(rs.getInt("idFaccio"));
                return character;
            }
        }
        return null;
    }

    public Personatge getBestDefenderByFaction(Connection connection, int idFaccio) throws SQLException {
        String query = "SELECT * FROM Personatge WHERE idFaccio = " + idFaccio + " ORDER BY defensa DESC LIMIT 1";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                Personatge character = new Personatge();
                character.setId(rs.getInt("id"));
                character.setNom(rs.getString("nom"));
                character.setAtac(rs.getFloat("atac"));
                character.setDefensa(rs.getFloat("defensa"));
                character.setIdFaccio(rs.getInt("idFaccio"));
                return character;
            }
        }
        return null;
    }
}
