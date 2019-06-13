/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stemarie.controleur;


import com.stemarie.javabeans.ScxData;
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
 * @author root
 */
@Named
@RequestScoped
public class ScxDataBean implements Serializable {
    
     @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;
    
    private ScxData scxData = new ScxData();
    private List<ScxData> listeScxData = new ArrayList<>();

    public ScxDataBean() {

    }

    public String ajouter() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  " successfully added", "");

        try {
            utx.begin();
            em.merge(scxData);
            utx.commit();
            scxData = new ScxData();
        } catch (Exception e) {

        }

        //ajouter le message JSF
        FacesContext.getCurrentInstance().addMessage(null, message);

        return "editScxData";
    }

    public List<ScxData> getListerScxData() {
        Query query = em.createNamedQuery("ScxData.findAll");
        return query.getResultList();
    }

    public void setListeScxData(List<ScxData> listeScxData) {
        this.listeScxData = listeScxData;
    }

    public void supprimer(ScxData c) {
        try {
            utx.begin();
            em.remove(em.merge(c));
            utx.commit();
        } catch (Exception e) {

        }
    }

    public String modifier(ScxData c) {

        this.scxData = c;
        return "editScxData.xhtml";

    }

    public ScxData getScxData() {
        return scxData;
    }

    public void setUser(ScxData scxData) {
        this.scxData = scxData;
    }

    
}
