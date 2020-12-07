package com.victorlh.registrocontable.movimientosservice.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.victorlh.registrocontable.movimientosservice.domain.conf.ETipoMovimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.CapitalCuenta;
import com.victorlh.registrocontable.movimientosservice.domain.model.Movimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.MovimientoBuilder;

public interface MovimientosService {

	public List<Movimiento> getMovimientosUsuario(String uid, Date fromDate, Date toDate);

	public List<Movimiento> getMovimientosCuenta(String cuentaId, Date fromDate, Date toDate);

	public List<Movimiento> getMovimientosUsuario(String uid, Date fromDate, Date toDate, ETipoMovimiento tipoMovimiento);

	public List<Movimiento> getMovimientosCuenta(String cuentaId, Date fromDate, Date toDate, ETipoMovimiento tipoMovimiento);
	
	public CapitalCuenta getCapitalCuenta(String cuentaId); 
	
	public Optional<Movimiento> getMovimiento(Long movimientoId);

	public Movimiento nuevoMovimiento(String uid, MovimientoBuilder builder);
	
	public Movimiento editarMovimiento(Movimiento movimiento, MovimientoBuilder builder);
	
	public void borrarMovimiento(Movimiento movimiento);
	
}
