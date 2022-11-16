package org.iesvegademijas.model;

import java.math.BigDecimal;

public class Producto {

	private int codigo;
	private String nombre;
	private BigDecimal precio;
	private int codigo_fabricante;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	
	public int getCodigoFabricante() {
		return this.codigo_fabricante;
	}

	public void setCodigoFabricante(int codigo_fabricante) {
		this.codigo_fabricante = codigo_fabricante;
	}

	@Override
	public String toString() {
		return "Producto [codigo=" + codigo + ", nombre=" + nombre + ", precio=" + precio + "]";
	}
	
	
	
}
