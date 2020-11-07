package com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response;

public class TipoCuentaResponseDTO {

	private String id;
	private String nombre;
	private boolean allowTarjetaDebito;
	private boolean allowTarjetaCredito;
	private boolean isDeuda;

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

	public boolean isAllowTarjetaDebito() {
		return allowTarjetaDebito;
	}

	public void setAllowTarjetaDebito(boolean allowTarjetaDebito) {
		this.allowTarjetaDebito = allowTarjetaDebito;
	}

	public boolean isAllowTarjetaCredito() {
		return allowTarjetaCredito;
	}

	public void setAllowTarjetaCredito(boolean allowTarjetaCredito) {
		this.allowTarjetaCredito = allowTarjetaCredito;
	}

	public boolean isDeuda() {
		return isDeuda;
	}
	
	public void setIsDeuda(boolean isDeuda) {
		this.isDeuda = isDeuda;
	}

}
