package com.victorlh.registrocontable.movimientosservice.api.dto.response;

public class CuentaResponseDTO {

	private String cuentaId;
	private String nombre;
	private MedioPagoResponseDTO medioPago;

	public String getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(String cuentaId) {
		this.cuentaId = cuentaId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public MedioPagoResponseDTO getMedioPago() {
		return medioPago;
	}

	public void setMedioPago(MedioPagoResponseDTO medioPago) {
		this.medioPago = medioPago;
	}
}
