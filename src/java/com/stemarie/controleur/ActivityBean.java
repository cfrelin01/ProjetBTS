package com.stemarie.controleur;

import com.stemarie.javabeans.Activity;
import com.stemarie.javabeans.DataActivity;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author clément FRELIN
 */
@Named
@SessionScoped
public class ActivityBean implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    //activite en cours
    private Activity activity;

    
    public ActivityBean() {

    }

   

    public List<Activity> getListActivities() {
        Query query = em.createNamedQuery("Activity.findAll");
        return query.getResultList();
    }

    
    
    public String detailActivity(Activity activity){
        this.activity=activity;
//        System.out.println("ACTIVITE : "+this.activity);
        return "detailActivity";
    }
    
    
    public List<DataActivity> getListDataActivities() {
        Query query = em.createQuery("SELECT da FROM DataActivity da WHERE da.activity=:activity");
        query.setParameter("activity", this.activity);
        return query.getResultList();
    } 
    
   
    
    public void supprimer(Activity a) {
        try {
            utx.begin();
            em.remove(em.merge(a));
            utx.commit();
        } catch (Exception e) {

        }
    }

    
    
    
    //recuperer la derniere dataActivity pour l'activity en live
    public DataActivity getLiveDataActivity() {
        Query query = em.createQuery("SELECT da FROM DataActivity da WHERE da.activity=:activity ORDER BY da.idDataActivity DESC");
        query.setParameter("activity", this.activity);
        query.setMaxResults(1);
        List<DataActivity> lda=query.getResultList();
        //retourner la derniere DataActivity
        return lda.get(0); 
    } 
    
    
    
    //getters et setters
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    
    
    
    

  
}
