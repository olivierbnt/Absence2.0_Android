package com.rss.oc.www.absences20.bdd.groupes;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.rss.oc.www.absences20.bdd.DAOBase;

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
}
