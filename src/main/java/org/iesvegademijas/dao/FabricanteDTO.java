package org.iesvegademijas.dao;

import org.iesvegademijas.model.Fabricante;

public class FabricanteDTO extends Fabricante {

	private int numProductos;

	public FabricanteDTO() {}

	public Integer getNumProductos() {
		return numProductos;
	}

	public void setNumProductos(int numProductos) {
		this.numProductos = numProductos;
	}

	@Override
	public String toString() {
		return super.toString() + ", número de productos=" + numProductos;
	}

}