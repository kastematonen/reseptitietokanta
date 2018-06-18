/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape;

/**
 *
 * @author julia
 */
import tikape.dao.RaakaAineDao;
import tikape.domain.RaakaAine;
import tikape.dao.AnnosDao;
import tikape.domain.Annos;
import tikape.domain.AnnosRaakaAine;
import tikape.database.Database;
import tikape.dao.AnnosRaakaAineDao;
import java.sql.*;
import java.util.*;
import spark.Spark;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import com.google.gson.Gson;

public class Main {
    
    public static void main(String[] args) throws Exception {
        
        Database database = new Database("jdbc:sqlite:reseptit.db");
        RaakaAineDao raakaAineet = new RaakaAineDao(database);

        
        AnnosDao annokset = new AnnosDao(database);
        AnnosRaakaAineDao annosRaakaAineet= new AnnosRaakaAineDao(database, annokset, raakaAineet);
        
        //aloitussivun tuominen
        Spark.get("/index.html", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annokset.findAll());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        //annossivun tuominen
        Spark.get("/annokset.html", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annokset.findAll());
            map.put("ainekset", raakaAineet.findAll());
            return new ModelAndView(map, "annokset");
        }, new ThymeleafTemplateEngine());
        
        //annossivu: annosten lisääminen
        Spark.post("/annosNimi", (req, res) -> {
            annokset.saveOrUpdate(new Annos(req.queryParams("nimi")));
            res.redirect("/annokset.html");
            return "";
        });
        
        //annossivu: annosten poistaminen
        Spark.post("/annokset/:id/delete", (req, res) -> {
            annosRaakaAineet.deleteRaakaAnnoksenId(Integer.parseInt(req.params(":id")));
            annokset.delete(Integer.parseInt(req.params(":id")));
            res.redirect("/annokset.html");
            return "";
        });
        
        //ainessivun hakeminen
        Spark.get("/ainekset.html", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("ainekset", raakaAineet.findAll());
            return new ModelAndView(map, "ainekset");
        }, new ThymeleafTemplateEngine());
        
        //ainessivu: aineksen lisääminen
        Spark.post("/ainekset.html", (req, res) -> {
            raakaAineet.saveOrUpdate(new RaakaAine(req.queryParams("nimi")));
            res.redirect("/ainekset.html");
            return "";
        });
        
        //ainessivu: aineksen poistaminen
        Spark.post("/ainekset/:id/delete", (req, res) -> {
            annosRaakaAineet.deleteRaakaAineenId(Integer.parseInt(req.params(":id")));
            raakaAineet.delete(Integer.parseInt(req.params(":id"))); 
            res.redirect("/ainekset.html");
            return "";
        });
        
        //annoskohtaisen sivun hakeminen
        Spark.get("/annos/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer annosId = Integer.parseInt(req.params(":id"));
            map.put("annos", annokset.findOne(annosId));
            map.put("ainekset", raakaAineet.findAll());
            map.put("yhteydet", annosRaakaAineet.findForOne(annosId));
            
            return new ModelAndView(map, "annos");
        }, new ThymeleafTemplateEngine());
        
        //annoskohtainen sivu: raaka-aineiden lisääminen
        Spark.post("/annos/:id", (req, res) -> {
            Integer raakaAine_id= Integer.parseInt(req.queryParams("ainesId"));
            Integer annos_id=Integer.parseInt(req.params(":id"));
            String maara = req.queryParams("maara");
            String ohje = req.queryParams("ohje");
            
            RaakaAine aine= raakaAineet.findOne(raakaAine_id);
            Annos annos= annokset.findOne(annos_id);
            
            annosRaakaAineet.saveOrUpdate(new AnnosRaakaAine(aine, annos, -1, maara, ohje));
            
            res.redirect("/annokset.html"); //omalls sivulle palaamisessa häikkää, joten mennään tänne
            return "";
        });
        
        //tilastosivun hakeminen
        Spark.get("/tilasto.html", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("tilastot", raakaAineet.findTilasto(raakaAineet.findAll()));
            return new ModelAndView(map, "tilasto");
        }, new ThymeleafTemplateEngine());
    }
}
