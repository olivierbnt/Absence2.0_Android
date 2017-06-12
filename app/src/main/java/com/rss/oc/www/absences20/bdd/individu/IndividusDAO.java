package com.rss.oc.www.absences20.bdd.individu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.rss.oc.www.absences20.bdd.Cours.CoursDAO;
import com.rss.oc.www.absences20.bdd.DAOBase;
import com.rss.oc.www.absences20.bdd.DataBaseHandler;

import java.util.ArrayList;

/**
 * Created by BRICE CESAR on 29/05/2017.
 */

public class IndividusDAO extends DAOBase {

    public static final String TABLE_NAME = "Individus";
    public static final String KEY = "id";
    public static final String NOM = "nom";
    public static final String VAL_DEBUT = "val_debut";
    public static final String VAL_FIN = "val_fin";
    public static final String PRENOM = "prenom";
    public static final String KEY_USER = "id_user";
    public static final String STATUT = "statut";



    public IndividusDAO(Context pContext) {
        super(pContext);
    }

    public void ajouterIndividu(Individus i){

        ContentValues value = new ContentValues();
        value.put(IndividusDAO.KEY, i.getIdIndividu());
        value.put(IndividusDAO.KEY_USER, i.getId_user());
        value.put(IndividusDAO.VAL_DEBUT, i.getVal_debut());
        value.put(IndividusDAO.VAL_FIN, i.getVal_fin());
        value.put(IndividusDAO.NOM, i.getNom());
        value.put(IndividusDAO.PRENOM, i.getPrenom());
        value.put(IndividusDAO.STATUT, i.getStatutIndividu());

        openDBWrite();
        mDb.insert(IndividusDAO.TABLE_NAME, null, value);
        close();
        Log.i("Table Individus","Ajoutée");
    }

    public ArrayList<String> listIndividusInstant (ArrayList<Long> listId){

        ArrayList<String> listFinal= new ArrayList<String>();
        int j = 0;
        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+NOM+","+PRENOM+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexNom = cursor.getColumnIndex(NOM);
            int indexPrenom = cursor.getColumnIndex(PRENOM);

            do{

                long individusId = cursor.getInt(indexId);
                String nom = cursor.getString(indexNom);
                String prenom = cursor.getString(indexPrenom);


                for (long l: listId){
                    if (individusId==l){
                        Log.i("Nom",nom);
                        listFinal.add(j,nom+" "+prenom);
                        j++;
                    }
                }


            }while (cursor.moveToNext());
        }
        close();

        return listFinal;
    }

    public ArrayList<Long> listIndicateur (ArrayList<Long> list){
        ArrayList<Long> listFinal= new ArrayList<Long>();
        int j = 0;
        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+VAL_DEBUT+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexVal_debut = cursor.getColumnIndex(VAL_DEBUT);

            do{

                long individusId = cursor.getInt(indexId);
                long val_debut = cursor.getInt(indexVal_debut);

                for (long l: list){
                    if (individusId==l){

                        listFinal.add(j,val_debut);
                        j++;
                    }
                }


            }while (cursor.moveToNext());
        }
        close();

        return listFinal;
    }

    public Long getIdIndividu(Long idUser){
        long idIndividu=-1;

        openDBRead();
        Cursor cursor = mDb.rawQuery("select " +KEY_USER+","+KEY+ " from "+TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexIdIndividu = cursor.getColumnIndex(KEY);
            int indexIdUsers = cursor.getColumnIndex(KEY_USER);

            do{

         if(idUser==cursor.getLong(indexIdUsers)){
             idIndividu=cursor.getLong(indexIdIndividu);
             cursor.moveToLast();
         }

            }while (cursor.moveToNext());
        }
        close();


        return idIndividu;
    }

    public void validerPresenceDebut(long id){
        ContentValues value = new ContentValues();
        value.put(IndividusDAO.VAL_DEBUT,1);
        mDb.update(TABLE_NAME,value,"id="+id,null);
    }
    public void validerRetardDebut(long id){

        ContentValues value = new ContentValues();
        value.put(IndividusDAO.VAL_DEBUT,2);
        mDb.update(TABLE_NAME,value,"id="+id,null);
    }

    public void validerPresenceFin(long id){
        ContentValues value = new ContentValues();
        value.put(IndividusDAO.VAL_FIN,1);
        mDb.update(TABLE_NAME,value,"id="+id,null);
    }

    public void resetPresence(long id){
        ContentValues value = new ContentValues();
        value.put(IndividusDAO.VAL_DEBUT,0);
        value.put(IndividusDAO.VAL_FIN,0);
        mDb.update(TABLE_NAME,value,"id="+id,null);
    }
}
