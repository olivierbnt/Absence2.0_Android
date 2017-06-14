package com.rss.oc.www.absences20.bdd.Cours;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.rss.oc.www.absences20.bdd.DAOBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    public long getIdCoursInstant (){
        long idFinal=-1;

        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+JOUR+","+MOIS+","+ANNEE+","+HEURE_DEBUT+","+HEURE_FIN+ " from " + TABLE_NAME, null);

        if(cursor.moveToNext()){

            int indexId = cursor.getColumnIndex(KEY);
            int indexJour = cursor.getColumnIndex(JOUR);
            int indexMois = cursor.getColumnIndex(MOIS);
            int indexAnnee = cursor.getColumnIndex(ANNEE);
            int indexHeureDebut = cursor.getColumnIndex(HEURE_DEBUT);
            int indexHeureFin = cursor.getColumnIndex(HEURE_FIN);
            do {

                String jour = cursor.getString(indexJour);
                String mois = cursor.getString(indexMois);
                String annee = cursor.getString(indexAnnee);
                String heureDebut = cursor.getString(indexHeureDebut);
                String heureFin = cursor.getString(indexHeureFin);
                String dateDebut = chaineDate(heureDebut,jour,mois,annee);
                Log.i("dateDebut",dateDebut);
                String dateFin = chaineDate(heureFin,jour,mois,annee);
                Log.i("dateFin",dateFin);
                if (isHour(dateDebut,dateFin)){
                    idFinal=cursor.getInt(indexId);
                   // Log.i("id", String.valueOf(idFinal));
                    cursor.moveToLast();
                }


            } while (cursor.moveToNext());
        }
          close();
        return idFinal;
    }

    public String getLibelleGroupeInstant(long idCoursInstant){

        String groupe = null;
        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+GROUPE+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexGroupe = cursor.getColumnIndex(GROUPE);

            do{

                long id = cursor.getInt(indexId);
                if (id==idCoursInstant){
                    groupe = cursor.getString(indexGroupe);
                    cursor.moveToLast();
                }


            }while (cursor.moveToNext());
        }
          close();
        return groupe;
    }

    public String getInfoCours(long idCoursInstant){

        String infosCours = null;
        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+GROUPE+","+HEURE_DEBUT+","+HEURE_FIN+","+LIB_CLASSE+","+SALLE_NOM+","+GROUPE+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexGroupe = cursor.getColumnIndex(GROUPE);
            int indexHeureDebut = cursor.getColumnIndex(HEURE_DEBUT);
            int indexHeureFin = cursor.getColumnIndex(HEURE_FIN);
            int indexSalleNom = cursor.getColumnIndex(SALLE_NOM);
            int indexLibClasse = cursor.getColumnIndex(LIB_CLASSE);

            do{

                long id = cursor.getInt(indexId);
                if (id==idCoursInstant){
                    infosCours = cursor.getString(indexGroupe)+";"+cursor.getString(indexHeureDebut)+";"+cursor.getString(indexHeureFin)
                            +";"+cursor.getString(indexSalleNom)+";"+cursor.getString(indexLibClasse);
                    cursor.moveToLast();
                }


            }while (cursor.moveToNext());
        }
        close();
        return infosCours;
    }

    private Boolean isHour (String debut, String fin){
        Boolean result = false;

        String pattern = "dd-MM-yyyy HH:mm:ss";
        SimpleDateFormat dt = new SimpleDateFormat(pattern);
        //  SimpleDateFormat minute = new SimpleDateFormat("mm");

        Calendar c = Calendar.getInstance();
        //Date dateInstant= c.getTime();

        try {
            Date dateInstant =dt.parse("13-06-2017 11:30:00");
            Date dateDebut = dt.parse(debut);
            Date dateFin  = dt.parse(fin);

            if((dateInstant.compareTo(dateDebut)>0)&&(dateInstant.compareTo(dateFin)<0))
                result=true;
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return result;
    }

    private String chaineDate (String heure, String mDay,String mMonth,String mYear){
        String chaine =null;
        chaine =mDay+"-"+mMonth+"-"+mYear+" "+heure;

        return chaine;
    }

    public ArrayList<String> getListInfoCours(ArrayList<Long> listIdCours){

        ArrayList<String> infosCours = new ArrayList<String>();
        int j =0;
        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+LIB_CLASSE+","+HEURE_DEBUT+","+HEURE_FIN+","+JOUR+","+MOIS+","+ANNEE+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexMois = cursor.getColumnIndex(MOIS);
            int indexHeureDebut = cursor.getColumnIndex(HEURE_DEBUT);
            int indexHeureFin = cursor.getColumnIndex(HEURE_FIN);
            int indexJour = cursor.getColumnIndex(JOUR);
            int indexLibClasse = cursor.getColumnIndex(LIB_CLASSE);
            int indexAnnee = cursor.getColumnIndex(ANNEE);

            do{
                long id = cursor.getInt(indexId);
                String libClass = cursor.getString(indexLibClasse);
                String jour =cursor.getString(indexJour);
                String mois =cursor.getString(indexMois);
                String annee =cursor.getString(indexAnnee);
                String heureDebut = cursor.getString(indexHeureDebut);
                String heureFin = cursor.getString(indexHeureFin);


                for (Long l : listIdCours){
                    if (l==id){
                        String chaine = "Cours:       "+libClass+"\n"+
                                        "Date:         "+jour+"/"+mois+"/"+annee+"\n"+
                                        "Horaires:   "+heureDebut+" - "+heureFin;
                        infosCours.add(j,chaine);
                    }
                }


            }while (cursor.moveToNext());
        }
        close();
        return infosCours;
    }
}
