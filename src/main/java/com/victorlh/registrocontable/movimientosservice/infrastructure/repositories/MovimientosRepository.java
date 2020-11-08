package com.victorlh.registrocontable.movimientosservice.infrastructure.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.victorlh.registrocontable.movimientosservice.infrastructure.entities.MovimientoEntity;

public interface MovimientosRepository extends JpaRepository<MovimientoEntity, Long> {

	@Query("select m from MovimientoEntity m WHERE m.uid = :uid and fecha >= :fromDate and fecha <= :toDate ORDER BY fecha ASC")
	List<MovimientoEntity> findByUidAndDates(String uid, Date fromDate, Date toDate);
	
	@Query("select m from MovimientoEntity m where m.cuentaId = :cuentaId and fecha >= :fromDate and fecha <= :toDate ORDER BY fecha ASC")
	List<MovimientoEntity> findByCuentaIdAndDates(String cuentaId, Date fromDate, Date toDate);
	
	@Query("select m from MovimientoEntity m where m.uid = :uid and tipoMovimientoId = :tipoMovimientoId and fecha >= :fromDate and fecha <= :toDate ORDER BY fecha ASC")
	List<MovimientoEntity> findByUidAndTipoMovimientoAndDates(String uid, String tipoMovimientoId, Date fromDate, Date toDate);
	
	@Query("select m from MovimientoEntity m where m.cuentaId = :cuentaId and tipoMovimientoId = :tipoMovimientoId and fecha >= :fromDate and fecha <= :toDate ORDER BY fecha ASC")
	List<MovimientoEntity> findByCuentaIdAndTipoMovimientoAndDates(String cuentaId, String tipoMovimientoId, Date fromDate, Date toDate);

	@Query("select m from MovimientoEntity m WHERE m.cuentaId = :cuentaId and fecha < :fecha ORDER BY fecha DESC LIMIT 1")
	Optional<MovimientoEntity> findMovimientoPrevio(String cuentaId, Date fecha);
	
	@Query("select m from MovimientoEntity m WHERE m.cuentaId = :cuentaId and fecha > :fecha ORDER BY fecha ASC")
	List<MovimientoEntity> findMovimientosPosteriores(String cuentaId, Date fecha);
}
