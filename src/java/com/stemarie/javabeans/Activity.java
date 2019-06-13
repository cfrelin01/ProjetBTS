/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stemarie.javabeans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lafosse
 */
@NamedQuery(name = "Activity.findAll", query = "SELECT a FROM Activity a")
@Entity
public class Activity implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idActivity;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime=new Date();
    @Temporal(TemporalType.TIMESTAMP)
    private Date stopTime;
     
    @ManyToOne
    private Cyclist cyclist;
    
    @OneToMany(mappedBy = "activity",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<DataActivity> listeDataActivities;
     
    
    public Activity() {

    }

    public int getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    @XmlTransient
    public Cyclist getCyclist() {
        return cyclist;
    }

    public void setCyclist(Cyclist cyclist) {
        this.cyclist = cyclist;
    }

    @XmlTransient
    public List<DataActivity> getListeDataActivities() {
        return listeDataActivities;
    }

    

    public void setListeDataActivities(List<DataActivity> listeDataActivities) {
        this.listeDataActivities = listeDataActivities;
    }

    @Override
    public String toString() {
        return "Activity{" + "idActivity=" + idActivity + ", startTime=" + startTime + ", stopTime=" + stopTime + ", cyclist=" + cyclist + ", listeDataActivities=" + listeDataActivities + '}';
    }

  
    
    
    
    
    
}
