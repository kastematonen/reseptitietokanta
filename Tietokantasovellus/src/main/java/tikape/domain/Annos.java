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

public class Annos {
    Integer id;
    String nimi;
    
    public Annos(String nimi){
        this.nimi=nimi;
    }
    public Annos(Integer id, String nimi){
        this.id=id;
        this.nimi=nimi;
    }
    public Integer getId(){
        return this.id;
    }
    public String getNimi(){
        return this.nimi;
    }
    public void setId(Integer id){
        this.id=id;
    }
    public void setNimi(String nimi){
        this.nimi=nimi;
    }
}
