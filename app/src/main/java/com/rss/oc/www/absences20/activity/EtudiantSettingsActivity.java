package com.rss.oc.www.absences20.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rss.oc.www.absences20.R;
import com.rss.oc.www.absences20.bdd.utilisateurs.UtilisateurDAO;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EtudiantSettingsActivity extends AppCompatActivity {
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
    EditText editText1;
    EditText editText2;
    Button confirmer;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant_settings);
        ButterKnife.bind(this);


        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        ItemAccueil = guillotineMenu.findViewById(R.id.accueil_group);
        ItemAbsence = guillotineMenu.findViewById(R.id.absence_group);
        ItemProfile = guillotineMenu.findViewById(R.id.profile_group);
        ItemParametres = guillotineMenu.findViewById(R.id.settins_group);
        ItemDeconnection = guillotineMenu.findViewById(R.id.deconnection_group);
        editText1 = (EditText)findViewById(R.id.editText11);
        editText2 = (EditText)findViewById(R.id.editText9);
        confirmer = (Button)findViewById(R.id.button2);
        TextView textPa = (TextView) findViewById(R.id.settins_group_text);
        textPa.setTextColor(getResources().getColor(R.color.selected_item_color));

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }


        TextView toolbar = (TextView) findViewById(R.id.toolbar_title);
        toolbar.setText(getString(R.string.action_settings));
        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        onClickMenu(ItemAccueil,ItemAbsence,ItemParametres,ItemProfile,ItemDeconnection,toolbar,66);


        confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mdp1 = String.valueOf(editText1.getText());
                String mdp2 = String.valueOf(editText2.getText());

                if(mdp1.equals(mdp2)){

                    UtilisateurDAO utilisateurDAO = new UtilisateurDAO(context);
                    utilisateurDAO.modifierMDP(96,mdp1);

                    Toast.makeText(getApplicationContext(), "La modification a été prise en compte",
                            Toast.LENGTH_LONG).show();
                    editText1.clearComposingText();
                    editText2.clearComposingText();

                }
                else{
                    Toast.makeText(getApplicationContext(), "Erreur recommencez !!",
                            Toast.LENGTH_LONG).show();
                    editText1.clearComposingText();
                    editText2.clearComposingText();

                }
            }
        });




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
        mViewAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView textAc = (TextView) findViewById(R.id.accueil_group_text);
                TextView textAb = (TextView) findViewById(R.id.absence_group_text);
                TextView textPa = (TextView) findViewById(R.id.settins_group_text);
                TextView textPr = (TextView) findViewById(R.id.profile_group_text);
                TextView textDe = (TextView) findViewById(R.id.deconnection_group_text);
                toolbar.setText(getText(R.string.action_profile));
                textAc.setTextColor(getResources().getColor(R.color.selected_item_color));
                textPa.setTextColor(getResources().getColor(R.color.white));
                textPr.setTextColor(getResources().getColor(R.color.white));
                textDe.setTextColor(getResources().getColor(R.color.white));
                textAb.setTextColor(getResources().getColor(R.color.white));
                loadMainActivity(id_individu);

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

    public void loadEtudiantAbsencesActivity(long id_individu){
        Intent myintent = new Intent(this, EtudiantAbsencesActivity.class);
        myintent.putExtra("id_individu", id_individu);
        startActivity(myintent);
        finish();
    }

    public void loadMainActivity(long id_individu){
        Intent myintent = new Intent(this, MainActivity.class);
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
        Intent myintent = new Intent(this, EtudiantProfileActivity.class);
        myintent.putExtra("id_individu", id_individu);
        startActivity(myintent);
        finish();
    }




}



