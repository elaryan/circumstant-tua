package com.artesanos;

import java.util.ArrayList;

import android.app.Application;

 class listaEstablecimientos extends Application{
  
 private ArrayList<Establecimiento> listaEst = null;
 
 	public ArrayList<Establecimiento> getListaEstablecimientos(){
	    return listaEst;
	  }
	  public void setState(ArrayList<Establecimiento> listEs){
	    listaEst = listEs;
	  }


}
