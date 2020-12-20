package com.victorlh.registrocontable.movimientosservice.infrastructure.feign.api;

import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.CuentaResponseDTO;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.MedioPagoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "${feign.cuentas-service.name}", path = "${feign.cuentas-service.parent-path}")
public interface CuentasFeign {

	@GetMapping("/")
	List<CuentaResponseDTO> lista();

	@GetMapping("/{id}")
	CuentaResponseDTO detalles(@PathVariable String id);

	@GetMapping("/{cuentaId}/mediosPago")
	List<MedioPagoResponseDTO> listaMediosPago(@PathVariable String cuentaId);

	@GetMapping("/{cuentaId}/mediosPago/{medioPagoId}")
	MedioPagoResponseDTO medioPagoDetalles(@PathVariable String cuentaId, @PathVariable String medioPagoId);

}
