package com.rss.oc.www.absences20.activity;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rss.oc.www.absences20.R;
import com.rss.oc.www.absences20.bdd.Cours.CoursDAO;
import com.rss.oc.www.absences20.bdd.groupe_individu.Groupe_individusDAO;
import com.rss.oc.www.absences20.bdd.groupes.GroupesDAO;
import com.rss.oc.www.absences20.bdd.individu.IndividusDAO;
import com.rss.oc.www.absences20.library.WrapperListAdapterImpl;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;
import com.tjerkw.slideexpandable.library.SlideExpandableListAdapter;
import com.tuesda.walker.circlerefresh.CircleRefreshLayout;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfesseurActivity extends AppCompatActivity {

    private static final long RIPPLE_DURATION = 250;

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
    ImageView indicateurPresence;
    View listAbsenceView;
    Button button_absence;
    ImageButton button_presence_epf;
    Button button_retard;
    private CircleRefreshLayout mRefreshLayout;
    private String[] prenoms = new String[]{"Benoit", "Brice", "Yann", "Jocelyn", "Arthur", "Glwadys", "Olivier", "Pierre", "Jean"};
    private String [] listIndividus = new String[75];
    private ArrayList<String> obj = new ArrayList<String>();

    private ArrayList<Long> listIndicateur ;
    private static final int DIALOG_PRESENT = 10;
    private static final int DIALOG_ABSENT = 20;
    private static final int DIALOG_RETARD = 30;
    private static final int DIALOG_EXCLU = 40;
    private static final int DIALOG_DEPART = 50;
    private Context context=this;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professeur);
        ButterKnife.bind(this);
        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine_prof, null);
       // listAbsenceView= LayoutInflater.from(this).inflate(R.layout.list_absence_view, null);
        //mRefreshLayout = (CircleRefreshLayout) findViewById(R.id.refresh_layout);


        LayoutInflater inflater =
                (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listAbsenceView = inflater.inflate(R.layout.list_absence_view, null, true);

        root.addView(guillotineMenu);

        ItemAccueil = guillotineMenu.findViewById(R.id.accueil_group);
        ItemAbsence = guillotineMenu.findViewById(R.id.absence_group);
        ItemProfile = guillotineMenu.findViewById(R.id.profile_group);
        ItemParametres = guillotineMenu.findViewById(R.id.settins_group);
        ItemDeconnection = guillotineMenu.findViewById(R.id.deconnection_group);
        listIndicateur = new ArrayList<Long>();


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        CoursDAO coursDAO = new CoursDAO(context);
         long idCoursInstant=coursDAO.getIdCoursInstantProf(71);
        if(idCoursInstant!=-1){

            // Log.i("id_cours", String.valueOf(coursDAO.getIdCoursInstant()));
            String libelleCours = coursDAO.getLibelleGroupeInstant(idCoursInstant);
            // Log.i("libelleCours",libelleCours);
            GroupesDAO groupesDAO = new GroupesDAO(context);
            long idGroupe = groupesDAO.getIdGroupe(libelleCours);
            Groupe_individusDAO groupe_individusDAO = new Groupe_individusDAO(context);
            ArrayList<Long> listIdIndividusInstant = groupe_individusDAO.listIdIndividusInstant(idGroupe);

            IndividusDAO individusDAO = new IndividusDAO(context);
            obj = individusDAO.listIndividusInstant(listIdIndividusInstant);
            listIndicateur = individusDAO.listIndicateur(listIdIndividusInstant);
            ListAdapter listAdapter = new ArrayAdapter<String>(ProfesseurActivity.this,
                    R.layout.list_absence_view, R.id.text, obj);

            Log.i("list indicateur",listIndicateur.toString());
            WrapperListAdapterImpl wrap = new WrapperListAdapterImpl(listAdapter) {
                @Override
                public View getView(int position, View view, ViewGroup viewGroup) {

                    View row = getLayoutInflater().inflate(R.layout.list_absence_view,viewGroup,false);

                    long l = listIndicateur.get(position);
                    if(l==0){
                        indicateurPresence = (ImageView) row.findViewById(R.id.imageViewIndicateurStatut);
                        indicateurPresence.setImageResource(R.drawable.button_rouge);
                    }
                    if(l==1){

                        indicateurPresence = (ImageView) row.findViewById(R.id.imageViewIndicateurStatut);
                        indicateurPresence.setImageResource(R.drawable.bouttonvert);
                    }

                    if(l==2){

                        indicateurPresence = (ImageView) row.findViewById(R.id.imageViewIndicateurStatut);
                        indicateurPresence.setImageResource(R.drawable.bouttongris);
                    }

                    return wrapped.getView(position,row,viewGroup);

                }
            };


            SlideExpandableListAdapter slideExpandableListAdapter = new SlideExpandableListAdapter(
                    wrap,
                    R.id.expandable_toggle_button,
                    R.id.expandable
            );


            ActionSlideExpandableListView listView = (ActionSlideExpandableListView) findViewById(R.id.list_absences);
            listView.setAdapter(slideExpandableListAdapter);


            listView.setItemActionListener(new ActionSlideExpandableListView.OnActionClickListener() {

                @Override
                public void onClick(View listView, View buttonView, int position) {
                    if (buttonView.getId() == R.id.button_presence_epf)
                        showDialog(DIALOG_PRESENT);
                    if (buttonView.getId() == R.id.button_absence_epf)
                        showDialog(DIALOG_ABSENT);
                    if (buttonView.getId() == R.id.boutton_retard)
                        showDialog(DIALOG_RETARD);
                    if (buttonView.getId() == R.id.button_exclusion_epf)
                        showDialog(DIALOG_EXCLU);
                    if (buttonView.getId() == R.id.button_depart_epf)
                        showDialog(DIALOG_DEPART);
                }
            }, R.id.button_absence_epf, R.id.boutton_retard, R.id.button_presence_epf,R.id.button_exclusion_epf,R.id.button_depart_epf);

       /* mRefreshLayout.setOnRefreshListener(
                new CircleRefreshLayout.OnCircleRefreshListener() {
                    @Override
                    public void refreshing() {

                       onRestart();
                    }

                    @Override
                    public void completeRefresh() {

                        mRefreshLayout.finishRefreshing();
                    }
                });*/


        }


        TextView toolbar = (TextView) findViewById(R.id.toolbar_title);
        toolbar.setText(getString(R.string.action_liste_etudiants));
        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();



        onClickMenu(ItemAccueil,ItemAbsence,ItemParametres,ItemProfile,ItemDeconnection,toolbar,71);





    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_PRESENT:
                // Create out AlterDialog
                Builder builder_present = new AlertDialog.Builder(this);
                builder_present.setMessage("Etes-vous sûr(e) de vouloir considérer cet étudiant comme présent ?");
                builder_present.setCancelable(true);
                builder_present.setPositiveButton("Oui", new OkOnClickListener());
                builder_present.setNegativeButton("Non", new CancelOnClickListener());
                AlertDialog dialog_present = builder_present.create();
                dialog_present.show();
                break;
            case DIALOG_ABSENT:
                Builder builder_absent = new AlertDialog.Builder(this);
                builder_absent.setMessage("Etes-vous sûr(e) de vouloir considérer cet étudiant comme absent ?");
                builder_absent.setCancelable(true);
                builder_absent.setPositiveButton("Oui", new OkOnClickListener());
                builder_absent.setNegativeButton("Non", new CancelOnClickListener());
                AlertDialog dialog_absent = builder_absent.create();
                dialog_absent.show();
                break;
            case DIALOG_RETARD:
                Builder builder_retard = new AlertDialog.Builder(this);
                builder_retard.setMessage("Etes-vous sûr(e) de vouloir considérer cet étudiant comme retardataire ?");
                builder_retard.setCancelable(true);
                builder_retard.setPositiveButton("Oui", new OkOnClickListener());
                builder_retard.setNegativeButton("Non", new CancelOnClickListener());
                AlertDialog dialog_retard = builder_retard.create();
                dialog_retard.show();
                break;
            case DIALOG_EXCLU:
                Builder builder_exclu = new AlertDialog.Builder(this);
                builder_exclu.setMessage("Etes-vous sûr(e) de vouloir considérer cet étudiant comme exclu ?");
                builder_exclu.setCancelable(true);
                builder_exclu.setPositiveButton("Oui", new OkOnClickListener());
                builder_exclu.setNegativeButton("Non", new CancelOnClickListener());
                AlertDialog dialog_exclu = builder_exclu.create();
                dialog_exclu.show();
                break;
            case DIALOG_DEPART:
                Builder builder_depart = new AlertDialog.Builder(this);
                builder_depart.setMessage("Confirmez vous le départ anticipé de cet étudiant ?");
                builder_depart.setCancelable(true);
                builder_depart.setPositiveButton("Oui", new OkOnClickListener());
                builder_depart.setNegativeButton("Non", new CancelOnClickListener());
                AlertDialog dialog_depart = builder_depart.create();
                dialog_depart.show();
        }
        return super.onCreateDialog(id);
    }

    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            /*Toast.makeText(getApplicationContext(), "Activity will continue",
                    Toast.LENGTH_LONG).show();*/
        }
    }

    private final class OkOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {

            Toast.makeText(getApplicationContext(), "La modification a été prise en compte",
                    Toast.LENGTH_LONG).show();

        }

    }

    private Boolean isDate (int mDay, int mMonth, int mYear){
        Boolean result = false;

        final Calendar c = Calendar.getInstance(Locale.FRANCE);
        int Year = c.get(Calendar.YEAR);
        int Month = c.get(Calendar.MONTH);
        int Day = c.get(Calendar.DAY_OF_MONTH);

        if(mDay==Day&&mMonth==Month&&mYear==Year)
            result = true;

        return result;

    }

    private Boolean isHour (String debut, String fin){
        Boolean result = false;

        String pattern = "dd-MM-yyyy HH:mm:ss";
        SimpleDateFormat dt = new SimpleDateFormat(pattern);
      //  SimpleDateFormat minute = new SimpleDateFormat("mm");

        Calendar c = Calendar.getInstance();
        String dateInstant= dt.format(c.getTime());

        try {
            Date dateDebut = dt.parse(debut);
            Date dateFin  = dt.parse(fin);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if((dateInstant.compareTo(debut)>0)&&(dateInstant.compareTo(fin)<0))
            result=true;


        return result;
    }

    private String chaineDate (String heure, int mDay,int mMonth,int mYear){
        String chaine =null;
        String day = String.valueOf(mDay);
        String month = String.valueOf(mMonth);
        String year = String.valueOf(mYear);
        chaine =day+"-"+month+"-"+year+" "+heure;

        return chaine;
    }

    private void onClickMenu(View mViewAc, View mViewAb, View mViewPa, View mViewPr, View mViewDe, final TextView toolbar, final long id_individu){

        mViewAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textAc = (TextView) findViewById(R.id.accueil_group_text);
                TextView textAb = (TextView) findViewById(R.id.absence_group_text);
                TextView textPa = (TextView) findViewById(R.id.settins_group_text);
                TextView textPr = (TextView) findViewById(R.id.profile_group_text);
                TextView textDe = (TextView) findViewById(R.id.deconnection_group_text);
                toolbar.setText(getText(R.string.action_absences));
                textAc.setTextColor(getResources().getColor(R.color.selected_item_color));
                textPa.setTextColor(getResources().getColor(R.color.white));
                textPr.setTextColor(getResources().getColor(R.color.white));
                textDe.setTextColor(getResources().getColor(R.color.white));
                textAb.setTextColor(getResources().getColor(R.color.white));
                loadMainActivity(id_individu);

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
        mViewDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLoginActivity(id_individu);
            }
        });
    }

    public void loadEtudiantSettingsActivity(long id_individu ){
        Intent myintent = new Intent(this, ProfesseurSettingsActivity.class);
        myintent.putExtra("id_individu", id_individu);
        startActivity(myintent);
        finish();
    }

    public void loadMainActivity(long id_individu){
        Intent myintent = new Intent(this, ProfesseurAccueilActivity.class);
        myintent.putExtra("id_individu", id_individu);
        startActivity(myintent);
        finish();
    }


    public void loadLoginActivity(long id_individu){
        Intent myintent = new Intent(this, LoginActivity.class);
        myintent.putExtra("id_individu", id_individu);
        startActivity(myintent);
        finish();
    }

    public void loadEtudiantProfileActivity(long id_individu){
        Intent myintent = new Intent(this, ProfesseurProfilActivity.class);
        myintent.putExtra("id_individu", id_individu);
        startActivity(myintent);
        finish();
    }


}


