package cat.iesesteveterradas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FaccioDAO {
    public List<Faccio> getAllFaccions(Connection connection) throws SQLException {
        List<Faccio> faccions = new ArrayList<>();
        String query = "SELECT * FROM Faccio";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Faccio faccio = new Faccio();
                faccio.setId(rs.getInt("id"));
                faccio.setNom(rs.getString("nom"));
                faccio.setResum(rs.getString("resum"));
                faccions.add(faccio);
            }
        }
        return faccions;
    }
}
