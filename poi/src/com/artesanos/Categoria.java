package com.artesanos;

public class Categoria {

	String idCategoria;
	String nombre;
	String descripcion;
	
	public Categoria(String idCategoria, String nombre, String descripcion){
		this.idCategoria = idCategoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		
	}
	
	public void setIdCategoria(String idCategoria){
		this.idCategoria = idCategoria;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	} 
	
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}
	
	public String getIdCategoria(){
		return this.idCategoria;
	}
	
	public String getNombre(){
		return this.nombre;
	} 
	
	public String getDescripcion(){
		return this.descripcion;
	}
}
