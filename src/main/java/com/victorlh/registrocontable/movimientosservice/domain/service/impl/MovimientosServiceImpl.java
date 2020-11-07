package com.victorlh.registrocontable.movimientosservice.domain.service.impl;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.victorlh.registrocontable.movimientosservice.domain.conf.ETipoMovimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.Movimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.MovimientoBuilder;
import com.victorlh.registrocontable.movimientosservice.domain.service.MovimientosService;
import com.victorlh.registrocontable.movimientosservice.infrastructure.entities.MovimientoEntity;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.api.TiposCuentasFeign;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.TipoMedioPagoResponseDTO;
import com.victorlh.registrocontable.movimientosservice.infrastructure.repositories.MovimientosRepository;
import com.victorlh.registrocontable.movimientosservice.mappers.MovimientosEntityMapper;

public class MovimientosServiceImpl implements MovimientosService {

	@Autowired
	private MovimientosRepository movimientosRepository;
	@Autowired
	private MovimientosEntityMapper movimientosEntityMapper;
	@Autowired
	private TiposCuentasFeign tipoCuentasFeign;

	@Override
	public List<Movimiento> getMovimientosUsuario(String uid, Date desde, Date hasta) {
		desde = desde != null ? desde : Date.from(Instant.MIN);
		hasta = hasta != null ? hasta : Date.from(Instant.MAX);

		List<MovimientoEntity> movimientosEntities = movimientosRepository.findByUidAndDates(uid, desde, hasta);

		return movimientosEntityMapper.listMovimientosEntityToListMovimientos(movimientosEntities);
	}

	@Override
	public List<Movimiento> getMovimientosCuenta(String cuentaId, Date desde, Date hasta) {
		desde = desde != null ? desde : Date.from(Instant.MIN);
		hasta = hasta != null ? hasta : Date.from(Instant.MAX);

		List<MovimientoEntity> movimientosEntities = movimientosRepository.findByCuentaIdAndDates(cuentaId, desde, hasta);

		return movimientosEntityMapper.listMovimientosEntityToListMovimientos(movimientosEntities);
	}

	@Override
	public List<Movimiento> getMovimientosUsuario(String uid, Date desde, Date hasta, ETipoMovimiento tipoMovimiento) {
		desde = desde != null ? desde : Date.from(Instant.MIN);
		hasta = hasta != null ? hasta : Date.from(Instant.MAX);

		List<MovimientoEntity> movimientosEntities = movimientosRepository.findByUidAndTipoMovimientoAndDates(uid, tipoMovimiento.name(), desde,
				hasta);

		return movimientosEntityMapper.listMovimientosEntityToListMovimientos(movimientosEntities);
	}

	@Override
	public List<Movimiento> getMovimientosCuenta(String cuentaId, Date desde, Date hasta, ETipoMovimiento tipoMovimiento) {
		desde = desde != null ? desde : Date.from(Instant.MIN);
		hasta = hasta != null ? hasta : Date.from(Instant.MAX);

		List<MovimientoEntity> movimientosEntities = movimientosRepository.findByCuentaIdAndTipoMovimientoAndDates(cuentaId, tipoMovimiento.name(),
				desde, hasta);

		return movimientosEntityMapper.listMovimientosEntityToListMovimientos(movimientosEntities);
	}

	@Override
	public Movimiento nuevoMovimiento(String uid, MovimientoBuilder builder) {
		if (builder.isMovimientoContable() == null) {
			TipoMedioPagoResponseDTO tipoMedioPagoDetalles = tipoCuentasFeign.tipoMedioPagoDetalles(builder.getMedioPagoId());
			builder.setMovimientoContable(tipoMedioPagoDetalles.isMovimientoContable());
		}

		MovimientoEntity entity = movimientosEntityMapper.movimientoBuilderToMovimientoEntity(builder);
		entity.setUid(uid);

		entity = updateCapitalAndSave(entity);

		List<MovimientoEntity> posterioresEntities = movimientosRepository.findMovimientosPosteriores(entity.getCuentaId(), entity.getFecha());
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

			List<MovimientoEntity> posterioresEntities = movimientosRepository.findMovimientosPosteriores(entity.getCuentaId(), entity.getFecha());
			posterioresEntities.forEach(e -> {
				updateCapitalAndSave(e);
			});
		}

		return movimientosEntityMapper.movimientoEntityToMovimiento(entity);
	}

	@Override
	public void borrarMovimiento(Movimiento movimiento) {
		movimientosRepository.deleteById(movimiento.getId());

		List<MovimientoEntity> posterioresEntities = movimientosRepository.findMovimientosPosteriores(movimiento.getCuentaId(),
				movimiento.getFecha());
		posterioresEntities.forEach(e -> {
			updateCapitalAndSave(e);
		});
	}

	private MovimientoEntity updateCapitalAndSave(MovimientoEntity entity) {
		Optional<MovimientoEntity> previoEntityOpt = movimientosRepository.findMovimientoPrevio(entity.getCuentaId(), entity.getFecha());
		double capitalPrevio = 0;
		if (previoEntityOpt.isPresent()) {
			MovimientoEntity previoEntity = previoEntityOpt.get();
			capitalPrevio = previoEntity.getCapitalPosterior();
		}
		entity.setCapitalPrevio(capitalPrevio);
		entity.setCapitalPosterior(capitalPrevio + entity.getCantidad());
		return movimientosRepository.save(entity);
	}

}
