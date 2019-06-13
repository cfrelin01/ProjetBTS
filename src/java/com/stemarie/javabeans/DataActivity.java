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
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lafosse
 */
@Entity
public class DataActivity implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDataActivity;
    private int pwr;
    private int cad;
    private int torque;
    private String gpsLatitude;
    private String gpsLongitude;
    private String distance;
    private String gpsAltitude;
    private String speed;
    private int hrm;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeData=new Date();
   
    @ManyToOne
    private Activity activity;

    
    public DataActivity() {
    }

    public int getIdDataActivity() {
        return idDataActivity;
    }

    public void setIdDataActivity(int idDataActivity) {
        this.idDataActivity = idDataActivity;
    }

    public int getPwr() {
        return pwr;
    }

    public void setPwr(int pwr) {
        this.pwr = pwr;
    }

    public int getCad() {
        return cad;
    }

    public void setCad(int cad) {
        this.cad = cad;
    }

    public int getTorque() {
        return torque;
    }

    public void setTorque(int torque) {
        this.torque = torque;
    }

    public String getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(String gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public String getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(String gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public int getHrm() {
        return hrm;
    }

    public void setHrm(int hrm) {
        this.hrm = hrm;
    }

    public Date getTimeData() {
        return timeData;
    }

    public void setTimeData(Date timeData) {
        this.timeData = timeData;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getGpsAltitude() {
        return gpsAltitude;
    }

    public void setGpsAltitude(String gpsAltitude) {
        this.gpsAltitude = gpsAltitude;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
    
    
    
    
   

    
}
