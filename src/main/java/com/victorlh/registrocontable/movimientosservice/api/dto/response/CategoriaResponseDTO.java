package com.victorlh.registrocontable.movimientosservice.api.dto.response;

public class CategoriaResponseDTO {

	private String categoriaId;
	private String nombre;
	private SubCategoriaResponseDTO subCategoria;

	public String getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(String categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public SubCategoriaResponseDTO getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(SubCategoriaResponseDTO subCategoria) {
		this.subCategoria = subCategoria;
	}

}
