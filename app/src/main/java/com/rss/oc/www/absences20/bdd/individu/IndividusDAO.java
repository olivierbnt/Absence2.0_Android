package com.rss.oc.www.absences20.bdd.individu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.rss.oc.www.absences20.bdd.Cours.CoursDAO;
import com.rss.oc.www.absences20.bdd.DAOBase;

import java.util.ArrayList;

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

        openDBWrite();
        mDb.insert(IndividusDAO.TABLE_NAME, null, value);
        close();
        Log.i("Table Individus","Ajoutée");
    }

    public ArrayList<String> listIndividusInstant (long [] listId){

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

                for (int i=0;i<listId.length;i++){
                    if (individusId==listId[i]){
                        Log.i("Nom",nom);
                        listFinal.add(j,nom+" "+prenom);
                        i=listId.length;
                        j++;
                    }
                }


            }while (cursor.moveToNext());
        }
        close();

        return listFinal;
    }
}
