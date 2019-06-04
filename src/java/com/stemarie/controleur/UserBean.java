/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stemarie.controleur;

import com.stemarie.javabeans.Car;
import com.stemarie.javabeans.LogUser;
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
public class UserBean implements Serializable {
    
     @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;
    
    private LogUser user = new LogUser();
    private List<LogUser> listeUser = new ArrayList<>();

    public UserBean() {

    }

    public String ajouter() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "" + user.getUname()+ " successfully added", "Cyclist ajouté avec succès");

        try {
            utx.begin();
            em.merge(user);
            utx.commit();
            user = new LogUser();
        } catch (Exception e) {

        }

        //ajouter le message JSF
        FacesContext.getCurrentInstance().addMessage(null, message);

        return "editUser";
    }

    public List<LogUser> getListerUser() {
        Query query = em.createNamedQuery("LogUser.findAll");
        return query.getResultList();
    }

    public void setListeUser(List<LogUser> listeComputer) {
        this.listeUser = listeComputer;
    }

    public void supprimer(LogUser c) {
        try {
            utx.begin();
            em.remove(em.merge(c));
            utx.commit();
        } catch (Exception e) {

        }
    }

    public String modifier(LogUser c) {

        this.user = c;
        return "editUser.xhtml";

    }

    public LogUser getUser() {
        return user;
    }

    public void setUser(LogUser user) {
        this.user = user;
    }

    
}
