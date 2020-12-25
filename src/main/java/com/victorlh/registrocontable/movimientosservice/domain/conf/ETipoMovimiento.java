package com.victorlh.registrocontable.movimientosservice.domain.conf;

import java.util.Optional;

public enum ETipoMovimiento {

	INGRESO, GASTO, TRASPASO, AJUSTE_CAPITAL;

	public static Optional<ETipoMovimiento> findById(String id) {
		try {
			ETipoMovimiento tipo = valueOf(id);
			return Optional.of(tipo);
		} catch (Throwable e) {
			return Optional.empty();
		}
	}
}
