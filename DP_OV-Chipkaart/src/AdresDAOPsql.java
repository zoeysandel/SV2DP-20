import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    Connection conn;

    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Adres adres) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO adres(adres_id, postcode, huisnummer, straat, woonplaats) VALUES(?, ?, ?, ?, ?)");

            stmt.setInt(1, adres.getId());
            stmt.setString(2, adres.getPostcode());
            stmt.setInt(3, adres.getHuisnummer());
            stmt.setString(4, adres.getStraat());
            stmt.setString(5, adres.getWoonplaats());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE adres SET postcode =?, huisnummer =?, straat =?, woonplaats =? WHERE adres_id =?");

            stmt.setString(1, adres.getPostcode());
            stmt.setInt(2, adres.getHuisnummer());
            stmt.setString(3, adres.getStraat());
            stmt.setString(4, adres.getWoonplaats());
            stmt.setInt(5, adres.getId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM adres WHERE adres_id =?");

            stmt.setInt(1, adres.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT a.* FROM adres AS a INNER JOIN reiziger AS r ON a.adres_id = r.reiziger_id");
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                int adres_id = res.getInt("adres_id");
                String postcode = res.getString("postcode");
                int huisnummer = res.getInt("huisnummer");
                String straat = res.getString("straat");
                String woonplaats = res.getString("woonplaats");

                Adres a = new Adres(adres_id, postcode, huisnummer, straat, woonplaats);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return null;
    }

    @Override
    public List<Adres> findAll() {
        List<Adres> adressen = new ArrayList<Adres>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM adres");
            while (res.next()) {
                Adres a = new Adres(res.getInt("adres_id"),
                        res.getString("postcode"),
                        res.getInt("huisnummer"),
                        res.getString("straat"),
                        res.getString("woonplaats"));
                adressen.add(a);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return adressen;
    }
}
