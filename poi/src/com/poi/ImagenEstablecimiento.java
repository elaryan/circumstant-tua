package com.poi;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.poi.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class ImagenEstablecimiento extends Activity{
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        setContentView(R.layout.imagen);
	        ImageView imagen = (ImageView) findViewById(R.id.imagen);
	        
	        Bundle bundle = getIntent().getExtras();
	        String imgURL = bundle.getString("urlImgSeleccionada");
	        
	    try{    
	        URLConnection conn = (new URL(imgURL)).openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            //Transforma los datos a bitmap
            Bitmap bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
            //Aplica el bitmap al imageView 
            imagen.setImageBitmap(bm);
    } catch (IOException e) {
            imagen.setImageResource(R.drawable.error);
            Log.e("DEBUGTAG", "Remote Image Exception", e);
    }
	       // imagen.setImageURI(Uri.parse(imgURL));
	        
	        
	 }

}
