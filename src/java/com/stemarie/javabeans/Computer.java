/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stemarie.javabeans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@NamedQueries({
    @NamedQuery(name = "Computer.findAll", query = "SELECT c FROM Computer c"),
    @NamedQuery(name = "Computer.findAllWithoutBike", query = "SELECT c FROM Computer c WHERE c.idComputer NOT IN (SELECT DISTINCT b.computer.idComputer FROM Bike b)"),})
@Entity
@XmlRootElement
public class Computer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idComputer;
    private String sim;
    private String addrMac;
    private String name;
    private boolean onOff=false;
    @Temporal(TemporalType.TIMESTAMP)
    private Date onOffTime;

    //gestion de la relation
    @OneToOne(mappedBy = "computer")
    private Bike bike;

    public Computer() {
    }

   

    public String getAddrMac() {
        return addrMac;
    }

    public void setAddrMac(String addrMac) {
        this.addrMac = addrMac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdComputer() {
        return idComputer;
    }

    public void setIdComputer(int idComputer) {
        this.idComputer = idComputer;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public boolean isOnOff() {
        return onOff;
    }

    public void setOnOff(boolean onOff) {
        this.onOff = onOff;
    }

    public Date getOnOffTime() {
        return onOffTime;
    }

    public void setOnOffTime(Date onOffTime) {
        this.onOffTime = onOffTime;
    }

    @Override
    public String toString() {
        return "Computer{" + "idComputer=" + idComputer + ", sim=" + sim + ", addrMac=" + addrMac + ", name=" + name + ", onOff=" + onOff + ", onOffTime=" + onOffTime + ", bike=" + bike + '}';
    }
    
    
    

}
