package com.poi;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class PuntoInteresOverlayItem extends OverlayItem{
	
	public final GeoPoint punto;
	public final PuntoInteres poi;
	
	public PuntoInteresOverlayItem(GeoPoint punto, PuntoInteres establecimiento){
		super(punto, establecimiento.getNombre(), "");
		this.punto = punto;
		this.poi = establecimiento;
	}
	
	

}
