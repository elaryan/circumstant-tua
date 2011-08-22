package com.artesanos;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class listadoEstablecimientos extends Activity{
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.lista);
	        String [] ESTABLECIMIENTOS = new String[]{"tienda1", "tienda2", "tienda3", "tienda4", "tienda5"};
	        final List <Establecimiento> establecimientosList = null;
	        Establecimiento est1 = new Establecimiento("tienda1", "tienda", "direccion", "13364740,-9406450");
	        Establecimiento est2 = new Establecimiento("tienda2", "tienda", "direccion", "23364740,-7406450");
	        //establecimientosList.add(est1);
	        //establecimientosList.add(est2);
	        Bundle bundle = getIntent().getExtras();
	        String categoria = bundle.getString("categoria");
	        Log.d("Categoría", categoria);
	        
	        final ListView listaEst = (ListView) findViewById(R.id.ListView01);
	        TextView titulo = (TextView) findViewById(R.id.TextView01);	        
	        titulo.setText(categoria);
	        listaEst.setAdapter(new ArrayAdapter<String>(this, R.layout.fila, ESTABLECIMIENTOS));
	        listaEst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				Intent myIntent = new Intent(listadoEstablecimientos.this, MapaEstablecimiento.class);
		        Bundle bundle = new Bundle();
		        bundle.putString("categoria", listaEst.getItemAtPosition(position).toString());
		        String [] coordenadas = null;
		        /*for (int i=0; i<establecimientosList.size(); i++){
		        	Establecimiento estTemp = (Establecimiento) establecimientosList.get(i);
		        	coordenadas[i] = estTemp.getCoordenadas();
		        }*/
		        bundle.putStringArray("coordenadas", coordenadas);
		        myIntent.putExtras(bundle);
		        Log.d("Categoria", listaEst.getItemAtPosition(position).toString());
				startActivityForResult(myIntent, 0);
			}
        	
		});
	        
	        
	       
	        //setListAdapter(new ArrayAdapter<String>(this, R.layout.main, getResources().getStringArray(R.array.categorias)));
	        //setListAdapter(new ArrayAdapter<String>(this, R.layout.main, ESTABLECIMIENTOS)); 
	       	    }
	   
	    protected void onListItemClick(ListView l, View v, int position, 
	    		long id){
	    	Intent myIntent = new Intent(v.getContext(), MapaEstablecimiento.class);
	        startActivityForResult(myIntent, 0);

	    }

}
