/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.dao;

/**
 *
 * @author julia
 */
import tikape.domain.Annos;
import tikape.database.Database;
import tikape.dao.Dao;
import java.util.*;
import java.sql.*;
public class AnnosDao implements Dao<Annos, Integer>{
    private Database database;
    
    public AnnosDao(Database database) {
        this.database = database;
    }
    @Override
    public Annos findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Annos WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Annos a = new Annos(rs.getInt("id"), rs.getString("nimi"));

        stmt.close();
        rs.close();

        conn.close();

        return a;
    }

    @Override
    public List<Annos> findAll() throws SQLException {
	List<Annos> annokset;
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Annos");
            ResultSet rs = stmt.executeQuery();
            annokset = new ArrayList<>();
            while (rs.next()) {
                Annos a = new Annos(rs.getInt("id"), rs.getString("nimi"));
 
                annokset.add(a);
            }
            stmt.close();
            rs.close();
        }
 
        return annokset;
    }

    @Override
    public Annos saveOrUpdate(Annos object) throws SQLException {
        if (object.getId() == null) {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Annos"
                + " (nimi)"
                + " VALUES (?)");
            stmt.setString(1, object.getNimi());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            return object;
        } else {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE Annos SET "
                + " nimi = ? WHERE id = ?");
            stmt.setString(1, object.getNimi());
            stmt.setInt(2, object.getId());
 
            stmt.executeUpdate();
 
            stmt.close();
            conn.close();
 
            return object;
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Annos WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
}
