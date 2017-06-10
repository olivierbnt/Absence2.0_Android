package com.rss.oc.www.absences20.bdd.groupe_individu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.rss.oc.www.absences20.bdd.DAOBase;

/**
 * Created by famille on 09/06/2017.
 */

public class Groupe_individusDAO extends DAOBase {

    public static final String INDIVIDUS_KEY = "individus_id";
    public static final String GROUPE_ID = "groupe_id";
    public static final String TABLE_NAME ="groupe_individus";

    public Groupe_individusDAO(Context pContext) {
        super(pContext);
    }

    public void ajouterGroupe_individus (Groupe_individus g){
        ContentValues value = new ContentValues();
        value.put(Groupe_individusDAO.INDIVIDUS_KEY, g.getIndividus_id());
        value.put(Groupe_individusDAO.GROUPE_ID, g.getGroupe_id());
        openDBWrite();
        mDb.insert(Groupe_individusDAO.TABLE_NAME, null, value);
        close();
        Log.i("Table Groupe_individus","Ajoutée");
    }

    public long [] listIdIndividusInstant (long idGroupeInstant){

        long[] listFinal= new long[75];
        int i =0;

        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +INDIVIDUS_KEY+","+GROUPE_ID+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexIndividusId = cursor.getColumnIndex(INDIVIDUS_KEY);
            int indexGroupeId = cursor.getColumnIndex(GROUPE_ID);

            do{

                long individusId = cursor.getInt(indexIndividusId);
                long groupeId = cursor.getInt(indexGroupeId);
                if (idGroupeInstant==groupeId){

                    listFinal[i] = individusId;
                    i++;
                }


            }while (cursor.moveToNext());
        }
        close();

        return listFinal;
    }

}
