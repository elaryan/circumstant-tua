package com.artesanos;

import java.io.Serializable;

public class Establecimiento implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String nombre;
	String direccion;
	String distancia;
	String latitud;
	String longitud;
	String telefono;
	String email;
	String descripcion;
	String web;
	String imagenes;
	String facebook;
	String twitter;
	
	public Establecimiento(String nombre, String direccion, String distancia){		
		this.nombre = nombre;
		this.direccion = direccion;
		this.distancia = distancia;
		
	}

	public Establecimiento(String nombre, String direccion, String latitud,
			String longitud) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.latitud = latitud;
		this.longitud = longitud;
	}
	

	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public void setDireccion(String direccion){
		this.direccion = direccion;
	}
	
	public void setDistancia(String distancia){
		this.distancia = distancia;
	}
	
	public void setLatitud(String latitud){
		this.latitud = latitud;
	}
	
	public void setLongitud(String longitud){
		this.longitud = longitud;
	}
	
	public void setTelefono(String telefono){
		this.telefono = telefono;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setWeb(String web){
		this.web = web;
	}
	
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}
	
	public void setFacebook(String facebook){
		this.facebook = facebook;
	}
	
	public void setTwitter(String twitter){
		this.twitter = twitter;
	}
	
	public void setImagenes(String imagenes){
		this.imagenes = imagenes;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public String getDireccion(){
		return this.direccion;
	}
	
	public String getDistancia(){
		return this.distancia;
	}
	
	public String getLatitud(){
		return this.latitud;
	}
	
	public String getLongitud(){
		return this.longitud;
	}
	
	public String getTelefono(){
		return this.telefono;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public String getWeb(){
		return this.web;
	}
	
	public String getDescripcion(){
		return this.descripcion;
	}
	
	public String getFacebook(){
		return this.facebook;
	}
	
	public String getTwitter(){
		return this.twitter;
	}
	
	public String getImagenes(){
		return this.imagenes;
	}

}
