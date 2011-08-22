package com.artesanos;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.Contacts.Intents.Insert;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class InformacionEstablecimiento extends Activity{
	 String telf;
	 String web;
	 ImageView imagen;
	 ImageAdapter imgAdapter;
	 //TODO: pasar ruta a variable de BD o configuración
	 //String ruta = "http://10.0.2.2:80/drupal/";
	 String ruta = "http://192.168.1.130:80/drupal/";
	 String puntoActual;
	 String puntoDestino;
	 String mail;
	 ArrayList<Establecimiento> listaEstablecimientos;
	 Establecimiento estSeleccionado;
	 TextView email;
	 TextView pagWeb;
	 String dirWeb;
	 
	 private String[] setFullPathImg(String URLimagenes){
		 String[] imagenes = null;
		 if (!(URLimagenes.equals(null)))
		 {
			 imagenes = URLimagenes.split(";");		 
			 for (int i=0;i<imagenes.length;i++){
				 if ((!(imagenes[i].equals(null)) || imagenes[i].contains("null")))
				 {
					 imagenes[i] = ruta.concat(imagenes[i]);
				 }
			 }			 
		 }		 
		 return imagenes;
	 }
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.info5);
	        
	   /* final Establecimiento estSeleccionado = new Establecimiento("nombre", "direccion", "distancia");
	   	 estSeleccionado.setWeb("http://www.google.es");
	   	 estSeleccionado.setDescripcion("Establecimiento de artesanía");
	   	 estSeleccionado.setDireccion("avda rio tamoga nº1, Cospeito");
	   	 estSeleccionado.setTwitter("http://www.twitter.com");
	   	 estSeleccionado.setFacebook("http://www.facebook.com");
	   	 estSeleccionado.setTelefono("555000000");
	   	 estSeleccionado.setEmail("tienda2@arte.com");*/
	   	 
        
	        Bundle bundle = getIntent().getExtras();
	        int posicion = bundle.getInt("posicion");
	        listaEstablecimientos = (ArrayList<Establecimiento>) bundle.getSerializable("listaEstablecimientos");
	        estSeleccionado = listaEstablecimientos.get(posicion);
	       
	        //web = bundle.getString("web");
	        
	        TextView nombre = (TextView) findViewById(R.id.nombre);
	        LinearLayout filanombre = (LinearLayout) findViewById(R.id.layoutnombre);
	        filanombre.setBackgroundColor(Color.WHITE);
	        String nombreEst = estSeleccionado.getNombre();
	        if (nombreEst.equals(null) || nombreEst.equals(""))
	        	nombre.setText(getResources().getString(R.string.nodisponible));
	        else
	        	nombre.setText(nombreEst);
	        nombre.setTextColor(Color.DKGRAY);
	        
	        
	        
	       /* TextView descripcion = (TextView) findViewById(R.id.descripcion);
	        //descripcion.setText(bundle.getString("descripcion"));
	        descripcion.setText(estSeleccionado.getDescripcion());*/
	        
	        
	        TextView direccion = (TextView) findViewById(R.id.direccion);
	        LinearLayout filadireccion = (LinearLayout) findViewById(R.id.layoutdireccion);
	        filadireccion.setBackgroundColor(Color.WHITE);
	        direccion.setTextColor(Color.BLACK);
	        if (estSeleccionado.getDireccion().equals(null) || estSeleccionado.getDireccion().equals(""))
	        	filadireccion.setVisibility(View.GONE);
	        	//direccion.setText(getResources().getString(R.string.nodisponible));
	        else
	        direccion.setText(estSeleccionado.getDireccion());
	        
	        TextView textoTelf = (TextView) findViewById(R.id.telefono);
	        LinearLayout filallamar = (LinearLayout) findViewById(R.id.layouttelefono);
	        filallamar.setBackgroundColor(Color.WHITE);
	        Button botonLLamar = (Button) findViewById(R.id.llamar);
	        botonLLamar.setText(getResources().getString(R.string.llamar));
	        //telf = bundle.getString("telefono");
	        telf = estSeleccionado.getTelefono();
	        if (telf.equals(null)|| telf.equals(""))
	        	filallamar.setVisibility(View.GONE);
	        	//telf = getResources().getString(R.string.nodisponible);
	        else
	        {
		        textoTelf.setText(telf);
		        
		        textoTelf.setTextColor(Color.BLACK);
		        filallamar.setOnClickListener(new OnClickListener(){
		        	public void onClick(View v){
		        		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "+telf));
		        		startActivity(intent);
		        	}
		        });
		        
		       
		        botonLLamar.setOnClickListener(new OnClickListener(){
		        	public void onClick(View v){
		        		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "+telf));
		        		startActivity(intent);
		        	}
		        });
	        }
	        
	        
	        pagWeb = (TextView) findViewById(R.id.web);
	        LinearLayout filaweb = (LinearLayout) findViewById(R.id.layoutweb);
	        dirWeb = estSeleccionado.getWeb();
	        if (dirWeb.equals(null) || dirWeb.compareTo("") ==0 || dirWeb.compareTo("null")==0 || dirWeb.compareTo("No disponible")==0)
	        	filaweb.setVisibility(View.GONE);
	        	//pagWeb.setText(getResources().getString(R.string.nodisponible));
	        else
		    {
	        	pagWeb.setText(dirWeb);	 
	        	pagWeb.setTextColor(Color.BLACK);
		        filaweb.setOnClickListener(new OnClickListener(){
		        	public void onClick(View v){
		        		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
		        		startActivity(intent);
		        	}
		        });
		        
		        LinearLayout filaWeb = (LinearLayout) findViewById(R.id.layoutweb);
		        filaWeb.setBackgroundColor(Color.WHITE);
		       /* filaWeb.setOnClickListener(new OnClickListener(){
		        	public void onClick(View v){
		        		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(dirWeb));
		        		startActivity(intent);
		        	}
		        });*/
		       
		        web =estSeleccionado.getWeb();
		        Button verWeb = (Button) findViewById(R.id.verWeb);
		        verWeb.setText(getResources().getString(R.string.web));
		        verWeb.setOnClickListener(new OnClickListener(){
		        	public void onClick(View v){
		        		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
		        		startActivity(intent);
		        	}
		        });
	        }
	        	        
	        email = (TextView) findViewById(R.id.email);
	        mail = estSeleccionado.getEmail();
	       // String mail = "tienda@artegal.com";
	        LinearLayout filaEmail = (LinearLayout) findViewById(R.id.layoutemail);
	        if (mail == null || mail.compareTo("")==0 || mail.compareTo("No disponible") == 0 || mail.compareTo("null")==0)
	        	filaEmail.setVisibility(View.GONE);
	        	//mail = getResources().getString(R.string.nodisponible);
	        else
	        {
	        	email.setText(mail);	        	
	        	filaEmail.setBackgroundColor(Color.WHITE);
	        	email.setTextColor(Color.BLACK);
	        	filaEmail.setOnClickListener(new OnClickListener(){
		        public void onClick(View v){
			        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		            emailIntent.setType("plain/text");
		            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ email.getText().toString()});
		            InformacionEstablecimiento.this.startActivity(Intent.createChooser(emailIntent, "Enviar correo"));
			    }
	        });  
	        	Button botonEmail = (Button) findViewById(R.id.mandarEmail);
	  	        botonEmail.setText(getResources().getString(R.string.email));
	  	        botonEmail.setOnClickListener(new OnClickListener(){
	  		        public void onClick(View v){
	  			        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
	  		            emailIntent.setType("plain/text");
	  		            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ email.getText().toString()});
	  		            //emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject.getText());     
	  		            //emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailtext.getText());	
	  		            InformacionEstablecimiento.this.startActivity(Intent.createChooser(emailIntent, "Enviar correo"));
	  			    }
	  	        }); 
	        }
	       
	        TextView facebook = (TextView) findViewById(R.id.facebook);
	        LinearLayout filaFacebook = (LinearLayout) findViewById(R.id.layoutfacebook);
	        //String fb = "http://www.facebook.com";
	        String fb = estSeleccionado.getFacebook();
	        Log.d("fb", fb);
	        if (fb.equals(null) || fb.compareTo("")==0 || fb.compareTo("No disponible")==0 || fb.compareTo("null")==0)
	        	filaFacebook.setVisibility(View.GONE);
	        	//facebook.setText(getResources().getString(R.string.nodisponible));
	        else
	        {
		        facebook.setText(fb);
		        filaFacebook.setBackgroundColor(Color.WHITE);
		        facebook.setTextColor(Color.BLACK);
		        filaFacebook.setOnClickListener(new OnClickListener(){
		        	public void onClick(View v){
		        		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(estSeleccionado.getFacebook()));
		        		startActivity(intent);
		        	}
		        });
	        }
	        	        
	        TextView twitter = (TextView) findViewById(R.id.twitter);
	        LinearLayout filaTwitter = (LinearLayout) findViewById(R.id.layouttwitter);
	        filaTwitter.setBackgroundColor(Color.WHITE);
	        //String tw = "http://www.twitter.com";
	        String tw = estSeleccionado.getTwitter();
	        if (tw.equals(null) || tw.compareTo("") == 0 || tw.compareTo("null")==0 || tw.compareTo("No disponible")==0){
	        	filaTwitter.setVisibility(View.GONE);
	        	twitter.setText(getResources().getString(R.string.nodisponible));
	        }
	        else
	        {
		        twitter.setText(estSeleccionado.getTwitter());		        
		        twitter.setTextColor(Color.BLACK);
		        filaTwitter.setOnClickListener(new OnClickListener(){
		        	public void onClick(View v){
		        		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(estSeleccionado.getTwitter()));
		        		startActivity(intent);
		        	}
		        });
	        }
	        
	        TextView descripcion = (TextView) findViewById(R.id.textinfo);
	        LinearLayout filaDescripcion = (LinearLayout) findViewById(R.id.layoutinfo);
	        filaDescripcion.setBackgroundColor(Color.WHITE);
	        String desc = estSeleccionado.getDescripcion();
	        if (desc.equals(null) || desc.compareTo("")==0 || desc.compareTo("null")==0 || desc.compareTo("No disponible")==0)
	        	filaDescripcion.setVisibility(View.GONE);
	        	//descripcion.setText(getApplication().getResources().getString(R.string.nodisponible));
	        else{
	        	descripcion.setText(desc);
	        	descripcion.setTextColor(Color.BLACK);
	        }

	        Button verRuta = (Button) findViewById(R.id.verRuta);
	        verRuta.setText(getResources().getString(R.string.ruta));
	        puntoActual = bundle.getString("puntoActual");
	        puntoDestino = bundle.getString("puntoDestino");
	      /*  puntoActual = "1,2";
	        puntoDestino = "3,4";*/
	        if (!(puntoActual.equals(null)) && (!puntoDestino.equals(null)))
	        {
		        verRuta.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Uri uri =Uri.parse("http://maps.google.com/maps?&saddr="+puntoActual+"&daddr="+puntoDestino);
		         	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		         	startActivity(intent);
				}
				});
	        }
	        
	        Button almacenarContacto = (Button) findViewById(R.id.contacto);
	        almacenarContacto.setText(getResources().getString(R.string.contacto));
	        almacenarContacto.setOnClickListener(new OnClickListener(){
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String notes = "facebook: "+estSeleccionado.getFacebook()+" "+
		               "twitter: "+estSeleccionado.getTwitter()+" "+
		               "web: "+estSeleccionado.getWeb()+" ";
	
				     Intent intentContacto = new Intent(Intent.ACTION_INSERT_OR_EDIT);				     
				  	intentContacto.putExtra(Insert.NAME,estSeleccionado.getNombre());  
				  	intentContacto.putExtra(Insert.PHONE, Uri.decode(estSeleccionado.getTelefono()));        	
				  	intentContacto.putExtra(Insert.EMAIL, estSeleccionado.getEmail());
				  	intentContacto.putExtra(Insert.NOTES, notes);
				  	//intentContacto.putExtra(Insert.POSTAL, estSeleccionado.getLocationPostalCode());
				  	intentContacto.setType(Contacts.People.CONTENT_ITEM_TYPE);
				  	startActivity(intentContacto);
				}
	        	
	        });
	        
	        Gallery g = (Gallery) findViewById(R.id.Gallery01);
	        String URLimagenes = bundle.getString("URLimagenesEstablecimiento");
	        if (URLimagenes.compareTo("") == 0 || URLimagenes.compareTo("null")==0 || URLimagenes.compareTo("No disponible")==0)
	        	g.setVisibility(View.GONE);
	       // String URLimagenes = "http://imgur.com/3t8A3.jpg";
	        //la ruta es local, incluimos la ruta absoluta
	        final String[] imagenes = setFullPathImg(URLimagenes);
	       
	        imgAdapter = new ImageAdapter(this, imagenes);
	        g.setAdapter(imgAdapter);
	        imagen = (ImageView) findViewById(R.id.imagen);
	        g.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView parent, View v, int position, long id) {             
	            	Intent intent = new Intent(InformacionEstablecimiento.this, ImagenEstablecimiento.class);
	            	Bundle bundle = new Bundle();
	            	bundle.putString("urlImgSeleccionada", imagenes[position]);
	            	intent.putExtras(bundle);
	            	startActivity(intent);
	            }
	        });
     
	 }
	 
	 public boolean onCreateOptionsMenu(Menu menu) {
	     MenuInflater inflater = getMenuInflater();
	     inflater.inflate(R.menu.menuestablecimiento, menu);
	     return true;
	 }
	 
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
		     // Handle item selection
			 
		     switch (item.getItemId()) {
		     case R.id.menullamar:
		    	 Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "+telf));
	        		startActivity(intent);
	        		Log.d("opcionMenu", "llamar");
	        		return true;
		     case R.id.menucontacto:
		    	 String notes = "facebook: "+estSeleccionado.getFacebook()+" "+
	               "twitter: "+estSeleccionado.getTwitter()+" "+
	               "web: "+estSeleccionado.getWeb()+" ";

			     Intent intentContacto = new Intent(Intent.ACTION_INSERT_OR_EDIT);				     
			  	intentContacto.putExtra(Insert.NAME,estSeleccionado.getNombre());  
			  	intentContacto.putExtra(Insert.PHONE, Uri.decode(estSeleccionado.getTelefono()));        	
			  	intentContacto.putExtra(Insert.EMAIL, estSeleccionado.getEmail());
			  	intentContacto.putExtra(Insert.NOTES, notes);
			  	//intentContacto.putExtra(Insert.POSTAL, estSeleccionado.getLocationPostalCode());
			  	intentContacto.setType(Contacts.People.CONTENT_ITEM_TYPE);
			  	startActivity(intentContacto);
			  	Log.d("opcionMenu", "contacto");
			  	return true;
		    
		     case R.id.menuemail:
		    	 final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		            emailIntent.setType("plain/text");
		            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{mail});
		            //emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject.getText());     
		            //emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailtext.getText());	
		            InformacionEstablecimiento.this.startActivity(Intent.createChooser(emailIntent, "Enviar correo"));
		            Log.d("opcionMenu", "email");
		            return true;
		            
		     case R.id.menuweb:
		    	 Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(dirWeb));
		    	 startActivity(intentWeb);
		    	 Log.d("opcionMenu", "web");
	        		return true;
	        		
		     case R.id.menuruta:
		    	Log.d("puntodestino", puntoDestino);
		    	 Uri uri =Uri.parse("http://maps.google.com/maps?&saddr="+puntoActual+"&daddr="+puntoDestino);
		         	Intent intentRuta = new Intent(Intent.ACTION_VIEW, uri);
		         	startActivity(intentRuta);
		         	Log.d("opcionMenu", "ruta");
		         	return true;
	     
	    	default:
	         return super.onOptionsItemSelected(item);
	    		//return true;
	    }
	   
	    }
	 
	 public class ImageAdapter extends BaseAdapter {
		    int mGalleryItemBackground;
		    private Context mContext;

//		    private Integer[] mImageIds = {
//		            R.drawable.img1,
//		            R.drawable.img10,
//		            R.drawable.img2,
//		            R.drawable.img3,
//		            R.drawable.img4,
//		            R.drawable.img5,
//		            R.drawable.img6,
//		            R.drawable.img7,
//		            R.drawable.img8,
//		            R.drawable.img9,         
//		    };
		    
		    private String[] mImageIds = new String[]{};

		    public ImageAdapter(Context c, String[] imagenes) {
		        mContext = c;
		        this.mImageIds = imagenes;
		        TypedArray a = obtainStyledAttributes(R.styleable.galeriaImagenes);
		        mGalleryItemBackground = a.getResourceId(
		                R.styleable.galeriaImagenes_android_galleryItemBackground, 0);
		        a.recycle();
		    }

		    public int getCount() {
		        return mImageIds.length;
		    }
		    
		    
		    public Object getItem(int position) {
		        return mImageIds[position];
		    }

		    public long getItemId(int position) {
		        return position;
		    }

		    /*public View getView(int position, View convertView, ViewGroup parent) {
		        ImageView i = new ImageView(mContext);

		        i.setImageResource(mImageIds[position]);
		        i.setLayoutParams(new Gallery.LayoutParams(150, 100));
		        i.setScaleType(ImageView.ScaleType.FIT_XY);
		        i.setBackgroundResource(mGalleryItemBackground);

		        return i;
		    }*/
		    
		    public View getView(int position, View convertView, ViewGroup parent) {
	            ImageView i = new ImageView(mContext);
	            i.setLayoutParams(new Gallery.LayoutParams(300, 200));
		        i.setScaleType(ImageView.ScaleType.FIT_XY);
		        i.setBackgroundResource(mGalleryItemBackground);
	            try {
	                                //Abre la URL de la imagen y obtiene el InputStream para cargar datos
	                                URL aURL = new URL(mImageIds[position]);
	                                URLConnection conn = aURL.openConnection();
	                                conn.connect();
	                                InputStream is = conn.getInputStream();
	                                BufferedInputStream bis = new BufferedInputStream(is);
	                                //Transforma los datos a bitmap
	                                Bitmap bm = BitmapFactory.decodeStream(bis);
	                                bis.close();
	                                is.close();
	                                //Aplica el bitmap al imageView 
	                                i.setImageBitmap(bm);
	                        } catch (IOException e) {
	                                i.setImageResource(R.drawable.error);
	                                Log.e("DEBUGTAG", "Remote Image Exception", e);
	                        }
	                        return i;
		    }
		    
		    public Bitmap getBmp(int position) {
		    	Bitmap bm = null;
	            try {
                   
                    URL aURL = new URL(mImageIds[position]);
                    URLConnection conn = aURL.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();                   
                    BufferedInputStream bis = new BufferedInputStream(is);
                    bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    is.close();
	            } catch (IOException e) {
                     Log.e("DEBUGTAG", "Remote Image Exception", e);
                }
	           return bm;
		    }
   
	 }	

}
