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

public class EstablecimientoItemizedOverlay extends ItemizedOverlay {
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	Context ctx;
	ArrayList<Establecimiento> listaEstablecimientos = new ArrayList<Establecimiento>();
	String[] imagenes;
	String puntoActual;
	
	
	public EstablecimientoItemizedOverlay(Drawable defaultMarker, Context context, ArrayList<Establecimiento> listaEst, String[] imagenes, String puntoActual) {
		super(boundCenterBottom(defaultMarker));
		this.ctx = context;
		this.listaEstablecimientos = listaEst;
		this.imagenes = imagenes;
		this.puntoActual = puntoActual;
		//this.populate();
		//this.establecimientos = null;
		// TODO Auto-generated constructor stub
	}
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	

	@Override
	protected OverlayItem createItem(int i) {
	  return mOverlays.get(i);
		//return this.establecimientos.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();
	}
	
	 @Override
	 protected boolean onTap(int index) {		 
		 /*Intent myIntent = new Intent(this.ctx, InformacionEstablecimiento.class);
	     this.ctx.startActivity(myIntent);*/
		 Establecimiento est;
		 Log.d("sizeFromMapa", ((Integer)this.listaEstablecimientos.size()).toString());	 		
		 Log.d("index", ((Integer)index).toString());
		 if (index==listaEstablecimientos.size()){
			 index = 0;
			 Log.d("index", "index cambiado");
		 }
		 est = this.listaEstablecimientos.get(index);
		
		 final int position = index;
		 LayoutInflater inflater = LayoutInflater.from(this.ctx);
		 View v = inflater.inflate(R.layout.detalles, null);
		 TextView nombre = (TextView) v.findViewById(R.id.estSelectNombre);
		 nombre.setText(est.getNombre());
		 /*TextView direccion = (TextView) v.findViewById(R.id.estSelectdir);
		 direccion.setText(est.getDireccion());*/
		 
		 new AlertDialog.Builder(this.ctx).setView(v).setPositiveButton("Más informacion", 
				 new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent( ctx, InformacionEstablecimiento.class);
						Bundle bundle = new Bundle();
						bundle.putString("web", listaEstablecimientos.get(position).getWeb());
						bundle.putString("telefono", listaEstablecimientos.get(position).getTelefono());
						bundle.putString("URLimagenesEstablecimiento", imagenes[position]);
						bundle.putString("nombre", listaEstablecimientos.get(position).telefono);						
						bundle.putString("puntoDestino", listaEstablecimientos.get(position).getLatitud()+ "," + listaEstablecimientos.get(position).getLongitud());
						bundle.putString("puntoActual", puntoActual);
						bundle.putSerializable("listaEstablecimientos", listaEstablecimientos);
						bundle.putInt("posicion", position);
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
		 
		/* Toast toast = Toast.makeText(this.ctx, "InformacionEstablecimiento", Toast.LENGTH_SHORT);
       	 toast.show();*/
		 
	 return true;
	 }
	 

}
