package com.rss.oc.www.absences20.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rss.oc.www.absences20.R;
import com.rss.oc.www.absences20.annexe.GetMyJson;
import com.rss.oc.www.absences20.bdd.utilisateurs.UtilisateurDAO;
import com.rss.oc.www.absences20.bdd.utilisateurs.Utilisateurs;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class ChargementActivity extends AppCompatActivity {

    Context context =this;

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

        long id;
        String identifiant;
        String statut;
        String password;

        try {
            URL url = new URL("https://saliferous-automobi.000webhostapp.com/api/v1/login?login=brice@epfedu.fr&password=1234");
            GetMyJson get = new GetMyJson();
            JSONObject jsonResponse = get.myJson(url);
            try {
                id=jsonResponse.getInt("id");
                identifiant=jsonResponse.getString("login");
                statut=jsonResponse.getString("statut");
                password=jsonResponse.getString("password");
                Utilisateurs utilisateurs = new Utilisateurs(id,identifiant,statut,password);
                UtilisateurDAO utilisateurDAO = new UtilisateurDAO(context);
                utilisateurDAO.ajouter(utilisateurs);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
