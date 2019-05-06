/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stemarie.controleur;

import com.stemarie.javabeans.Computer;
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
public class ComputerBean implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    private Computer computer = new Computer();
    private List<Computer> listeComputer;

    public ComputerBean() {

    }

    public String ajouter() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Computer " + computer.getName() + " successfully added", "Cyclist ajouté avec succès");

        try {
            utx.begin();
            em.merge(computer);
            utx.commit();
            computer = new Computer();
        } catch (Exception e) {

        }

        //ajouter le message JSF
        FacesContext.getCurrentInstance().addMessage(null, message);

        return "editComputer";
    }

    public List<Computer> getListerComputer() {
        Query query = em.createNamedQuery("Computer.findAll");
        this.listeComputer = query.getResultList();
        System.out.println("LISTE : " + this.listeComputer.size());
        return this.listeComputer;
    }

    public List<Computer> getListerComputerWithoutBike() {
        Query query = em.createNamedQuery("Computer.findAllWithoutBike");
        return query.getResultList();
    }

    public void setListeComputer(List<Computer> listeComputer) {
        this.listeComputer = listeComputer;
    }

    public void supprimer(Computer c) {
        try {
            utx.begin();
            em.remove(em.merge(c));
            utx.commit();
        } catch (Exception e) {

        }
    }

    public String modifier(Computer c) {

        this.computer = c;
        return "editComputer.xhtml";

    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

}
