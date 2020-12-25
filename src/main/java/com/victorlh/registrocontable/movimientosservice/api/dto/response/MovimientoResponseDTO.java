package com.victorlh.registrocontable.movimientosservice.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MovimientoResponseDTO {

	private Long id;
	private String uid;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fecha;
	private String tipoMovimientoId;
	private CategoriaResponseDTO categoria;
	private CuentaResponseDTO cuenta;
	private double cantidad;
	private String concepto;
	private String nota;

	private CuentaResponseDTO cuentaDestino;
	private CuentaResponseDTO cuentaOrigen;

	private boolean isMovimientoContable;

	private Double capitalAnterior;
	private Double capitalPosterior;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

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

	public CategoriaResponseDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaResponseDTO categoria) {
		this.categoria = categoria;
	}

	public CuentaResponseDTO getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaResponseDTO cuenta) {
		this.cuenta = cuenta;
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

	public boolean isMovimientoContable() {
		return isMovimientoContable;
	}

	public void setMovimientoContable(boolean isMovimientoContable) {
		this.isMovimientoContable = isMovimientoContable;
	}

	public Double getCapitalAnterior() {
		return capitalAnterior;
	}

	public void setCapitalAnterior(Double capitalAnterior) {
		this.capitalAnterior = capitalAnterior;
	}

	public Double getCapitalPosterior() {
		return capitalPosterior;
	}

	public void setCapitalPosterior(Double capitalPosterior) {
		this.capitalPosterior = capitalPosterior;
	}

	public CuentaResponseDTO getCuentaDestino() {
		return cuentaDestino;
	}

	public void setCuentaDestino(CuentaResponseDTO cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}

	public CuentaResponseDTO getCuentaOrigen() {
		return cuentaOrigen;
	}

	public void setCuentaOrigen(CuentaResponseDTO cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}
}
