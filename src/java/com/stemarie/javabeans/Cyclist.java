package com.stemarie.javabeans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQuery(name = "Cyclist.findAll", query = "SELECT c FROM Cyclist c")
@Entity
@XmlRootElement
public class Cyclist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCyclist;
    private String lastName;
    private String firstName;
    private String height;
    private String weight;
    @Temporal(TemporalType.DATE)
    private Date dateBirth;
    private boolean onLine=false;
    //PMA
    private int PMA;
    // zones de PMA
    private int i1Min;
    private int i1Max;
    private int i2Min;
    private int i2Max;
    private int i3Min;
    private int i3Max;
    private int i4Min;
    private int i4Max;
    private int i5Min;
    private int i5Max;
    // img
    private String imgCyclist="";


    //gestion de la relation
    @ManyToOne
    private Bike bike = new Bike();

    public Cyclist() {
    }


    public int getIdCyclist() {
        return idCyclist;
    }

    public void setIdCyclist(int idCyclist) {
        this.idCyclist = idCyclist;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

  

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public boolean isOnLine() {
        return onLine;
    }

    public void setOnLine(boolean onLine) {
        this.onLine = onLine;
    }

    public int getPMA() {
        return PMA;
    }

    public void setPMA(int PMA) {
        this.PMA = PMA;
    }

    public int getI1Min() {
        return i1Min;
    }

    public void setI1Min(int i1Min) {
        this.i1Min = i1Min;
    }

    public int getI1Max() {
        return i1Max;
    }

    public void setI1Max(int i1Max) {
        this.i1Max = i1Max;
    }

    public int getI2Min() {
        return i2Min;
    }

    public void setI2Min(int i2Min) {
        this.i2Min = i2Min;
    }

    public int getI2Max() {
        return i2Max;
    }

    public void setI2Max(int i2Max) {
        this.i2Max = i2Max;
    }

    public int getI3Min() {
        return i3Min;
    }

    public void setI3Min(int i3Min) {
        this.i3Min = i3Min;
    }

    public int getI3Max() {
        return i3Max;
    }

    public void setI3Max(int i3Max) {
        this.i3Max = i3Max;
    }

    public int getI4Min() {
        return i4Min;
    }

    public void setI4Min(int i4Min) {
        this.i4Min = i4Min;
    }

    public int getI4Max() {
        return i4Max;
    }

    public void setI4Max(int i4Max) {
        this.i4Max = i4Max;
    }

    public int getI5Min() {
        return i5Min;
    }

    public void setI5Min(int i5Min) {
        this.i5Min = i5Min;
    }

    public int getI5Max() {
        return i5Max;
    }

    public void setI5Max(int i5Max) {
        this.i5Max = i5Max;
    }

    public String getImgCyclist() {
        return imgCyclist;
    }

    public void setImgCyclist(String imgCyclist) {
        this.imgCyclist = imgCyclist;
    }

    
    
    
    @Override
    public String toString() {
        return "Cyclist{" + "idCyclist=" + idCyclist + ", lastName=" + lastName + ", firstName=" + firstName + ", height=" + height + ", weight=" + weight + ", dateBirth=" + dateBirth + ", bike=" + bike + '}';
    }
    
    

}
