package com.rss.oc.www.absences20.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.rss.oc.www.absences20.R;
import com.rss.oc.www.absences20.annexe.postRequest;
import com.rss.oc.www.absences20.bdd.Cours.Cours;
import com.rss.oc.www.absences20.bdd.Cours.CoursDAO;
import com.rss.oc.www.absences20.bdd.absences.Absences;
import com.rss.oc.www.absences20.bdd.absences.AbsencesDAO;
import com.rss.oc.www.absences20.bdd.groupe_individu.Groupe_individus;
import com.rss.oc.www.absences20.bdd.groupe_individu.Groupe_individusDAO;
import com.rss.oc.www.absences20.bdd.groupes.Groupes;
import com.rss.oc.www.absences20.bdd.groupes.GroupesDAO;
import com.rss.oc.www.absences20.bdd.individu.Individus;
import com.rss.oc.www.absences20.bdd.individu.IndividusDAO;
import com.rss.oc.www.absences20.bdd.utilisateurs.UtilisateurDAO;
import com.rss.oc.www.absences20.bdd.utilisateurs.Utilisateurs;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChargementActivity extends AppCompatActivity {

    Context context =this;
    public final int VAL_DEBUT=0;
    public final int VAL_FIN=0;
    private String api;
    private View mProgressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargement);
        mProgressView = findViewById(R.id.progressBar2);


        // Synchronisation synchronisation =new Synchronisation();
                // synchronisation.execute((Void)null);
                Base base =new Base();
                base.execute((Void)null);


    }



    public class Synchronisation extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            //api = getApi();

            //Log.i("api",api);
            //getBase("gestion@admin.fr","1234","1c8d10d42f72dafd2be3da81388ffe2c");
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);



        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

    }

    public class Base extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            //createUser("mathilda.thomas@epfedu.fr","12345");
            //api = getApi();
            showProgress(true);

            getBase("gestion@admin.fr","1234","3c95d648d40790b382b8e6b06abb5739");
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            Intent myIntent = new Intent (context,LoginActivity.class);
            startActivity(myIntent);
            finish();

        }
    }


    private String getApi (){
        String mApi = null;

        List pairs = new ArrayList() ;
        //pairs.add(new BasicNameValuePair("login","gestion@admin.fr"));
        //pairs.add(new BasicNameValuePair("password","admiNEPF2017"));
        String urlDuServeur = "https://saliferous-automobi.000webhostapp.com/api/v1/key";
        postRequest Requete = new postRequest();
        Requete.sendRequest(urlDuServeur, pairs);
        mApi = Requete.getResultat();
        return mApi;
    }

    private void createUser(String mLogin, String mPassword){
        List pairs = new ArrayList() ;
        pairs.add(new BasicNameValuePair("login",mLogin));
        pairs.add(new BasicNameValuePair("password",mPassword));
        String urlDuServeur = "https://saliferous-automobi.000webhostapp.com/api/v1/register";
        postRequest maRequete = new postRequest();
        maRequete.sendRequest(urlDuServeur, pairs);
    }
    private void getBase(String mLogin, String mPassword, String api){
        String users;
        String cours;
        String individus;
        String absences;
        String groupeBase;
        String groupe_individusBase;

        List pairs = new ArrayList() ;
        pairs.add(new BasicNameValuePair("login",mLogin));
        pairs.add(new BasicNameValuePair("password",mPassword));
       // pairs.add(new BasicNameValuePair("api_key",api));
        String urlDuServeur = "https://saliferous-automobi.000webhostapp.com/api/v1/all";
        postRequest maRequete = new postRequest();
        maRequete.sendRequest(urlDuServeur, pairs);
        JSONObject jsonResponse = maRequete.getJsonResponse();


        try {
            users = jsonResponse.getString("users");
            JSONArray jsonArrayUsers = new JSONArray(users);
            for (int i =0;i<jsonArrayUsers.length();i++){
                JSONObject jsonObject = jsonArrayUsers.getJSONObject(i);
                long id=jsonObject.getInt("id");
                String identifiant = jsonObject.getString("login");
                String statut=jsonObject.getString("statut");
                String password=jsonObject.getString("password");
                Utilisateurs utilisateurs = new Utilisateurs(id,identifiant,statut,password);
                UtilisateurDAO utilisateurDAO = new UtilisateurDAO(context);
                utilisateurDAO.ajouter(utilisateurs);
            }
            individus = jsonResponse.getString("individus");
            JSONArray jsonArrayIndividus = new JSONArray(individus);
            for(int i=0;i<jsonArrayIndividus.length();i++){
                JSONObject jsonObject =jsonArrayIndividus.getJSONObject(i);
                long id = jsonObject.getInt("id");
                long id_user=jsonObject.getInt("id_user");
                String statut=jsonObject.getString("statut");
                String nom = jsonObject.getString("nom");
                String prenom=jsonObject.getString("prenom");
                long rue=jsonObject.getInt("numero_rue");
                long code_postal=jsonObject.getInt("code_postal");
                String adresse=jsonObject.getString("adresse");
                String ville=jsonObject.getString("ville");
                String pays=jsonObject.getString("pays");
                String email=jsonObject.getString("email");
                Individus individus_epf = new Individus(id,id_user,VAL_DEBUT,VAL_FIN,statut,nom,prenom,email,rue,adresse,code_postal,ville,pays);
                IndividusDAO individusDAO = new IndividusDAO(context);
                individusDAO.ajouterIndividu(individus_epf);
            }

            absences = jsonResponse.getString("absences");
            JSONArray jsonArrayAbsences = new JSONArray(absences);
            for (int i =0;i<jsonArrayAbsences.length();i++){
                JSONObject jsonObject = jsonArrayAbsences.getJSONObject(i);
                long id = jsonObject.getInt("id");
                long id_individu = jsonObject.getInt("id_individu");
                long id_cours = jsonObject.getInt("id_cours");
                String type_individu = jsonObject.getString("type_individu");
                String motif = jsonObject.getString("motif");
                String valeur = jsonObject.getString("valeur");
                Absences absences_epf= new Absences(id,id_individu,id_cours,type_individu,motif,valeur);
                AbsencesDAO absencesDAO = new AbsencesDAO(context);
                absencesDAO.ajouterAbsences(absences_epf);

            }

            groupeBase = jsonResponse.getString("groupes");
            JSONArray jsonArrayGroupes = new JSONArray(groupeBase);
            for (int i=0; i<jsonArrayGroupes.length(); i++ ){
                JSONObject jsonObject = jsonArrayGroupes.getJSONObject(i);
                long id = jsonObject.getInt("id");
                String libelle = jsonObject.getString("libelle");
                Groupes groupes_epf = new Groupes(id,libelle);
                GroupesDAO groupesDAO = new GroupesDAO(context);
                groupesDAO.ajouterGroupes(groupes_epf);

            }

            groupe_individusBase = jsonResponse.getString("groupe_individus");
            JSONArray jsonArrayGroupe_individu = new JSONArray(groupe_individusBase);
            for (int i=0; i<jsonArrayGroupe_individu.length(); i++ ){
                JSONObject jsonObject = jsonArrayGroupe_individu.getJSONObject(i);
                long individus_id = jsonObject.getInt("individus_id");
                long groupe_id = jsonObject.getInt("groupe_id");
                Groupe_individus groupe_individus_epf = new Groupe_individus(individus_id,groupe_id);
                Groupe_individusDAO groupe_individusDAO = new Groupe_individusDAO(context);
                groupe_individusDAO.ajouterGroupe_individus(groupe_individus_epf);

            }

            cours = jsonResponse.getString("cours");
            JSONArray jsonArrayCours = new JSONArray(cours);
            for (int i =0;i<jsonArrayCours.length();i++){
                JSONObject jsonObject=jsonArrayCours.getJSONObject(i);
                long id = jsonObject.getInt("id");
                long id_prof=jsonObject.getInt("id_prof");
                String jour_semaine=jsonObject.getString("jour_semaine");
                String jour = jsonObject.getString("jour");
                String mois=jsonObject.getString("mois");
                String annee=jsonObject.getString("annee");
                String heure_debut = jsonObject.getString("heure_debut");
                String heure_fin=jsonObject.getString("heure_fin");
                String type_enseignement=jsonObject.getString("type_enseignement");
                String groupe=jsonObject.getString("groupe");
                String lib_class=jsonObject.getString("lib_classe");
                String salle_nom=jsonObject.getString("salle_nom");
                String rem_cours=jsonObject.getString("rem_cours");
                Cours cours_classe= new Cours(id,id_prof,jour_semaine,jour,mois,annee,heure_debut,heure_fin,type_enseignement,groupe,lib_class,salle_nom,rem_cours);
                CoursDAO coursDAO = new CoursDAO(context);
                coursDAO.ajouterCours(cours_classe);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
