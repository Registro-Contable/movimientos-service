package com.victorlh.registrocontable.movimientosservice.api.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.victorlh.registrocontable.movimientosservice.api.dto.response.MovimientoResponseDTO;
import com.victorlh.registrocontable.movimientosservice.domain.conf.ETipoMovimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.Movimiento;
import com.victorlh.registrocontable.movimientosservice.domain.service.MovimientosService;
import com.victorlh.registrocontable.movimientosservice.mappers.MovimientosApiMapper;

@RestController
@RequestMapping("/")
public class MovimientosController {

	@Autowired
	private MovimientosService movimientoService;
	@Autowired
	private MovimientosApiMapper movimientosApiMapper;

	@GetMapping({ "/", "" })
	public List<MovimientoResponseDTO> lista(@RequestParam(name = "tipoMovimiento") String tipoMovimientoId,
			@RequestParam(name = "cuentaId", required = false) String cuentaId,
			@RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = ISO.DATE_TIME) Date fromDate,
			@RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = ISO.DATE_TIME) Date toDate, Authentication auth) {
		String uid = (String) auth.getPrincipal();

		Optional<ETipoMovimiento> tipoMovimientoOpt = ETipoMovimiento.findById(tipoMovimientoId);
		ETipoMovimiento tipoMovimiento = tipoMovimientoOpt
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tipo de movimiento no es valido"));

		List<Movimiento> movimientos;
		if (StringUtils.isEmpty(cuentaId)) {
			movimientos = movimientoService.getMovimientosUsuario(uid, fromDate, toDate, tipoMovimiento);
		} else {
			movimientos = movimientoService.getMovimientosCuenta(cuentaId, fromDate, toDate, tipoMovimiento);
		}

		return movimientosApiMapper.listaMovimientosToListaMovimientosResponse(movimientos);
	}
}
