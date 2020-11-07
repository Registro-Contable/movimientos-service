package com.victorlh.registrocontable.movimientosservice.domain.model;

import java.util.Date;
import java.util.List;

import com.victorlh.registrocontable.movimientosservice.domain.conf.ETipoMovimiento;

public class MovimientoBuilder {

	private Date fecha;
	private ETipoMovimiento tipoMovimiento;
	private String categoriaId;
	private String cuentaId;
	private String medioPagoId;
	private double cantidad;
	private String concepto;
	private String nota;

	private List<Movimiento> movimietosAsociados;

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

	public List<Movimiento> getMovimietosAsociados() {
		return movimietosAsociados;
	}

	public void setMovimietosAsociados(List<Movimiento> movimietosAsociados) {
		this.movimietosAsociados = movimietosAsociados;
	}

}
