package com.stemarie.controleur;

import com.stemarie.javabeans.Bike;
import java.io.Serializable;
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
 * @author clement frelin.
 */
@Named
@RequestScoped
public class BikeBean implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    private Bike bike = new Bike();
    private List<Bike> listBike;

    public BikeBean() {

    }

    public String ajouter() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bike " + bike.getSerialNum() + " successfully added", "Cyclist ajouté avec succès");

        try {
            utx.begin();
            em.merge(bike);
            utx.commit();
            bike = new Bike();
        } catch (Exception e) {

        }

        //ajouter le message JSF
        FacesContext.getCurrentInstance().addMessage(null, message);

        return "editBike";
    }

    public List<Bike> getListBike() {
        Query query = em.createNamedQuery("Bike.findAll");
        return query.getResultList();
    }

    public List<Bike> getListerBikeWithoutCyclist() {
        Query query = em.createNamedQuery("Bike.findAllWithoutCyclist");
        return query.getResultList();
    }

    public void setListbike(List<Bike> listBike) {
        this.listBike = listBike;
    }

    public void supprimer(Bike c) {
        try {
            utx.begin();
            em.remove(em.merge(c));
            utx.commit();
        } catch (Exception e) {

        }
    }

    public String modifier(Bike c) {
        this.bike = c;
        return "editBike";
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }
}
