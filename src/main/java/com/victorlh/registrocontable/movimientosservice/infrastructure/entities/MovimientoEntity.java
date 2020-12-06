package com.victorlh.registrocontable.movimientosservice.infrastructure.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movimientos", schema = "movimientos")
public class MovimientoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Date fecha;

	@Column(name = "tipo_movimiento_id", length = 30, nullable = false)
	private String tipoMovimientoId;

	@Column(name = "uid", nullable = false)
	private String uid;

	@Column(name = "cuenta_id")
	private String cuentaId;

	@Column(name = "medio_pago_id")
	private String medioPagoId;

	@Column(nullable = false)
	private double cantidad;

	@Column(name = "categoria_id")
	private String categoriaId;

	@Column(name = "suv_categoria_id")
	private String subCategoriaId;

	@Column
	private String concepto;

	@Column
	private String nota;

	@Column(name = "is_movimiento_contable", nullable = false)
	private boolean isMovimientoContable;

	@Column(name = "capital_previo")
	private Double capitalPrevio;

	@Column(name = "capital_posterior")
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

	public String getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(String categoriaId) {
		this.categoriaId = categoriaId;
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

	public Double getCapitalPrevio() {
		return capitalPrevio;
	}

	public void setCapitalPrevio(Double capitalPrevio) {
		this.capitalPrevio = capitalPrevio;
	}

	public Double getCapitalPosterior() {
		return capitalPosterior;
	}

	public void setCapitalPosterior(Double capitalPosterior) {
		this.capitalPosterior = capitalPosterior;
	}

	public String getSubCategoriaId() {
		return subCategoriaId;
	}

	public void setSubCategoriaId(String subCategoriaId) {
		this.subCategoriaId = subCategoriaId;
	}

}
