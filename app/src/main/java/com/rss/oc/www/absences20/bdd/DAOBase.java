package com.rss.oc.www.absences20.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by BRICE CESAR on 29/05/2017.
 */

public  abstract class DAOBase {
    // Nous sommes à la première version de la base
    // Si je décide de la mettre à jour, il faudra changer cet attribut
    protected  int VERSION ;
    // Le nom du fichier qui représente ma base
    protected final static String NOM = "zezffefffffffffvbbbbffnnpbbreg.db";

    protected SQLiteDatabase mDb = null;
    protected DataBaseHandler mHandler = null;

    public DAOBase(Context pContext) {
        VERSION = 1;
        this.mHandler = new DataBaseHandler(pContext, NOM, null, VERSION);
    }

    public SQLiteDatabase openDBWrite() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        mDb = mHandler.getWritableDatabase();

        return mDb;
    }
    public SQLiteDatabase openDBRead() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        mDb = mHandler.getReadableDatabase();

        return mDb;
    }



    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }
}
