package com.victorlh.registrocontable.movimientosservice.api.dto.response;

public class CapitalCuentaResponseDTO {

	private CuentaResponseDTO cuenta;
	private double capital;

	public CuentaResponseDTO getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaResponseDTO cuenta) {
		this.cuenta = cuenta;
	}

	public double getCapital() {
		return capital;
	}

	public void setCapital(double capital) {
		this.capital = capital;
	}

}
