package com.rss.oc.www.absences20.bdd.individu;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.rss.oc.www.absences20.bdd.Cours.CoursDAO;
import com.rss.oc.www.absences20.bdd.DAOBase;

/**
 * Created by BRICE CESAR on 29/05/2017.
 */

public class IndividusDAO extends DAOBase {

    public static final String TABLE_NAME = "Individus";
    public static final String KEY = "id";
    public static final String NOM = "nom";
    public static final String PRENOM = "prenom";
    public static final String KEY_USER = "id_user";
    public static final String STATUT = "statut";
    public static final String EMAIL = "email";


    public IndividusDAO(Context pContext) {
        super(pContext);
    }

    public void ajouterIndividu(Individus i){

        ContentValues value = new ContentValues();
        value.put(IndividusDAO.KEY, i.getIdIndividu());
        value.put(IndividusDAO.KEY_USER, i.getId_user());
        value.put(IndividusDAO.NOM, i.getNom());
        value.put(IndividusDAO.PRENOM, i.getPrenom());
        value.put(IndividusDAO.STATUT, i.getStatutIndividu());
        value.put(IndividusDAO.EMAIL, i.getEmail());
        openDBWrite();
        mDb.insert(IndividusDAO.TABLE_NAME, null, value);
        close();
        Log.i("Table Individus","Ajoutée");
    }
}
