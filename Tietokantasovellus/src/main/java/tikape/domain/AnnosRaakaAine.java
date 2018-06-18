/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.domain;

/**
 *
 * @author julia
 */

public class AnnosRaakaAine {
    Integer id;
    Integer jarjestys;
    String maara;
    String ohje;
    Annos annos;
    RaakaAine raakaAine;
    
    public AnnosRaakaAine(RaakaAine raakaAine, Annos annos, Integer jarjestys, String maara, String ohje){
        this.raakaAine=raakaAine;
        this.annos=annos;
        this.jarjestys=jarjestys;
        this.maara=maara;
        this.ohje=ohje;
    }
    public AnnosRaakaAine(Integer id, RaakaAine raakaAine, Annos annos, Integer jarjestys, String maara, String ohje){
        this.id=id;
        this.raakaAine=raakaAine;
        this.annos=annos;
        this.jarjestys=jarjestys;
        this.maara=maara;
        this.ohje=ohje;
    }
    
    public RaakaAine getRaakaAine(){
        return this.raakaAine;
    }
    public Annos getAnnos(){
        return this.annos;
    }
    public Integer getJarjestys(){
        return this.jarjestys;
    }
    public String getMaara(){
        return this.maara;
    }
    public String getOhje(){
        return this.ohje;
    }
    public Integer getId(){
        return this.id;
    }
    
    public void setRaakaAine(RaakaAine aine){
        this.raakaAine=aine;
    }
    public void setAnnos(Annos annos){
        this.annos=annos;
    }
    public void setJarjestys(Integer jarjestys){
        this.jarjestys=jarjestys;
    }
    public void setMaara(String maara){
        this.maara=maara;
    }
    public void setOhje(String ohje){
        this.ohje=ohje;
    }
    public void setId(Integer id){
        this.id=id;
    }
}
