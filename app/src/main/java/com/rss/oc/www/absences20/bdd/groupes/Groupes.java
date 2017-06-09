package com.rss.oc.www.absences20.bdd.groupes;

/**
 * Created by famille on 09/06/2017.
 */

public class Groupes {
    private long id;
    private String libelle;


    public Groupes (long id, String libelle) {

        this.id = id;
        this.libelle = libelle;
    }


    long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    String getGroupeLibelle() {
        return libelle;
    }

    private void setGroupeLibelle(String libelle) {
        this.libelle = libelle;
    }


}
