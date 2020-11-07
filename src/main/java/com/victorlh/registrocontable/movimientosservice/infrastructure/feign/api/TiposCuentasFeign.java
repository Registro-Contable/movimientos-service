package com.victorlh.registrocontable.movimientosservice.infrastructure.feign.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.TipoCuentaResponseDTO;
import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.TipoMedioPagoResponseDTO;

@FeignClient(name = "${feign.tiposCuentasService.name}")
@RequestMapping("${feign.tiposCuentasService.parentPath}")
public interface TiposCuentasFeign {

	@GetMapping("/tipocuenta")
	public List<TipoCuentaResponseDTO> listaTiposCuentas();

	@GetMapping({ "/tipocuenta/{id}" })
	public TipoCuentaResponseDTO tipoCuentaDetalles(@PathVariable String id);

	@GetMapping("/tipomediopago")
	public List<TipoMedioPagoResponseDTO> listaTiposMediosPago();

	@GetMapping({ "/tipomediopago/{id}" })
	public TipoMedioPagoResponseDTO tipoMedioPagoDetalles(@PathVariable String id);

}
