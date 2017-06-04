package com.rss.oc.www.absences20.bdd.Cours;

/**
 * Created by BRICE CESAR on 29/05/2017.
 */

public class Cours {
    private long id;
    private long id_prof;
    private String jour_semaine;
    private String jour;
    private String mois;
    private String annee;
    private String heure_debut;
    private String heure_fin;
    private String type_enseignement;
    private String groupe;
    private String lib_class;
    private String salle_nom;
    private String rem_cours;





    public Cours(long id, long id_prof,String jour_semaine,String jour,String mois,String annee,String heure_debut,String heure_fin,String type_enseignement,
                 String groupe,String lib_class,String salle_nom,String rem_cours ) {
        super();
        this.id = id;
        this.id_prof = id_prof;
        this.jour_semaine = jour_semaine;
        this.jour = jour;
        this.mois = mois;
        this.annee = annee;
        this.heure_debut=heure_debut;
        this.heure_fin = heure_fin;
        this.type_enseignement = type_enseignement;
        this.groupe = groupe;
        this.lib_class = lib_class;
        this.salle_nom = salle_nom;
        this.rem_cours = rem_cours;
    }

    long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    long getIdPof() {
        return id_prof;
    }

    private void setId_prof(long id_prof) {
        this.id_prof = id_prof;
    }

    String getJour_semaine() {
        return jour_semaine;
    }

    private void setJour_semaine(String jour_semaine) {
        this.jour_semaine = jour_semaine;
    }

    String getJour() {
        return jour;
    }

    private void setJour(String jour) {
        this.jour = jour;
    }

    String getMois() {
        return mois;
    }

    private void setMois(String mois) {
        this.mois = mois;
    }

    String getAnnee() {
        return annee;
    }

    private void setAnnee(String annee) {
        this.annee = annee;
    }

    String getHeure_debut() {
        return heure_debut;
    }

    private void setHeure_debut(String heure_debut) {
        this.heure_debut = heure_debut;
    }

    String getHeure_fin() {
        return heure_fin;
    }

    private void setHeure_fin(String heure_fin) {
        this.heure_fin = heure_fin;
    }

    String getType_enseignement() {
        return type_enseignement;
    }

    private void setType_enseignement(String type_enseignement) {
        this.type_enseignement = type_enseignement;
    }

    String getGroupe() {
        return groupe;
    }

    private void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    String getLib_class() {
        return lib_class;
    }

    private void setLib_class(String lib_class) {
        this.lib_class = lib_class;
    }

    String getSalle_nom() {
        return salle_nom;
    }

    private void setSalle_nom(String salle_nom) {
        this.salle_nom = salle_nom;
    }

    String getRem_cours() {
        return rem_cours;
    }

    private void setRem_cours(String rem_cours) {
        this.rem_cours = rem_cours;
    }

}
