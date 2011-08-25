package com.poi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

public class ListadoPoi extends ListActivity{
	private EstablecimientoAdapter estAdapter;
	protected static ArrayList<Establecimiento> listaEst = null;
	private LocationManager locationManager;
	private LocationProvider locationProvider;
	private Establecimiento[] arrayEst = new Establecimiento[]{};
	String[] COORDENADAS = new String[] {"1", "2", "3"};
	String categoriaSeleccionada = null;
	String web = null;
	String [] URLImagenes = new String[]{};
	
	//latitud y longitud se inicializan con valores por defecto
	String latitude = "43.364740";
	String longitude ="-8.406450" ;
	
	JSONArray vocabulario;
	
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	       
	    	//sacamos el valor del radio de SharedPreferences, por defecto ser�a 100000 (10km)
	    	SharedPreferences settings = getApplicationContext().getSharedPreferences("RadioPrefs", Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
	    	String radio =  settings.getString("radio", "10000");
	        
	        listaEst = new ArrayList<Establecimiento>();
	        
	        //se recupera la categor�a de la que se mostrar�n los puntos
	        Bundle bundle = getIntent().getExtras();
	        categoriaSeleccionada = bundle.getString("idCatSeleccionada");
	       
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
	        
	        //String urlJSON = "http://im.dygrafilms.es:8081/es/services/json";
	        // String urlJSON = "http://10.0.2.2:80/drupal/?q=services/json";
	        String urlJSON = "http://192.168.1.130:80/drupal/?q=services/json";
	         
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
	         client.AddParam("nodeLanguage", "\"es\"");
	         
	         try {
	             client.Execute(RequestMethod.POST);
	         } catch (Exception e) {
	        	 Toast toast = Toast.makeText(ListadoPoi.this, "Conexi�n no disponible. No puede descargarse la informaci�n", Toast.LENGTH_LONG);
	  	         toast.show();
	  	         Handler handlerSinConexion = new Handler();
	  	         handlerSinConexion.postDelayed(new sinconexionhandler(), 100);
	             e.printStackTrace();
	         }
	
	        String response1 = client.getResponse();
	        if (response1 == null)
	         {
	        	 Toast toast = Toast.makeText(ListadoPoi.this, "Conexi�n no disponible. No puede descargarse la informaci�n", Toast.LENGTH_LONG);
	  	         toast.show();
	  	         Handler handlerSinConexion = new Handler();
	  	         handlerSinConexion.postDelayed(new sinconexionhandler(), 100);	        
		 }
		 
		 
	         Log.d("ResponseEst",""+ response1);
	      	               		
	          try {
	             JSONObject jSonObject = new JSONObject(response1);
	             vocabulario = jSonObject.getJSONArray("#data");    	           
	             Log.d("vocabulario",vocabulario.toString());
	             Log.d("vocabulario-cantidad","valor:"+vocabulario.length());
	             COORDENADAS = new String[vocabulario.length()];
	             arrayEst = new Establecimiento[vocabulario.length()];
	             URLImagenes = new String[vocabulario.length()];
	            
	            for (int i = 0; i < vocabulario.length(); i++) {
	            	JSONObject location = vocabulario.getJSONObject(i).getJSONObject("location");
	            	 
	            	String street = location.getString("street");
	            	String city = location.getString("city");
	            	String distancia = vocabulario.getJSONObject(i).getString("distance");      
	            	
	            	Establecimiento est;
	            	est = new Establecimiento(
	            			 vocabulario.getJSONObject(i).getString("title").toString(), 
	            			 street +" "+ city, 
	            			 vocabulario.getJSONObject(i).getString("latitude").toString(), 
	            			 vocabulario.getJSONObject(i).getString("longitude").toString());
	            	 
	            	est.setDistancia(distancia);
	            	est.setTelefono(location.getString("phone"));
 	            	est.setDescripcion(vocabulario.getJSONObject(i).getString("body").toString());
	            	
	            	 Log.d("establecimiento", est.getNombre() + est.getDireccion() + est.getTelefono() + est.getEmail());
	            	 
	            	 //ya se obtienen ordenados por distancia, se anhaden directamente
	            	 listaEst.add(est);    	            	 
	            	 COORDENADAS[i] =  vocabulario.getJSONObject(i).getString("latitude").toString() +
	            	  					";" + 
	            			 			vocabulario.getJSONObject(i).getString("longitude").toString();
	            	 
	            	 try{
	            	  
		            	 JSONArray arrayWeb = vocabulario.getJSONObject(i).getJSONArray("field_web");
							
							Log.d("json web",arrayWeb.toString());
							if (arrayWeb.length()>0)
							{
								est.setWeb(arrayWeb.getJSONObject(0).getString("value"));
								Log.d("web", est.getWeb());
							}
		                   JSONArray arrayMail = vocabulario.getJSONObject(i).getJSONArray("field_email");
							
							Log.d("json mail",arrayMail.toString());
							if (arrayMail.length()>0)
							{
								est.setEmail((arrayMail.getJSONObject(0).getString("value")));
								Log.d("mail", est.getEmail());
							}
		                      
							JSONArray arrayFacebook = vocabulario.getJSONObject(i).getJSONArray("field_facebook");
							
							Log.d("json fb",arrayFacebook.toString());
							if (arrayFacebook.length()>0)
							{
								est.setFacebook(arrayFacebook.getJSONObject(0).getString("value"));
								Log.d("fb", est.getFacebook());
							}
		                    JSONArray arrayTwitter = vocabulario.getJSONObject(i).getJSONArray("field_twitter");
							
							Log.d("json tw",arrayTwitter.toString());
							if (arrayTwitter.length()>0)
							{
								est.setTwitter(arrayTwitter.getJSONObject(0).getString("value"));
								Log.d("twitter",est.getTwitter());
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
	            		est.setImagenes(URLImagenes[i]);	
	            	}
	            	}catch(JSONException e){
	            		e.printStackTrace();
	            		 Toast toast = Toast.makeText(ListadoPoi.this, "Error al obtener la informaci�n", Toast.LENGTH_LONG);
	    	  	         toast.show();
	            	}      	            	 
	             }
	            
	         } catch (JSONException e) {
	             e.printStackTrace();
	             Toast toast = Toast.makeText(ListadoPoi.this, "Error al obtener la informaci�n", Toast.LENGTH_LONG);
	  	         toast.show();
	             Intent intent = new Intent(ListadoPoi.this, listadoCategorias.class);
	  	         startActivity(intent);
	         }      
	        	        	        
	        this.setContentView(R.layout.listaest3);	       
	        Button botonMostrarMapa = (Button) findViewById(R.id.mostrarVistaMapa);
	        botonMostrarMapa.setText(getResources().getString(R.string.botonmapa));
	        botonMostrarMapa.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		Intent intent = new Intent(ListadoPoi.this, MapaEstablecimiento.class);
	        		Bundle bundle = new Bundle();
	    	    	bundle.putStringArray("coordenadasEstablecimientos", COORDENADAS);
	    	    	bundle.putString("categoriaSeleccionada", categoriaSeleccionada);
	    	    	bundle.putSerializable("listaEstablecimientos", listaEst);
	    	    	bundle.putStringArray("imagenes", URLImagenes);
	    	    	bundle.putString("latitudActual", latitude);
	    	    	bundle.putString("longitudActual", longitude);
	    	    	intent.putExtras(bundle);	    	
	    	        startActivityForResult(intent, 0);
	        	}
	        });
	 }

	 protected void onListItemClick(ListView l, View v, int position, 
	    		long id){
	    	Intent myIntent = new Intent(v.getContext(), InformacionEstablecimiento.class);
	    	Bundle bundle = new Bundle();
	    	bundle.putStringArray("coordenadasEstablecimientos", COORDENADAS);
	    	bundle.putString("nombre", listaEst.get(position).getNombre());
	    	bundle.putString("telefono", listaEst.get(position).getTelefono());
	    	bundle.putString("email", listaEst.get(position).getEmail());
	    	bundle.putString("web", listaEst.get(position).getWeb());
	    	bundle.putString("direccion", listaEst.get(position).getDireccion());
	    	bundle.putString("descripcion", listaEst.get(position).getDescripcion());
	    	bundle.putString("URLimagenesEstablecimiento", URLImagenes[position]);
	    	bundle.putSerializable("listaEstablecimientos", listaEst);
	    	bundle.putString("puntoDestino", listaEst.get(position).getLatitud()+ "," + listaEst.get(position).getLongitud());
	    	bundle.putString("puntoActual", latitude+","+longitude);
	    	bundle.putString("latitudActual", latitude);
	    	bundle.putString("longitudActual", longitude);
	    	bundle.putInt("posicion", position);
	    	myIntent.putExtras(bundle);	    	
	        startActivityForResult(myIntent, 0);

	 }
	    
	 public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
			Intent myIntent = new Intent(ListadoPoi.this, MapaEstablecimiento.class);  
			Bundle bundle = new Bundle();
			bundle.putStringArray("coordenadasEstablecimientos", COORDENADAS);
	    	bundle.putString("nombre", listaEst.get(position).getNombre());
	    	bundle.putString("telefono", listaEst.get(position).getTelefono());
	    	bundle.putString("email", listaEst.get(position).getEmail());
	    	bundle.putString("web", listaEst.get(position).getWeb());
	    	bundle.putString("descripcion", listaEst.get(position).getDescripcion());
	    	bundle.putString("direccion", listaEst.get(position).getDireccion());
	    	bundle.putSerializable("listaEstablecimientos", listaEst);
	    	bundle.putString("URLimagenesEstablecimiento", URLImagenes[position]);
	    	bundle.putString("puntoDestino", listaEst.get(position).getLatitud()+ "," + listaEst.get(position).getLongitud());
	    	bundle.putString("puntoActual", latitude+","+longitude);
	    	bundle.putString("latitudActual", latitude);
	    	bundle.putString("longitudActual", longitude);
	    	bundle.putInt("posicion", position);
	    	myIntent.putExtras(bundle);
			startActivityForResult(myIntent, 0);
		}
    	
	 class sinconexionhandler implements Runnable{

			public void run() {
				startActivity(new Intent(getApplication(), listadoCategorias.class));
				ListadoPoi.this.finish();
			}
	         }
	
	  
}

