package com.poi;

import com.poi.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EstablecimientoListView extends LinearLayout{
	
	private TextView nombre;
	private TextView direccion;
	private TextView distancia;
	private ImageButton flecha;
		
	public EstablecimientoListView(Context ctx, String nombre, String direccion, String distancia){
		super(ctx);
		
		setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
										ViewGroup.LayoutParams.WRAP_CONTENT,
										ViewGroup.LayoutParams.WRAP_CONTENT);
		params.setMargins(5, 3, 5, 0);
		
		this.nombre = new TextView(ctx);
		this.nombre.setText(nombre);
		this.nombre.setTextSize(16f);
		this.nombre.setTextColor(Color.WHITE);
		this.addView(this.nombre, params);
		
		this.direccion = new TextView(ctx);
		this.direccion.setText(direccion);
		this.direccion.setTextSize(16f);
		this.direccion.setTextColor(Color.WHITE);
		this.addView(this.direccion, params);
		
		this.distancia = new TextView(ctx);
		this.distancia.setText(distancia + " metros");
		this.distancia.setTextSize(16f);
		this.distancia.setTextColor(Color.WHITE);
		this.addView(this.distancia, params);
		
		this.flecha = new ImageButton(ctx);
		Drawable icono = getResources().getDrawable(R.drawable.flecha);
		this.flecha.setBackgroundDrawable(icono);
		this.addView(this.flecha, params);
		
		
	}
}
