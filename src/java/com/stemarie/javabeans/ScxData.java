/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stemarie.javabeans;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 *
 * @author root
 */
@NamedQuery(name = "ScxData.findAll", query = "SELECT c FROM ScxData c")
@Entity
public class ScxData implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idScxData;
    private int coefRoulement;
    private int temp;
    private int scxOpti;
    private int pressBaro;
    private int rhoMax; 

    public int getCoefRoulement() {
        return coefRoulement;
    }

    public void setCoefRoulement(int coefRoulement) {
        this.coefRoulement = coefRoulement;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getScxOpti() {
        return scxOpti;
    }

    public void setScxOpti(int scxOpti) {
        this.scxOpti = scxOpti;
    }

    public int getPressBaro() {
        return pressBaro;
    }

    public void setPressBaro(int pressBaro) {
        this.pressBaro = pressBaro;
    }

    public int getRhoMax() {
        return rhoMax;
    }

    public void setRhoMax(int rhoMax) {
        this.rhoMax = rhoMax;
    }

    public int getIdScxData() {
        return idScxData;
    }

    public void setIdScxData(int idScxData) {
        this.idScxData = idScxData;
    }
    
    
    
    

    
    
    
}
