package com.victorlh.registrocontable.movimientosservice.domain.service.impl;

import com.victorlh.registrocontable.movimientosservice.domain.conf.ETipoMovimiento;
import com.victorlh.registrocontable.movimientosservice.domain.exceptions.FechaRepetidaException;
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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovimientosServiceImpl implements MovimientosService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovimientosServiceImpl.class);

	private final MovimientosRepository movimientosRepository;
	private final MovimientosEntityMapper movimientosEntityMapper;
	private final CuentasFeign cuentasFeign;
	private final CategoriasFeign categoriasFeign;

	@Autowired
	public MovimientosServiceImpl(MovimientosRepository movimientosRepository, MovimientosEntityMapper movimientosEntityMapper, CuentasFeign cuentasFeign, CategoriasFeign categoriasFeign) {
		this.movimientosRepository = movimientosRepository;
		this.movimientosEntityMapper = movimientosEntityMapper;
		this.cuentasFeign = cuentasFeign;
		this.categoriasFeign = categoriasFeign;
	}

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
	public Movimiento nuevoMovimiento(String uid, MovimientoBuilder builder) {
		ETipoMovimiento tipoMovimiento = builder.getTipoMovimiento();
		Movimiento movimientoDestino = null;
		if (tipoMovimiento == ETipoMovimiento.TRASPASO) {
			MovimientoBuilder builderDestino = builder.clone();
			builderDestino.setCuentaDestinoId(null);

			builderDestino.setCuentaOrigenId(builder.getCuentaId());
			builderDestino.setMedioPagoOrigenId(builder.getMedioPagoId());

			builderDestino.setCuentaId(builder.getCuentaDestinoId());
			builderDestino.setMedioPagoId(null);
			movimientoDestino = nuevoMovimientoUnico(uid, builderDestino);

			builder.setCantidad(builder.getCantidad() * -1);
		}

		Movimiento movimiento = nuevoMovimientoUnico(uid, builder);

		if (movimientoDestino != null) {
			Optional<MovimientoEntity> destinoEntityOpt = movimientosRepository.findById(movimientoDestino.getId());
			MovimientoEntity destinoEntity = destinoEntityOpt.orElseThrow(IllegalAccessError::new);
			destinoEntity.setIdMovimientoTraspasoAsociado(movimiento.getId());
			movimientosRepository.save(destinoEntity);


			Optional<MovimientoEntity> movimientoEntityOpt = movimientosRepository.findById(movimiento.getId());
			MovimientoEntity movimientoEntity = movimientoEntityOpt.orElseThrow(IllegalAccessError::new);
			movimientoEntity.setIdMovimientoTraspasoAsociado(movimientoDestino.getId());
			movimientosRepository.save(movimientoEntity);

			movimiento.setIdMovimientoTraspasoAsociado(movimientoDestino.getId());
		}

		return movimiento;
	}

	private Movimiento nuevoMovimientoUnico(String uid, MovimientoBuilder builder) {
		if (builder.isMovimientoContable() == null) {
			String cuentaId = builder.getCuentaId();
			String medioPagoId = builder.getMedioPagoId();
			if (StringUtils.isNotBlank(cuentaId) && StringUtils.isNotBlank(medioPagoId)) {
				MedioPagoResponseDTO medioPagoDetalles = cuentasFeign.medioPagoDetalles(cuentaId, medioPagoId);
				TipoMedioPagoResponseDTO tipoMedioPago = medioPagoDetalles.getTipoMedioPago();
				builder.setMovimientoContable(tipoMedioPago.isMovimientoContable());
			} else {
				builder.setMovimientoContable(true);
			}
		}

		MovimientoEntity entity = movimientosEntityMapper.movimientoBuilderToMovimientoEntity(builder);
		entity.setUid(uid);

		entity = updateCapitalAndSave(entity);

		List<MovimientoEntity> posterioresEntities = movimientosRepository.findByCuentaIdAndFechaGreaterThanOrderByFechaAsc(entity.getCuentaId(),
				entity.getFecha());
		posterioresEntities.forEach(this::updateCapitalAndSave);

		return getFullDataMovimientos(entity);
	}

	@Override
	public Movimiento editarMovimiento(Movimiento movimiento, MovimientoBuilder builder) {
		LOGGER.trace("Cuenta igual: {}", StringUtils.equals(movimiento.getCuenta().getCuentaId(), builder.getCuentaId()));
		LOGGER.trace("Medio pago igual: {}", StringUtils.equals(movimiento.getCuenta().getMedioPago().getMedioPagoId(), builder.getMedioPagoId()));
		LOGGER.trace("Fecha igual: {}", movimiento.getFecha().getTime() == builder.getFecha().getTime());
		LOGGER.trace("Tipo igual: {}", movimiento.getTipoMovimiento() == builder.getTipoMovimiento());
		LOGGER.trace("Tipo traspaso: {}", movimiento.getTipoMovimiento() == ETipoMovimiento.TRASPASO);

		if (!StringUtils.equals(movimiento.getCuenta().getCuentaId(), builder.getCuentaId())
				|| !StringUtils.equals(movimiento.getCuenta().getMedioPago().getMedioPagoId(), builder.getMedioPagoId())
				|| movimiento.getFecha().getTime() != builder.getFecha().getTime() || movimiento.getTipoMovimiento() != builder.getTipoMovimiento()
				|| movimiento.getTipoMovimiento() == ETipoMovimiento.TRASPASO) {
			borrarMovimiento(movimiento);
			return nuevoMovimiento(movimiento.getUid(), builder);
		}

		Optional<MovimientoEntity> entityOpt = movimientosRepository.findById(movimiento.getId());
		MovimientoEntity entity = entityOpt.orElseThrow(IllegalAccessError::new);

		entity.setCategoriaId(builder.getCategoriaId());
		entity.setSubCategoriaId(builder.getSubCategoriaId());
		entity.setConcepto(builder.getConcepto());
		entity.setNota(builder.getNota());

		if (entity.getCantidad() != builder.getCantidad()) {
			entity.setCantidad(builder.getCantidad());

			entity = updateCapitalAndSave(entity);

			List<MovimientoEntity> posterioresEntities = movimientosRepository.findByCuentaIdAndFechaGreaterThanOrderByFechaAsc(entity.getCuentaId(),
					entity.getFecha());
			posterioresEntities.forEach(this::updateCapitalAndSave);
		} else {
			entity = movimientosRepository.save(entity);
		}

		return getFullDataMovimientos(entity);
	}

	@Override
	public void borrarMovimiento(Movimiento movimiento) {
		Long idMovimientoTraspasoAsociado = movimiento.getIdMovimientoTraspasoAsociado();
		if (idMovimientoTraspasoAsociado != null) {
			Optional<Movimiento> movimientoAsociado = getMovimiento(idMovimientoTraspasoAsociado);
			movimientoAsociado.ifPresent(this::borrarMovimientoUnico);
		}
		borrarMovimientoUnico(movimiento);
	}

	private void borrarMovimientoUnico(Movimiento movimiento) {
		movimientosRepository.deleteById(movimiento.getId());

		List<MovimientoEntity> posterioresEntities = movimientosRepository
				.findByCuentaIdAndFechaGreaterThanOrderByFechaAsc(movimiento.getCuenta().getCuentaId(), movimiento.getFecha());
		posterioresEntities.forEach(this::updateCapitalAndSave);
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
		return new GregorianCalendar(1900, Calendar.JANUARY, 1).getTime();
	}

	private Date maxDate() {
		return new GregorianCalendar(2900, Calendar.DECEMBER, 31).getTime();
	}

	private Movimiento getFullDataMovimientos(MovimientoEntity entity) {
		String cuentaId = entity.getCuentaId();
		String medioPagoId = entity.getMedioPagoId();
		Cuenta cuenta = getCuenta(cuentaId, medioPagoId);

		String categoriaId = entity.getCategoriaId();
		String subCategoriaId = entity.getSubCategoriaId();
		Categoria categoria = getCategoria(categoriaId, subCategoriaId);

		Movimiento movimiento = movimientosEntityMapper.movimientoEntityToMovimiento(entity);
		movimiento.setCuenta(cuenta);
		movimiento.setCategoria(categoria);

		String cuentaOrigenId = entity.getCuentaOrigenId();
		if (StringUtils.isNotBlank(cuentaOrigenId)) {
			String medioPagoOrigenId = entity.getMedioPagoOrigenId();
			Cuenta cuentaOrigen = getCuenta(cuentaOrigenId, medioPagoOrigenId);
			movimiento.setCuentaOrigen(cuentaOrigen);
		}

		String cuentaDestinoId = entity.getCuentaDestinoId();
		if (StringUtils.isNotBlank(cuentaDestinoId)) {
			String medioPagoDestinoId = entity.getMedioPagoDestinoId();
			Cuenta cuentaDestino = getCuenta(cuentaDestinoId, medioPagoDestinoId);
			movimiento.setCuentaDestino(cuentaDestino);
		}

		return movimiento;
	}

	private Cuenta getCuenta(String cuentaId, String medioPagoId) {
		CuentaResponseDTO detallesCuenta;
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
				.filter(mp -> StringUtils.equals(medioPagoId, mp.getId()))
				.findFirst();
		MedioPagoResponseDTO medioPago = medioPagoOpt.orElse(null);
		return movimientosEntityMapper.cuentaResponseDtoToCuenta(detallesCuenta, medioPago);
	}

	private Categoria getCategoria(String categoriaId, String subCategoriaId) {
		CategoriaResponse detallesCategoria;
		try {
			detallesCategoria = categoriasFeign.detalles(categoriaId);
		} catch (FeignException e) {
			int status = e.status();
			if (status != HttpStatus.NOT_FOUND.value()) {
				LOGGER.error(e.getLocalizedMessage(), e);
			}
			detallesCategoria = new CategoriaResponse();
			detallesCategoria.setId(categoriaId);
		}

		Optional<SubCategoriaResponse> subCategoriaOpt = detallesCategoria.getSubCategorias()
				.stream()
				.filter(sc -> StringUtils.equals(subCategoriaId, sc.getId()))
				.findFirst();
		SubCategoriaResponse subCategoria = subCategoriaOpt.orElse(null);

		return movimientosEntityMapper.categoriaResponseDtoToCategoria(detallesCategoria, subCategoria);
	}

}
