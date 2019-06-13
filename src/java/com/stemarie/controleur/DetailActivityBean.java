package com.stemarie.controleur;

import com.stemarie.javabeans.Activity;
import com.stemarie.javabeans.Cyclist;
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
import org.primefaces.model.chart.BarChartSeries;
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
    //private Activity activity;

    //graphique 
    private LineChartModel lineModelPwr;
    private LineChartModel lineModelCad;
    private LineChartModel lineModelHrm;
    private LineChartModel lineModelTime;
    

    
    //dernieres valeurs live
    private String maxSpeed ; 
        

    //injecter le controleur d'activite pour recuperer l'activite en cours
    @Inject
    private ActivityBean activityBean;

    //lancement automatique lors d'un appel
    @PostConstruct
    public void init() {
        
        this.createLineModels();
        //createDateModel();
    }

    //definir les parametres du graphique (legende, abscisse, ordonnée..)
    private void createLineModels(){

//charger les graphiques dans la base de données
        lineModelPwr = this.initLinearModel();
        lineModelPwr.setTitle("Power");
        lineModelPwr.setLegendPosition("ne");
        lineModelPwr.setSeriesColors("b230ff,f52323,f79833,f0e531,33f83f,60d1ed,000000");
        //lineModelPwr.setShadow(false);
        Axis yAxis = lineModelPwr.getAxis(AxisType.Y);
        Axis xAxis = lineModelPwr.getAxis(AxisType.X);

        lineModelCad = this.initLinearModelCad();
        lineModelCad.setTitle("Cadence");
        lineModelCad.setLegendPosition("ne");
        lineModelCad.setSeriesColors("E13A18");
        Axis yAxis2 = lineModelCad.getAxis(AxisType.Y);
        Axis xAxis2 = lineModelCad.getAxis(AxisType.X);
        
        lineModelHrm = this.initLinearModelHrm();
        lineModelHrm.setTitle("Hrm");
        lineModelHrm.setLegendPosition("ne");
        lineModelHrm.setSeriesColors("4B4B43");
        Axis yAxis3 = lineModelHrm.getAxis(AxisType.Y);
        Axis xAxis3 = lineModelHrm.getAxis(AxisType.X);
        
        lineModelTime = this.initLinearModelTime();
        lineModelTime.setTitle("Time");
        lineModelTime.setLegendPosition("ne");
        lineModelTime.setSeriesColors("4B4B43");
        Axis yAxis4 = lineModelTime.getAxis(AxisType.Y);
        Axis xAxis4 = lineModelTime.getAxis(AxisType.X);

    }

    //générer les lignes du graphique
    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();

        //créer le trait des watts (PWR)
        LineChartSeries pwr = new LineChartSeries();
        pwr.setLabel("Power");
        
         LineChartSeries i1 = new LineChartSeries();
        i1.setLabel("I1");
        i1.setFill(true);
        
        LineChartSeries i2 = new LineChartSeries();
        i2.setLabel("I2");
        i2.setFill(true);
        
        LineChartSeries i3 = new LineChartSeries();
        i3.setLabel("I3");
        i3.setFill(true);
        
        LineChartSeries i4 = new LineChartSeries();
        i4.setLabel("I4");
        i4.setFill(true);
        
        LineChartSeries i5 = new LineChartSeries();
        i5.setLabel("I5");
        i5.setFill(true);
        
        LineChartSeries i6 = new LineChartSeries();
        i6.setLabel("I6");
        i6.setFill(true);

        //remplir le graphique
        List<DataActivity> listeDataActivities = this.activityBean.getListDataActivities();
        //recuperer le cycliste en cours
        Cyclist cyclist = this.activityBean.getActivity().getCyclist();
        int i1max = cyclist.getI1Max();
        int i2max = cyclist.getI2Max();
        int i3max = cyclist.getI3Max();
        int i4max = cyclist.getI4Max();
        int i5max = cyclist.getI5Max();
        int i6max = cyclist.getI6Max();
        
       

        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // myDate is the java.util.Date in yyyy-mm-dd format

        String minAxis = "";
        String maxAxis = "";
        int compteur = 0;

        //parcourir les données de l'activité
        for (DataActivity data : listeDataActivities) {
            pwr.set(sm.format(data.getTimeData()), data.getPwr());
            i1.set(sm.format(data.getTimeData()), i1max);
            i2.set(sm.format(data.getTimeData()), i2max);
            i3.set(sm.format(data.getTimeData()), i3max);
            i4.set(sm.format(data.getTimeData()), i4max);
            i5.set(sm.format(data.getTimeData()), i5max);
            i6.set(sm.format(data.getTimeData()), i6max);
        
            //conserver le min
            if (compteur == 0) {
                minAxis = sm.format(data.getTimeData());
            }
            maxAxis = sm.format(data.getTimeData());
            //System.out.println("Ajouter le point : "+sm.format(data.getTimeData()));
            compteur++;
        }
        //System.out.println("Max axis : "+maxAxis);
        model.addSeries(i6);
        model.addSeries(i5);
        model.addSeries(i4);
        model.addSeries(i3);
        model.addSeries(i2);
        model.addSeries(i1);
        model.addSeries(pwr);
        
        
        

        //activer le clic droit pour zoomer
        model.setTitle("Zoom for Details");
        model.setZoom(true);
        model.getAxis(AxisType.Y).setLabel("Values");
        

        //format de la date 
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
    
    //générer les lignes du graphique
    private LineChartModel initLinearModelCad() {
        LineChartModel model     = new LineChartModel();

        //créer le trait des watts (PWR)
      
        //créer le trait cadence(CAD)
        LineChartSeries cad = new LineChartSeries();
        cad.setLabel("Cadence");

      

        //remplir le graphique
        List<DataActivity> listeDataActivities = this.activityBean.getListDataActivities();

        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // myDate is the java.util.Date in yyyy-mm-dd format

        String minAxis = "";
        String maxAxis = "";
        int compteur = 0;

        //parcourir les données de l'activité
        for (DataActivity data : listeDataActivities) {
            
            cad.set(sm.format(data.getTimeData()), data.getCad());
            
            //conserver le min
            if (compteur == 0) {
                minAxis = sm.format(data.getTimeData());
            }
            maxAxis = sm.format(data.getTimeData());
            //System.out.println("Ajouter le point : "+sm.format(data.getTimeData()));
            compteur++;
        }
        //System.out.println("Max axis : "+maxAxis);

        
        model.addSeries(cad);
       

        //activer le clic droit pour zoomer
        model.setTitle("Zoom for Details");
        model.setZoom(true);
        model.getAxis(AxisType.Y).setLabel("Values");

        //format de la date 
        DateAxis axis = new DateAxis("Time");
        axis.setTickAngle(-70);
        axis.setMin(minAxis);
        axis.setMax(maxAxis);

        axis.setTickFormat("%d-%m-%y %H:%#M:%S");
        model.getAxes().put(AxisType.X, axis);

        //retourner l'objet modele terminé
        return model;
    }
    
    
    //graphique HRM
    //générer les lignes du graphique
    private LineChartModel initLinearModelHrm() {
        LineChartModel model = new LineChartModel();

    
        //créer le trait cradiaque (HRM)
        LineChartSeries hrm = new LineChartSeries();
        hrm.setLabel("HRM");

        //remplir le graphique
        List<DataActivity> listeDataActivities = this.activityBean.getListDataActivities();

        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // myDate is the java.util.Date in yyyy-mm-dd format

        String minAxis = "";
        String maxAxis = "";
        int compteur = 0;

        //parcourir les données de l'activité
        for (DataActivity data : listeDataActivities) {
            
            
            hrm.set(sm.format(data.getTimeData()), data.getHrm());
            //conserver le min
            if (compteur == 0) {
                minAxis = sm.format(data.getTimeData());
            }
            maxAxis = sm.format(data.getTimeData());
            //System.out.println("Ajouter le point : "+sm.format(data.getTimeData()));
            compteur++;
        }
        //System.out.println("Max axis : "+maxAxis);

        
        
        model.addSeries(hrm);

        //activer le clic droit pour zoomer
        model.setTitle("Zoom for Details");
        model.setZoom(true);
        model.getAxis(AxisType.Y).setLabel("Values");

        //format de la date 
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
    
    
    
    
    
    
     private LineChartModel initLinearModelTime(){
         
         LineChartModel model = new LineChartModel();

    
        //créer le trait cradiaque (HRM)
        LineChartSeries timeDist = new LineChartSeries();
        timeDist.setLabel("Time");

        //remplir le graphique
        List<DataActivity> listeDataActivities = this.activityBean.getListDataActivities();

        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // myDate is the java.util.Date in yyyy-mm-dd format

        String minAxis = "";
        String maxAxis = "";
        int compteur = 0;
        
        

        //parcourir les données de l'activité
        for (DataActivity data : listeDataActivities) {
            
//            System.out.println("Distance : "+Integer.valueOf(data.getDistance()));

            if(data.getDistance()>0){
                timeDist.set(sm.format(data.getTimeData()), data.getDistance());

                //conserver le min
                if (compteur == 0) {
                    minAxis = sm.format(data.getTimeData());
                }
                maxAxis = sm.format(data.getTimeData());
                //System.out.println("Ajouter le point : "+sm.format(data.getTimeData()));
                compteur++;
            }
        }
        //System.out.println("Max axis : "+maxAxis);

        
        
        model.addSeries(timeDist);

        //activer le clic droit pour zoomer
        model.setTitle("Zoom for Details");
        model.setZoom(true);
        model.getAxis(AxisType.Y).setLabel("Values");

        //format de la date 
        DateAxis axis = new DateAxis("Time");
        axis.setTickAngle(-70);
        //axis.set
        axis.setMin(minAxis);
        axis.setMax(maxAxis);

        axis.setTickFormat("%d-%m-%y %H:%#M:%S");
        model.getAxes().put(AxisType.X, axis);
     
     
         
         return model;
     }
     
     
     

    //recupérer les data pour l'activité courante BackingBean
//    public List<DataActivity> getListDataActivities() {
//        Query query = em.createQuery("SELECT da FROM DataActivity da WHERE da.activity=:activity");
//        query.setParameter("activity", this.activity);
//        return query.getResultList();
//    }

    // getter et setter 
    public LineChartModel getLineModel() {
        this.createLineModels();
        return lineModelPwr;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModelPwr = lineModel;
    }

    public LineChartModel getLineModelCad() {
        return lineModelCad;
    }

    public void setLineModelCad(LineChartModel lineModelCad) {
        this.lineModelCad = lineModelCad;
    }

    public LineChartModel getLineModelHrm() {
        return lineModelHrm;
    }

    public void setLineModelHrm(LineChartModel lineModelHrm) {
        this.lineModelHrm = lineModelHrm;
    }

 

    public ActivityBean getActivityBean() {
        return activityBean;
    }

    public void setActivityBean(ActivityBean activityBean) {
        this.activityBean = activityBean;
    }

    public String getMaxSpeed() {
        return maxSpeed;
    }

    public LineChartModel getLineModelPwr() {
        return lineModelPwr;
    }

    public void setLineModelPwr(LineChartModel lineModelPwr) {
        this.lineModelPwr = lineModelPwr;
    }

    public LineChartModel getLineModelTime() {
        return lineModelTime;
    }

    public void setLineModelTime(LineChartModel lineModelTime) {
        this.lineModelTime = lineModelTime;
    }
    
    
//    public void setMaxSpeed(String maxSpeed) {
//        
//        List<DataActivity> listeDataActivities = this.activityBean.getListDataActivities();
//        DataActivity lastActivity=listeDataActivities.get(listeDataActivities.size()-1);
//        
//        this.maxSpeed=lastActivity.getSpeed();
//    }

    
    
    
    

}
