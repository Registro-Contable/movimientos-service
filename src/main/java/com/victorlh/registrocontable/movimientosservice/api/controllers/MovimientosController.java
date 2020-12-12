package com.victorlh.registrocontable.movimientosservice.api.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.victorlh.registrocontable.movimientosservice.api.dto.request.MovimientoRequestDTO;
import com.victorlh.registrocontable.movimientosservice.api.dto.response.MovimientoResponseDTO;
import com.victorlh.registrocontable.movimientosservice.domain.conf.ETipoMovimiento;
import com.victorlh.registrocontable.movimientosservice.domain.exceptions.FechaRepetidaException;
import com.victorlh.registrocontable.movimientosservice.domain.model.Movimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.MovimientoBuilder;
import com.victorlh.registrocontable.movimientosservice.domain.service.MovimientosService;
import com.victorlh.registrocontable.movimientosservice.mappers.MovimientosApiMapper;

@RestController
@RequestMapping
public class MovimientosController {

	@Autowired
	private MovimientosService movimientoService;
	@Autowired
	private MovimientosApiMapper movimientosApiMapper;

	@GetMapping({ "/", "" })
	public List<MovimientoResponseDTO> listaMovimientos(@RequestParam(name = "tipoMovimiento", required = false) String tipoMovimientoId,
			@RequestParam(name = "cuentaId", required = false) String cuentaId,
			@RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromDate,
			@RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date toDate, Authentication auth) {
		String uid = (String) auth.getPrincipal();

		Optional<ETipoMovimiento> tipoMovimientoOpt = ETipoMovimiento.findById(tipoMovimientoId);

		List<Movimiento> movimientos;

		if (tipoMovimientoOpt.isPresent()) {
			ETipoMovimiento tipoMovimiento = tipoMovimientoOpt.get();
			if (StringUtils.isEmpty(cuentaId)) {
				movimientos = movimientoService.getMovimientosUsuario(uid, fromDate, toDate, tipoMovimiento);
			} else {
				movimientos = movimientoService.getMovimientosCuenta(cuentaId, fromDate, toDate, tipoMovimiento);
			}
		} else {
			if (StringUtils.isEmpty(cuentaId)) {
				movimientos = movimientoService.getMovimientosUsuario(uid, fromDate, toDate);
			} else {
				movimientos = movimientoService.getMovimientosCuenta(cuentaId, fromDate, toDate);
			}
		}

		return movimientosApiMapper.listaMovimientosToListaMovimientosResponse(movimientos);
	}

	@GetMapping("/{id}")
	public MovimientoResponseDTO detallesMovimiento(@PathVariable Long id, Authentication auth) {
		String uid = (String) auth.getPrincipal();

		Optional<Movimiento> movimientoOpt = movimientoService.getMovimiento(id);
		Movimiento movimiento = movimientoOpt
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El movimiento indicado no existe"));

		validarPropiedad(movimiento, uid);

		return movimientosApiMapper.movimientoToMovimientoResponse(movimiento);

	}

	@PostMapping({ "/", "" })
	public MovimientoResponseDTO crearMovimiento(@RequestBody MovimientoRequestDTO request, Authentication auth) {
		String uid = (String) auth.getPrincipal();

		MovimientoBuilder builder = movimientosApiMapper.movimientoRequestToMovimientoBuilder(request);

		try {
			Movimiento movimiento = movimientoService.nuevoMovimiento(uid, builder);
			return movimientosApiMapper.movimientoToMovimientoResponse(movimiento);
		} catch (FechaRepetidaException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
		}
	}

	@PutMapping("/{id}")
	public MovimientoResponseDTO modificarMovimiento(@PathVariable Long id, @RequestBody MovimientoRequestDTO request, Authentication auth) {
		String uid = (String) auth.getPrincipal();

		Optional<Movimiento> movimientoOpt = movimientoService.getMovimiento(id);
		Movimiento movimiento = movimientoOpt
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El movimiento indicado no existe"));

		validarPropiedad(movimiento, uid);

		MovimientoBuilder builder = movimientosApiMapper.movimientoRequestToMovimientoBuilder(request);

		try {
			movimiento = movimientoService.editarMovimiento(movimiento, builder);
		} catch (FechaRepetidaException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
		}

		return movimientosApiMapper.movimientoToMovimientoResponse(movimiento);
	}

	@DeleteMapping("/{id}")
	public void borrarMovimiento(@PathVariable Long id, Authentication auth) {
		String uid = (String) auth.getPrincipal();

		Optional<Movimiento> movimientoOpt = movimientoService.getMovimiento(id);
		Movimiento movimiento = movimientoOpt
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El movimiento indicado no existe"));

		validarPropiedad(movimiento, uid);

		movimientoService.borrarMovimiento(movimiento);
	}

	private void validarPropiedad(Movimiento movimiento, String uid) {
		if (!StringUtils.equals(movimiento.getUid(), uid)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para acceder a este recurso");
		}
	}
}
