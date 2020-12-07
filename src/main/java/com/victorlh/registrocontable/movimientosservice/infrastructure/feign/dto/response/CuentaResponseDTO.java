package com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CuentaResponseDTO {

	private String id;
	private String uid;
	private TipoCuentaResponseDTO tipoCuenta;
	private String nombre;
	private double capital;
	private Date fechaAlta;
	private List<MedioPagoResponseDTO> mediosPago;

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

	public TipoCuentaResponseDTO getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(TipoCuentaResponseDTO tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getCapital() {
		return capital;
	}

	public void setCapital(double capital) {
		this.capital = capital;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public List<MedioPagoResponseDTO> getMediosPago() {
		if (mediosPago == null) {
			mediosPago = new ArrayList<>();
		}
		return mediosPago;
	}

	public void setMediosPago(List<MedioPagoResponseDTO> mediosPago) {
		this.mediosPago = mediosPago;
	}

}
