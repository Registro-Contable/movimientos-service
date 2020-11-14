package com.victorlh.registrocontable.movimientosservice.domain.service.impl;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victorlh.registrocontable.movimientosservice.domain.conf.ETipoMovimiento;
import com.victorlh.registrocontable.movimientosservice.domain.exceptions.FechaRepetidaException;
import com.victorlh.registrocontable.movimientosservice.domain.model.Movimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.MovimientoBuilder;
import com.victorlh.registrocontable.movimientosservice.domain.service.MovimientosService;
import com.victorlh.registrocontable.movimientosservice.infrastructure.entities.MovimientoEntity;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.api.CuentasFeign;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.MedioPagoResponseDTO;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.TipoMedioPagoResponseDTO;
import com.victorlh.registrocontable.movimientosservice.infrastructure.repositories.MovimientosRepository;
import com.victorlh.registrocontable.movimientosservice.mappers.MovimientosEntityMapper;

@Service
public class MovimientosServiceImpl implements MovimientosService {

	@Autowired
	private MovimientosRepository movimientosRepository;
	@Autowired
	private MovimientosEntityMapper movimientosEntityMapper;
	@Autowired
	private CuentasFeign cuentasFeign;

	@Override
	public List<Movimiento> getMovimientosUsuario(String uid, Date fromDate, Date toDate) {
		fromDate = fromDate != null ? fromDate : minDate();
		toDate = toDate != null ? toDate : maxDate();

		List<MovimientoEntity> movimientosEntities = movimientosRepository.findByUidAndFechaBetweenOrderByFechaAsc(uid, fromDate, toDate);

		return movimientosEntityMapper.listMovimientosEntityToListMovimientos(movimientosEntities);
	}

	@Override
	public List<Movimiento> getMovimientosCuenta(String cuentaId, Date fromDate, Date toDate) {
		fromDate = fromDate != null ? fromDate : minDate();
		toDate = toDate != null ? toDate : maxDate();
		
		List<MovimientoEntity> movimientosEntities = movimientosRepository.findByCuentaIdAndFechaBetweenOrderByFechaAsc(cuentaId, fromDate, toDate);

		return movimientosEntityMapper.listMovimientosEntityToListMovimientos(movimientosEntities);
	}

	@Override
	public List<Movimiento> getMovimientosUsuario(String uid, Date fromDate, Date toDate, ETipoMovimiento tipoMovimiento) {
		fromDate = fromDate != null ? fromDate : minDate();
		toDate = toDate != null ? toDate : maxDate();

		List<MovimientoEntity> movimientosEntities = movimientosRepository.findByUidAndTipoMovimientoIdAndFechaBetweenOrderByFechaAsc(uid,
				tipoMovimiento.name(), fromDate, toDate);

		return movimientosEntityMapper.listMovimientosEntityToListMovimientos(movimientosEntities);
	}

	@Override
	public List<Movimiento> getMovimientosCuenta(String cuentaId, Date fromDate, Date toDate, ETipoMovimiento tipoMovimiento) {
		fromDate = fromDate != null ? fromDate : minDate();
		toDate = toDate != null ? toDate : maxDate();

		List<MovimientoEntity> movimientosEntities = movimientosRepository.findByCuentaIdAndTipoMovimientoIdAndFechaBetweenOrderByFechaAsc(cuentaId,
				tipoMovimiento.name(), fromDate, toDate);

		return movimientosEntityMapper.listMovimientosEntityToListMovimientos(movimientosEntities);
	}
	
	@Override
	public Optional<Movimiento> getMovimiento(Long movimientoId) {
		Optional<MovimientoEntity> movimiento = movimientosRepository.findById(movimientoId);
		return movimiento.map(movimientosEntityMapper::movimientoEntityToMovimiento);
	}

	@Override
	public Movimiento nuevoMovimiento(String uid, MovimientoBuilder builder) {
		if (builder.isMovimientoContable() == null) {
			MedioPagoResponseDTO medioPagoDetalles = cuentasFeign.medioPagoDetalles(builder.getCuentaId(), builder.getMedioPagoId());
			TipoMedioPagoResponseDTO tipoMedioPago = medioPagoDetalles.getTipoMedioPago();
			builder.setMovimientoContable(tipoMedioPago.isMovimientoContable());
		}

		MovimientoEntity entity = movimientosEntityMapper.movimientoBuilderToMovimientoEntity(builder);
		entity.setUid(uid);

		entity = updateCapitalAndSave(entity);

		List<MovimientoEntity> posterioresEntities = movimientosRepository.findByCuentaIdAndFechaGreaterThanOrderByFechaAsc(entity.getCuentaId(),
				entity.getFecha());
		posterioresEntities.forEach(e -> {
			updateCapitalAndSave(e);
		});

		return movimientosEntityMapper.movimientoEntityToMovimiento(entity);
	}

	@Override
	public Movimiento editarMovimiento(Movimiento movimiento, MovimientoBuilder builder) {
		if (!StringUtils.equals(movimiento.getCuentaId(), builder.getCuentaId())
				|| !StringUtils.equals(movimiento.getMedioPagoId(), builder.getMedioPagoId()) || !movimiento.getFecha().equals(builder.getFecha())) {
			borrarMovimiento(movimiento);
			return nuevoMovimiento(movimiento.getUid(), builder);
		}

		Optional<MovimientoEntity> entityOpt = movimientosRepository.findById(movimiento.getId());
		MovimientoEntity entity = entityOpt.get();

		entity.setCategoriaId(builder.getCategoriaId());
		entity.setConcepto(builder.getConcepto());
		entity.setNota(builder.getNota());

		if (entity.getCantidad() != builder.getCantidad()) {
			entity.setCantidad(builder.getCantidad());

			entity = updateCapitalAndSave(entity);

			List<MovimientoEntity> posterioresEntities = movimientosRepository.findByCuentaIdAndFechaGreaterThanOrderByFechaAsc(entity.getCuentaId(),
					entity.getFecha());
			posterioresEntities.forEach(e -> {
				updateCapitalAndSave(e);
			});
		}

		return movimientosEntityMapper.movimientoEntityToMovimiento(entity);
	}

	@Override
	public void borrarMovimiento(Movimiento movimiento) {
		movimientosRepository.deleteById(movimiento.getId());

		List<MovimientoEntity> posterioresEntities = movimientosRepository.findByCuentaIdAndFechaGreaterThanOrderByFechaAsc(movimiento.getCuentaId(),
				movimiento.getFecha());
		posterioresEntities.forEach(e -> {
			updateCapitalAndSave(e);
		});
	}

	private MovimientoEntity updateCapitalAndSave(MovimientoEntity entity) {
		Optional<MovimientoEntity> previoEntityOpt = movimientosRepository.findFirstByCuentaIdAndFechaLessThanOrderByFechaDesc(entity.getCuentaId(),
				entity.getFecha());
		double capitalPrevio = 0;
		if (previoEntityOpt.isPresent()) {
			MovimientoEntity previoEntity = previoEntityOpt.get();
			capitalPrevio = previoEntity.getCapitalPosterior();
		}
		entity.setCapitalPrevio(capitalPrevio);
		entity.setCapitalPosterior(capitalPrevio + entity.getCantidad());
		
		try {
			return movimientosRepository.save(entity);
		}catch (Exception e) {
			throw new FechaRepetidaException(e);
		}
	}
	
	private Date minDate() {
		return new GregorianCalendar(1900, 0, 1).getTime();
	}
	
	private Date maxDate() {
		return new GregorianCalendar(2900, 11, 31).getTime();
	}

}
