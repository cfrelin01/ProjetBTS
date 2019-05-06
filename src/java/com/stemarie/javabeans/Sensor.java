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
import javax.xml.bind.annotation.XmlRootElement;

@NamedQuery(name = "Sensor.findAll", query = "SELECT c FROM Sensor c")
@Entity
@XmlRootElement
public class Sensor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSensor;
    private String name;
    private String numCadAndSpd;
    private String numPwr;
    private String numHrm;


    
    public Sensor() {
    }

    
    public int getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumCadAndSpd() {
        return numCadAndSpd;
    }

    public void setNumCadAndSpd(String numCadAndSpd) {
        this.numCadAndSpd = numCadAndSpd;
    }

    public String getNumPwr() {
        return numPwr;
    }

    public void setNumPwr(String numPwr) {
        this.numPwr = numPwr;
    }

    public String getNumHrm() {
        return numHrm;
    }

    public void setNumHrm(String numHrm) {
        this.numHrm = numHrm;
    }

    @Override
    public String toString() {
        return "Sensor{" + "idSensor=" + idSensor + ", name=" + name + ", numCadAndSpd=" + numCadAndSpd + ", numPwr=" + numPwr + ", numHrm=" + numHrm + '}';
    }

   
    

    

}
