package com.rss.oc.www.absences20.activity;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rss.oc.www.absences20.R;
import com.rss.oc.www.absences20.annexe.postRequest;
import com.rss.oc.www.absences20.bdd.Cours.CoursDAO;
import com.rss.oc.www.absences20.bdd.groupe_individu.Groupe_individusDAO;
import com.rss.oc.www.absences20.bdd.groupes.GroupesDAO;
import com.rss.oc.www.absences20.bdd.individu.IndividusDAO;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfesseurAccueilActivity extends AppCompatActivity {
    private static final long RIPPLE_DURATION = 250;
    private static final String TAG = "MainActivity";

    Button button;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;
    View ItemAccueil;
    View ItemAbsence;
    View ItemProfile;
    View ItemParametres;
    View ItemDeconnection;

    Fragment fragment_beacon;
    public BeaconArrayAdapter arrayAdapter;
    ListView mListView;
    boolean profPresent;
    boolean etudiantPresent;
    ArrayList<String> listCoursJournee;
    private String nomGroupe;
    private ListAdapter adapter;
    private long id_individu;
    private long idCoursInstant;
    private String login;

    private Context context = this;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professeur_accueil);
        ButterKnife.bind(this);
        addListenerOnButton();
        Intent intent = getIntent();
        id_individu = 71;//intent.getLongExtra("id_individu", -1);
        login=intent.getStringExtra("login");

        IndividusDAO individusDAO = new IndividusDAO(context);
        Log.i("harris", String.valueOf(id_individu));
        //individusDAO.validerPresenceDebut(id_individu);
        //individusDAO.resetPresence(66);

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine_prof, null);
        root.addView(guillotineMenu);
        //View fragment_beacon =  LayoutInflater.from(this).inflate(R.layout.fragment_main, null);


        // Demand demand = new Demand();
        // demand.execute((Void)null);

        ItemAccueil = guillotineMenu.findViewById(R.id.accueil_group);
        ItemAbsence = guillotineMenu.findViewById(R.id.absence_group);
        ItemProfile = guillotineMenu.findViewById(R.id.profile_group);
        ItemParametres = guillotineMenu.findViewById(R.id.settins_group);
        ItemDeconnection = guillotineMenu.findViewById(R.id.deconnection_group);
        TextView textAc = (TextView) findViewById(R.id.accueil_group_text);
        textAc.setTextColor(getResources().getColor(R.color.selected_item_color));
        mListView = (ListView) findViewById(R.id.liste_des_prochains_cours);








        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        TextView datetxt = (TextView) findViewById(R.id.Date);
        String ct = DateFormat.getDateInstance().format(new Date());
        datetxt.setText(ct);


        Groupe_individusDAO groupe_individusDAO = new Groupe_individusDAO(context);
        GroupesDAO groupesDAO = new GroupesDAO(context);
        nomGroupe = groupesDAO.getNomGroupe(groupe_individusDAO.getIdGroupeIndividu(id_individu));
        Log.i("Groupe",nomGroupe);
        CoursDAO coursDAO = new CoursDAO(context);
        idCoursInstant = coursDAO.getIdCoursInstantProf(id_individu);
        Log.i("idCours", String.valueOf(idCoursInstant));
        if(idCoursInstant!=-1){

            String chaine = coursDAO.getLibelleCoursInstant(idCoursInstant);
            String heureCours = coursDAO.getHeureCoursInstant(idCoursInstant);
            //String chaineDepart = coursDAO.getTempsRestant(idCoursInstant);


            TextView actuelCours = (TextView) findViewById(R.id.CoursActuel2);
            TextView actuelheureCours = (TextView) findViewById(R.id.Date);
            actuelheureCours.setText(heureCours);
            Log.i("chaine",chaine);
            actuelCours.setText(chaine);



        }

        Button bouttonDebutCours = (Button) findViewById(R.id.debutcours);
        bouttonDebutCours.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Demand demand = new Demand();
                demand.execute((Void) null);

            }
        });



        TextView toolbar = (TextView) findViewById(R.id.toolbar_title);
        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        onClickMenu(ItemAccueil, ItemAbsence, ItemParametres, ItemProfile, ItemDeconnection, toolbar, id_individu);



        // IndividusDAO individusDAO =new IndividusDAO(context);
        // individusDAO.validerPresenceDebut(7);



      /*  Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                CoursDAO coursDAO = new CoursDAO(context);
                                idCoursInstant = coursDAO.getIdCoursInstantProf(id_individu);
                                String chaine = coursDAO.getLibelleCoursInstant(idCoursInstant);
                                String heureCours = coursDAO.getHeureCoursInstant(idCoursInstant);
                                //String chaineDepart = coursDAO.getTempsRestant(idCoursInstant);

                                String[] values = new String[] { "Systeme et reseau CM - Amphi B 8h15-10h15", "Systeme et reseau TD - 3L 10h30-12h30", "Systeme et reseau TD - 3L 14h00-16h00",};


                                TextView actuelCours = (TextView) findViewById(R.id.CoursActuel2);
                                TextView actuelheureCours = (TextView) findViewById(R.id.Date);
                                actuelheureCours.setText(heureCours);
                                actuelCours.setText(chaine);
                                Button bouttonDebutCours = (Button) findViewById(R.id.debutcours);
                                bouttonDebutCours.setOnClickListener( new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // TODO faire un truc magique avec le serveur pour enlever le cadenas des etudiants :D

                                    }
                                });

                                adapter = new ArrayAdapter<String>(context, R.layout.row_prochians_cours, R.id.prochain_cours, values);
                                mListView.setAdapter(adapter);

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t2.start();*/

        Thread t3 = new Thread(){
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                CoursDAO coursDAO = new CoursDAO(context);
                                listCoursJournee = coursDAO.getListCoursAvenir2("TICA");
                                adapter = new ArrayAdapter<String>(context, R.layout.row_prochians_cours, R.id.prochain_cours, listCoursJournee);
                                mListView.setAdapter(adapter);


                            }
                        });
                        Thread.sleep(10000);
                    }
                } catch (InterruptedException e) {

                }
            }
        };
        t3.start();



    }

    public class Demand extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {


            //getApi();
            List pairs = new ArrayList() ;
            pairs.add(new BasicNameValuePair("id_cours", String.valueOf(idCoursInstant)));
            pairs.add(new BasicNameValuePair("id_individu",String.valueOf(id_individu)));
            pairs.add(new BasicNameValuePair("api_key","7aa434ccbaefd931459abd17402d4c3d"));
            String urlDuServeur = "https://saliferous-automobi.000webhostapp.com/api/v1/presenceProfesseur";
            postRequest maRequete = new postRequest();
            maRequete.sendRequest(urlDuServeur, pairs);
            String resultat = maRequete.getResultat();

            if (resultat.equals(true))
                toast2();
            else
                toast1();


            return null;
        }
    }


    private void toast1(){
        Toast.makeText(getApplicationContext(), "Echec de connexion",
                Toast.LENGTH_LONG).show();
    }

    private void toast2(){
        Toast.makeText(getApplicationContext(), "Le cours peut commencer !",
                Toast.LENGTH_LONG).show();
    }


    private void onClickMenu(View mViewAc, View mViewAb, View mViewPa, View mViewPr, View mViewDe, final TextView toolbar, final long id_individu) {

        mViewAb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textAc = (TextView) findViewById(R.id.accueil_group_text);
                TextView textAb = (TextView) findViewById(R.id.absence_group_text);
                TextView textPa = (TextView) findViewById(R.id.settins_group_text);
                TextView textPr = (TextView) findViewById(R.id.profile_group_text);
                TextView textDe = (TextView) findViewById(R.id.deconnection_group_text);
                toolbar.setText(getText(R.string.action_absences));
                textAc.setTextColor(getResources().getColor(R.color.white));
                textPa.setTextColor(getResources().getColor(R.color.white));
                textPr.setTextColor(getResources().getColor(R.color.white));
                textDe.setTextColor(getResources().getColor(R.color.white));
                textAb.setTextColor(getResources().getColor(R.color.selected_item_color));
                loadEtudiantAbsencesActivity(id_individu);

            }
        });
        mViewPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView textAc = (TextView) findViewById(R.id.accueil_group_text);
                TextView textAb = (TextView) findViewById(R.id.absence_group_text);
                TextView textPa = (TextView) findViewById(R.id.settins_group_text);
                TextView textPr = (TextView) findViewById(R.id.profile_group_text);
                TextView textDe = (TextView) findViewById(R.id.deconnection_group_text);
                toolbar.setText(getText(R.string.action_profile));
                textAc.setTextColor(getResources().getColor(R.color.white));
                textPa.setTextColor(getResources().getColor(R.color.white));
                textPr.setTextColor(getResources().getColor(R.color.selected_item_color));
                textDe.setTextColor(getResources().getColor(R.color.white));
                textAb.setTextColor(getResources().getColor(R.color.white));
                loadEtudiantProfileActivity(id_individu);

            }
        });
        mViewPa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView textAc = (TextView) findViewById(R.id.accueil_group_text);
                TextView textAb = (TextView) findViewById(R.id.absence_group_text);
                TextView textPa = (TextView) findViewById(R.id.settins_group_text);
                TextView textPr = (TextView) findViewById(R.id.profile_group_text);
                TextView textDe = (TextView) findViewById(R.id.deconnection_group_text);
                toolbar.setText(getText(R.string.action_profile));
                textAc.setTextColor(getResources().getColor(R.color.white));
                textPa.setTextColor(getResources().getColor(R.color.selected_item_color));
                textPr.setTextColor(getResources().getColor(R.color.white));
                textDe.setTextColor(getResources().getColor(R.color.white));
                textAb.setTextColor(getResources().getColor(R.color.white));
                loadEtudiantSettingsActivity(id_individu);
            }
        });
        mViewDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLoginActivity(id_individu);
            }
        });
    }

    public void loadEtudiantAbsencesActivity(long id_individu) {
        Intent myintent = new Intent(this, ProfesseurActivity.class);
        myintent.putExtra("id_individu", id_individu);
        startActivity(myintent);
        finish();
    }

    public void loadEtudiantProfileActivity(long id_individu) {
        Intent myintent = new Intent(this, ProfesseurProfilActivity.class);
        myintent.putExtra("id_individu", id_individu);
        startActivity(myintent);
        finish();
    }

    public void loadEtudiantSettingsActivity(long id_individu) {
        Intent myintent = new Intent(this, ProfesseurSettingsActivity.class);
        myintent.putExtra("id_individu", id_individu);
        startActivity(myintent);
        finish();
    }

    public void loadLoginActivity(long id_individu) {
        Intent myintent = new Intent(this, LoginActivity.class);
        myintent.putExtra("id_individu", id_individu);
        startActivity(myintent);
        finish();
    }

    public void addListenerOnButton() {

        final Context context = this;
    }

    public String validerPresenceDebut(long id_user, long id_cours, String api) {

        String reponse = null;
        List pairs = new ArrayList();
        pairs.add(new BasicNameValuePair("id_etudiant", String.valueOf(id_user)));
        pairs.add(new BasicNameValuePair("id_cours", String.valueOf(id_cours)));
        pairs.add(new BasicNameValuePair("api_key", api));
        String urlDuServeur = "https://saliferous-automobi.000webhostapp.com/api/v1/presenceEtudiant";
        postRequest maRequete = new postRequest();
        maRequete.sendRequest(urlDuServeur, pairs);
        reponse = maRequete.getResultat();
        return reponse;
    }

    public String validerPresenceFin(long id_user, long id_cours, String api) {

        String reponse = null;
        List pairs = new ArrayList();
        pairs.add(new BasicNameValuePair("id_etudiant", String.valueOf(id_user)));
        pairs.add(new BasicNameValuePair("id_cours", String.valueOf(id_cours)));
        pairs.add(new BasicNameValuePair("api_key", api));
        String urlDuServeur = "https://saliferous-automobi.000webhostapp.com/api/v1/validerPresence";
        postRequest maRequete = new postRequest();
        maRequete.sendRequest(urlDuServeur, pairs);
        reponse = maRequete.getResultat();
        return reponse;
    }

    private String getApi() {
        String mApi = null;

        List pairs = new ArrayList();
        pairs.add(new BasicNameValuePair("login", "houpert.frederique@outlook.com"));
        //pairs.add(new BasicNameValuePair("password","admiNEPF2017"));
        String urlDuServeur = "https://saliferous-automobi.000webhostapp.com/api/v1/key";
        postRequest Requete = new postRequest();
        Requete.sendRequest(urlDuServeur, pairs);
        mApi = Requete.getResultat();
        return mApi;
    }

}

