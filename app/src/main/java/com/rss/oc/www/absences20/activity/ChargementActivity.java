package com.rss.oc.www.absences20.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.rss.oc.www.absences20.R;
import com.rss.oc.www.absences20.annexe.GetMyJson;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class ChargementActivity extends AppCompatActivity {

    Context context =this;
    public final int VAL_DEBUT=0;
    public final int VAL_FIN=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargement);
        Button button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Synchronisation synchronisation =new Synchronisation();
                synchronisation.execute((Void)null);
            }
        });

    }



       public class Synchronisation extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            base();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }


    private void base(){

        String users;
        String cours;
        String individus;
        String absences;
        String groupeBase;
        String groupe_individusBase;

        try {
            URL url = new URL("https://saliferous-automobi.000webhostapp.com/api/v1/all?login=gestion@admin.fr&password=admiNEPF2017&api_key=9b6292e91c2525e8e36c52d5cae4e268");
            GetMyJson get = new GetMyJson();
            JSONObject jsonResponse = get.myJson(url);
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

                individus = jsonResponse.getString("individus");
                JSONArray jsonArrayIndividus = new JSONArray(individus);
                for(int i=0;i<jsonArrayIndividus.length();i++){
                    JSONObject jsonObject =jsonArrayIndividus.getJSONObject(i);
                    long id = jsonObject.getInt("id");
//                    long id_user=jsonObject.getInt("id_user");
                    String statut=jsonObject.getString("statut");
                    String nom = jsonObject.getString("nom");
                    String prenom=jsonObject.getString("prenom");
                    Individus individus_epf = new Individus(id,0,VAL_DEBUT,VAL_FIN,statut,nom,prenom);
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

               // Log.i("JsonArray",jsonArray.toString());
                //Log.i("statut",statut);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Log.i("Json",jsonResponse.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
