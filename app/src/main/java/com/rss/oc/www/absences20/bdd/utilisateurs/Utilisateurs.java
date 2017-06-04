package com.rss.oc.www.absences20.bdd.utilisateurs;

/**
 * Created by BRICE CESAR on 29/05/2017.
 */

public class Utilisateurs {
    private long id;
    private String login;
    private String statut;
    private String password;

    public Utilisateurs(long id, String login,String statut,String password) {
        super();
        this.id = id;
        this.login = login;
        this.statut = statut;
        this.password = password;
    }

    long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    String getLogin() {
        return login;
    }

    private void setIdentifiant(String login) {
        this.login = login;
    }

    String getStatut() {
        return statut;
    }

    private void setStatut(String statut) {
        this.statut = statut;
    }
    String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }


}

