import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ReizigerDAOsql implements ReizigerDAO {
    Connection conn;

    public ReizigerDAOsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES(?, ?, ?, ?, ?)");

            stmt.setInt(1, reiziger.getId());
            stmt.setString(2, reiziger.getVoorletters());
            stmt.setString(3, reiziger.getTussenvoegsel());
            stmt.setString(4, reiziger.getAchternaam());
            stmt.setDate(5, Date.valueOf(reiziger.getGeboortedatum()));
            stmt.executeUpdate();
            return true;

        }
        catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE reiziger SET voorletters =?, tussenvoegsel =?, achternaam =?, geboortedatum =? WHERE id =?");
            stmt.setString(1, reiziger.getVoorletters());
            stmt.setString(2, reiziger.getTussenvoegsel());
            stmt.setString(3, reiziger.getAchternaam());
            stmt.setDate(4, Date.valueOf(reiziger.getGeboortedatum()));
            stmt.setInt(5, reiziger.getId());
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM reiziger WHERE reiziger_id =?");
            stmt.setInt(1, reiziger.getId());
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id =?");
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            while(res.next()) {
                int reiziger_id = res.getInt("reiziger_id");
                String voorletters = res.getString("voorletters");
                String tussenvoegsel = res.getString("tussenvoegsel");
                String achternaam = res.getString("achternaam");
                LocalDate geboortedatum = res.getDate("geboortedatum").toLocalDate();

                Reiziger r = new Reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum);
                return r;
            }
        }
        catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return null;
    }

    @Override
    public Reiziger findByGbdatum(String datum) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum =?");
            stmt.setString(1, datum);
            ResultSet res = stmt.executeQuery();
            while(res.next()) {
                int id = res.getInt("reiziger_id");
                String voorletters = res.getString("voorletters");
                String tussenvoegsel = res.getString("tussenvoegsel");
                String achternaam = res.getString("achternaam");
                LocalDate geboortedatum = res.getDate("geboortedatum").toLocalDate();

                Reiziger r = new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum);
                return r;
            }

        }
        catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        return null;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> reizigers = new ArrayList<Reiziger>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM reiziger");
            while (res.next()) {
                Reiziger r = new Reiziger(res.getInt("reiziger_id"),
                        res.getString("voorletters"),
                        res.getString("tussenvoegsel"),
                        res.getString("achternaam"),
                        res.getDate("geboortedatum").toLocalDate());
                reizigers.add(r);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return reizigers;
    }
}
