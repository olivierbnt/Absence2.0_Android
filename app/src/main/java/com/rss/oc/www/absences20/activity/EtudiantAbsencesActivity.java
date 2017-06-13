package com.rss.oc.www.absences20.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rss.oc.www.absences20.R;
import com.rss.oc.www.absences20.bdd.Cours.CoursDAO;
import com.rss.oc.www.absences20.bdd.absences.AbsencesDAO;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EtudiantAbsencesActivity extends AppCompatActivity {

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
    private ArrayList<String> listAbsences ;
    private ArrayList<Long> listIdCours ;
    private Context context =this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant_absences);
        ButterKnife.bind(this);


        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        ItemAccueil = guillotineMenu.findViewById(R.id.accueil_group);
        ItemAbsence = guillotineMenu.findViewById(R.id.absence_group);
        ItemProfile = guillotineMenu.findViewById(R.id.profile_group);
        ItemParametres = guillotineMenu.findViewById(R.id.settins_group);
        ItemDeconnection = guillotineMenu.findViewById(R.id.deconnection_group);
        listAbsences = new ArrayList<String>();
        listIdCours = new ArrayList<Long>();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }


        TextView toolbar = (TextView) findViewById(R.id.toolbar_title);
        toolbar.setText(getString(R.string.action_absences));

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        TextView textAb = (TextView) findViewById(R.id.absence_group_text);
        textAb.setTextColor(getResources().getColor(R.color.selected_item_color));


        AbsencesDAO absencesDAO = new AbsencesDAO(context);
        Intent intent = getIntent();
        //long id_individu = intent.getLongExtra("id_individu",-1);
       // Log.i("id_individu", String.valueOf(id_individu));

        //if(id_individu!=-1){
            listIdCours = absencesDAO.getListIdCoursAbsences(66);
        Log.i("listIdCours",listIdCours.toString());
            CoursDAO coursDAO = new CoursDAO(context);
            listAbsences = coursDAO.getListInfoCours(listIdCours);
      //  }


        onClickMenu(ItemAccueil,ItemAbsence,ItemParametres,ItemProfile,ItemDeconnection,toolbar);

        ListView listView = (ListView) findViewById(R.id.list_absences_etudiant);

        listView.setAdapter(new ArrayAdapter<String>(EtudiantAbsencesActivity.this, R.layout.list_item_absence_etudiant,
                R.id.text_absence_etudiant,listAbsences));




    }

    private void onClickMenu(View mViewAc,View mViewAb, View mViewPa, View mViewPr, View mViewDe, final TextView toolbar){

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
                loadMainActivity();

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
                loadEtudiantSettingsActivity();

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
                loadEtudiantProfileActivity();

            }
        });
        mViewDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLoginActivity();
            }
        });
    }

    public void loadEtudiantSettingsActivity(){
        Intent myintent = new Intent(this, EtudiantSettingsActivity.class);
        startActivity(myintent);
        finish();
    }

    public void loadMainActivity(){
        Intent myintent = new Intent(this, MainActivity.class);
        startActivity(myintent);
        finish();
    }


    public void loadLoginActivity(){
        Intent myintent = new Intent(this, LoginActivity.class);
        startActivity(myintent);
        finish();
    }

    public void loadEtudiantProfileActivity(){
        Intent myintent = new Intent(this, EtudiantProfileActivity.class);
        startActivity(myintent);
        finish();
    }


}


