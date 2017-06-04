package com.rss.oc.www.absences20.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BRICE CESAR on 29/05/2017.
 */

public class DataBaseHandler extends SQLiteOpenHelper {


    public static final String UTILISATEUR_KEY = "id";
    public static final String UTILISATEUR_LOGIN = "login";
    public static final String UTILISATEUR_STATUT = "statut";
    public static final String UTILISATEUR_PASSWORD = "password";

    public static final String COURS_KEY = "id";
    public static final String COURS_KEYPROF = "id_prof";
    public static final String COURS_JOUR_SEMAINE = "jour_semaine";
    public static final String COURS_JOUR = "jour";
    public static final String COURS_MOIS = "mois";
    public static final String COURS_ANNEE = "annee";
    public static final String COURS_HEURE_DEBUT = "heure_debut";
    public static final String COURS_HEURE_FIN = "heure_fin";
    public static final String COURS_TYPE_ENSEIGNEMENT = "type_enseignement";
    public static final String COURS_GROUPE = "groupe";
    public static final String COURS_LIB_CLASSE = "lib_class";
    public static final String COURS_SALLE_NOM = "salle_nom";
    public static final String COURS_REM_COURS = "rem_cours";


    public static final String INDIVIDU_KEY = "id";
    public static final String INDIVIDU_NOM = "nom";
    public static final String INDIVIDU_PRENOM = "prenom";
    public static final String INDIVIDU_KEY_USER = "id_user";
    public static final String INDIVIDU_STATUT = "statut";
    public static final String INDIVIDU_EMAIL = "email";



    public static final String UTILISATEUR_TABLE_NAME = "Utilisateur";
    public static final String UTILISATEUR_TABLE_CREATE =
            "CREATE TABLE " + UTILISATEUR_TABLE_NAME + " (" +
                    UTILISATEUR_KEY + " INTEGER, " +
                    UTILISATEUR_LOGIN + " TEXT, " +
                    UTILISATEUR_STATUT + " TEXT, " +
                    UTILISATEUR_PASSWORD + " TEXT);";

    public static final String UTILISATEUR_TABLE_DROP = "DROP TABLE IF EXISTS " + UTILISATEUR_TABLE_NAME + ";";


    public static final String COURS_TABLE_NAME = "Cours";
    public static final String COURS_TABLE_CREATE =
            "CREATE TABLE " + COURS_TABLE_NAME + " (" +
                    COURS_KEY                + " INTEGER, " +
                    COURS_KEYPROF            + " INTEGER, " +
                    COURS_JOUR_SEMAINE       + " TEXT, " +
                    COURS_JOUR               + " TEXT, " +
                    COURS_MOIS               + " TEXT, " +
                    COURS_ANNEE              + " TEXT, " +
                    COURS_HEURE_DEBUT        + " TEXT, " +
                    COURS_HEURE_FIN          + " TEXT, " +
                    COURS_TYPE_ENSEIGNEMENT  + " TEXT, " +
                    COURS_GROUPE             + " TEXT, " +
                    COURS_LIB_CLASSE         + " TEXT, " +
                    COURS_SALLE_NOM          + " TEXT, " +
                    COURS_REM_COURS          + " TEXT);";

    public static final String COURS_TABLE_DROP = "DROP TABLE IF EXISTS " + COURS_TABLE_NAME + ";";


    public static final String INDIVIDU_TABLE_NAME = "Individu";
    public static final String INDIVIDU_TABLE_CREATE =
            "CREATE TABLE " + INDIVIDU_TABLE_NAME + " (" +
                    INDIVIDU_KEY             + " INTEGER, " +
                    INDIVIDU_KEY_USER        + " INTEGER, " +
                    INDIVIDU_NOM             + " TEXT, " +
                    INDIVIDU_PRENOM          + " TEXT, " +
                    INDIVIDU_STATUT          + " TEXT, " +
                    INDIVIDU_EMAIL           + " TEXT);";

    public static final String INDIVIDU_TABLE_DROP = "DROP TABLE IF EXISTS " + INDIVIDU_TABLE_NAME + ";";

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UTILISATEUR_TABLE_DROP);
        db.execSQL(COURS_TABLE_DROP);
        db.execSQL(INDIVIDU_TABLE_DROP);
        onCreate(db);

    }


    public DataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(UTILISATEUR_TABLE_CREATE);
        db.execSQL(COURS_TABLE_CREATE);
        db.execSQL(INDIVIDU_TABLE_CREATE);


    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);

    }
}
