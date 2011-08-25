package com.poi;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.poi.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


public class MapaEstablecimiento extends MapActivity{
	LinearLayout linearLayout;
	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	Drawable globo;
	Double latitud;
	Double longitud;
	Establecimiento establecimiento;
	EstablecimientoItemizedOverlay itemizedOverlay;	
	List<EstablecimientoOverlayItem> establecimientoOverlays;
	ArrayList<Establecimiento> listaEst;
	private LocationManager locationManager;
	private LocationProvider locationProvider;
	private String categoriaSeleccionada = null;
	String[] imagenes = new String[]{};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa2);
        
        //obtenemos el array de coordenadas que pasamos desde la actividad anterior
        Bundle bundle = getIntent().getExtras();
        
        //obtenemos la lista de establecimientos
       listaEst = (ArrayList<Establecimiento>)getIntent().getExtras().get("listaEstablecimientos");
        
        String [] coordenadas = bundle.getStringArray("coordenadasEstablecimientos");
        categoriaSeleccionada = bundle.getString("categoriaSeleccionada");
        imagenes = new String[coordenadas.length];
        imagenes = bundle.getStringArray("imagenes");
        mapView = (MapView) findViewById(R.id.mapview);
        final MapController mc = mapView.getController();
        mapView.setBuiltInZoomControls(true);
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.pinmapa);
        globo = this.getResources().getDrawable(R.drawable.pinmapa);
        
        //obtenemos la localización actual    
        this.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	    this.locationProvider = (LocationProvider) this.locationManager.getProvider(LocationManager.GPS_PROVIDER);
	    Location ultimaLocalizacion = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
      String puntoActual;
        if (ultimaLocalizacion == null) {
        	Log.d("ultimaLocalizacion", "null");
        	latitud = 43364740.0;
        	longitud = -5406450.0;
        	puntoActual = latitud + "," + longitud;
        }
        else{
        	latitud = ultimaLocalizacion.getLatitude() * 1000000;  
            longitud = ultimaLocalizacion.getLongitude() * 1000000;
            Log.d("latitudACTUAL",latitud.toString());
            Log.d("longitudACTUAL",longitud.toString());
            puntoActual = ((Double)ultimaLocalizacion.getLatitude()).toString() + "," + ((Double)ultimaLocalizacion.getLongitude()).toString();
       }
       
        for (int i=0;i<this.listaEst.size();i++){
       	 Log.d("listaEstMapa", ((Integer)i).toString() + " " + listaEst.get(i).getNombre());

        }
       EstablecimientoItemizedOverlay itemizedOverlayGlobo = new EstablecimientoItemizedOverlay(drawable, this, listaEst, imagenes, puntoActual);
       GeoPoint ultimoPunto = new GeoPoint(latitud.intValue(), longitud.intValue());        
      
       //mostramos los establecimientos
       
       for (int i=0;i<coordenadas.length; i++){
    	 String [] punto = new String[2];
         GeoPoint geoPunto = null;
      	 Log.d("coordenadas[i]", coordenadas[i]);
    	 punto = (coordenadas[i].toString().split(";"));
    	 Double latTem= ((Double)Double.parseDouble(punto[0])) * 1000000;
    	 int latitud = latTem.intValue();
    	 Log.d("latitud", ((Integer)latitud).toString());
    	 Double longTem= ((Double)Double.parseDouble(punto[1])) * 1000000;
    	 int longitud = longTem.intValue();
    	 Log.d("longitud", ((Integer)longitud).toString());
    	 geoPunto = new GeoPoint(latitud, longitud);
    	 OverlayItem overlayItemEst =  new OverlayItem(geoPunto, "", "");
    	 itemizedOverlayGlobo.addOverlay(overlayItemEst);
               
        }
       mapOverlays.add(itemizedOverlayGlobo);      
       
       Button botonMostrarLista = (Button) findViewById(R.id.mostrarListado);
       botonMostrarLista.setText(getResources().getString(R.string.botonlista));
       botonMostrarLista.setOnClickListener(new OnClickListener(){
       	public void onClick(View v){
       		Intent intent = new Intent(MapaEstablecimiento.this, ListadoPoi.class);
       		Bundle bundle = new Bundle();
       		bundle.putString("idCatSeleccionada", categoriaSeleccionada);
   	    	intent.putExtras(bundle);	    	
   	        startActivityForResult(intent, 0);
       	}
       });
       final MyLocationOverlay myLocation = new MyLocationOverlay(this, mapView);
      mapView.getOverlays().add(myLocation);
      myLocation.enableCompass();
      myLocation.enableMyLocation();
      /*myLocation.runOnFirstFix(new Runnable() {
          public void run() {
              mc.animateTo(myLocation.getMyLocation());
          }
            
       });*/
    }
   
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}
