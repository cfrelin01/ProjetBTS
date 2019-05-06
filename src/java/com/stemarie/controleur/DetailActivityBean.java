package com.stemarie.controleur;

import com.stemarie.javabeans.Activity;
import com.stemarie.javabeans.DataActivity;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Thomas TURLIN
 */
@Named
@SessionScoped
public class DetailActivityBean implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    //backing bean
    private Activity activity;

    //graphique 
    private LineChartModel lineModel;

    
    //injecter le controleur d'activite pour recuperer l'activite en cours
    @Inject
    private ActivityBean activityBean;
    
    
    // private LineChartModel zoomModel;
//    private CartesianChartModel fillToZero;
//    
//    private BarChartModel animatedModel2;
//    private LineChartModel dateModel;
    //lancement automatique lors d'un appel
    @PostConstruct
    public void init() {
        System.out.println("Merci de générer le graphique :"+activityBean.getListDataActivities());
        this.createLineModels();
        //createDateModel();
    }

    //definir les parametres du graphique (legende...)
    private void createLineModels() {
        //charger les graphiques dans la base de données
        lineModel = this.initLinearModel();
        lineModel.setTitle("Live Activity");
        lineModel.setLegendPosition("ne");
        Axis yAxis = lineModel.getAxis(AxisType.Y);
//        yAxis.setMin(0);
//        yAxis.setMax(2000);
        Axis xAxis = lineModel.getAxis(AxisType.X);
//        xAxis.setMin(0);
//        xAxis.setMax(600);
    }

    
    //générer les traits des graphiques
    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();


        //créer le trait des watts PWR
        LineChartSeries pwr = new LineChartSeries();
        pwr.setLabel("Power");
        
        //remplir le graphique
        List<DataActivity> listeDataActivities=this.activityBean.getListDataActivities();
        
//    pwr.set("2014-01-01 00:10:50", 51);
//    pwr.set("2014-01-01 00:10:51", 22);
//    pwr.set("2014-01-01 00:10:52", 65);
//    pwr.set("2014-01-01 00:10:53", 35);
        
    SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // myDate is the java.util.Date in yyyy-mm-dd format

        String minAxis="";
        String maxAxis="";
        int compteur=0;
        
        //parcourir les données de l'activité
        for(DataActivity data:listeDataActivities){
            pwr.set(sm.format(data.getTimeData()), data.getPwr());
            //conserver le min
            if(compteur==0){
             minAxis=sm.format(data.getTimeData());   
            }
            maxAxis=sm.format(data.getTimeData());
            //System.out.println("Ajouter le point : "+sm.format(data.getTimeData()));
            compteur++;
        }
        //System.out.println("Max axis : "+maxAxis);
        


         model.addSeries(pwr);
//        model.addSeries(CAD);
//        model.addSeries(HRM);


        //activer le clic droit pour zoomer
        model.setTitle("Zoom for Details");
        model.setZoom(true);
        model.getAxis(AxisType.Y).setLabel("Values");

        
        //format date 
        DateAxis axis = new DateAxis("Time");
        axis.setTickAngle(-70);
        //axis.set
        axis.setMin(minAxis);
        axis.setMax(maxAxis);
        
        
        axis.setTickFormat("%d-%m-%y %H:%#M:%S");
        model.getAxes().put(AxisType.X, axis);
        
        
        //retourner l'objet modele terminé
        return model;
    }
    

    
    
    
    //recupérer les data pour l'activité courante BackingBean
    public List<DataActivity> getListDataActivities() {
        Query query = em.createQuery("SELECT da FROM DataActivity da WHERE da.activity=:activity");
        query.setParameter("activity", this.activity);
        return query.getResultList();
    }
    
    
    
   
    
    

//    public void itemSelect(ItemSelectEvent event) {
//        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
//                "Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());
//
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }

//    public LineChartModel getZoomModel() {
//        return zoomModel;
//    }
//
//    public CartesianChartModel getFillToZero() {
//        return fillToZero;
//    }
//
//    public LineChartModel getAnimatedModel1() {
//        return animatedModel1;
//    }
//
//    public BarChartModel getAnimatedModel2() {
//        return animatedModel2;
//    }
//
//    public LineChartModel getDateModel() {
//        return dateModel;
//    }
    /*private void createFillToZero() {
        fillToZero = new CartesianChartModel();
 
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");
 
        series1.set("4, -3, 3, 6, 2, -2", 0);
 
        fillToZero.addSeries(series1);
   
    private static final int NAME = 0x320;*/

    public LineChartModel getLineModel() {
        this.createLineModels();
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    public ActivityBean getActivityBean() {
        return activityBean;
    }

    public void setActivityBean(ActivityBean activityBean) {
        this.activityBean = activityBean;
    }
    
}
