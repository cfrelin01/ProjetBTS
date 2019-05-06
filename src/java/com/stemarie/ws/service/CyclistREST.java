/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stemarie.ws.service;

import com.stemarie.javabeans.Activity;
import com.stemarie.javabeans.Computer;
import com.stemarie.javabeans.Cyclist;
import com.stemarie.javabeans.DataActivity;
import com.stemarie.javabeans.Sensor;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author lafosse
 */
@Stateless
@Path("/cyclist")
public class CyclistREST {

    //inserer le modele
    @PersistenceContext
    private EntityManager em;

    public CyclistREST() {

    }

    
    
    @GET
    @Produces({"text/plain"})
    @Path("/test")
    public String test() {
        return "WebService CYCLIST";
    }

    
    
    @GET
    @Produces({"text/plain"})
    @Path("/serverDate")
    public long serverDate() {
        return new Date().getTime();
    }
    
    
    
    @GET
    @Path("/onoff/{addrMac}")
    public String onoff(@PathParam("addrMac") String addrMac) {

        System.out.println("addrMAC : " + addrMac);

        // chercher les infos du cyclist
        if (!addrMac.equalsIgnoreCase("")) {
            // chercher le rider et 
            Computer computer = this.findComputer(addrMac);
            System.out.println("Computer trouvé : " + computer);
            // computer trouve
            if (computer != null) {
                computer.setOnOff(true);
                computer.setOnOffTime(new Date());
                em.merge(computer);
            }
        }

        return "1";
    }
    
    
    
    
    @GET
    @Path("/connect/{addrMac}")
    public String connect(@PathParam("addrMac") String addrMac) {

        System.out.println("addrMAC : " + addrMac);

        // chercher les infos du cyclist
        if (!addrMac.equalsIgnoreCase("")) {
            // chercher le rider et 
            Cyclist cyclist = this.findSensorCyclist(addrMac);
            System.out.println("Cycliste trouvé : " + cyclist);
            // cyclist trouve
            if (cyclist != null) {
                // creer une nouvelle activite pour le rider
                Activity activity = new Activity();
                activity.setCyclist(cyclist);
                activity = this.editActivity(activity);

                // connecter le rider
                cyclist.setOnLine(true);
                this.editCyclist(cyclist);

                // formater la reponse
                Sensor sensor = cyclist.getBike().getSensor();
                String result = "idActivity=" + activity.getIdActivity() + "#sensorNUMPWR=" + sensor.getNumPwr() + "#sensorNUMSPEEDANDCADENCE=" + sensor.getNumCadAndSpd() + "#sensorNUMHRM=" + sensor.getNumHrm();
                return result;
            }
        }

        return "0";
    }

    
    
    
    
    @GET
    @Path("/disconnect/{addrMac}/{idActivity}")
    public String disconnect(@PathParam("addrMac") String addrMac, @PathParam("idActivity") String idActivity) {

        // chercher les infos du cyclist
        if (!addrMac.equalsIgnoreCase("") && !idActivity.equalsIgnoreCase("")) {
            // chercher le cyclist
            Cyclist cyclist = this.findSensorCyclist(addrMac);
            // cyclist trouve
            if (cyclist != null) {
                // fin de l'activite
                int idAc = Integer.parseInt(idActivity);
                Activity activity = this.findActivity(idAc);
                if (activity != null) {
                    activity.setStopTime(new Date());
                    this.editActivity(activity);
                }
                // deconnecter le cyclist
                cyclist.setOnLine(false);
                this.editCyclist(cyclist);
                return "1";
            }
        }

        return "0";
    }
    
    
    
    
    
    @GET
    @Path("/send/{addrMac}/{idActivity}/{pwr}/{cad}/{hrm}/{lati}/{longi}/{alti}")
    public Response send(@PathParam("addrMac") String addrMac, @PathParam("idActivity") String idActivity, @PathParam("pwr") String pwr, @PathParam("cad") String cad, @PathParam("hrm") String hrm, @PathParam("lati") String lati, @PathParam("longi") String longi, @PathParam("alti") String alti) {

        Response response = Response.status(400).build();
        
        // chercher les infos du rider
        if (!addrMac.trim().equalsIgnoreCase("") && !idActivity.trim().equalsIgnoreCase("") && !pwr.trim().equalsIgnoreCase("")) {
            // chercher le cyclist
            Cyclist cyclist = this.findSensorCyclist(addrMac);
            // cyclist trouve
            if (cyclist != null) {
                // chercher l'activite
                int idAc = Integer.parseInt(idActivity);
                Activity activity = this.findActivity(idAc);
                // activity trouve
                if (activity != null) {
                    // ajouter la nouvelle DataActivity
                    DataActivity da = new DataActivity();
                    // setter les valeurs
                    da.setPwr(Integer.parseInt(pwr));
                    da.setCad(Integer.parseInt(cad));
                    da.setHrm(Integer.parseInt(hrm));
                    da.setGpsLatitude(lati);
                    da.setGpsLongitude(longi);
                    // enregistrer l'activite                     
                    da.setActivity(activity);
                    // coordonnees GPS
                    if (!lati.trim().equalsIgnoreCase("null")) {
                        da.setGpsLatitude(lati);
                        da.setGpsLongitude(longi);
                    }

                    // creer la DataActivity avec le cascade.PERSIST
                    activity.getListeDataActivities().add(da);
                    this.editActivity(activity);
                    // reponse HTTP
                    response = Response.status(200).build();
                }
            }
        }

        return response;
    }
    
    
    
    
    
    //trouver un computer a partir de l'adresse mac
    public Computer findComputer(String addrMac) {
        // chercher le cyclist
        Query query = em.createQuery("SELECT c FROM Computer c WHERE c.addrMac=:addrMac");
        query.setParameter("addrMac", addrMac);

        Computer computer = null;
        try {
            computer = (Computer) query.getSingleResult();
        } catch (Exception e) {

        }

        return computer;
    }
    
    
    
    //trouver un cycliste a partir de l'adresse mac
    public Cyclist findSensorCyclist(String addrMac) {
        // chercher le cyclist
        Query query = em.createQuery("SELECT c FROM Cyclist c WHERE c.bike.computer.addrMac=:addrMac");
        query.setParameter("addrMac", addrMac);

        Cyclist cyclist = null;
        try {
            cyclist = (Cyclist) query.getSingleResult();
        } catch (Exception e) {

        }

        return cyclist;
    }

    
    
    
    
    //editer une activite
    public Activity editActivity(Activity activity) {
        try {
            Activity a = em.merge(activity);
            em.flush();
            return a;
        } catch (Exception e) {

        }
        return null;
    }

    
    
    
    //editer le cycliste
    public Cyclist editCyclist(Cyclist cyclist) {
        try {
            Cyclist c = em.merge(cyclist);
            em.flush();
            return c;
        } catch (Exception e) {

        }
        return null;
    }

    
    // trouver une activite a partir de son id
    public Activity findActivity(int idActivity){
        try {
            Activity a = em.find(Activity.class, idActivity);
            return a;
        } catch (Exception e) {

        }
        return null;
    }
    
    
    /*
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Cyclist entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Cyclist entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Cyclist find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cyclist> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cyclist> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
     */
}
