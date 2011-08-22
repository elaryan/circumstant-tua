package com.artesanos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class listadoCategorias extends Activity {
    
	//array en el que se almacenan los id de categoria
	String [] ID = new String[]{};
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista);
        String [] CATEGORIAS = new String[]{};        
        final ListView listaCat = (ListView) findViewById(R.id.ListView01);
       
       // String url = "http://im.dygrafilms.es:8081/es/services/json";
      
       // String urlJSON = "http://10.0.2.2:80/drupal/?q=services/json";
        
        String urlJSON = "http://192.168.1.130:80/drupal/?q=services/json";//para depurar en dispositivo, revisar IP

        /* Uri uri = Uri.parse(url);
         Log.d("uri - porto", uri.getPort()+"");
        
         RestClient client = new RestClient(url);
         client.AddParam("method", "taxonomy.getTree");
         client.AddParam("vid", "1");
         client.AddParam("nodeLanguage", "es");
        // client.AddParam("language", "es");
         client.AddParam("parent", "0");*/
      
        Uri uri = Uri.parse(urlJSON);
        Log.d("uri - porto", uri.getPort()+"");
       
        RestClient client = new RestClient(urlJSON);
        client.AddParam("method", "\"taxonomy.getTree\"");
        client.AddParam("vid", "3");
        client.AddParam("parent", "0");
        client.AddParam("max_depth", "1");

         try {
             client.Execute(RequestMethod.POST);
         } catch (Exception e) {
             e.printStackTrace();
             Toast toast = Toast.makeText(listadoCategorias.this, "Conexión no disponible. No puede descargarse la información", Toast.LENGTH_LONG);
 	         toast.show();
         }

	     String response1 = client.getResponse();
	     Log.d("Response1",""+ response1);
	   /* response1 =  "{ \"#error\": false, \"#data\": [ { \"tid\": \"1\", \"vid\": \"1\", \"name\": " +
	     		"\"Obradoiros Artesans\", \"description\": \"Centros que admiten visitas\", "
	    	 + "\"weight\": \"0\", \"depth\": 0, \"parents\": [ \"0\" ] }, { \"tid\": \"3\", \"vid\":" 
	    	 + "\"1\", \"name\": \"Puntos de interes artesanal\", \"description\": "
	    	 + " \"Exposicions, centros de artesanua, museos, etc.\", \"weight\": \"1\"," 
	    	 + " \"depth\": 0, \"parents\": [ \"0\" ] }, { \"tid\": \"2\", \"vid\": \"1\", \"name\": "
	    	 + "\"Puntos de venda\", \"description\": \"Tendas significativas\", \"weight\": "
	    	 + "\"2\", \"depth\": 0, \"parents\": [ \"0\" ] } ] }";^*/
	    
	   /* response1 = "{ \"#error\": false, \"#data\": [ { \"tid\": \"1\", \"vid\": \"1\", \"name\": " +
 		"\"Monumentos\", \"description\": \"Centros que admiten visitas\", "
   	 + "\"weight\": \"0\", \"depth\": 0, \"parents\": [ \"0\" ] }, { \"tid\": \"3\", \"vid\":" 
   	 + "\"1\", \"name\": \"Espacions naturales\", \"description\": "
   	 + " \"Exposicions, centros de artesanua, museos, etc.\", \"weight\": \"1\"," 
   	 + " \"depth\": 0, \"parents\": [ \"0\" ] }, { \"tid\": \"2\", \"vid\": \"1\", \"name\": "
   	 + "\"Museos\", \"description\": \"Tendas significativas\", \"weight\": "
   	 + "\"2\", \"depth\": 0, \"parents\": [ \"0\" ] } ] }";*/
	    
	    if (response1 == null)
        {
       	 Toast toast = Toast.makeText(listadoCategorias.this, "Conexión no disponible. No puede descargarse la información", Toast.LENGTH_LONG);
 	         toast.show();
 	         Handler handlerSinConexion = new Handler();
 	         handlerSinConexion.postDelayed(new sinconexionhandler(), 100);	        
	 }	
	
	     try {
	         JSONObject jSonObject = new JSONObject(response1);
	         JSONArray vocabulario = jSonObject.getJSONArray("#data");
	         Log.d("vocabulario",vocabulario.toString());
	         vocabulario.getString(1);
	         Log.d("vocabulario-cantidad","valor:"+vocabulario.length());
	         CATEGORIAS = new String[vocabulario.length()];
	         ID = new String[vocabulario.length()];
	        
	         for (int i = 0; i < vocabulario.length(); i++) {
	        	 String name = vocabulario.getJSONObject(i).getString("name").toString();      	 
	        	 Log.d("name",name);
	        	 CATEGORIAS[i] =  name;
	        	 
	        	 String description = vocabulario.getJSONObject(i).getString("description").toString();	       	                
	        	 Log.d("description",description);
	        	 
	        	 String idCat = vocabulario.getJSONObject(i).getString("tid").toString();
	        	 ID[i] = idCat;
	        	 Log.d("idCat", ID[i]);
	         }
         } catch (JSONException e) {
             e.printStackTrace();
         }
         
       // listaCat.setAdapter(new ArrayAdapter<String>(this, R.layout.fila, CATEGORIAS));
        //View v = getLayoutInflater().inflate(R.layout.footer2, null);
       // listaCat.addFooterView(v);
        View v = getLayoutInflater().inflate(R.layout.header, null);
        listaCat.addHeaderView(v);
        listaCat.setAdapter(new CategoriaAdapter(this, R.layout.fila, CATEGORIAS));
        listaCat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				Intent myIntent = new Intent(listadoCategorias.this, ListadoPoi.class);
				//Intent myIntent = new Intent(listadoCategorias.this, InformacionEstablecimiento.class);
				
		        Bundle bundle = new Bundle();
		        bundle.putString("categoria", listaCat.getItemAtPosition(position).toString());
		        bundle.putString("idCatSeleccionada", ID[position-1]);
		        bundle.putString("position",((Integer)position).toString() );
		        myIntent.putExtras(bundle);
		        //Log.d("Categoria", listaCat.getItemAtPosition(position).toString());
		        //Log.d("idCatSel", ID[position]);
		       // Log.d("idCatSeleccionada", ID[position]);
		       
		        //Log.d("pos",((Integer)position).toString() );
				startActivityForResult(myIntent, 0);
			}       	
		});    
        listaCat.setDivider(null);
        
       /* ImageButton configuracion = (ImageButton) findViewById(R.id.botonImagenConfigurar);
        //configuracion.setText("Configuración");
        configuracion.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent(listadoCategorias.this, Configuracion.class);
        		startActivity(intent);
        	}
        });*/
        
    }
    class sinconexionhandler implements Runnable{

		public void run() {
			startActivity(new Intent(getApplication(), listadoCategorias.class));
			listadoCategorias.this.finish();
		}
         }

   
//    protected void onListItemClick(ListView l, View v, int position, 
//    		long id){
//    	Intent myIntent = new Intent(v.getContext(), listadoEstablecimientos.class);
//        startActivityForResult(myIntent, 0);	
//    }
    
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	  MenuInflater inflater = getMenuInflater();
    	  inflater.inflate(R.menu.menu, menu);
    	  return true;
    	}*/
}