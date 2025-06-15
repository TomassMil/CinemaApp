package lv.venta.cbs.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.cbs.model.Ticket;
import lv.venta.cbs.model.User;
import lv.venta.cbs.repository.TicketRepository;
import lv.venta.cbs.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;
	 
	@Override
	public boolean cancelTicket(int ticketId, User currentUser) {
		Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
	    if (ticketOpt.isPresent()) {
	        Ticket ticket = ticketOpt.get();
	        if (ticket.getUser().getUserId() == currentUser.getUserId() && "BOOKED".equalsIgnoreCase(ticket.getStatus())) {
	    ticket.setStatus("CANCELLED");
	            ticketRepository.save(ticket);
	            return true;
	        }
	    }
	    return false;
	}

}
