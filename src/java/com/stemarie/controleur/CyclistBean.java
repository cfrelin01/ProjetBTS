package com.stemarie.controleur;

import com.stemarie.javabeans.Cyclist;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author clement
 */
@Named
@RequestScoped
public class CyclistBean implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    //backing bean
    private Cyclist cyclist = new Cyclist();
    
     
//    private List<Cyclist> listeCyclists=new ArrayList<>();
    
    //image du cycliste
    private UploadedFile file;

    
    
    
    public CyclistBean() {

    }
    
    
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    
    

    public String edit() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cyclist " + cyclist.getLastName() + " successfully added", "Cyclist ajouté avec succès");

        try {
            utx.begin();
            em.merge(cyclist);
            utx.commit();
            cyclist = new Cyclist();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //ajouter le message JSF
        FacesContext.getCurrentInstance().addMessage(null, message);
        return "editCyclist?faces-redirect=true";
    }
    
    
    


    public List<Cyclist> getListeCyclists() {
        Query query = em.createNamedQuery("Cyclist.findAll");
        return query.getResultList();
    }

   

    
    
    

    public void delete(Cyclist c) {
        // supprimer l'image si elle existe
        this.removeImg(c.getImgCyclist());
        
        try {
            utx.begin();
            em.remove(em.merge(c));
            utx.commit();
        } catch (Exception e) {

        }
    }

    
    
    public String edit(Cyclist cyclist) {
        this.cyclist = cyclist;
        return "editCyclist.xhtml";
    }
    
    
    
    
    // uploader le fichier image du cycliste
    public void upload() {
        // fichier uploade
        if(file != null && !file.getFileName().equalsIgnoreCase("")) 
        {       
            // verifier si c'est une modification de l'image
            int idCyclist=this.cyclist.getIdCyclist();
            System.out.println("VIRE : "+idCyclist+" file "+file.getFileName());
            if(idCyclist>0){
               Cyclist c=em.find(Cyclist.class, idCyclist);
               this.removeImg(c.getImgCyclist());
            }
            
            final ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
            final String PATH_IMG_RIDER = external.getInitParameter("PATH_IMG_CYCLIST");

            try {
                // chemin du fichier de destination
                String imgRiderName = this.generatedRandomNameFile();
                String destinationPath = PATH_IMG_RIDER + java.io.File.separator + imgRiderName;
                System.out.println("Copier le fichier dans le répetoire : " + destinationPath);
                // creer le fichier sur le serveur
                File destinationFile = new File(destinationPath);
                // copier le fichier source vers la destination
                Files.copy(file.getInputstream(), destinationFile.toPath());
                // affecter le chemin au cycliste
                this.cyclist.setImgCyclist(imgRiderName);

                // message de succes
                FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }     

    }

    
    
    // supprimer l'image du rider si elle existe
    public void removeImg(String imgCyclist) {
        System.out.println("Supprimer l'image : " + imgCyclist);
        if (imgCyclist != null && !imgCyclist.equalsIgnoreCase("")) {
            final ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
            final String PATH_IMG_RIDER = external.getInitParameter("PATH_IMG_CYCLIST");

            // url de stockage de la photo
            String destinationPath = PATH_IMG_RIDER + java.io.File.separator + imgCyclist;
            System.out.println("Supprimer l'image : " + destinationPath);
            File oldFile = new File(destinationPath);
            if (oldFile.exists()) {
                oldFile.delete();
            }
        }
    }

    
    // generer un nom au hasard
    public String generatedRandomNameFile() {
        int i = (int) (Math.random() * 100000000);
        return String.valueOf(i) + ".png";
    }

    
    
    
    //getters et setters
    public Cyclist getCyclist() {
        return cyclist;
    }

    public void setCyclist(Cyclist cyclist) {
        this.cyclist = cyclist;
    }
    
        

}
