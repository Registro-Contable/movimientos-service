package com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response;

import java.util.List;

public class CategoriaResponse {

	public String id;
	public String uid;
	public String tipoMovimiento;
	public String nombre;
	public List<SubCategoriaResponse> subCategorias;

	public CategoriaResponse() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<SubCategoriaResponse> getSubCategorias() {
		return subCategorias;
	}

	public void setSubCategorias(List<SubCategoriaResponse> subCategorias) {
		this.subCategorias = subCategorias;
	}

}
