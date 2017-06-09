package com.rss.oc.www.absences20.bdd.groupe_individu;

/**
 * Created by famille on 09/06/2017.
 */

public class Groupe_individus {

    long individus_id;
    long groupe_id;

    public Groupe_individus(long individus_id, long groupe_id){

        this.individus_id = individus_id;
        this.groupe_id = groupe_id;
    }

    long getIndividus_id() {
        return individus_id;
    }
    private void setIndividus_id(long individus_id) {
        this.individus_id = individus_id;
    }

    long getGroupe_id() {
        return groupe_id;
    }
    private void setGroupe_id(long groupe_id) {
        this.groupe_id = groupe_id;
    }


}
