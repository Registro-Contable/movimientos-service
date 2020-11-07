package com.victorlh.registrocontable.movimientosservice.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.victorlh.registrocontable.movimientosservice.domain.conf.ETipoMovimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.Movimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.MovimientoBuilder;
import com.victorlh.registrocontable.movimientosservice.infrastructure.entities.MovimientoEntity;

@Mapper(componentModel = "spring")
public interface MovimientosEntityMapper {

	@Mapping(target = "capitalAnterior", source = "entity.capitalPrevio")
	@Mapping(target = "tipoMovimiento", source = "entity.tipoMovimientoId", qualifiedByName = "idToTipoMovimiento")
	@Mapping(target = "movimietosAsociados", ignore = true)
	Movimiento movimientoEntityToMovimiento(MovimientoEntity entity);

	List<Movimiento> listMovimientosEntityToListMovimientos(List<MovimientoEntity> list);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "capitalPosterior", ignore = true)
	@Mapping(target = "capitalPrevio", ignore = true)
	@Mapping(target = "tipoMovimientoId", source = "builder.tipoMovimiento")
	MovimientoEntity movimientoBuilderToMovimientoEntity(MovimientoBuilder builder);

	@Named("idToTipoMovimiento")
	default ETipoMovimiento idToTipoMovimiento(String tipoMovimientoId) {
		return ETipoMovimiento.findById(tipoMovimientoId).orElse(null);
	}
}
