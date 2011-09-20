package com.poi;

import java.util.ArrayList;
//import java.util.List;

//import android.app.AlertDialog;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toast;

import com.poi.R;
import com.google.android.maps.ItemizedOverlay;
//import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class PuntoInteresItemizedOverlay extends ItemizedOverlay {
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	Context ctx;
	ArrayList<PuntoInteres> listaPuntosInteres = new ArrayList<PuntoInteres>();
	String[] imagenes;
	String puntoActual;
	String ip;
	
	public PuntoInteresItemizedOverlay(Drawable defaultMarker, Context context, ArrayList<PuntoInteres> listaPoi, String[] imagenes, String puntoActual, String ip) {
		super(boundCenterBottom(defaultMarker));
		this.ctx = context;
		this.listaPuntosInteres = listaPoi;
		this.imagenes = imagenes;
		this.puntoActual = puntoActual;
		this.ip = ip;
		
	}
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	

	@Override
	protected OverlayItem createItem(int i) {
	  return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	
	 @Override
	 protected boolean onTap(int index) {		 
		
		 PuntoInteres poi;
		 Log.d("sizeFromMapa", ((Integer)this.listaPuntosInteres.size()).toString());	 		
		 Log.d("index", ((Integer)index).toString());
		 if (index==listaPuntosInteres.size()){
			 index = 0;
			 Log.d("index", "index cambiado");
		 }
		 poi = this.listaPuntosInteres.get(index);
		
		 final int position = index;
		 LayoutInflater inflater = LayoutInflater.from(this.ctx);
		 View v = inflater.inflate(R.layout.detalles, null);
		 TextView nombre = (TextView) v.findViewById(R.id.estSelectNombre);
		 nombre.setText(poi.getNombre());
		
		 new AlertDialog.Builder(this.ctx).setView(v).setPositiveButton("Más informacion", 
				 new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent( ctx, InformacionPuntoInteres.class);
						Bundle bundle = new Bundle();
						bundle.putString("web", listaPuntosInteres.get(position).getWeb());
						bundle.putString("telefono", listaPuntosInteres.get(position).getTelefono());
						bundle.putString("URLimagenesEstablecimiento", imagenes[position]);
						bundle.putString("nombre", listaPuntosInteres.get(position).telefono);						
						bundle.putString("puntoDestino", listaPuntosInteres.get(position).getLatitud()+ "," + listaPuntosInteres.get(position).getLongitud());
						bundle.putString("puntoActual", puntoActual);
						bundle.putSerializable("listaPuntosInteres", listaPuntosInteres);
						bundle.putInt("posicion", position);
						bundle.putString("IPDrupal", ip);
						intent.putExtras(bundle);
						ctx.startActivity(intent);
						
					}
				})
				.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				})
				.show();
	
		 
	 return true;
	 }
	 

}
