package com.poi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SelectorOrigenDatos extends Activity{
	
	//formulario que permite al usuario seleccionar el origen de los datos
	//opciones: sitio local / indicar ip 
	String bundledIP = "";

	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	              
	        setContentView(R.layout.origendatos);
	        final EditText ipDrupalText = (EditText) findViewById(R.id.ipdrupal); 
	        ipDrupalText.setText("");
	                
	        Button continuar = (Button) findViewById(R.id.continuar);
	        continuar.setText("Continuar");
	        continuar.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		
	        		Spinner origenDatosSpinner = (Spinner) findViewById(R.id.Spinner01);
	    	        String origenSeleccionado = origenDatosSpinner.getSelectedItem().toString();
	    	        
	    	        if (origenSeleccionado.equals("localhost"))
	    	        {	        	        		
	    	        	bundledIP = "10.10.0.2";
	    	        }
	    	        else
	    	        {	
	      	        	bundledIP = ipDrupalText.getText().toString();
	    	        }
	    	        
	        		Intent myIntent = new Intent(v.getContext(), ListadoCategorias.class);
	        		Bundle bundle = new Bundle();
	    	    	bundle.putString("IPDrupal", bundledIP);
	    	    	myIntent.putExtras(bundle);	    	
	    	        startActivityForResult(myIntent, 0);
	        	}
	        });
	    
	        
	 }

}
