package com.victorlh.registrocontable.movimientosservice.domain.service;

import java.util.Date;
import java.util.List;

import com.victorlh.registrocontable.movimientosservice.domain.conf.ETipoMovimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.Movimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.MovimientoBuilder;

public interface MovimientosService {

	public List<Movimiento> getMovimientosUsuario(String uid, Date desde, Date hasta);

	public List<Movimiento> getMovimientosCuenta(String cuentaId, Date desde, Date hasta);

	public List<Movimiento> getMovimientosUsuario(String uid, Date desde, Date hasta, ETipoMovimiento tipoMovimiento);

	public List<Movimiento> getMovimientosCuenta(String cuentaId, Date desde, Date hasta, ETipoMovimiento tipoMovimiento);

	public Movimiento nuevoMovimiento(String uid, MovimientoBuilder builder);
	
	public Movimiento editarMovimiento(Movimiento movimiento, MovimientoBuilder builder);
	
	public void borrarMovimiento(Movimiento movimiento);
	
}
