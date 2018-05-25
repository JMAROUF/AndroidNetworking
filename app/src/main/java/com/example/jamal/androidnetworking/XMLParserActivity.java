package com.example.jamal.androidnetworking;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Documented;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by jamal on 25/05/2018.
 */

public class XMLParserActivity extends AppCompatActivity {
    private static final String TAG="XMLParser";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new WebServiceAccess().execute("appel");
    }


    private InputStream httpConnexnion(String urlString) throws IOException{
        InputStream in=null;
        int response=-1;
        URL url= new URL(urlString);
        URLConnection connexion = url.openConnection();
        if(!(connexion instanceof HttpURLConnection))
            throw  new IOException("erreur de requete  HTTP  ");
        try{
            HttpURLConnection httpConnexion = (HttpURLConnection)connexion;
            httpConnexion.setAllowUserInteraction(false);
            httpConnexion.setInstanceFollowRedirects(true);
            httpConnexion.setRequestMethod("GET");
            httpConnexion.connect();
            response=httpConnexion.getResponseCode();
            if(response==HttpsURLConnection.HTTP_OK){
                in=httpConnexion.getInputStream();

            }
        }catch(Exception e){
            throw new IOException("Erreur de connexion ");
        }
        return in;
    }

    private String valueString(String word){
        InputStream in=null;
        String stringValue="";
        try{
            in=httpConnexnion("http://services.aonaware.com/DictService/DictService.asmx/Define?word="
                    + word);
            Document doc=null;
            DocumentBuilderFactory dbf =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder db ;
            try{
                db= dbf.newDocumentBuilder();
                doc=db.parse(in);

            }catch (ParserConfigurationException e){
                e.printStackTrace();
            }
            catch (Exception e ){
                e.printStackTrace();
            }
            doc.getDocumentElement().normalize();
            // extraire tous les noeuds Definition dans le fichier XML
            NodeList definitionElements = doc.getElementsByTagName("Definition");
            for(int i = 0;i<definitionElements.getLength();i++){
                Node itemNode=definitionElements.item(i);
                if(itemNode.getNodeType() == Node.ELEMENT_NODE){
                    // convertir le noeud Defenition en Eelement
                    Element definitionElement = (Element) itemNode;
                    //extraire le noeud WordDefinition
                    NodeList wordDefinitions =
                            definitionElement.getElementsByTagName("WordDefinition");
                    String valeur;
                    for(int j=0;j<wordDefinitions.getLength();j++){
                        Element wordDefinition = (Element) wordDefinitions.item(j);
                        //obtenir tous les noeuds fils du noeud wordDefinition
                        NodeList childNodes = ((Node)wordDefinition).getChildNodes();
                        // obtenir le premier noeud (0) qui est la valeur recherchée dans notre cas
                        stringValue+=((Node)childNodes.item(0)).getNodeValue()+"\n";
                    }
                }
            }
        }
        catch (IOException e){
            Log.d(TAG,e.getLocalizedMessage());
        }

        return stringValue;
    }



    // l'appel asynchrone
    private class WebServiceAccess extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return valueString(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(),s,Toast.LENGTH_LONG).show();
        }
    }


}
