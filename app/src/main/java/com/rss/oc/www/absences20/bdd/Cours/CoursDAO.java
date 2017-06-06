package com.rss.oc.www.absences20.bdd.Cours;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.rss.oc.www.absences20.bdd.DAOBase;

/**
 * Created by BRICE CESAR on 29/05/2017.
 */

public class CoursDAO extends DAOBase {

    public static final String TABLE_NAME = "Cours";
    public static final String KEY = "id";
    public static final String KEYPROF = "id_prof";
    public static final String JOUR_SEMAINE = "jour_semaine";
    public static final String JOUR = "jour";
    public static final String MOIS = "mois";
    public static final String ANNEE = "annee";
    public static final String HEURE_DEBUT = "heure_debut";
    public static final String HEURE_FIN = "heure_fin";
    public static final String TYPE_ENSEIGNEMENT = "type_enseignement";
    public static final String GROUPE = "groupe";
    public static final String LIB_CLASSE = "lib_class";
    public static final String SALLE_NOM = "salle_nom";
    public static final String REM_COURS = "rem_cours";



    public CoursDAO(Context pContext) {
        super(pContext);
    }


    public void ajouterCours(Cours c) {



        ContentValues value = new ContentValues();
        value.put(CoursDAO.KEY, c.getId());
        value.put(CoursDAO.KEYPROF, c.getIdPof());
        value.put(CoursDAO.JOUR_SEMAINE, c.getJour_semaine());
        value.put(CoursDAO.JOUR, c.getJour());
        value.put(CoursDAO.MOIS, c.getMois());
        value.put(CoursDAO.ANNEE, c.getAnnee());
        value.put(CoursDAO.HEURE_DEBUT, c.getHeure_debut());
        value.put(CoursDAO.HEURE_FIN, c.getHeure_fin());
        value.put(CoursDAO.TYPE_ENSEIGNEMENT, c.getType_enseignement());
        value.put(CoursDAO.GROUPE, c.getGroupe());
        value.put(CoursDAO.LIB_CLASSE, c.getLib_class());
        value.put(CoursDAO.SALLE_NOM, c.getSalle_nom());
        value.put(CoursDAO.REM_COURS, c.getRem_cours());
        openDBWrite();
        mDb.insert(CoursDAO.TABLE_NAME, null, value);
        close();
        Log.i("Table Cours","Ajoutée");



    }
}
