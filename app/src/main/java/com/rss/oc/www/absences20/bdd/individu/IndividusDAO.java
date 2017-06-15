package com.rss.oc.www.absences20.bdd.individu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.rss.oc.www.absences20.bdd.DAOBase;

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
    public static final String EMAIL = "email";
    public static final String RUE = "numero_rue";
    public static final String ADRESSE = "adresse";
    public static final String CODE_POSTAL = "code_postal";
    public static final String VILLE = "ville";
    public static final String PAYS = "pays";




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
        value.put(IndividusDAO.EMAIL, i.getId_user());
        value.put(IndividusDAO.RUE, i.getVal_debut());
        value.put(IndividusDAO.ADRESSE, i.getVal_fin());
        value.put(IndividusDAO.CODE_POSTAL, i.getNom());
        value.put(IndividusDAO.VILLE, i.getPrenom());
        value.put(IndividusDAO.PAYS, i.getStatutIndividu());

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

    public String getNomIndividu (long id_individu){

        String name="";

        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+NOM+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexNom = cursor.getColumnIndex(NOM);

            do{

                long individusId = cursor.getInt(indexId);
                String nom = cursor.getString(indexNom);


                if (individusId==id_individu){
                        Log.i("Nom",nom);
                        name=nom;
                    }



            }while (cursor.moveToNext());
        }
        close();

        return name;
    }

    public String getPrenomIndividu (long id_individu){

        String name="";

        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+PRENOM+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexpreNom = cursor.getColumnIndex(PRENOM);

            do{

                long individusId = cursor.getInt(indexId);
                String nom = cursor.getString(indexpreNom);


                if (individusId==id_individu){
                    Log.i("Nom",nom);
                    name=nom;
                }


            }while (cursor.moveToNext());
        }
        close();

        return name;
    }


    public String getEmailIndividu (long id_individu){

        String email="";

        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+EMAIL+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexEmail = cursor.getColumnIndex(EMAIL);

            do{

                long individusId = cursor.getInt(indexId);
                String mail = cursor.getString(indexEmail);


                if (individusId==id_individu){
                    Log.i("Mail",mail);
                    email=mail;
                }


            }while (cursor.moveToNext());
        }
        close();

        return email;
    }

    public String getStatutIndividu (long id_individu){

        String statutIndiv="";

        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+STATUT+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexStatut = cursor.getColumnIndex(STATUT);

            do{

                long individusId = cursor.getInt(indexId);
                String statut = cursor.getString(indexStatut);


                if (individusId==id_individu){

                    statutIndiv=statut;
                }


            }while (cursor.moveToNext());
        }
        close();

        return statutIndiv;
    }

    public String getAdresseIndividu (long id_individu){

        String adresseEtu="";

        openDBRead();

        Cursor cursor = mDb.rawQuery("select " +KEY+","+RUE+","+ADRESSE+","+CODE_POSTAL+","+VILLE+","+PAYS+ " from " + TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexId = cursor.getColumnIndex(KEY);
            int indexRue = cursor.getColumnIndex(RUE);
            int indexADRESSE = cursor.getColumnIndex(ADRESSE);
            int indexCodePostal = cursor.getColumnIndex(CODE_POSTAL);
            int indexVille = cursor.getColumnIndex(VILLE);
            int indexPays = cursor.getColumnIndex(PAYS);
            do{

                long individusId = cursor.getInt(indexId);
                long rue = cursor.getLong(indexRue);
                String adresse = cursor.getString(indexADRESSE);
                long codePostal = cursor.getLong(indexCodePostal);
                String ville = cursor.getString(indexVille);
                String pays = cursor.getString(indexPays);
                if (individusId==id_individu){


                    adresseEtu=rue+" "+adresse+" "+codePostal+" "+ville;
                }


            }while (cursor.moveToNext());
        }
        close();

        return adresseEtu;
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

    public Long getIdIndividu(long id_user){
        long idIndividu=-1;


        openDBRead();
        Cursor cursor = mDb.rawQuery("select " +KEY+","+KEY_USER+ " from "+TABLE_NAME, null);

        if (cursor.moveToNext()){
            int indexIdIndividu = cursor.getColumnIndex(KEY);
            int indexUser = cursor.getColumnIndex(KEY_USER);

            do{


                  Log.i("cherche", String.valueOf(cursor.getLong(indexUser)));
         if(id_user==cursor.getLong(indexUser)){

             idIndividu=cursor.getLong(indexIdIndividu);
             cursor.moveToLast();
         }

            }while (cursor.moveToNext());
        }
        close();


        return idIndividu;
    }

    public void validerPresenceDebut(long id){

        String query = "UPDATE "+TABLE_NAME+" SET "+VAL_DEBUT+" = 1 WHERE "+KEY+" = "+id;
        openDBWrite();
        mDb.execSQL(query);
        close();

    }
    public void validerRetardDebut(long id){

        String query = "UPDATE "+TABLE_NAME+" SET "+VAL_DEBUT+" = 2 WHERE "+KEY+" = "+id;
        openDBWrite();
        mDb.execSQL(query);
        close();

    }

    public void validerPresenceFin(long id){
        String query = "UPDATE "+TABLE_NAME+" SET "+VAL_FIN+" = 1 WHERE "+KEY+" = "+id;
        openDBWrite();
        mDb.execSQL(query);
        close();

    }

    public void resetPresence(long id){
        String query = "UPDATE "+TABLE_NAME+" SET "+VAL_FIN+" = 0"+" , "+VAL_FIN+" = 0 WHERE "+KEY+" = "+id;
        openDBWrite();
        mDb.execSQL(query);
        close();
    }
}
