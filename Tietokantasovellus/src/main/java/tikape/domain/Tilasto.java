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
public class Tilasto {
    RaakaAine aine;
    Integer annosLkm;
    
    public Tilasto(RaakaAine aine, Integer annosLkm){
        this.aine=aine;
        this.annosLkm=annosLkm;
    }
    
    public RaakaAine getRaakaAine(){
        return this.aine;
    }
    public Integer getLkm(){
        return this.annosLkm;
    }
    public void setRaakaAine(RaakaAine aine){
        this.aine=aine;
    }
    public void setLkm(Integer aine){
        this.annosLkm=aine;
    }
    
}
