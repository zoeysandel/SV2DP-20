import java.util.List;
import java.sql.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Get a connection to database
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "root");
            ReizigerDAO rdao = new ReizigerDAOsql(conn);
//            // 2. Create a statement
//            Statement stmt = conn.createStatement();
//            // 3. Execute SQL query
//            ResultSet res = stmt.executeQuery("SELECT * FROM reiziger");
//            // 4. Process the result set
//            while (res.next()) {
//                System.out.println(res.getString("voorletters") + ", " + res.getString("achternaam") + ", " + res.getString("geboortedatum"));
//            }
            testReizigerDAO(rdao);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", LocalDate.of(1981, 03, 14));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.println("Momenteel bevat de database: " + reizigers.size() + " reizigers");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println("Na delete bevat de database: " + reizigers.size() + " reizigers");
    }
}
