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
import com.rss.oc.www.absences20.annexe.GetMyJson;
import com.rss.oc.www.absences20.annexe.postRequest;
import com.rss.oc.www.absences20.bdd.Cours.CoursDAO;
import com.rss.oc.www.absences20.bdd.individu.IndividusDAO;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import org.apache.http.message.BasicNameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rss.oc.www.absences20.R.drawable.my_progress;

public class MainActivity extends AppCompatActivity   {
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

    private String[] mStrings = {
            "AAAAAAAA", "BBBBBBBB", "CCCCCCCC", "DDDDDDDD", "EEEEEEEE",
            "FFFFFFFF", "GGGGGGGG", "HHHHHHHH", "IIIIIIII", "JJJJJJJJ",
            "KKKKKKKK", "LLLLLLLL", "MMMMMMMM", "NNNNNNNN", "OOOOOOOO",
            "PPPPPPPP", "QQQQQQQQ", "RRRRRRRR", "SSSSSSSS", "TTTTTTTT",
            "UUUUUUUU", "VVVVVVVV", "WWWWWWWW", "XXXXXXXX", "YYYYYYYY",
            "ZZZZZZZZ"
    };

    private Context context = this;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();






    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addListenerOnButton();
        Intent intent = getIntent();
        final long id_individu =intent.getLongExtra("id_individu",-1);
        fragment_beacon = new BeaconViewerFragment();


        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
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
        progressBar = (ProgressBar)findViewById(R.id.progressBarBeacon);
        final ImageView aPresent = (ImageView)findViewById(R.id.arrivee_present);
        final ImageView aAbsent = (ImageView)findViewById(R.id.arrivee_absent);
        final ImageView dPresent = (ImageView)findViewById(R.id.depart_present);
        final ImageView dAbsent = (ImageView)findViewById(R.id.depart_absent);
        final TextView confirmation = (TextView) findViewById(R.id.confirmation);
        final TextView raprochez = (TextView) findViewById(R.id.raprochez);
        mListView = (ListView) findViewById(R.id.liste_des_prochains_cours);

        ListAdapter adapter = new ArrayAdapter<String>(this,R.layout.row_prochians_cours,R.id.prochain_cours, mStrings);
        mListView.setAdapter(adapter);



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



        CoursDAO coursDAO = new CoursDAO(context);
        long idCoursInstant=coursDAO.getIdCoursInstant();
        String chaine = coursDAO.getInfoCours(idCoursInstant);
        Log.i(TAG, "idCoursInstant" + idCoursInstant);



        TextView actueltxt = (TextView) findViewById(R.id.CoursActuel);
        StringBuffer Actu = new StringBuffer();
        Actu.append(chaine);
        actueltxt.setText(Actu);


        TextView toolbar = (TextView) findViewById(R.id.toolbar_title);
        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        onClickMenu(ItemAccueil,ItemAbsence,ItemParametres,ItemProfile,ItemDeconnection,toolbar,id_individu);

       // IndividusDAO individusDAO =new IndividusDAO(context);
       // individusDAO.validerPresenceDebut(7);

        final BeaconViewerFragment fr = (BeaconViewerFragment) getFragmentManager().findFragmentById(R.id.fragment_beacon);
        if(fr != null) {


            // nouveau thread pour mettre a jour la progressBar afin de representer les beacons

            // Demande des autorisations pour utiliser le Bluetooth
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                    Toast.makeText(this, "The permission to get BLE location data is required", Toast.LENGTH_SHORT).show();
                }else{
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }else{
                Toast.makeText(this, "Location permissions already granted", Toast.LENGTH_SHORT).show();
            }
            Log.d(TAG, "Autorisations done (Bluetooth)");


            arrayAdapter = fr.getMyList();
            Log.i(TAG, "ArrayAdapterUpdate");

            //Nouveau thread pour la recuperation des beacons
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        while (!isInterrupted()) {
                            Thread.sleep(500);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    //prepare for a progress bar dialog
                                    int nombreBeacons  = arrayAdapter.getCount();
                                    //for (int i = 0; i < arrayAdapter.getCount(); i++){
                                    // }
                                    progressBar.setMax(4);
                                    progressBar.setProgress(nombreBeacons);
                                    progressBar.setProgressDrawable(getResources().getDrawable(my_progress));

                                    if (nombreBeacons == 4 ){
                       IndividusDAO individusDAO =new IndividusDAO(context);
                                       // individusDAO.validerPresenceDebut(7);

                                        aPresent.setVisibility(View.VISIBLE);
                                        aAbsent.setVisibility(View.INVISIBLE);
                                        confirmation.setVisibility(View.VISIBLE);
                                        raprochez.setVisibility(View.INVISIBLE);
                                    }else{
                                        confirmation.setVisibility(View.INVISIBLE);
                                        raprochez.setVisibility(View.VISIBLE);

                                    }

                                    // Ajouter condition de départ temporelle
                                    if (nombreBeacons == 4 ){
                                       // aPresent.setVisibility(View.VISIBLE);
                                       // aAbsent.setVisibility(View.INVISIBLE);
                                    }

                                }
                            });
                        }
                    } catch (InterruptedException e) {
                    }
                }
            };

            t.start();

            // ... do some fun stuff
        }




    }
    public class Demand extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {


            String mLogin ="eleve1@epfedu.fr";
            GetMyJson get = new GetMyJson();
            try {
                URL url =new URL("https://saliferous-automobi.000webhostapp.com/api/v1/key?login="+mLogin);
                String api =  get.getApi(url);
                validerPresenceDebut(7,1,api);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private void onClickMenu(View mViewAc, View mViewAb, View mViewPa, View mViewPr, View mViewDe, final TextView toolbar, final long id_individu){

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
    public void loadEtudiantAbsencesActivity(long id_individu){
        Intent myintent = new Intent(this, EtudiantAbsencesActivity.class);
        myintent.putExtra("id_individu",id_individu);
        startActivity(myintent);
        finish();
    }

    public void loadEtudiantProfileActivity(long id_individu){
        Intent myintent = new Intent(this, EtudiantProfileActivity.class);
        myintent.putExtra("id_individu",id_individu);
        startActivity(myintent);
        finish();
    }
    public void loadEtudiantSettingsActivity(long id_individu){
        Intent myintent = new Intent(this, EtudiantSettingsActivity.class);
        myintent.putExtra("id_individu",id_individu);
        finish();
    }

    public void loadLoginActivity(long id_individu){
        Intent myintent = new Intent(this, LoginActivity.class);
        myintent.putExtra("id_individu",id_individu);
        startActivity(myintent);
        finish();
    }

    public void addListenerOnButton() {

        final Context context = this;
    }

    public String validerPresenceDebut (long id_user,long id_cours, String api){

        String reponse=null;
        List pairs = new ArrayList() ;
        pairs.add(new BasicNameValuePair("id_etudiant",String.valueOf(id_user)));
        pairs.add(new BasicNameValuePair("id_cours",String.valueOf(id_cours)));
        pairs.add(new BasicNameValuePair("api_key",api));
        String urlDuServeur = "https://saliferous-automobi.000webhostapp.com/api/v1/presenceEtudiant";
        postRequest maRequete = new postRequest();
        maRequete.sendRequest(urlDuServeur, pairs);
        reponse = maRequete.getResultat();
         return reponse;
        }

    public String validerPresenceFin (long id_user,long id_cours, String api){

        String reponse=null;
        List pairs = new ArrayList() ;
        pairs.add(new BasicNameValuePair("id_etudiant",String.valueOf(id_user)));
        pairs.add(new BasicNameValuePair("id_cours",String.valueOf(id_cours)));
        pairs.add(new BasicNameValuePair("api_key",api));
        String urlDuServeur = "https://saliferous-automobi.000webhostapp.com/api/v1/validerPresence";
        postRequest maRequete = new postRequest();
        maRequete.sendRequest(urlDuServeur, pairs);
        reponse = maRequete.getResultat();
        return reponse;
    }

    private String getApi (){
        String mApi = null;

        List pairs = new ArrayList() ;
        pairs.add(new BasicNameValuePair("login","gestion@admin.fr"));
        //pairs.add(new BasicNameValuePair("password","admiNEPF2017"));
        String urlDuServeur = "https://saliferous-automobi.000webhostapp.com/api/v1/key";
        postRequest Requete = new postRequest();
        Requete.sendRequest(urlDuServeur, pairs);
        mApi = Requete.getResultat();
        return mApi;
    }

}

