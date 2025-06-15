package lv.venta.cbs.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.cbs.model.User;
import lv.venta.cbs.service.UserService;
import lv.venta.cbs.service.TicketService;

@Controller
@RequestMapping("/ticket")
public class TicketController {
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private TicketService ticketService;
	
	@PostMapping("/cancel/{ticketId}")
	public ResponseEntity<String> cancelTicket(@PathVariable int ticketId, Principal principal) {
	    User user = userService.getCurrentUser(principal.getName());
	    boolean success = ticketService.cancelTicket(ticketId, user);

	    if (success) {
	        return ResponseEntity.ok("Ticket cancelled");
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to cancel");
	    }
	}
}
