package com.rss.oc.www.absences20.activity;


import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rss.oc.www.absences20.R;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;

import com.tjerkw.slideexpandable.library.SlideExpandableListAdapter;
import com.yalantis.guillotine.animation.GuillotineAnimation;

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
    View button_presence;
    Button button_absence;
    ImageButton button_presence_epf;
    Button button_retard;
    private String[] prenoms = new String[]{"Benoit", "Brice", "Yann", "Jocelyn", "Arthur", "Glwadys", "Olivier", "Pierre", "Jean"};
    private static final int DIALOG_PRESENT = 10;
    private static final int DIALOG_ABSENT = 20;
    private static final int DIALOG_RETARD = 30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professeur);
        ButterKnife.bind(this);
        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine_prof, null);

        root.addView(guillotineMenu);

        ItemAccueil = guillotineMenu.findViewById(R.id.accueil_group);
        ItemAbsence = guillotineMenu.findViewById(R.id.absence_group);
        ItemProfile = guillotineMenu.findViewById(R.id.profile_group);
        ItemParametres = guillotineMenu.findViewById(R.id.settins_group);
        ItemDeconnection = guillotineMenu.findViewById(R.id.deconnection_group);


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


        ListAdapter listAdapter = new ArrayAdapter<String>(ProfesseurActivity.this,
                R.layout.list_absence_view, R.id.text, prenoms);


        SlideExpandableListAdapter slideExpandableListAdapter = new SlideExpandableListAdapter(
                listAdapter,
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
            }
        }, R.id.button_absence_epf, R.id.boutton_retard, R.id.button_presence_epf);


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
}