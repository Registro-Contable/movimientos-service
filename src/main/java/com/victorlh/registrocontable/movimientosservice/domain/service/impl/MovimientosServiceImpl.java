package com.victorlh.registrocontable.movimientosservice.domain.service.impl;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.victorlh.registrocontable.movimientosservice.domain.conf.ETipoMovimiento;
import com.victorlh.registrocontable.movimientosservice.domain.exceptions.FechaRepetidaException;
import com.victorlh.registrocontable.movimientosservice.domain.model.CapitalCuenta;
import com.victorlh.registrocontable.movimientosservice.domain.model.Categoria;
import com.victorlh.registrocontable.movimientosservice.domain.model.Cuenta;
import com.victorlh.registrocontable.movimientosservice.domain.model.Movimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.MovimientoBuilder;
import com.victorlh.registrocontable.movimientosservice.domain.service.MovimientosService;
import com.victorlh.registrocontable.movimientosservice.infrastructure.entities.MovimientoEntity;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.api.CategoriasFeign;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.api.CuentasFeign;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.CategoriaResponse;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.CuentaResponseDTO;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.MedioPagoResponseDTO;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.SubCategoriaResponse;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.TipoMedioPagoResponseDTO;
import com.victorlh.registrocontable.movimientosservice.infrastructure.repositories.MovimientosRepository;
import com.victorlh.registrocontable.movimientosservice.mappers.MovimientosEntityMapper;

import feign.FeignException;

@Service
public class MovimientosServiceImpl implements MovimientosService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovimientosServiceImpl.class);

	@Autowired
	private MovimientosRepository movimientosRepository;
	@Autowired
	private MovimientosEntityMapper movimientosEntityMapper;
	@Autowired
	private CuentasFeign cuentasFeign;
	@Autowired
	private CategoriasFeign categoriasFeign;

	@Override
	public List<Movimiento> getMovimientosUsuario(String uid, Date fromDate, Date toDate) {
		fromDate = fromDate != null ? fromDate : minDate();
		toDate = toDate != null ? toDate : maxDate();

		List<MovimientoEntity> movimientosEntities = movimientosRepository.findByUidAndFechaBetweenOrderByFechaDesc(uid, fromDate, toDate);
		return movimientosEntities.stream().map(this::getFullDataMovimientos).collect(Collectors.toList());
	}

	@Override
	public List<Movimiento> getMovimientosCuenta(String cuentaId, Date fromDate, Date toDate) {
		fromDate = fromDate != null ? fromDate : minDate();
		toDate = toDate != null ? toDate : maxDate();

		List<MovimientoEntity> movimientosEntities = movimientosRepository.findByCuentaIdAndFechaBetweenOrderByFechaDesc(cuentaId, fromDate, toDate);
		return movimientosEntities.stream().map(this::getFullDataMovimientos).collect(Collectors.toList());
	}

	@Override
	public List<Movimiento> getMovimientosUsuario(String uid, Date fromDate, Date toDate, ETipoMovimiento tipoMovimiento) {
		fromDate = fromDate != null ? fromDate : minDate();
		toDate = toDate != null ? toDate : maxDate();

		List<MovimientoEntity> movimientosEntities = movimientosRepository.findByUidAndTipoMovimientoIdAndFechaBetweenOrderByFechaDesc(uid,
				tipoMovimiento.name(), fromDate, toDate);
		return movimientosEntities.stream().map(this::getFullDataMovimientos).collect(Collectors.toList());
	}

	@Override
	public List<Movimiento> getMovimientosCuenta(String cuentaId, Date fromDate, Date toDate, ETipoMovimiento tipoMovimiento) {
		fromDate = fromDate != null ? fromDate : minDate();
		toDate = toDate != null ? toDate : maxDate();

		List<MovimientoEntity> movimientosEntities = movimientosRepository.findByCuentaIdAndTipoMovimientoIdAndFechaBetweenOrderByFechaDesc(cuentaId,
				tipoMovimiento.name(), fromDate, toDate);
		return movimientosEntities.stream().map(this::getFullDataMovimientos).collect(Collectors.toList());
	}

	@Override
	public Optional<Movimiento> getMovimiento(Long movimientoId) {
		Optional<MovimientoEntity> movimiento = movimientosRepository.findById(movimientoId);
		return movimiento.map(this::getFullDataMovimientos);
	}

	@Override
	public CapitalCuenta getCapitalCuenta(String cuentaId) {
		CuentaResponseDTO cuentaResponseDTO = cuentasFeign.detalles(cuentaId);
		Cuenta cuenta = movimientosEntityMapper.cuentaResponseDtoToCuenta(cuentaResponseDTO, null);

		List<Movimiento> movimientos = this.getMovimientosCuenta(cuentaId, null, null);
		Optional<Movimiento> findFirst = movimientos.stream().findFirst();

		CapitalCuenta capitalCuenta = new CapitalCuenta();
		capitalCuenta.setCuenta(cuenta);
		findFirst.ifPresent(m -> capitalCuenta.setCapital(m.getCapitalPosterior()));
		return capitalCuenta;
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

		return getFullDataMovimientos(entity);
	}

	@Override
	public Movimiento editarMovimiento(Movimiento movimiento, MovimientoBuilder builder) {
		LOGGER.trace("Cuenta igual: {}", StringUtils.equals(movimiento.getCuenta().getCuentaId(), builder.getCuentaId()));
		LOGGER.trace("Medio pago igual: {}", StringUtils.equals(movimiento.getCuenta().getMedioPago().getMedioPagoId(), builder.getMedioPagoId()));
		LOGGER.trace("fecha igual: {}", movimiento.getFecha().getTime() == builder.getFecha().getTime());

		if (!StringUtils.equals(movimiento.getCuenta().getCuentaId(), builder.getCuentaId())
				|| !StringUtils.equals(movimiento.getCuenta().getMedioPago().getMedioPagoId(), builder.getMedioPagoId())
				|| movimiento.getFecha().getTime() != builder.getFecha().getTime()) {
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

		return getFullDataMovimientos(entity);
	}

	@Override
	public void borrarMovimiento(Movimiento movimiento) {
		movimientosRepository.deleteById(movimiento.getId());

		List<MovimientoEntity> posterioresEntities = movimientosRepository
				.findByCuentaIdAndFechaGreaterThanOrderByFechaAsc(movimiento.getCuenta().getCuentaId(), movimiento.getFecha());
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
		} catch (Exception e) {
			throw new FechaRepetidaException(e);
		}
	}

	private Date minDate() {
		return new GregorianCalendar(1900, 0, 1).getTime();
	}

	private Date maxDate() {
		return new GregorianCalendar(2900, 11, 31).getTime();
	}

	private Movimiento getFullDataMovimientos(MovimientoEntity entity) {
		String cuentaId = entity.getCuentaId();
		CuentaResponseDTO detallesCuenta = null;
		try {
			detallesCuenta = cuentasFeign.detalles(cuentaId);
		} catch (FeignException e) {
			int status = e.status();
			if (status != HttpStatus.NOT_FOUND.value()) {
				LOGGER.error(e.getLocalizedMessage(), e);
			}
			detallesCuenta = new CuentaResponseDTO();
			detallesCuenta.setId(cuentaId);
		}

		Optional<MedioPagoResponseDTO> medioPagoOpt = detallesCuenta.getMediosPago()
				.stream()
				.filter(mp -> StringUtils.equals(entity.getMedioPagoId(), mp.getId()))
				.findFirst();
		MedioPagoResponseDTO medioPago = medioPagoOpt.orElse(null);
		Cuenta cuenta = movimientosEntityMapper.cuentaResponseDtoToCuenta(detallesCuenta, medioPago);

		String categoriaId = entity.getCategoriaId();
		CategoriaResponse detallesCategoria = null;

		try {
			detallesCategoria = categoriasFeign.detalles(categoriaId);
		} catch (FeignException e) {
			int status = e.status();
			if (status != HttpStatus.NOT_FOUND.value()) {
				LOGGER.error(e.getLocalizedMessage(), e);
			}
			detallesCategoria = new CategoriaResponse();
			detallesCategoria.setId(cuentaId);
		}
		
		Optional<SubCategoriaResponse> subCategoriaOpt = detallesCategoria.getSubCategorias()
				.stream()
				.filter(sc -> StringUtils.equals(entity.getSubCategoriaId(), sc.getId()))
				.findFirst();
		SubCategoriaResponse subCategoria = subCategoriaOpt.orElse(null);

		Categoria categoria = movimientosEntityMapper.categoriaResponseDtoToCategoria(detallesCategoria, subCategoria);

		return movimientosEntityMapper.movimientoEntityToMovimiento(entity, cuenta, categoria);
	}

}
