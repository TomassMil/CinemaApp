package lv.venta.cbs.service;

import lv.venta.cbs.model.User;

public interface TicketService {
	boolean cancelTicket(int ticketId, User currentUser);
}
