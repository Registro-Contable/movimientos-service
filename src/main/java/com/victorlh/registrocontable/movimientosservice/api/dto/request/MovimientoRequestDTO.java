package com.victorlh.registrocontable.movimientosservice.api.dto.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MovimientoRequestDTO {

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date fecha;
	private String tipoMovimientoId;
	private String categoriaId;
	private String cuentaId;
	private String medioPagoId;
	private double cantidad;
	private String concepto;
	private String nota;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipoMovimientoId() {
		return tipoMovimientoId;
	}

	public void setTipoMovimientoId(String tipoMovimientoId) {
		this.tipoMovimientoId = tipoMovimientoId;
	}

	public String getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(String categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(String cuentaId) {
		this.cuentaId = cuentaId;
	}

	public String getMedioPagoId() {
		return medioPagoId;
	}

	public void setMedioPagoId(String medioPagoId) {
		this.medioPagoId = medioPagoId;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

}
