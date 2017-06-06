package com.rss.oc.www.absences20.bdd.individu;

/**
 * Created by BRICE CESAR on 29/05/2017.
 */

public class Individus {



    private long id;
    private long id_user;
    private String statut;
    private String nom;
    private String prenom;

    public Individus(long id, long id_user,String statut,String nom,String prenom) {
        super();
        this.id = id;
        this.id_user = id_user;
        this.statut = statut;
        this.nom = nom;
        this.prenom =prenom;

    }

    long getIdIndividu() {
        return id;
    }

     void setIdIndividu(long id) {
        this.id = id;
    }

     long getId_user() {
        return id_user;
    }

    private void setId_user(long id_user) {
        this.id_user = id_user;
    }

    String getStatutIndividu() {
        return statut;
    }

    private void setStatutIndividu(String statut) {
        this.statut = statut;
    }

    String getNom() {
        return nom;
    }

    private void setNom(String nom) {
        this.nom = nom;
    }

    String getPrenom() {
        return prenom;
    }

    private void setPrenom(String prenom) {
        this.prenom = prenom;
    }



}
