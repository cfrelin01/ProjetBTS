/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stemarie.controleur;

import com.stemarie.javabeans.Sensor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author clement
 */
@Named
@RequestScoped
public class SensorBean implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    private Sensor sensor = new Sensor();
    private List<Sensor> listeSensor = new ArrayList<>();

    public SensorBean() {

    }

    public String ajouter() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sensor " + " ajouté avec succès", "Cyclist ajouté avec succès");

        try {
            utx.begin();
            em.merge(sensor);
            utx.commit();
            sensor = new Sensor();
        } catch (Exception e) {

        }

        //ajouter le message JSF
        FacesContext.getCurrentInstance().addMessage(null, message);

        return "editSensor";
    }

    public List<Sensor> getListerSensor() {
        Query query = em.createNamedQuery("Sensor.findAll");
        return query.getResultList();
    }

    public void setListeSensor(List<Sensor> listeSensor) {
        this.listeSensor = listeSensor;
    }

    public void supprimer(Sensor c) {
        try {
            utx.begin();
            em.remove(em.merge(c));
            utx.commit();
        } catch (Exception e) {

        }
    }

    public String addTypeSensor(Sensor c) {
        this.sensor = c;
        return "editTypeSensor.xhtml";
    }

    public String modifier(Sensor c) {

        this.sensor = c;
        return "editSensor.xhtml";

    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setClient(Sensor sensor) {
        this.sensor = sensor;
    }
}
