package lv.venta.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lv.venta.cbs.model.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
} 