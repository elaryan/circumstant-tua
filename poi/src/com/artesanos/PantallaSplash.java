package com.artesanos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class PantallaSplash extends Activity{
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        
	        setContentView(R.layout.splash);
	        ImageView splash = (ImageView) findViewById(R.id.splashview);
	        Drawable pantallaSplash = this.getResources().getDrawable(R.drawable.fondo);
	        splash.setImageDrawable(pantallaSplash);
	        Handler handlerSplash = new Handler();
	        handlerSplash.postDelayed(new splashhandler(), 2000);	        
	 }
	 
	 class splashhandler implements Runnable{

		public void run() {
			startActivity(new Intent(getApplication(), listadoCategorias.class));
			//startActivity(new Intent(getApplication(), InformacionEstablecimiento.class));
			PantallaSplash.this.finish();
		}
		 
	 }

}
