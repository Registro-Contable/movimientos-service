package com.victorlh.registrocontable.movimientosservice.domain.service;

import com.victorlh.registrocontable.movimientosservice.domain.conf.ETipoMovimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.Movimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.MovimientoBuilder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MovimientosService {

	List<Movimiento> getMovimientosUsuario(String uid, Date fromDate, Date toDate);

	List<Movimiento> getMovimientosCuenta(String cuentaId, Date fromDate, Date toDate);

	List<Movimiento> getMovimientosUsuario(String uid, Date fromDate, Date toDate, ETipoMovimiento tipoMovimiento);

	List<Movimiento> getMovimientosCuenta(String cuentaId, Date fromDate, Date toDate, ETipoMovimiento tipoMovimiento);

	Optional<Movimiento> getMovimiento(Long movimientoId);

	Movimiento nuevoMovimiento(String uid, MovimientoBuilder builder);

	Movimiento editarMovimiento(Movimiento movimiento, MovimientoBuilder builder);

	void borrarMovimiento(Movimiento movimiento);

}
