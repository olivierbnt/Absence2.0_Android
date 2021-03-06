package com.rss.oc.www.absences20.bdd.individu;

/**
 * Created by BRICE CESAR on 29/05/2017.
 */

public class Individus {



    private long id;
    private long id_user;
    private long val_debut;
    private long val_fin;
    private String statut;
    private String nom;
    private String prenom;
    private String email;
    private long rue;
    private String adresse;
    private long codePostal;
    private String ville;
    private String pays;

    public Individus(long id, long id_user,long val_debut,long val_fin,String statut,String nom,String prenom,String email, long rue, String adresse,long codePostal,String ville,String pays) {
        super();
        this.id = id;
        this.id_user = id_user;
        this.val_debut = val_debut;
        this.val_fin = val_fin;
        this.statut = statut;
        this.nom = nom;
        this.prenom =prenom;
        this.email=email;
        this.rue=rue;
        this.adresse=adresse;
        this.codePostal=codePostal;
        this.ville = ville;
        this.pays=pays;

    }

    long getIdIndividu() {
        return id;
    }

     void setIdIndividu(long id) {
        this.id = id;
    }

    long getVal_debut() {
        return val_debut;
    }

    String getEmail() {
        return email;
    }

    long getRue() {
        return rue;
    }

    String getAdresse() {
        return adresse;
    }

    long getCodePostal() {
        return codePostal;
    }

    String getVille() {
        return ville;
    }

    String getPays() {
        return pays;
    }

    void setVal_debut(long val_debut) {
        this.val_debut = val_debut;
    }

    long getVal_fin() {
        return val_fin;
    }

    void setVal_fin(long val_fin) {
        this.val_fin = val_fin;
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
