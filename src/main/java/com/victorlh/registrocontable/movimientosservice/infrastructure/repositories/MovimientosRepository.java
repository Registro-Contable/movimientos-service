package com.victorlh.registrocontable.movimientosservice.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victorlh.registrocontable.movimientosservice.infrastructure.entities.MovimientoEntity;

public interface MovimientosRepository extends JpaRepository<MovimientoEntity, Long> {

}
