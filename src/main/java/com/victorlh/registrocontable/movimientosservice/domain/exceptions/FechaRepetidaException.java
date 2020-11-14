package com.victorlh.registrocontable.movimientosservice.domain.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class FechaRepetidaException extends DataIntegrityViolationException {
	private static final long serialVersionUID = -4769300506489432123L;

	private static final String MSG = "Ya existe un movimiento con esta fecha";

	public FechaRepetidaException(Throwable e) {
		super(MSG, e);
	}

}
