package com.poi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.poi.R;
import com.poi.PantallaSplash.splashhandler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ListadoPuntosInteres extends ListActivity{
	private PuntoInteresAdapter poiAdapter;
	protected static ArrayList<PuntoInteres> listaPoi = null;
	private LocationManager locationManager;
	private LocationProvider locationProvider;
	private PuntoInteres[] arrayPoi = new PuntoInteres[]{};
	String[] COORDENADAS = new String[] {"1", "2", "3"};
	String categoriaSeleccionada = null;
	String web = null;
	String [] URLImagenes = new String[]{};
	
	//latitud y longitud se inicializan con valores por defecto
	String latitude = "43.364740";
	String longitude ="-8.406450" ;
	String ip = "";
	
	JSONArray vocabulario;
	
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	       
	    	//sacamos el valor del radio de SharedPreferences, por defecto sería 100000 (10km)
	    	SharedPreferences settings = getApplicationContext().getSharedPreferences("RadioPrefs", Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
	    	String radio =  settings.getString("radio", "10000");
	        
	        listaPoi = new ArrayList<PuntoInteres>();
	        
	        //se recupera la categoría de la que se mostrarán los puntos
	        Bundle bundle = getIntent().getExtras();
	        categoriaSeleccionada = bundle.getString("idCatSeleccionada");
	        ip = bundle.getString("IPDrupal");
	       
	        //se obtiene la localizacion actual
	        this.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		    this.locationProvider = (LocationProvider) this.locationManager.getProvider(LocationManager.GPS_PROVIDER);
		    Location ultimaLocalizacion = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		    
		    //si no se puede obtener la localizacion se toman los valores por defecto
	        if (ultimaLocalizacion != null){
	        	 Log.d("gps", "OK");
	        	 Log.d("GPS", ((Double)ultimaLocalizacion.getLatitude()).toString() + ";" +((Double)ultimaLocalizacion.getLongitude()).toString());
		         latitude = ((Double)ultimaLocalizacion.getLatitude()).toString();
		         longitude = ((Double)ultimaLocalizacion.getLongitude()).toString();
	        }	        
	        
	        //obtenemos el lenguaje del dispositivo
	        String idioma = Locale.getDefault().getLanguage();
	        Log.d("idioma", idioma);
	        
	        //String urlJSON = "http://im.dygrafilms.es:8081/es/services/json";
	         //String urlJSON = "http://10.0.2.2:80/drupal/?q=services/json";
	        String urlJSON = "http://" + ip + ":80/drupal/?q=services/json";
	         
	         Uri uri = Uri.parse(urlJSON);
	         Log.d("uri - porto", uri.getPort()+"");
	         Log.d("latitud - longitud", latitude + " " + longitude);
	         Log.d("radio", radio);	       
	        
	         RestClient client = new RestClient(urlJSON);
	         client.AddParam("method", "\"geoposition.selectNodes\"");
	         client.AddParam("tid",categoriaSeleccionada);
	         client.AddParam("fields", "[\"nid\",\"title\",\"location\",\"field_imagenes\",\"latitude\",\"longitude\",\"distance\",\"body\",\"field_web\",\"field_email\",\"field_facebook\",\"field_twitter\"]");
	         client.AddParam("latitude", latitude);
	         client.AddParam("longitude", longitude);
	         client.AddParam("radio", radio);
	         //client.AddParam("nodeLanguage", "\"es\"");
	         client.AddParam("nodeLanguage", "\"" + idioma + "\"" );
	         
	         try {
	             client.Execute(RequestMethod.POST);
	         } catch (Exception e) {
	        	 Toast toast = Toast.makeText(ListadoPuntosInteres.this, "Conexión no disponible. No puede descargarse la información", Toast.LENGTH_LONG);
	  	         toast.show();
	  	         Handler handlerSinConexion = new Handler();
	  	         handlerSinConexion.postDelayed(new sinconexionhandler(), 100);
	             e.printStackTrace();
	         }
	
	        String response1 = client.getResponse();
	        if (response1 == null)
	         {
	        	 Toast toast = Toast.makeText(ListadoPuntosInteres.this, "Conexión no disponible. No puede descargarse la información", Toast.LENGTH_LONG);
	  	         toast.show();
	  	         Handler handlerSinConexion = new Handler();
	  	         handlerSinConexion.postDelayed(new sinconexionhandler(), 100);	        
		 }
		 
		 
	         Log.d("ResponseEst",""+ response1);
	         
	      	               		
	          try {
	        	  JSONObject jSonObject = new JSONObject(response1);
	         
	             if (jSonObject.isNull("#data")){
	            	 Log.d("n8kl", "sin resultados");
	             	 Toast toast = Toast.makeText(ListadoPuntosInteres.this, "No hay puntos de interés cercanos en esta categoría", Toast.LENGTH_LONG);
	    	  	     toast.show();
	    	  	     Intent intent = new Intent(ListadoPuntosInteres.this, ListadoCategorias.class);
		  	         startActivity(intent);
	             }
	                
	             
	             else
	             {
	             vocabulario = jSonObject.getJSONArray("#data");
	             Log.d("vocabulario",vocabulario.toString());
	             Log.d("vocabulario-cantidad","valor:"+vocabulario.length());
	             COORDENADAS = new String[vocabulario.length()];
	             arrayPoi = new PuntoInteres[vocabulario.length()];
	             URLImagenes = new String[vocabulario.length()];
	            
	            for (int i = 0; i < vocabulario.length(); i++) {
	            	JSONObject location = vocabulario.getJSONObject(i).getJSONObject("location");
	            	 
	            	String street = location.getString("street");
	            	String city = location.getString("city");
	            	String distancia = vocabulario.getJSONObject(i).getString("distance");      
	            	
	            	PuntoInteres poi;
	            	poi = new PuntoInteres(
	            			 vocabulario.getJSONObject(i).getString("title").toString(), 
	            			 street +" "+ city, 
	            			 vocabulario.getJSONObject(i).getString("latitude").toString(), 
	            			 vocabulario.getJSONObject(i).getString("longitude").toString());
	            	 
	            	poi.setDistancia(distancia);
	            	poi.setTelefono(location.getString("phone"));
 	            	poi.setDescripcion(vocabulario.getJSONObject(i).getString("body").toString());
	            	
	            	 Log.d("poi", poi.getNombre() + poi.getDireccion() + poi.getTelefono() + poi.getEmail());
	            	 
	            	 //ya se obtienen ordenados por distancia, se anhaden directamente
	            	 listaPoi.add(poi);    	            	 
	            	 COORDENADAS[i] =  vocabulario.getJSONObject(i).getString("latitude").toString() +
	            	  					";" + 
	            			 			vocabulario.getJSONObject(i).getString("longitude").toString();
	            	 
	            	 try{
	            	  
		            	 JSONArray arrayWeb = vocabulario.getJSONObject(i).getJSONArray("field_web");
							
							Log.d("json web",arrayWeb.toString());
							if (arrayWeb.length()>0)
							{
								poi.setWeb(arrayWeb.getJSONObject(0).getString("value"));
								Log.d("web", poi.getWeb());
							}
		                   JSONArray arrayMail = vocabulario.getJSONObject(i).getJSONArray("field_email");
							
							Log.d("json mail",arrayMail.toString());
							if (arrayMail.length()>0)
							{
								poi.setEmail((arrayMail.getJSONObject(0).getString("value")));
								Log.d("mail", poi.getEmail());
							}
		                      
							JSONArray arrayFacebook = vocabulario.getJSONObject(i).getJSONArray("field_facebook");
							
							Log.d("json fb",arrayFacebook.toString());
							if (arrayFacebook.length()>0)
							{
								poi.setFacebook(arrayFacebook.getJSONObject(0).getString("value"));
								Log.d("fb", poi.getFacebook());
							}
		                    JSONArray arrayTwitter = vocabulario.getJSONObject(i).getJSONArray("field_twitter");
							
							Log.d("json tw",arrayTwitter.toString());
							if (arrayTwitter.length()>0)
							{
								poi.setTwitter(arrayTwitter.getJSONObject(0).getString("value"));
								Log.d("twitter",poi.getTwitter());
							}
						}catch(JSONException e){
							e.printStackTrace();
						}
						
	            	JSONArray imagenes = vocabulario.getJSONObject(i).getJSONArray("field_imagenes");
	            	URLImagenes[i] = "";
	            	try{
	            	for (int j=0; j<imagenes.length();j++){
	            		
	            		if (imagenes.getJSONObject(j) != null){	            		
	            		
		            		String urlImg = imagenes.getJSONObject(j).getString("filepath");
		            		if (imagenes.getJSONObject(j).isNull("filepath"))
		            			Log.d("filepath", "null");
		            		if (imagenes.isNull(j))
		            			Log.d("imagenes", "null");
		            		if (!(urlImg.equals(null)))
		            		{            			
		            			URLImagenes[i]+= imagenes.getJSONObject(j).getString("filepath") + ";";
		            			Log.d("imgURL", imagenes.getJSONObject(j).getString("filepath"));
		            		}
	            		}
	            		poi.setImagenes(URLImagenes[i]);	
	            	}
	            	}catch(JSONException e){
	            		e.printStackTrace();
	            		 Toast toast = Toast.makeText(ListadoPuntosInteres.this, "Error al obtener la información", Toast.LENGTH_LONG);
	    	  	         toast.show();
	            	}      	            	 
	             }
	             }
	         } catch (JSONException e) {
	             e.printStackTrace();
	             Toast toast = Toast.makeText(ListadoPuntosInteres.this, "Error al obtener la información", Toast.LENGTH_LONG);
	  	         toast.show();
	             Intent intent = new Intent(ListadoPuntosInteres.this, ListadoCategorias.class);
	  	         startActivity(intent);
	         }      
	        	        	        
	        this.setContentView(R.layout.listapoi);	       
	        Button botonMostrarMapa = (Button) findViewById(R.id.mostrarVistaMapa);
	        botonMostrarMapa.setText(getResources().getString(R.string.botonmapa));
	        botonMostrarMapa.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		Intent intent = new Intent(ListadoPuntosInteres.this, MapaPuntosInteres.class);
	        		Bundle bundle = new Bundle();
	    	    	bundle.putStringArray("coordenadasEstablecimientos", COORDENADAS);
	    	    	bundle.putString("categoriaSeleccionada", categoriaSeleccionada);
	    	    	bundle.putSerializable("listaPuntosInteres", listaPoi);
	    	    	bundle.putStringArray("imagenes", URLImagenes);
	    	    	bundle.putString("latitudActual", latitude);
	    	    	bundle.putString("longitudActual", longitude);
	    	    	bundle.putString("IPDrupal", ip);
	    	    	intent.putExtras(bundle);	    	
	    	        startActivityForResult(intent, 0);
	        	}
	        });
	        
	        poiAdapter = new PuntoInteresAdapter(ListadoPuntosInteres.this, listaPoi);
	        setListAdapter(poiAdapter);
	        Log.d("sizeFromLista",((Integer)listaPoi.size()).toString());
	        
	 }

	 protected void onListItemClick(ListView l, View v, int position, 
	    		long id){
	    	Intent myIntent = new Intent(v.getContext(), InformacionPuntoInteres.class);
	    	Bundle bundle = new Bundle();
	    	bundle.putStringArray("coordenadasEstablecimientos", COORDENADAS);
	    	bundle.putString("nombre", listaPoi.get(position).getNombre());
	    	bundle.putString("telefono", listaPoi.get(position).getTelefono());
	    	bundle.putString("email", listaPoi.get(position).getEmail());
	    	bundle.putString("web", listaPoi.get(position).getWeb());
	    	bundle.putString("direccion", listaPoi.get(position).getDireccion());
	    	bundle.putString("descripcion", listaPoi.get(position).getDescripcion());
	    	bundle.putString("URLimagenesEstablecimiento", URLImagenes[position]);
	    	bundle.putSerializable("listaPuntosInteres", listaPoi);
	    	bundle.putString("puntoDestino", listaPoi.get(position).getLatitud()+ "," + listaPoi.get(position).getLongitud());
	    	bundle.putString("puntoActual", latitude+","+longitude);
	    	bundle.putString("latitudActual", latitude);
	    	bundle.putString("longitudActual", longitude);
	    	bundle.putInt("posicion", position);
	    	bundle.putString("IPDrupal", ip);
	    	myIntent.putExtras(bundle);	    	
	        startActivityForResult(myIntent, 0);

	 }
	    
	 public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
			Intent myIntent = new Intent(ListadoPuntosInteres.this, MapaPuntosInteres.class);  
			Bundle bundle = new Bundle();
			bundle.putStringArray("coordenadasEstablecimientos", COORDENADAS);
	    	bundle.putString("nombre", listaPoi.get(position).getNombre());
	    	bundle.putString("telefono", listaPoi.get(position).getTelefono());
	    	bundle.putString("email", listaPoi.get(position).getEmail());
	    	bundle.putString("web", listaPoi.get(position).getWeb());
	    	bundle.putString("descripcion", listaPoi.get(position).getDescripcion());
	    	bundle.putString("direccion", listaPoi.get(position).getDireccion());
	    	bundle.putSerializable("listaPuntosInteres", listaPoi);
	    	bundle.putString("URLimagenesEstablecimiento", URLImagenes[position]);
	    	bundle.putString("puntoDestino", listaPoi.get(position).getLatitud()+ "," + listaPoi.get(position).getLongitud());
	    	bundle.putString("puntoActual", latitude+","+longitude);
	    	bundle.putString("latitudActual", latitude);
	    	bundle.putString("longitudActual", longitude);
	    	bundle.putInt("posicion", position);
	    	bundle.putString("IPDrupal", ip);
	    	myIntent.putExtras(bundle);
			startActivityForResult(myIntent, 0);
		}
    	
	 class sinconexionhandler implements Runnable{

			public void run() {
				startActivity(new Intent(getApplication(), ListadoCategorias.class));
				ListadoPuntosInteres.this.finish();
			}
	         }
	
	  
}

