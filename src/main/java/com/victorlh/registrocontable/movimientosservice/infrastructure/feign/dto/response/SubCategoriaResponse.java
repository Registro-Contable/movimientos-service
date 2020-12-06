package com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response;

public class SubCategoriaResponse {

	private String id;
	private String nombre;
	private String parentId;

	public SubCategoriaResponse() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
