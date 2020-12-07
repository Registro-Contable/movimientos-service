package com.victorlh.registrocontable.movimientosservice.infrastructure.feign.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.victorlh.registrocontable.movimientosservice.infrastructure.feign.dto.response.CategoriaResponse;

@FeignClient(name = "${feign.categorias-service.name}", path = "${feign.categorias-service.parent-path}")
public interface CategoriasFeign {

	@GetMapping("/")
	public List<CategoriaResponse> lista(@RequestParam(name = "filter", required = false) String tipoCategoria);

	@GetMapping("{id}")
	public CategoriaResponse detalles(@PathVariable String id);

}
