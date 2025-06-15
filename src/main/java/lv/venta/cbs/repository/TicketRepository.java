package lv.venta.cbs.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lv.venta.cbs.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
} 