import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO {
    public boolean save(Reiziger reiziger);
    public boolean update(Reiziger reiziger) throws SQLException;
    public boolean delete(Reiziger reiziger);

    public Reiziger findById(int id);
    public Reiziger findByGbdatum(String datum);
    public List<Reiziger> findAll() throws SQLException;
}
