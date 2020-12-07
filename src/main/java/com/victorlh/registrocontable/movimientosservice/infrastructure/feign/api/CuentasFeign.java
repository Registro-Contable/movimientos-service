package com.victorlh.registrocontable.movimientosservice.infrastructure.feign.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.CuentaResponseDTO;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.MedioPagoResponseDTO;

@FeignClient(name = "${feign.cuentas-service.name}", path = "${feign.cuentas-service.parent-path}")
public interface CuentasFeign {

	@GetMapping("/")
	public List<CuentaResponseDTO> lista();

	@GetMapping("/{id}")
	public CuentaResponseDTO detalles(@PathVariable String id);

	@GetMapping("/{cuentaId}/mediosPago")
	public List<MedioPagoResponseDTO> listaMediosPago(@PathVariable String cuentaId);

	@GetMapping("/{cuentaId}/mediosPago/{medioPagoId}")
	public MedioPagoResponseDTO medioPagoDetalles(@PathVariable String cuentaId, @PathVariable String medioPagoId);

}
