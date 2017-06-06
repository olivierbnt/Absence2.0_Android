package com.rss.oc.www.absences20.bdd.absences;

/**
 * Created by BRICE CESAR on 07/06/2017.
 */

public class Absences {

    private long id;
    private long id_individu;
    private long id_cours;
    private String type_individu;
    private String motif;
    private String valeur;

    public Absences(long id,long id_individu,long id_cours,String type_individu,String motif,String valeur){
        super();
        this.id = id;
        this.id_individu = id_individu;
        this.id_cours = id_cours;
        this.type_individu =type_individu;
        this.motif=motif;
        this.valeur=valeur;
    }


    long getIdAbsences() {
        return id;
    }

    void setIdAbsences(long id) {
        this.id = id;
    }

    long getId_individu() {
        return id_individu;
    }

    private void setId_individu(long id_individu) {
        this.id_individu = id_individu;
    }

    long getId_cours() {
        return id_cours;
    }

    private void setId_cours(long id_cours) {
        this.id_cours = id_cours;
    }

    String getType_individu() {
        return type_individu;
    }

    private void setType_individu(String type_individu) {
        this.type_individu = type_individu;
    }

    String getMotif() {
        return motif;
    }

    private void setMotif(String motif) {
        this.motif = motif;
    }

    String getValeur() {
        return valeur;
    }

    private void setValeur(String valeur) {
        this.valeur = valeur;
    }


}
