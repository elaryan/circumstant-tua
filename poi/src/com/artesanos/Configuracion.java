package com.artesanos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Configuracion extends Activity{
	
	EditText radio;
	
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.configuracion);
	        
	        TextView textoRadio = (TextView) findViewById(R.id.textoRadio);
	        textoRadio.setText("Radio de búsqueda (m)");
	        
	        radio = (EditText) findViewById(R.id.radio);
	        radio.setText("");
	        	        
	        Button guardarPrefRadio = (Button) findViewById(R.id.guardarPreferenciasRadio);
	        guardarPrefRadio.setText("Guardar");
	        guardarPrefRadio.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		SharedPreferences settings = getSharedPreferences("RadioPrefs", Context.MODE_WORLD_WRITEABLE);
	  	        	SharedPreferences.Editor editor = settings.edit();
	  	        	editor.putString("radio", radio.getText().toString());
	  	        	Log.d("radioPref", radio.getText().toString());
	  	        	Toast toast = Toast.makeText(Configuracion.this, "Preferencias guardadas", Toast.LENGTH_SHORT);
	  	        	toast.show();
	        	}
	        });
	  }
}
