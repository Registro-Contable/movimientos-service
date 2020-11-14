package com.victorlh.registrocontable.movimientosservice.infrastructure.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.victorlh.registrocontable.movimientosservice.infrastructure.entities.MovimientoEntity;

@Repository
public interface MovimientosRepository extends JpaRepository<MovimientoEntity, Long> {

	List<MovimientoEntity> findByUidAndFechaBetweenOrderByFechaDesc(String uid, Date fromDate, Date toDate);
	
	List<MovimientoEntity> findByCuentaIdAndFechaBetweenOrderByFechaDesc(String cuentaId, Date fromDate, Date toDate);
	
	List<MovimientoEntity> findByUidAndTipoMovimientoIdAndFechaBetweenOrderByFechaDesc(String uid, String tipoMovimientoId, Date fromDate, Date toDate);
	
	List<MovimientoEntity> findByCuentaIdAndTipoMovimientoIdAndFechaBetweenOrderByFechaDesc(String cuentaId, String tipoMovimientoId, Date fromDate, Date toDate);

	Optional<MovimientoEntity> findFirstByCuentaIdAndFechaLessThanOrderByFechaDesc(String cuentaId, Date fecha);
	
	List<MovimientoEntity> findByCuentaIdAndFechaGreaterThanOrderByFechaAsc(String cuentaId, Date fecha);
}
