package com.victorlh.registrocontable.movimientosservice.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MovimientoRequestDTO {

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fecha;
	private String tipoMovimientoId;
	private String categoriaId;
	private String subCategoriaId;
	private String cuentaId;
	private String medioPagoId;
	private double cantidad;
	private String concepto;
	private String nota;
	private String cuentaDestinoId;
	//	private String medioPagoDestinoId;

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

	public String getSubCategoriaId() {
		return subCategoriaId;
	}

	public void setSubCategoriaId(String subCategoriaId) {
		this.subCategoriaId = subCategoriaId;
	}

	public String getCuentaDestinoId() {
		return cuentaDestinoId;
	}

	public void setCuentaDestinoId(String cuentaDestinoId) {
		this.cuentaDestinoId = cuentaDestinoId;
	}

}
