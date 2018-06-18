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
import tikape.domain.RaakaAine;
import tikape.domain.Tilasto;
import tikape.database.Database;
import tikape.dao.Dao;
import java.util.*;
import java.sql.*;

public class RaakaAineDao implements Dao<RaakaAine, Integer>{
    private Database database;
    
    public RaakaAineDao(Database database) {
        this.database = database;
    }
    
    
    @Override
    public RaakaAine findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        RaakaAine a = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));

        stmt.close();
        rs.close();

        conn.close();

        return a;
    }

    @Override
    public List<RaakaAine> findAll() throws SQLException {
	List<RaakaAine> raakaAineet;
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM RaakaAine");
            ResultSet rs = stmt.executeQuery();
            raakaAineet = new ArrayList<>();
            while (rs.next()) {
                RaakaAine a = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
 
                raakaAineet.add(a);
            }
            stmt.close();
            rs.close();
        }
 
        return raakaAineet;
    }

    @Override
    public RaakaAine saveOrUpdate(RaakaAine object) throws SQLException {
        
        if (object.getId() == null) {
            Connection conn = database.getConnection();
            
            PreparedStatement eka = conn.prepareStatement("SELECT * FROM RaakaAine WHERE "
                + " nimi = ?");
            eka.setString(1, object.getNimi());
            ResultSet rs = eka.executeQuery();
            boolean hasOne = rs.next();
            System.out.println("");
            if (hasOne) {
                eka.close();
                conn.close();
                return object;
            }
            
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAine"
                + " (nimi)"
                + " VALUES (?)");
            stmt.setString(1, object.getNimi());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            return object;
        } else {
            Connection conn = database.getConnection();
            
            PreparedStatement stmt = conn.prepareStatement("UPDATE RaakaAine SET "
                + " nimi = ? WHERE id = ?");
            stmt.setString(1, object.getNimi());
            stmt.setInt(2, object.getId());
 
            stmt.executeUpdate();
 
            stmt.close();
            conn.close();
 
            return object;
        }
        
        // ei toteutettu
        //return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM RaakaAine WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    public List<Tilasto> findTilasto(List<RaakaAine> ainelista) throws SQLException{
        List<Tilasto> tilasto= new ArrayList<>();
        
        Connection conn = database.getConnection();
            
        //PreparedStatement eka = conn.prepareStatement("");

        for(int i =0;i<ainelista.size();i++){
            PreparedStatement eka = conn.prepareStatement("SELECT  COUNT(*) FROM RaakaAine, AnnosRaakaAine, Annos"
            + " WHERE RaakaAine.id=?"
            + " AND AnnosRaakaAine.raaka_aine_id=RaakaAine.id"
            + " AND Annos.id=AnnosRaakaAine.annos_id");
            eka.setInt(1, ainelista.get(i).getId());
            ResultSet rs = eka.executeQuery();
            Tilasto a = new Tilasto(ainelista.get(i), rs.getInt(1));
            tilasto.add(a);
            //System.out.println(ainelista.get(i).getNimi() + " "+rs.getInt(1));
            rs.close();
            eka.close();
        }
        
        conn.close();
        
        return tilasto;
    }
}
