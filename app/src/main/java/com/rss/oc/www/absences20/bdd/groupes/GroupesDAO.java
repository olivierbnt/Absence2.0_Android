package com.rss.oc.www.absences20.bdd.groupes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.rss.oc.www.absences20.bdd.DAOBase;

import java.util.ArrayList;

/**
 * Created by famille on 09/06/2017.
 */

public class GroupesDAO extends DAOBase {

    private static final String TABLE_NAME = "Groupes";
    private static final String KEY = "id";
    private static final String LIBELLE = "libelle";

    public GroupesDAO(Context pContext) {
        super(pContext);
    }

    public void ajouterGroupes (Groupes g){
        ContentValues value = new ContentValues();
        value.put(GroupesDAO.KEY, g.getId());
        value.put(GroupesDAO.LIBELLE, g.getGroupeLibelle());
        openDBWrite();
        mDb.insert(GroupesDAO.TABLE_NAME, null, value);
        close();
        Log.i("Table Groupes","Ajoutée");
    }

    public long getIdGroupe(String libelle){
        long idFinal=-1;
        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+LIBELLE+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexGroupe = cursor.getColumnIndex(LIBELLE);
            int indexId = cursor.getColumnIndex(KEY);

            do{

                String groupe = cursor.getString(indexGroupe);
                if (libelle.equals(groupe)){
                    idFinal = cursor.getInt(indexId);
                    cursor.moveToLast();
                }


            }while (cursor.moveToNext());
        }
        close();
        return idFinal;
    }
    public String getNomGroupe(ArrayList<Long> idGroupe){
        String groupeFinal="";
        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+LIBELLE+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexGroupe = cursor.getColumnIndex(LIBELLE);
            int indexId = cursor.getColumnIndex(KEY);

            do{

                String groupe = cursor.getString(indexGroupe);
                long id = cursor.getInt(indexId);
                for (long l :idGroupe){

                    if (id==l){
                        groupeFinal = groupeFinal+groupe;

                    }
                }



            }while (cursor.moveToNext());
        }
        close();
        return groupeFinal;
    }


}
