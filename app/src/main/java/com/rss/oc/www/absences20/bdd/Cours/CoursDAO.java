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
import java.util.Locale;

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
    private Locale locale= Locale.FRANCE;



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

    public long getIdCoursInstant (String nomGroupe){
        long idFinal=-1;

        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+JOUR+","+MOIS+","+ANNEE+","+HEURE_DEBUT+","+HEURE_FIN+","+GROUPE+ " from " + TABLE_NAME, null);

        if(cursor.moveToNext()){

            int indexId = cursor.getColumnIndex(KEY);
            int indexJour = cursor.getColumnIndex(JOUR);
            int indexMois = cursor.getColumnIndex(MOIS);
            int indexAnnee = cursor.getColumnIndex(ANNEE);
            int indexHeureDebut = cursor.getColumnIndex(HEURE_DEBUT);
            int indexHeureFin = cursor.getColumnIndex(HEURE_FIN);
            int indexGroupe = cursor.getColumnIndex(GROUPE);
            do {

                String jour = cursor.getString(indexJour);
                String mois = cursor.getString(indexMois);
                String annee = cursor.getString(indexAnnee);
                String heureDebut = cursor.getString(indexHeureDebut);
                String heureFin = cursor.getString(indexHeureFin);
                String groupe = cursor.getString(indexGroupe);
                String dateDebut = chaineDate(heureDebut,jour,mois,annee);
                Log.i("dateDebut",dateDebut);
                String dateFin = chaineDate(heureFin,jour,mois,annee);
                Log.i("dateFin",dateFin);
               // Log.i("test", String.valueOf(nomGroupe.indexOf(groupe)));
                if (isHour(dateDebut,dateFin)&&(nomGroupe.indexOf(groupe)!=-1)){
                    idFinal=cursor.getInt(indexId);
                   // Log.i("id", String.valueOf(idFinal));
                    cursor.moveToLast();
                }


            } while (cursor.moveToNext());
        }
          close();
        return idFinal;
    }

    public long getIdCoursInstantProf (long idProf){
        long idFinal=-1;

        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+JOUR+","+MOIS+","+ANNEE+","+HEURE_DEBUT+","+HEURE_FIN+","+KEYPROF+ " from " + TABLE_NAME, null);

        if(cursor.moveToNext()){

            int indexId = cursor.getColumnIndex(KEY);
            int indexJour = cursor.getColumnIndex(JOUR);
            int indexMois = cursor.getColumnIndex(MOIS);
            int indexAnnee = cursor.getColumnIndex(ANNEE);
            int indexHeureDebut = cursor.getColumnIndex(HEURE_DEBUT);
            int indexHeureFin = cursor.getColumnIndex(HEURE_FIN);
            int indexKeyProf = cursor.getColumnIndex(KEYPROF);
            do {

                String jour = cursor.getString(indexJour);
                String mois = cursor.getString(indexMois);
                String annee = cursor.getString(indexAnnee);
                String heureDebut = cursor.getString(indexHeureDebut);
                String heureFin = cursor.getString(indexHeureFin);
                long keyProf = cursor.getLong(indexKeyProf);
                String dateDebut = chaineDate(heureDebut,jour,mois,annee);
                Log.i("dateDebut",dateDebut);
                String dateFin = chaineDate(heureFin,jour,mois,annee);
                Log.i("dateFin",dateFin);
                // Log.i("test", String.valueOf(nomGroupe.indexOf(groupe)));
                if (isHour(dateDebut,dateFin)&&(keyProf==idProf)){
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

    public String getLibelleCoursInstant(long idCoursInstant){

        String cours = null;
        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+LIB_CLASSE+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexlib = cursor.getColumnIndex(LIB_CLASSE);

            do{

                long id = cursor.getInt(indexId);
                if (id==idCoursInstant){
                    cours = cursor.getString(indexlib);
                    cursor.moveToLast();
                }


            }while (cursor.moveToNext());
        }
        close();
        return cours;
    }

    public String getHeureCoursInstant(long idCoursInstant){

        String heure = null;
        String heureDebut =null;
        String heureFin= null;
        String heureDebut1 = null;
        String heureFin1 = null;
        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+HEURE_DEBUT+","+HEURE_FIN+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexHeureDebut = cursor.getColumnIndex(HEURE_DEBUT);
            int indexHeureFin = cursor.getColumnIndex(HEURE_FIN);

            do{

                long id = cursor.getInt(indexId);

                if (id==idCoursInstant){
                   heureDebut = cursor.getString(indexHeureDebut);
                    heureDebut1 = heureDebut.substring(0,5);
                    heureFin  = cursor.getString(indexHeureFin);
                    heureFin1 = heureFin.substring(0,5);
                    cursor.moveToLast();
                }


            }while (cursor.moveToNext());
        }



        heure = heureDebut1+" - "+heureFin1;
        close();
        return heure;
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


    public String getTempsRestant(long idCoursInstant){

        String heure = null;
        String heureDebut =null;
        String heureFin= null;
        long dureeMilli=0L;
        long duree=0;
        long heureI=0;
        long minuteI=0;
        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+HEURE_DEBUT+","+HEURE_FIN+","+ANNEE+","+MOIS+","+JOUR+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexHeureDebut = cursor.getColumnIndex(HEURE_DEBUT);
            int indexHeureFin = cursor.getColumnIndex(HEURE_FIN);
            int indexAnneFin = cursor.getColumnIndex(ANNEE);
            int indexMoisFin = cursor.getColumnIndex(MOIS);
            int indexJourFin = cursor.getColumnIndex(JOUR);

            do{

                long id = cursor.getInt(indexId);
                String anneeFin=cursor.getString(indexAnneFin);
                String moisFin=cursor.getString(indexMoisFin);
                String joursFin=cursor.getString(indexJourFin);

                if (id==idCoursInstant){

                    heureDebut = cursor.getString(indexHeureDebut);
                    heureFin  = cursor.getString(indexHeureFin);

                    Calendar c = Calendar.getInstance(locale);
                    Date timeInstant = c.getTime();

                    Log.i("dateTime",timeInstant.toString());
                        String heureIChaine = String.valueOf(timeInstant);
                         heureI= Long.parseLong(heureIChaine.substring(11,13))*60;
                        Log.i("heureI", String.valueOf(heureI));

                         minuteI= Long.parseLong(heureIChaine.substring(14,16));
                        Log.i("minuteI", String.valueOf(minuteI));


                    Log.i("calendar", String.valueOf(timeInstant));

                    Long heureF = Long.parseLong(heureFin.substring(0,2))*60;
                    Log.i("heureF", String.valueOf(heureF));
                    Long minuteF = Long.parseLong(heureFin.substring(3,5));
                    Log.i("minuteF", String.valueOf(minuteF));

                     duree= heureF+minuteF-(heureI+minuteI);

                    Log.i("durée", String.valueOf(duree));

                        heure = "Validez votre sortie dans "+duree+" min" ;




                    cursor.moveToLast();
                }


            }while (cursor.moveToNext());
        }
        close();
        return heure;
    }






    private Boolean isHour (String debut, String fin){
        Boolean result = false;

        String pattern = "dd-MM-yyyy HH:mm:ss";
        SimpleDateFormat dt = new SimpleDateFormat(pattern);
        //  SimpleDateFormat minute = new SimpleDateFormat("mm");

        Calendar c = Calendar.getInstance();
        Date dateInstant= c.getTime();

        try {
            //Date dateInstant =dt.parse("13-06-2017 11:30:00");
            Date dateDebut = dt.parse(debut);
            Date dateFin  = dt.parse(fin);


            if((dateInstant.compareTo(dateDebut)>0)&&(dateInstant.compareTo(dateFin)<0))
                result=true;
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return result;
    }

    public ArrayList<String> getListCoursAvenir (long idCoursInstant,String libelleGroupe){
        ArrayList<String> list = new ArrayList<String>();

        Calendar c = Calendar.getInstance();
        String mJour=null;
        String mMois=null;
        String mAnnee=null;
        String resultat=null;
        int j=0;

        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+HEURE_DEBUT+","+HEURE_FIN+","+LIB_CLASSE+","+SALLE_NOM+","+JOUR+","+MOIS+","+ANNEE+","+GROUPE+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexJour = cursor.getColumnIndex(JOUR);
            int indexMois = cursor.getColumnIndex(MOIS);
            int indexAnnee = cursor.getColumnIndex(ANNEE);

            do{

                String jour = cursor.getString(indexJour);
                String mois = cursor.getString(indexMois);
                String annee = cursor.getString(indexAnnee);

                long id = cursor.getInt(indexId);

                if (id==idCoursInstant){

                    mJour=jour;
                    mMois=mois;
                    mAnnee=annee;

                    cursor.moveToLast();
                }

            }while (cursor.moveToNext());
        }

        cursor.moveToFirst();
        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexGroupe = cursor.getColumnIndex(GROUPE);
            int indexHeureDebut = cursor.getColumnIndex(HEURE_DEBUT);
            int indexHeureFin = cursor.getColumnIndex(HEURE_FIN);
            int indexSalleNom = cursor.getColumnIndex(SALLE_NOM);
            int indexLibClasse = cursor.getColumnIndex(LIB_CLASSE);
            int indexJour = cursor.getColumnIndex(JOUR);
            int indexMois = cursor.getColumnIndex(MOIS);
            int indexAnnee = cursor.getColumnIndex(ANNEE);

            do{

                String jourbis = cursor.getString(indexJour);
                String groupe = cursor.getString(indexGroupe);
                String moisbis = cursor.getString(indexMois);
                String anneebis = cursor.getString(indexAnnee);
                String heureDebut = cursor.getString(indexHeureDebut);
                String heureFin = cursor.getString(indexHeureFin);
                String dateDebut = chaineDate(heureDebut,jourbis,moisbis,anneebis);
                String dateFin = chaineDate(heureFin,jourbis,moisbis,anneebis);
                String lib = cursor.getString(indexLibClasse);
                String salle = cursor.getString(indexSalleNom);
                long id = cursor.getInt(indexId);


                Log.i("DATE_DATE",jourbis+" "+moisbis+" "+anneebis+" "+groupe+" "+libelleGroupe+" "+libelleGroupe.indexOf(groupe)+" "+mJour+" "+mMois+" "+mAnnee);


                if ((mJour.equals(jourbis)) && (mMois.equals(moisbis)) && (mAnnee.equals(anneebis)) && (libelleGroupe.indexOf(groupe)!=-1)){



                     String pattern = "dd-MM-yyyy HH:mm:ss";
                     SimpleDateFormat dt = new SimpleDateFormat(pattern);
                    try {
                        Date debut = dt.parse(dateDebut);
                        Date instant = c.getTime();
                        //Date instant=dt.parse("13-06-2017 11:30:00");
                        Boolean valeur = isHour(dateDebut,dateFin);

                        if(valeur==true){

                            resultat = lib+"\n"+heureDebut.substring(0,5)+
                                    "-"+heureFin.substring(0,5)+"\n"+
                                    salle+"   En cours";
                            list.add(j,resultat);
                            j++;
                        }
                        else{
                            if(instant.compareTo(debut)>0){
                                resultat = lib+"\n"+heureDebut.substring(0,5)+
                                            "-"+heureFin.substring(0,5)+"\n"+
                                            salle+"  Terminé";
                                list.add(j,resultat);
                                j++;
                            }
                            else{

                                resultat = lib+"\n"+heureDebut.substring(0,5)+
                                        "-"+heureFin.substring(0,5)+"\n"+
                                        salle+"     A venir";
                                list.add(j,resultat);
                                j++;
                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }



                }

            }while (cursor.moveToNext());
        }
        close();

        return list;
    }

    public ArrayList<String> getListCoursAvenir2 (String libelleGroupe){
        ArrayList<String> list = new ArrayList<String>();

        Calendar c = Calendar.getInstance(locale);
        Date now = c.getTime();
        Log.i("string",now.toString());
        String mJour= "19";
        String mMois= "06";
        String mAnnee= "2017";
        String resultat=null;
        int j=0;

        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+HEURE_DEBUT+","+HEURE_FIN+","+LIB_CLASSE+","+SALLE_NOM+","+JOUR+","+MOIS+","+ANNEE+","+GROUPE+ " from " + TABLE_NAME, null);


        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexGroupe = cursor.getColumnIndex(GROUPE);
            int indexHeureDebut = cursor.getColumnIndex(HEURE_DEBUT);
            int indexHeureFin = cursor.getColumnIndex(HEURE_FIN);
            int indexSalleNom = cursor.getColumnIndex(SALLE_NOM);
            int indexLibClasse = cursor.getColumnIndex(LIB_CLASSE);
            int indexJour = cursor.getColumnIndex(JOUR);
            int indexMois = cursor.getColumnIndex(MOIS);
            int indexAnnee = cursor.getColumnIndex(ANNEE);

            do{

                String jourbis = cursor.getString(indexJour);
                String groupe = cursor.getString(indexGroupe);
                String moisbis = cursor.getString(indexMois);
                String anneebis = cursor.getString(indexAnnee);
                String heureDebut = cursor.getString(indexHeureDebut);
                String heureFin = cursor.getString(indexHeureFin);
                String dateDebut = chaineDate(heureDebut,jourbis,moisbis,anneebis);
                String dateFin = chaineDate(heureFin,jourbis,moisbis,anneebis);
                String lib = cursor.getString(indexLibClasse);
                String salle = cursor.getString(indexSalleNom);
                long id = cursor.getInt(indexId);


                Log.i("DATE_DATE",jourbis+" "+moisbis+" "+anneebis+" "+groupe+" "+libelleGroupe+" "+libelleGroupe.indexOf(groupe)+" "+mJour+" "+mMois+" "+mAnnee);


                if ((mJour.equals(jourbis)) && (mMois.equals(moisbis)) && (mAnnee.equals(anneebis)) && (libelleGroupe.indexOf(groupe)!=-1)){



                    String pattern = "dd-MM-yyyy HH:mm:ss";
                    SimpleDateFormat dt = new SimpleDateFormat(pattern);
                    try {
                        Date debut = dt.parse(dateDebut);
                        Date instant = c.getTime();
                        //Date instant=dt.parse("13-06-2017 11:30:00");
                        Boolean valeur = isHour(dateDebut,dateFin);

                        if(valeur==false){

                            if(instant.compareTo(debut)>0){
                                resultat = lib+"\n"+heureDebut.substring(0,5)+
                                        "-"+heureFin.substring(0,5)+"\n"+
                                        salle+"  Terminé";
                                list.add(j,resultat);
                                j++;
                            }
                        }
                        else{

                            resultat = lib+"\n"+heureDebut.substring(0,5)+
                                    "-"+heureFin.substring(0,5)+"\n"+
                                    salle+"   En cours";
                            list.add(j,resultat);
                            j++;
                        }

                        if (valeur==false&&instant.compareTo(debut)<=0){

                            resultat = lib+"\n"+heureDebut.substring(0,5)+
                                    "-"+heureFin.substring(0,5)+"\n"+
                                    salle+"     A venir";
                            list.add(j,resultat);
                            j++;

                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }



                }

            }while (cursor.moveToNext());
        }

        close();
        return list;
    }

    public Boolean A_LHEURE (long idCoursInstant){
        Boolean valeur = false;
        Calendar cProf = Calendar.getInstance();
        String heure = null;
        String heureDebut =null;
        String heureFin= null;
        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+HEURE_DEBUT+","+HEURE_FIN+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexHeureDebut = cursor.getColumnIndex(HEURE_DEBUT);
            int indexHeureFin = cursor.getColumnIndex(HEURE_FIN);

            do{

                long id = cursor.getInt(indexId);

                if (id==idCoursInstant){

                    heureDebut = cursor.getString(indexHeureDebut);
                    heureFin  = cursor.getString(indexHeureFin);
                    Calendar c = Calendar.getInstance();
                    Date dateInstant = c.getTime();
                    long hi = Long.parseLong(dateInstant.toString().substring(11,13));
                    long mi = Long.parseLong(dateInstant.toString().substring(14,16));
                    long heureInst = hi*60 + mi;

                    long h = Long.parseLong(heureDebut.substring(0,2))*60;
                    long m = Long.parseLong(heureDebut.substring(3,5));

                    long heureD = h+m;

                    long heureRetard = heureD + 5;


                    if(heureInst<heureRetard&&heureInst>heureD)
                        valeur = true;

                    cursor.moveToLast();
                }


            }while (cursor.moveToNext());
        }

         close();
        return valeur;
    }

    public Boolean EN_RETARD (long idCoursInstant){
        Boolean valeur = false;
        Calendar cProf= Calendar.getInstance();
        String heureDebut =null;
        String heureFin= null;
        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+HEURE_DEBUT+","+HEURE_FIN+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexHeureDebut = cursor.getColumnIndex(HEURE_DEBUT);
            int indexHeureFin = cursor.getColumnIndex(HEURE_FIN);

            do{

                long id = cursor.getInt(indexId);

                if (id==idCoursInstant){

                    heureDebut = cursor.getString(indexHeureDebut);
                    heureFin  = cursor.getString(indexHeureFin);
                    Calendar c = Calendar.getInstance();
                    Date dateInstant = c.getTime();
                    long hi = Long.parseLong(dateInstant.toString().substring(11,13));
                    long mi = Long.parseLong(dateInstant.toString().substring(14,16));
                    long heureInst = hi*60 + mi;

                    long h = Long.parseLong(heureDebut.substring(0,2))*60;
                    long m = Long.parseLong(heureDebut.substring(3,5));

                    long heureD = h+m;

                    long heureRetard = heureD + 5;
                    long heureAbsence = heureD + 15;


                    if(heureInst>heureRetard&&heureInst<heureAbsence)
                        valeur = true;

                    cursor.moveToLast();
                }


            }while (cursor.moveToNext());
        }


        return valeur;
    }

    public Boolean A_LA_FIN (long idCoursInstant){
        Boolean valeur = false;

        String heureDebut =null;
        String heureFin= null;
        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+HEURE_DEBUT+","+HEURE_FIN+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexHeureDebut = cursor.getColumnIndex(HEURE_DEBUT);
            int indexHeureFin = cursor.getColumnIndex(HEURE_FIN);

            do{

                long id = cursor.getInt(indexId);

                if(id==idCoursInstant){

                    heureDebut = cursor.getString(indexHeureDebut);
                    heureFin  = cursor.getString(indexHeureFin);
                    Calendar c = Calendar.getInstance();
                    Date dateInstant = c.getTime();
                    long hi = Long.parseLong(dateInstant.toString().substring(11,13));
                    long mi = Long.parseLong(dateInstant.toString().substring(14,16));
                    long heureInst = hi*60 + mi;
                    long h = Long.parseLong(heureFin.substring(0,2))*60;
                    long m = Long.parseLong(heureFin.substring(3,5));

                    long heureF = h+m;

                   Log.i("hbdddddd", String.valueOf(heureInst));

                    if(heureInst<heureF+5&&heureInst>heureF-5)
                        valeur = true;


                    cursor.moveToLast();
                }



            }while (cursor.moveToNext());
        }


        return valeur;
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
