package com.rss.oc.www.absences20.bdd.absences;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.rss.oc.www.absences20.bdd.DAOBase;


/**
 * Created by BRICE CESAR on 07/06/2017.
 */

public class AbsencesDAO extends DAOBase{

    public static final String TABLE_NAME = "Absences";
    public static final String KEY = "id";
    public static final String KEY_INDIVIDU = "id_individu";
    public static final String KEY_COURS = "id_cours";
    public static final String TYPE_INDIVIDU = "type_individu";
    public static final String MOTIF = "motif";
    public static final String VALEUR = "valeur";

    public AbsencesDAO(Context pContext) {
        super(pContext);
    }

    public void ajouterAbsences(Absences a){

        ContentValues value = new ContentValues();
        value.put(AbsencesDAO.KEY, a.getIdAbsences());
        value.put(AbsencesDAO.KEY_INDIVIDU, a.getId_individu());
        value.put(AbsencesDAO.KEY_COURS, a.getId_cours());
        value.put(AbsencesDAO.TYPE_INDIVIDU, a.getType_individu());
        value.put(AbsencesDAO.MOTIF, a.getMotif());
        value.put(AbsencesDAO.VALEUR, a.getValeur());

        openDBWrite();
        mDb.insert(AbsencesDAO.TABLE_NAME, null, value);
        close();
        Log.i("Table Absences","Ajoutée");
    }
}
