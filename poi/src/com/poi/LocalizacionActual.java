package com.poi;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;

public class LocalizacionActual extends MapActivity{

	private LocationManager locationManager;
	private LocationProvider locationProvider;
	
	public Location getLocalizacionActual(LocationManager locationManager){
		//obtenemos la localización actual
	    //this.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		this.locationManager = locationManager;
	    this.locationProvider = (LocationProvider) this.locationManager.getProvider(LocationManager.GPS_PROVIDER);
	    //GeoPoint ultimaLocalizacion = LocationHelper.getGeoPoint(this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
	    Location ultimaLocalizacion = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	    
	    return ultimaLocalizacion;
	}
	
	public String getDistancia(Location localizacionActual, Location localizacionEstablecimiento){
		 //calculo de distancia entre dos localizaciones
		
        Float distancia = localizacionActual.distanceTo(localizacionEstablecimiento);
        return ((Integer)(distancia.intValue())).toString() + " metros";
	}
	
	public String getDistancia(String latIni, String latFin, String longIni, String longFin){
		
		double lat1, lat2, long1, long2;
		//Location("reverseGeocode");
		try {
			lat1 = Double.parseDouble(latIni);
			lat2 = Double.parseDouble(latFin);
			long1 = Double.parseDouble(longIni);
			long2 = Double.parseDouble(longFin);
			
			float [] resultados = new float[]{};
			Location.distanceBetween(lat1, long1, lat2, long2, resultados);		
			return ((Float)resultados[0]).toString() + " metros";
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error al obtener la distancia entre los dos puntos";
		}	

	}
	
	  public int distanciaFormula2(double latitudPunto1, double 
			  longitudPunto1,double latitudPunto2, double longitudPunto2)
			           {
			            Location bLocation = new Location("reverseGeocoded");
			            bLocation.setLatitude(latitudPunto1);
			            bLocation.setLongitude(longitudPunto1);
			            Location aLocation = new Location("reverseGeocoded");
			            aLocation.setLatitude(latitudPunto2);
			            aLocation.setLongitude(longitudPunto2);


			            int distance = (int)aLocation.distanceTo(bLocation);
			            return distance;
	 }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
