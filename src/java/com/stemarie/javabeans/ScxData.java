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
    private double coefRoulement;
    private double temp;
    private double scxOpti;
    private double pressBaro;
    private double rhoMax; 

    public int getIdScxData() {
        return idScxData;
    }

    public void setIdScxData(int idScxData) {
        this.idScxData = idScxData;
    }

    public double getCoefRoulement() {
        return coefRoulement;
    }

    public void setCoefRoulement(double coefRoulement) {
        this.coefRoulement = coefRoulement;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getScxOpti() {
        return scxOpti;
    }

    public void setScxOpti(double scxOpti) {
        this.scxOpti = scxOpti;
    }

    public double getPressBaro() {
        return pressBaro;
    }

    public void setPressBaro(double pressBaro) {
        this.pressBaro = pressBaro;
    }

    public double getRhoMax() {
        return rhoMax;
    }

    public void setRhoMax(double rhoMax) {
        this.rhoMax = rhoMax;
    }

    
    
    
    
    

    
    
    
}
