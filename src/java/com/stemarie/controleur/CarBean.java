package com.stemarie.controleur;

import com.stemarie.javabeans.Car;
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
public class CarBean implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    private Car car = new Car();
    private List<Car> listeCar = new ArrayList<>();

    public CarBean() {

    }

    public String ajouter() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Car " + car.getName() + " successfully added", "Cyclist ajouté avec succès");

        try {
            utx.begin();
            em.merge(car);
            utx.commit();
            car = new Car();
        } catch (Exception e) {

        }

        //ajouter le message JSF
        FacesContext.getCurrentInstance().addMessage(null, message);

        return "editCar";
    }

    public List<Car> getListerCar() {
        Query query = em.createNamedQuery("Car.findAll");
        return query.getResultList();
    }

    public void setListeCar(List<Car> listeComputer) {
        this.listeCar = listeComputer;
    }

    public void supprimer(Car c) {
        try {
            utx.begin();
            em.remove(em.merge(c));
            utx.commit();
        } catch (Exception e) {

        }
    }

    public String modifier(Car c) {

        this.car = c;
        return "editCar.xhtml";

    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

}
