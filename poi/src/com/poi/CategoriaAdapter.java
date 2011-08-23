package com.poi;

import com.poi.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

	public class CategoriaAdapter extends ArrayAdapter<String> {
		Context contexto;
        private String[] items;

        public CategoriaAdapter(Context context, int textViewResourceId, String[] items) {
                super(context, textViewResourceId, items);
                this.items = items;
                this.contexto = context;
        }
	

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)this.contexto.getSystemService
           (Context.LAYOUT_INFLATER_SERVICE);
         View row=inflater.inflate(R.layout.filacategoria, parent, false);
         
         TextView categoria=(TextView)row.findViewById(R.id.filaCategoria);
         categoria.setText(items[position]);
         categoria.setTextColor(Color.WHITE);
       
         ImageView icon=(ImageView)row.findViewById(R.id.iconoCategoria);

         if (position == 0)
          icon.setImageResource(R.drawable.monumento);        
         else if (position == 1)
          icon.setImageResource(R.drawable.museo);
         else if (position == 2)
              icon.setImageResource(R.drawable.naturaleza);
         else if (position == 3)
        	 icon.setImageResource(R.drawable.hotel);
         else
             icon.setImageResource(R.drawable.generico);
         
        // LinearLayout fila = (LinearLayout) row.findViewById(R.id.layoutFilaLista);
        // fila.setBackgroundColor(Color.TRANSPARENT);
         
         
         return row;
        }
 }
