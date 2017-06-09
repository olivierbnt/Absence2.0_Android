package com.rss.oc.www.absences20.activity;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rss.oc.www.absences20.R;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;

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


    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addListenerOnButton();

        fragment_beacon = new BeaconViewerFragment();



        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);
        //View fragment_beacon =  LayoutInflater.from(this).inflate(R.layout.fragment_main, null);
        BeaconViewerFragment fr = (BeaconViewerFragment) getFragmentManager().findFragmentById(R.id.fragment_beacon);
        if(fr != null) {
            arrayAdapter = fr.getMyList();
            Log.i(TAG, "ArrayAdapterUpdate");

            // ... do some fun stuff
        }

        ItemAccueil = guillotineMenu.findViewById(R.id.accueil_group);
        ItemAbsence = guillotineMenu.findViewById(R.id.absence_group);
        ItemProfile = guillotineMenu.findViewById(R.id.profile_group);
        ItemParametres = guillotineMenu.findViewById(R.id.settins_group);
        ItemDeconnection = guillotineMenu.findViewById(R.id.deconnection_group);
        TextView textAc = (TextView) findViewById(R.id.accueil_group_text);
        textAc.setTextColor(getResources().getColor(R.color.selected_item_color));
        progressBar = (ProgressBar)findViewById(R.id.progressBarBeacon);

        //prepare for a progress bar dialog
        int nombreBeacons  = arrayAdapter.getCount();
        progressBar.setProgress(2);
        progressBar.setMax(3);



        //reset progress bar status
       // progressBarStatus =fragment_beacon.


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }


        TextView toolbar = (TextView) findViewById(R.id.toolbar_title);
        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        onClickMenu(ItemAccueil,ItemAbsence,ItemParametres,ItemProfile,ItemDeconnection,toolbar);




    }

    private void onClickMenu(View mViewAc,View mViewAb, View mViewPa, View mViewPr, View mViewDe, final TextView toolbar){

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
                loadEtudiantAbsencesActivity();

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
        mViewDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLoginActivity();
            }
        });
    }
    public void loadEtudiantAbsencesActivity(){
        Intent myintent = new Intent(this, EtudiantAbsencesActivity.class);
        startActivity(myintent);
        finish();
    }

    public void loadEtudiantProfileActivity(){
        Intent myintent = new Intent(this, EtudiantProfileActivity.class);
        startActivity(myintent);
        finish();
    }
    public void loadEtudiantSettingsActivity(){
        Intent myintent = new Intent(this, EtudiantSettingsActivity.class);
        startActivity(myintent);
        finish();
    }

    public void loadLoginActivity(){
        Intent myintent = new Intent(this, LoginActivity.class);
        startActivity(myintent);
        finish();
    }

    public void addListenerOnButton() {

        final Context context = this;

        button = (Button) findViewById(R.id.buttonBeaconActivity);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, BeaconViewerActivity.class);
                startActivity(intent);

            }

        });

    }

}

