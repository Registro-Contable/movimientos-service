package com.victorlh.registrocontable.movimientosservice.domain.model;

import java.util.Date;
import java.util.List;

import com.victorlh.registrocontable.movimientosservice.domain.conf.ETipoMovimiento;

public class Movimiento {

	private Long id;
	private String uid;
	private Date fecha;
	private ETipoMovimiento tipoMovimiento;
	private Categoria categoria;
	private Cuenta cuenta;
	private double cantidad;
	private String concepto;
	private String nota;

	private boolean isMovimientoContable;

	private Double capitalAnterior;
	private Double capitalPosterior;

	private List<Movimiento> movimietosAsociados;

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

	public ETipoMovimiento getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(ETipoMovimiento tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
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

	public List<Movimiento> getMovimietosAsociados() {
		return movimietosAsociados;
	}

	public void setMovimietosAsociados(List<Movimiento> movimietosAsociados) {
		this.movimietosAsociados = movimietosAsociados;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

}
