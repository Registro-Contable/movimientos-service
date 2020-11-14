package com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response;

import java.util.Date;

public class MedioPagoResponseDTO {

	private String id;
	private String uid;
	private TipoMedioPagoResponseDTO tipoMedioPago;
	private String nombre;
	private Date fechaAlta;

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

	public TipoMedioPagoResponseDTO getTipoMedioPago() {
		return tipoMedioPago;
	}

	public void setTipoMedioPago(TipoMedioPagoResponseDTO tipoMedioPago) {
		this.tipoMedioPago = tipoMedioPago;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

}
