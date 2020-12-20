package com.victorlh.registrocontable.movimientosservice.mappers;

import com.victorlh.registrocontable.movimientosservice.api.dto.request.MovimientoRequestDTO;
import com.victorlh.registrocontable.movimientosservice.api.dto.response.MovimientoResponseDTO;
import com.victorlh.registrocontable.movimientosservice.domain.conf.ETipoMovimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.Movimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.MovimientoBuilder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovimientosApiMapper {

	@Mapping(target = "tipoMovimientoId", source = "movimiento.tipoMovimiento")
	MovimientoResponseDTO movimientoToMovimientoResponse(Movimiento movimiento);

	List<MovimientoResponseDTO> listaMovimientosToListaMovimientosResponse(List<Movimiento> movimientos);

	@Mapping(target = "movimientoContable", ignore = true)
	@Mapping(target = "movimietosAsociados", ignore = true)
	@Mapping(target = "tipoMovimiento", source = "request.tipoMovimientoId", qualifiedByName = "idToTipoMovimiento")
	MovimientoBuilder movimientoRequestToMovimientoBuilder(MovimientoRequestDTO request);

	@Named("idToTipoMovimiento")
	default ETipoMovimiento idToTipoMovimiento(String tipoMovimientoId) {
		return ETipoMovimiento.findById(tipoMovimientoId).orElse(null);
	}
}
