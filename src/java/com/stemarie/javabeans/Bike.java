package com.stemarie.javabeans;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQueries({
    @NamedQuery(name = "Bike.findAll", query = "SELECT c FROM Bike c"),
    @NamedQuery(name = "Bike.findAllWithoutCyclist", query = "SELECT c FROM Bike c WHERE c.idBike NOT IN (SELECT DISTINCT b.bike.idBike FROM Cyclist b)"),})
@Entity
@XmlRootElement
public class Bike implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBike;
    private String serialNum;
    private String typeBike;

    //gestion de la relation
    @OneToOne
    private Computer computer = new Computer();

    @OneToOne
    private Sensor sensor = new Sensor();

    public Bike() {
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }


    public int getIdBike() {
        return idBike;
    }

    public void setIdBike(int idBike) {
        this.idBike = idBike;
    }

    public String getTypeBike() {
        return typeBike;
    }

    public void setTypeBike(String typeBike) {
        this.typeBike = typeBike;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "Bike{" + "idBike=" + idBike + ", serialNum=" + serialNum + ", typeBike=" + typeBike + ", computer=" + computer + ", sensor=" + sensor + '}';
    }

   

}
