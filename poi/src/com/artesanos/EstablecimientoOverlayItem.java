package com.artesanos;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class EstablecimientoOverlayItem extends OverlayItem{
	
	public final GeoPoint punto;
	public final Establecimiento establecimiento;
	
	public EstablecimientoOverlayItem(GeoPoint punto, Establecimiento establecimiento){
		super(punto, establecimiento.getNombre(), "");
		this.punto = punto;
		this.establecimiento = establecimiento;
	}
	
	

}
