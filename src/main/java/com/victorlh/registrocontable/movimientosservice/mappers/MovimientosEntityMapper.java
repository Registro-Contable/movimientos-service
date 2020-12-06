package com.victorlh.registrocontable.movimientosservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.victorlh.registrocontable.movimientosservice.domain.conf.ETipoMovimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.Categoria;
import com.victorlh.registrocontable.movimientosservice.domain.model.Cuenta;
import com.victorlh.registrocontable.movimientosservice.domain.model.MedioPago;
import com.victorlh.registrocontable.movimientosservice.domain.model.Movimiento;
import com.victorlh.registrocontable.movimientosservice.domain.model.MovimientoBuilder;
import com.victorlh.registrocontable.movimientosservice.domain.model.SubCategoria;
import com.victorlh.registrocontable.movimientosservice.infrastructure.entities.MovimientoEntity;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.CategoriaResponse;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.CuentaResponseDTO;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.MedioPagoResponseDTO;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.SubCategoriaResponse;

@Mapper(componentModel = "spring")
public interface MovimientosEntityMapper {

	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "uid", source = "entity.uid")
	@Mapping(target = "capitalAnterior", source = "entity.capitalPrevio")
	@Mapping(target = "tipoMovimiento", source = "entity.tipoMovimientoId", qualifiedByName = "idToTipoMovimiento")
	@Mapping(target = "movimietosAsociados", ignore = true)
	@Mapping(target = "cuenta", source = "cuenta")
	@Mapping(target = "categoria", source = "categoria")
	Movimiento movimientoEntityToMovimiento(MovimientoEntity entity, Cuenta cuenta, Categoria categoria);

	@Mapping(target = "cuentaId", source = "dto.id")
	@Mapping(target = "nombre", source = "dto.nombre")
	@Mapping(target = "medioPago", source = "medioPagoDto")
	Cuenta cuentaResponseDtoToCuenta(CuentaResponseDTO dto, MedioPagoResponseDTO medioPagoDto);

	@Mapping(target = "medioPagoId", source = "dto.id")
	MedioPago medioPagoResponseDtoToMedioPago(MedioPagoResponseDTO dto);

	@Mapping(target = "categoriaId", source = "dto.id")
	@Mapping(target = "nombre", source = "dto.nombre")
	@Mapping(target = "subCategoria", source = "subCategoriaDto")
	Categoria categoriaResponseDtoToCategoria(CategoriaResponse dto, SubCategoriaResponse subCategoriaDto);

	@Mapping(target = "subCategoriaId", source = "dto.id")
	SubCategoria subCategoriaResponseDtoToSubCategoria(SubCategoriaResponse dto);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "uid", ignore = true)
	@Mapping(target = "capitalPosterior", ignore = true)
	@Mapping(target = "capitalPrevio", ignore = true)
	@Mapping(target = "tipoMovimientoId", source = "builder.tipoMovimiento")
	MovimientoEntity movimientoBuilderToMovimientoEntity(MovimientoBuilder builder);

	@Named("idToTipoMovimiento")
	default ETipoMovimiento idToTipoMovimiento(String tipoMovimientoId) {
		return ETipoMovimiento.findById(tipoMovimientoId).orElse(null);
	}

}
