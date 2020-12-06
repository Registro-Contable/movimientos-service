package com.victorlh.registrocontable.movimientosservice.domain.model;

public class SubCategoria {

	private String subCategoriaId;
	private String parentId;
	private String nombre;

	public String getSubCategoriaId() {
		return subCategoriaId;
	}

	public void setSubCategoriaId(String subCategoriaId) {
		this.subCategoriaId = subCategoriaId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
