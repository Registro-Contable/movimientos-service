package com.victorlh.registrocontable.movimientosservice.domain.model;

import com.victorlh.registrocontable.movimientosservice.domain.conf.ETipoMovimiento;

import java.util.Date;
import java.util.List;

public class MovimientoBuilder implements Cloneable {

	private Date fecha;
	private ETipoMovimiento tipoMovimiento;
	private String categoriaId;
	private String subCategoriaId;
	private String cuentaId;
	private String medioPagoId;
	private double cantidad;
	private String concepto;
	private String nota;
	private Boolean isMovimientoContable;

	private String cuentaDestinoId;
	private String cuentaOrigenId;
	private String medioPagoOrigenId;

	private List<Movimiento> movimientosAsociados;

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

	public List<Movimiento> getMovimientosAsociados() {
		return movimientosAsociados;
	}

	public void setMovimientosAsociados(List<Movimiento> movimientosAsociados) {
		this.movimientosAsociados = movimientosAsociados;
	}

	public Boolean isMovimientoContable() {
		return isMovimientoContable;
	}

	public void setMovimientoContable(Boolean isMovimientoContable) {
		this.isMovimientoContable = isMovimientoContable;
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

	public String getCuentaOrigenId() {
		return cuentaOrigenId;
	}

	public void setCuentaOrigenId(String cuentaOrigenId) {
		this.cuentaOrigenId = cuentaOrigenId;
	}

	public String getMedioPagoOrigenId() {
		return medioPagoOrigenId;
	}

	public void setMedioPagoOrigenId(String medioPagoOrigenId) {
		this.medioPagoOrigenId = medioPagoOrigenId;
	}

	@Override
	public MovimientoBuilder clone() {
		try {
			return (MovimientoBuilder) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
