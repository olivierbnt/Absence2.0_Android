package com.rss.oc.www.absences20.bdd.utilisateurs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.rss.oc.www.absences20.bdd.DAOBase;

/**
 * Created by BRICE CESAR on 29/05/2017.
 */

public class UtilisateurDAO extends DAOBase {

    public static final String TABLE_NAME = "Utilisateur";
    public static final String KEY= "id";
    public static final String LOGIN = "login";
    public static final String STATUT = "statut";
    public static final String PASSWORD = "password";



   /* public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                   KEY + " INTEGER " +
                    IDENTIFIANT + " TEXT, " +
                    STATUT + " TEXT, " +
                    PASSWORD + " TEXT);";

    public static final String UTILISATEUR_TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";*/

    public UtilisateurDAO(Context pContext) {
        super(pContext);

    }

    public void ajouter(Utilisateurs u) {



        ContentValues value = new ContentValues();
        value.put(UtilisateurDAO.KEY,u.getId());
        value.put(UtilisateurDAO.LOGIN, u.getLogin());
        value.put(UtilisateurDAO.STATUT, u.getStatut());
        value.put(UtilisateurDAO.PASSWORD, u.getPassword());
        openDBWrite();
        mDb.insert(UtilisateurDAO.TABLE_NAME, null, value);
        close();
        Log.i("sdd","dfsfsf");



    }

    public Boolean verificationConnection (String mPassword, String mLogin){
        openDBRead();
        Boolean test = false;
        String login = null;
        String password = null;
        Cursor cursor = mDb.rawQuery("select "+LOGIN+","+PASSWORD+ " from " + TABLE_NAME , null);
        if (cursor.moveToFirst()) {


            int indexLogin = cursor.getColumnIndex(LOGIN);
            int indexPassword = cursor.getColumnIndex(PASSWORD);
          //  Log.i("Index", String.valueOf(indexLogin));

            do {

                login = cursor.getString(indexLogin);
                Log.i("login",login);
                Log.i("mlogin",mLogin);

                if(mLogin.equals(login)){
                    password = cursor.getString(indexPassword);
                    if(mPassword.equals(password)){
                        test = true;
                        Log.i("trouvé","trouvé");
                    }

                }
            } while (cursor.moveToNext());



        } else {

            Log.i(" pas trouvé","rzggzrgzg");

        }

        close();

        return test;
    }

    public String get_statut(String mLogin){
        String statut = null;
        String login;
        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +LOGIN+","+STATUT+ " from " + TABLE_NAME, null);

        if(cursor.moveToFirst()){
          int indexLogin = cursor.getColumnIndex(LOGIN);
          int indexStatut = cursor.getColumnIndex(STATUT);
            do {

                login = cursor.getString(indexLogin);
                if(mLogin.equals(login))
                    statut = cursor.getString(indexStatut);

            } while (cursor.moveToNext());

        }else{

            Log.i(" pas trouvé","rzggzrgzg");

        }

        close();

        return statut;
    }


    public void supprimer(long id) {


        openDBWrite();
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
        close();


    }




    public void modifier(Utilisateurs u) {
        // CODE
    }


   /* public Utilisateurs selectionner(long id) {

    }*/
}
