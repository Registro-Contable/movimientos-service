package com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response;

public class TipoMedioPagoResponseDTO {

	private String id;
	private String nombre;
	private boolean isMovimientoContable;

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

	public boolean isMovimientoContable() {
		return isMovimientoContable;
	}

	public void setIsMovimientoContable(boolean isMovimientoContable) {
		this.isMovimientoContable = isMovimientoContable;
	}

}
