package com.rss.oc.www.absences20.activity;


import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.DateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rss.oc.www.absences20.R.drawable.my_progress;

public class MainActivity extends AppCompatActivity {
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
    ProgressBar progressBar;
    Fragment fragment_beacon;
    public BeaconArrayAdapter arrayAdapter;
    ListView mListView;
    boolean profPresent =false;
    boolean dejaPresentEtudiant =false;
    boolean etudiantPresentDebut=false;
    boolean etudiantPresentFin=false;
    private Date dateProf;
    ArrayList<String> listCoursJournee;
    private String nomGroupe;
    private ListAdapter adapter;
    private Thread t;

    private long id_individu;
    private long idCoursInstant;
    private Context context = this;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    public int inst=0;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addListenerOnButton();
        Intent intent = getIntent();
        id_individu = 66;//intent.getLongExtra("id_individu", -1);
        fragment_beacon = new BeaconViewerFragment();


        IndividusDAO individusDAO = new IndividusDAO(context);
        Log.i("harris", String.valueOf(id_individu));
        //individusDAO.validerPresenceDebut(id_individu);
        //individusDAO.resetPresence(66);

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);
        //View fragment_beacon =  LayoutInflater.from(this).inflate(R.layout.fragment_main, null);


        ItemAccueil = guillotineMenu.findViewById(R.id.accueil_group);
        ItemAbsence = guillotineMenu.findViewById(R.id.absence_group);
        ItemProfile = guillotineMenu.findViewById(R.id.profile_group);
        ItemParametres = guillotineMenu.findViewById(R.id.settins_group);
        ItemDeconnection = guillotineMenu.findViewById(R.id.deconnection_group);
        TextView textAc = (TextView) findViewById(R.id.accueil_group_text);
        textAc.setTextColor(getResources().getColor(R.color.selected_item_color));
        progressBar = (ProgressBar) findViewById(R.id.progressBarBeacon);
        final ImageView aPresent = (ImageView) findViewById(R.id.arrivee_present);
        final ImageView aAbsent = (ImageView) findViewById(R.id.arrivee_absent);
        final ImageView dPresent = (ImageView) findViewById(R.id.depart_present);
        final ImageView dAbsent = (ImageView) findViewById(R.id.depart_absent);
        final ImageView cadenasImage = (ImageView) findViewById(R.id.cadenasImage);
        final TextView confirmation = (TextView) findViewById(R.id.confirmation);
        final TextView raprochez = (TextView) findViewById(R.id.raprochez);
        final TextView cadenasTexte = (TextView) findViewById(R.id.cadenasText);
        mListView = (ListView) findViewById(R.id.liste_des_prochains_cours);
        listCoursJournee = new ArrayList<String>();


        //validerPresenceDebut(96,idCoursInstant,"fvfvvzv");


        confirmation.setVisibility(View.INVISIBLE);
        aPresent.setVisibility(View.INVISIBLE);
        dPresent.setVisibility(View.INVISIBLE);


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



        TextView toolbar = (TextView) findViewById(R.id.toolbar_title);
        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();


        // IndividusDAO individusDAO =new IndividusDAO(context);
        // individusDAO.validerPresenceDebut(7);

        final BeaconViewerFragment fr = (BeaconViewerFragment) getFragmentManager().findFragmentById(R.id.fragment_beacon);




        Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                CoursDAO coursDAO = new CoursDAO(context);
                                 idCoursInstant = coursDAO.getIdCoursInstant(nomGroupe);
                                if(idCoursInstant!=-1){

                                    String chaine = coursDAO.getLibelleCoursInstant(idCoursInstant);
                                    String heureCours = coursDAO.getHeureCoursInstant(idCoursInstant);
                                    String chaineDepart = coursDAO.getTempsRestant(idCoursInstant);
                                    Log.i(TAG, "idCoursInstant" + idCoursInstant);
                                    Log.i("cours",chaine);


                                    TextView actuelCours = (TextView) findViewById(R.id.CoursActuel2);
                                    TextView actuelheureCours = (TextView) findViewById(R.id.textView);
                                    TextView depart = (TextView) findViewById(R.id.depart);
                                    actuelheureCours.setText(heureCours);
                                    actuelCours.setText(chaine);
                                    depart.setText(chaineDepart);



                                }

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };





       Thread t3 = new Thread(){
           @Override
           public void run() {
               try {
                   while (!isInterrupted()) {

                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {

                               CoursDAO coursDAO = new CoursDAO(context);
                               listCoursJournee = coursDAO.getListCoursAvenir2(nomGroupe);
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

        Thread t4 = new Thread(){
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                               Demand demand = new Demand();
                                demand.execute((Void)null);

                            }
                        });
                        Thread.sleep(3000);
                    }
                } catch (InterruptedException e) {

                }
            }
        };
        Thread t5 = new Thread(){
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                SituationEtudiant situationEtudiant = new SituationEtudiant();
                                situationEtudiant.execute((Void)null);
                                if(etudiantPresentDebut==true){

                                    aPresent.setVisibility(View.VISIBLE);
                                    aAbsent.setVisibility(View.INVISIBLE);
                                    confirmation.setVisibility(View.VISIBLE);
                                    raprochez.setVisibility(View.INVISIBLE);

                                }

                            }
                        });
                        Thread.sleep(3000);
                    }
                } catch (InterruptedException e) {

                }
            }
        };


        t3.start();
        t2.start();

        if(idCoursInstant!=-1){

            t5.start();
            t4.start();
        }




        if (fr != null) {


            // nouveau thread pour mettre a jour la progressBar afin de representer les beacons

            // Demande des autorisations pour utiliser le Bluetooth
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(this, "The permission to get BLE location data is required", Toast.LENGTH_SHORT).show();
                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            } else {
                Toast.makeText(this, "Location permissions already granted", Toast.LENGTH_SHORT).show();
            }
            Log.d(TAG, "Autorisations done (Bluetooth)");


            arrayAdapter = fr.getMyList();
            Log.i(TAG, "ArrayAdapterUpdate");

            //Nouveau thread pour la recuperation des beacons
             t = new Thread() {
                @Override
                public void run() {
                    try {
                        while (!isInterrupted()) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    //prepare for a progress bar dialog
                                    int nombreBeacons = arrayAdapter.getCount();
                                    //for (int i = 0; i < arrayAdapter.getCount(); i++){
                                    // }
                                    progressBar.setMax(4);
                                    progressBar.setProgress(nombreBeacons);
                                    progressBar.setProgressDrawable(getResources().getDrawable(my_progress));


                                    Log.i("profPresent", String.valueOf(profPresent));
                                    Log.i("etudiantPresenDebut", String.valueOf(etudiantPresentDebut));
                                    if (profPresent == true) {


                                        cadenasTexte.setVisibility(View.INVISIBLE);
                                        cadenasImage.setVisibility(View.INVISIBLE);
                                         if(etudiantPresentDebut==true){
                                             aPresent.setVisibility(View.VISIBLE);
                                             aAbsent.setVisibility(View.INVISIBLE);
                                             confirmation.setVisibility(View.VISIBLE);
                                             raprochez.setVisibility(View.INVISIBLE);
                                         }
                                         else
                                             aAbsent.setVisibility(View.VISIBLE);


                                        if (nombreBeacons != 4) {
                                            CoursDAO coursDAO = new CoursDAO(context);

                                            if (etudiantPresentFin==false){

                                                if(etudiantPresentDebut==false){


                                                    if(coursDAO.A_LHEURE(idCoursInstant)==true){

                                                        PresenceDebut presence = new PresenceDebut();
                                                        presence.execute((Void)null);
                                                        etudiantPresentDebut = true;
                                                        aPresent.setVisibility(View.VISIBLE);
                                                        aAbsent.setVisibility(View.INVISIBLE);
                                                        confirmation.setVisibility(View.VISIBLE);
                                                        raprochez.setVisibility(View.INVISIBLE);
                                                    }
                                                    else if (coursDAO.EN_RETARD(idCoursInstant)==true){

                                                        PresenceDebut presence = new PresenceDebut();
                                                        presence.execute((Void)null);
                                                        etudiantPresentDebut = true;
                                                        aPresent.setVisibility(View.VISIBLE);
                                                        aAbsent.setVisibility(View.INVISIBLE);
                                                        confirmation.setVisibility(View.VISIBLE);
                                                        raprochez.setVisibility(View.INVISIBLE);

                                                    }

                                                }
                                                else {

                                                    if(coursDAO.A_LA_FIN(idCoursInstant)==true){

                                                        PresenceFin presenceFin = new PresenceFin();
                                                        presenceFin.execute((Void)null);
                                                        etudiantPresentFin=true;
                                                        dPresent.setVisibility(View.VISIBLE);
                                                        dAbsent.setVisibility(View.INVISIBLE);
                                                    }

                                                }

                                            }

                                        } else {
                                            confirmation.setVisibility(View.INVISIBLE);
                                            raprochez.setVisibility(View.VISIBLE);
                                        }
                                    } else {
                                        aAbsent.setVisibility(View.INVISIBLE);
                                        aPresent.setVisibility(View.INVISIBLE);
                                        cadenasTexte.setVisibility(View.VISIBLE);
                                        cadenasImage.setVisibility(View.VISIBLE);

                                    }
                                }
                            });

                            Thread.sleep(3000);
                        }
                    } catch (InterruptedException e) {
                    }
                }
            };

            t.start();

            // ... do some fun stuff
        }

        onClickMenu(ItemAccueil, ItemAbsence, ItemParametres, ItemProfile, ItemDeconnection, toolbar, id_individu,t,t2,t3,t4,t5);




    }

    public class Demand extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {


            String reponse = null;
            List pairs = new ArrayList();
            pairs.add(new BasicNameValuePair("id_cours", String.valueOf(idCoursInstant)));
            pairs.add(new BasicNameValuePair("id_individu", String.valueOf(71)));
            //pairs.add(new BasicNameValuePair("api_key", api));
            String urlDuServeur = "https://saliferous-automobi.000webhostapp.com/api/v1/estPresentProfesseur";
            postRequest maRequete = new postRequest();
            maRequete.sendRequest(urlDuServeur, pairs);
            reponse = maRequete.getResultat();
            if(reponse.equals("true")){
                profPresent = true;
            }
            else
                Log.i("oooooooooo","ooooooooo");

            return null;
        }
    }

    public class PresenceDebut extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {

           //  getApi();
         validerPresenceDebut(66,idCoursInstant,"e4305ded91176c818e91fdd81704555b");
            return null;
        }
    }

    public class PresenceFin extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {

            //  getApi();
            validerPresenceFin(66,idCoursInstant,"e4305ded91176c818e91fdd81704555b");
            return null;
        }
    }

    public class SituationEtudiant extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {

            JSONArray reponse = null;
            Boolean test = true;
            List pairs = new ArrayList();
            pairs.add(new BasicNameValuePair("id_cours", String.valueOf(idCoursInstant)));
            String urlDuServeur = "https://saliferous-automobi.000webhostapp.com/api/v1/absences";
            postRequest maRequete = new postRequest();
            maRequete.sendRequest(urlDuServeur, pairs);
            reponse = maRequete.getJsonArray();
            try {

                if(reponse!=null){

                    for (int i=0; i<reponse.length(); i++ ){
                        JSONObject jsonObject = reponse.getJSONObject(i);
                        if(jsonObject.optLong("id_individu",-2)==id_individu){

                            test = false;
                            i=reponse.length();
                        }

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (test==true)
                etudiantPresentDebut=true;




            return null;
        }
    }




    private void onClickMenu(View mViewAc, View mViewAb, View mViewPa, View mViewPr, View mViewDe, final TextView toolbar, final long id_individu, final Thread t1, final Thread t2, final Thread t3, final Thread t4, final Thread t5) {

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
                loadEtudiantAbsencesActivity(id_individu,t1,t2,t3,t4,t5);

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
                loadEtudiantProfileActivity(id_individu,t1,t2,t3,t4,t5);

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
                loadEtudiantSettingsActivity(id_individu,t1,t2,t3,t4,t5);
            }
        });
        mViewDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLoginActivity(id_individu,t1,t2,t3,t4,t5);
            }
        });
    }

    public void loadEtudiantAbsencesActivity(long id_individu,Thread t1,Thread t2, Thread t3, Thread t4, Thread t5) {
        Intent myintent = new Intent(this, EtudiantAbsencesActivity.class);
        myintent.putExtra("id_individu", id_individu);
        t1.interrupt();
        t2.interrupt();
        t3.interrupt();
        t4.interrupt();
        t5.interrupt();
        startActivity(myintent);
        finish();

    }

    public void loadEtudiantProfileActivity(long id_individu,Thread t1,Thread t2, Thread t3, Thread t4, Thread t5) {
        Intent myintent = new Intent(this, EtudiantProfileActivity.class);
        myintent.putExtra("id_individu", id_individu);
        startActivity(myintent);
        t1.interrupt();
        t2.interrupt();
        t3.interrupt();
        t4.interrupt();
        t5.interrupt();
        finish();
    }

    public void loadEtudiantSettingsActivity(long id_individu,Thread t1,Thread t2, Thread t3, Thread t4, Thread t5) {
        Intent myintent = new Intent(this, EtudiantSettingsActivity.class);
        myintent.putExtra("id_individu", id_individu);
        startActivity(myintent);
        t1.interrupt();
        t2.interrupt();
        t3.interrupt();
        t4.interrupt();
        t5.interrupt();
        finish();
    }

    public void loadLoginActivity(long id_individu,Thread t1,Thread t2, Thread t3, Thread t4, Thread t5) {
        Intent myintent = new Intent(this, LoginActivity.class);
        myintent.putExtra("id_individu", id_individu);
        startActivity(myintent);
        t1.interrupt();
        t2.interrupt();
        t3.interrupt();
        t4.interrupt();
        t5.interrupt();
        finish();
    }

    public void addListenerOnButton() {

        final Context context = this;
    }

    public String validerPresenceDebut(long id_etudiant, long id_cours, String api) {

        String reponse = null;
        List pairs = new ArrayList();
        pairs.add(new BasicNameValuePair("id_etudiant", String.valueOf(id_etudiant)));
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
        pairs.add(new BasicNameValuePair("login", "mathilda.thomas@epfedu.fr"));
        //pairs.add(new BasicNameValuePair("password","admiNEPF2017"));
        String urlDuServeur = "https://saliferous-automobi.000webhostapp.com/api/v1/key";
        postRequest Requete = new postRequest();
        Requete.sendRequest(urlDuServeur, pairs);
        mApi = Requete.getResultat();
        return mApi;
    }

}

