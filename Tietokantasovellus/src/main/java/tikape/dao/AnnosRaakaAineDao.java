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
import tikape.dao.Dao;
import java.util.*;
import java.sql.*;
import tikape.domain.Annos;
import tikape.domain.AnnosRaakaAine;
import tikape.database.Database;
import tikape.domain.RaakaAine;

public class AnnosRaakaAineDao implements Dao<AnnosRaakaAine, Integer>{
    private Database database;
    private Dao<Annos, Integer> annosDao;
    private Dao<RaakaAine, Integer> raakaAineDao;

    public AnnosRaakaAineDao(Database database, Dao<Annos, Integer> annosDao, Dao<RaakaAine, Integer> raakaAineDao) {
        this.database = database;
        this.annosDao=annosDao;
        this.raakaAineDao=raakaAineDao;
    }
    @Override
    public AnnosRaakaAine findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        RaakaAine a = raakaAineDao.findOne(rs.getInt("raaka_aine_id"));
        Annos an =this.annosDao.findOne(rs.getInt("annos_id"));

        AnnosRaakaAine annosRaakaAine = new AnnosRaakaAine(rs.getInt("id"),a, an, rs.getInt("jarjestys"), rs.getString("maara"), rs.getString("ohje"));

        rs.close();
        stmt.close();
        connection.close();

        return annosRaakaAine;
    }

    @Override
    public List<AnnosRaakaAine> findAll() throws SQLException {
	List<AnnosRaakaAine> lista = new ArrayList<>();
 
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM AnnosRaakaAine");
 
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
 
            Annos a = annosDao.findOne(rs.getInt("annos_id"));
            RaakaAine an = raakaAineDao.findOne(rs.getInt("raaka_aine_id"));
 
            AnnosRaakaAine t = new AnnosRaakaAine(rs.getInt("id"), an,
                    a, rs.getInt("jarjestys"), rs.getString("maara"), rs.getString("ohje"));
 
            lista.add(t);
 
        }
 
        rs.close();
        stmt.close();
        connection.close();
 
        return lista;
    }


    @Override
    public AnnosRaakaAine saveOrUpdate(AnnosRaakaAine object) throws SQLException {
        if (object.getId() == null) {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO AnnosRaakaAine"
                + " (raaka_aine_id, annos_id,jarjestys, maara, ohje)"
                + " VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, object.getRaakaAine().getId());
            stmt.setInt(2, object.getAnnos().getId());
            stmt.setInt(3, object.getJarjestys());
            stmt.setString(4, object.getMaara());
            stmt.setString(5, object.getOhje());
            
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            return object;
        } else {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE AnnosRaakaAine SET "
                + " raaka_aine_id = ?, annos_id=?,jarjestys=?,maara=?,ohje=? WHERE id = ?");
            stmt.setInt(1, object.getRaakaAine().getId());
            stmt.setInt(2, object.getAnnos().getId());
            stmt.setInt(3, object.getJarjestys());
            stmt.setString(4, object.getMaara());
            stmt.setString(5, object.getOhje());
            stmt.setInt(6, object.getId());
            
            stmt.executeUpdate();
 
            stmt.close();
            conn.close();
 
            return object;
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM AnnosRaakaAine WHERE id = ?");
 
        stmt.setInt(1, key);
        stmt.executeUpdate();
 
        stmt.close();
        conn.close();
    }
    
    public List<AnnosRaakaAine> findForOne(Integer annosId)throws SQLException{
        List<AnnosRaakaAine> lista = new ArrayList<>();
        
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE annos_id=?");
        stmt.setInt(1, annosId);
        
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
 
            Annos a = annosDao.findOne(rs.getInt("annos_id"));
            RaakaAine an = raakaAineDao.findOne(rs.getInt("raaka_aine_id"));
 
            AnnosRaakaAine t = new AnnosRaakaAine(rs.getInt("id"), an,
                    a, rs.getInt("jarjestys"), rs.getString("maara"), rs.getString("ohje"));
 
            lista.add(t);
 
        }
 
        rs.close();
        stmt.close();
        connection.close();
        
        return lista;
    }
    public void deleteRaakaAineenId(Integer id) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM AnnosRaakaAine WHERE raaka_aine_id = ?");
 
        stmt.setInt(1, id);
        stmt.executeUpdate();
 
        stmt.close();
        conn.close();
    }
    public void deleteRaakaAnnoksenId(Integer id) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM AnnosRaakaAine WHERE annos_id = ?");
 
        stmt.setInt(1, id);
        stmt.executeUpdate();
 
        stmt.close();
        conn.close();
    }
}
