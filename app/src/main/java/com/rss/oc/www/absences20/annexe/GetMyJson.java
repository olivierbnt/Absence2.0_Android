package com.rss.oc.www.absences20.annexe;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by BRICE CESAR on 29/05/2017.
 */

public class GetMyJson{


    private String mEmail;
    private String mPassword;
    private URL mUrl;
    public GetMyJson(){



    }



    public JSONObject myJson(URL url){

        JSONObject jsonResponse = null;
        try {

            // instanciation de l'url pointant vers notre fichiers.



            //On ouvre la connexion en vue de lire le fichiers.
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            // On récupère le flux réseaux dans un inputStream
            // il faut voir que quand votre navigateur accède à une page web lui reçoit des trames réseaux
            // il les décode et vous affiche le contenu de la page lisible par un humain.
            // ici c'est la même chose sauf que l'on décompose le travail
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            // j'ai fait la mêthode readStream un peut plus bas pour simplifier la lecture de l'inputstream
            // et récuperer directement un string contenant l'intégralité du fichier que l'on vas lire.
            String strJson = readStream(in);


            //à ce niveau la chaine récupéré est tout simplement notre fichier json distant ( {"repertoire":[{"id":1,"nom":"toto","numero":"0654893453"},{"id":2,"nom":"tata","numero":"0649538453"},{"id":3,"nom":"roger","numero":"0693845543"},{"id":4,"nom":"test","numero":"0653845349"},{"id":5,"nom":"tutu","numero":"0693854453"}]}
            Log.d("AAAAAAAAAAA", "" + strJson);

            // on peu fermer la connection car on en a plus besoin. il nous reste plus qu'a parser le fichier
            urlConnection.disconnect();

            //On crée un objet JSON qui contiendra notre tableau JSON.
            jsonResponse = new JSONObject(strJson);

            Log.i("tttt",jsonResponse.toString());




            //On défini que le noeud principale de notre JSON est 'repertoire' ( la première clé )
              /* JSONArray jsonMainNode = jsonResponse.optJSONArray("repertoire");

               // pour chaque noeud contenu dans le principal on boucle et on récupére les données.
               for (int i = 0; i < jsonMainNode.length(); i++) {

                   //on récupére le noeud fils de notre tableau JSON
                   JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                   //au premier tour de boucle on récupère donc un tableau json du type : [0] => [[id] => 1 [nom] => toto [numero] => 0654893453 ]

                   // on récupère chacune des valeurs de notre noeud fils par sa clé
                   int id = Integer.parseInt(jsonChildNode.optString("id").toString());
                   String nom = jsonChildNode.optString("nom").toString();
                   String numero = jsonChildNode.optString("numero").toString();


                   String OutputData = "Node :  " + id + " | "+ nom + " | "+ numero + " ";

                   // et on les affiche.
                   Log.d("AAAAAAAAAAA", OutputData);


               }*/

        }
        catch(JSONException | IOException ex )
        {
            ex.printStackTrace();

        }
        return jsonResponse;
    }
    protected void sendBDD(){

        URL url=null;
        JSONObject jsonResponse = null;
        try {
            url = new URL("https://saliferous-automobi.000webhostapp.com/api/v1/presenceEtudiant?id_etudiant=10&id_cours=1");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {

            // instanciation de l'url pointant vers notre fichiers.



            //On ouvre la connexion en vue de lire le fichiers.
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            // On récupère le flux réseaux dans un inputStream
            // il faut voir que quand votre navigateur accède à une page web lui reçoit des trames réseaux
            // il les décode et vous affiche le contenu de la page lisible par un humain.
            // ici c'est la même chose sauf que l'on décompose le travail
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            // j'ai fait la mêthode readStream un peut plus bas pour simplifier la lecture de l'inputstream
            // et récuperer directement un string contenant l'intégralité du fichier que l'on vas lire.
            String strJson = readStream(in);


            //à ce niveau la chaine récupéré est tout simplement notre fichier json distant ( {"repertoire":[{"id":1,"nom":"toto","numero":"0654893453"},{"id":2,"nom":"tata","numero":"0649538453"},{"id":3,"nom":"roger","numero":"0693845543"},{"id":4,"nom":"test","numero":"0653845349"},{"id":5,"nom":"tutu","numero":"0693854453"}]}
            Log.d("AAAAAAAAAAA", "" + strJson);

            // on peu fermer la connection car on en a plus besoin. il nous reste plus qu'a parser le fichier
            urlConnection.disconnect();

            //On crée un objet JSON qui contiendra notre tableau JSON.
            jsonResponse = new JSONObject(strJson);

            Log.i("tttt",jsonResponse.toString());




            //On défini que le noeud principale de notre JSON est 'repertoire' ( la première clé )
              /* JSONArray jsonMainNode = jsonResponse.optJSONArray("repertoire");

               // pour chaque noeud contenu dans le principal on boucle et on récupére les données.
               for (int i = 0; i < jsonMainNode.length(); i++) {

                   //on récupére le noeud fils de notre tableau JSON
                   JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                   //au premier tour de boucle on récupère donc un tableau json du type : [0] => [[id] => 1 [nom] => toto [numero] => 0654893453 ]

                   // on récupère chacune des valeurs de notre noeud fils par sa clé
                   int id = Integer.parseInt(jsonChildNode.optString("id").toString());
                   String nom = jsonChildNode.optString("nom").toString();
                   String numero = jsonChildNode.optString("numero").toString();


                   String OutputData = "Node :  " + id + " | "+ nom + " | "+ numero + " ";

                   // et on les affiche.
                   Log.d("AAAAAAAAAAA", OutputData);


               }*/

        }
        catch(JSONException | IOException ex )
        {
            ex.printStackTrace();

        }


    }

    private String readStream(InputStream is) throws IOException {

        String sb="";
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb+=line;
        }
        is.close();
        return sb;
    }





}

