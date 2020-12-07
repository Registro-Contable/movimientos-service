package com.victorlh.registrocontable.movimientosservice.domain.model;

public class CapitalCuenta {

	private Cuenta cuenta;
	private Double capital;

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public Double getCapital() {
		return capital;
	}

	public void setCapital(Double capital) {
		this.capital = capital;
	}

}
