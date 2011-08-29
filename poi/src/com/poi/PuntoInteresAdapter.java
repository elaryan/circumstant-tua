package com.poi;

import java.util.List;

import com.poi.R;

import android.R.color;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PuntoInteresAdapter extends BaseAdapter{
	
	private final Context contexto;
	private final List<PuntoInteres> puntosInteres;
	
	
	public PuntoInteresAdapter(Context ctx, List<PuntoInteres> establecimientos){
		this.contexto = ctx;
		this.puntosInteres = establecimientos;
	}

	public int getCount() {
		return this.puntosInteres.size();
	}

	public Object getItem(int posicion) {		
		return this.puntosInteres.get(posicion);
	}

	public long getItemId(int posicion) {
		return posicion;
	}

	/*public View getView(int posicion, View vista, ViewGroup parent) {
		PuntoInteres poi = this.establecimientos.get(posicion);
		return new PuntoInteresListView(this.contexto, poi.getNombre(), poi.getDireccion(),  poi.getDistancia());
	}*/
	
	
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater mInflater = null;
        ViewHolder holder;
        mInflater = LayoutInflater.from(this.contexto);
        convertView = mInflater.inflate(R.layout.filamaquetada, null);
        PuntoInteres poi = this.puntosInteres.get(position);

        // Creates a ViewHolder and store references to the children views
        holder = new ViewHolder();
        holder.nombre = (TextView) convertView.findViewById(R.id.nombreEstFila);
        holder.direccion = (TextView) convertView.findViewById(R.id.direccionEstFila);
        holder.distancia = (TextView) convertView.findViewById(R.id.distanciaEstFila);
        convertView.setTag(holder);

        // Bind the data with the holder.
        holder.nombre.setText(poi.getNombre());  
        holder.direccion.setText(poi.getDireccion());
        holder.distancia.setText(poi.getDistancia() + " m");

        convertView.setBackgroundColor(Color.WHITE);
        
        return convertView;
    }

    static class ViewHolder {
        TextView nombre;
        TextView direccion;
        TextView distancia;
    }
    
    public View getView(int position) {
		LayoutInflater mInflater = null;
		View convertView;
        ViewHolder holder;
        mInflater = LayoutInflater.from(this.contexto);
        convertView = mInflater.inflate(R.layout.filamaquetada, null);
        PuntoInteres poi = this.puntosInteres.get(position);

        // Creates a ViewHolder and store references to the children views
        holder = new ViewHolder();
        holder.nombre = (TextView) convertView.findViewById(R.id.nombreEstFila);
        holder.direccion = (TextView) convertView.findViewById(R.id.direccionEstFila);
        holder.distancia = (TextView) convertView.findViewById(R.id.distanciaEstFila);
        convertView.setTag(holder);

        // Bind the data with the holder.
        holder.nombre.setText(poi.getNombre());
        holder.direccion.setText(poi.getDireccion());
        holder.distancia.setText(poi.getDistancia());
        
        convertView.setBackgroundColor(Color.WHITE);
        holder.nombre.setTextColor(color.black);
        holder.direccion.setTextColor(color.black);
        holder.distancia.setTextColor(color.black);
        return convertView;
    }


}
