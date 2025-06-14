package lv.venta.cbs.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lv.venta.cbs.model.Seat;
import lv.venta.cbs.model.Showtime;
import lv.venta.cbs.model.Ticket;
import lv.venta.cbs.model.User;
import lv.venta.cbs.repository.SeatRepository;
import lv.venta.cbs.repository.ShowtimeRepository;
import lv.venta.cbs.repository.TicketRepository;
import lv.venta.cbs.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

    private final ShowtimeRepository showtimeRepository;
    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;

    @Autowired
    public BookingServiceImpl(ShowtimeRepository showtimeRepository, 
                            TicketRepository ticketRepository,
                            SeatRepository seatRepository) {
        this.showtimeRepository = showtimeRepository;
        this.ticketRepository = ticketRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    @Transactional
    public void createBooking(int showtimeId, List<Map<String, Integer>> selectedSeats, User user) {
        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        for (Map<String, Integer> seatInfo : selectedSeats) {
            int row = seatInfo.get("row");
            int column = seatInfo.get("column");

            // Create and save seat first
            Seat seat = new Seat();
            seat.setSeatRow(row);
            seat.setSeatColumn(column);
            seat.setSeatNumber((row - 1) * showtime.getRoom().getSeatcolumns() + column); // Calculate seat number
            seat.setSeatType("STANDARD"); // Default to standard seats
            seat.setShowtime(showtime);
            seat = seatRepository.save(seat);

            // Create ticket with the saved seat
            Ticket ticket = new Ticket(
                LocalDateTime.now(),
                0, //showtime.getPrice(), pagaidam 0
                "BOOKED",
                showtime,
                user,
                seat
            );

            ticketRepository.save(ticket);
        }
    }

    @Override
    public List<Map<String, Integer>> getOccupiedSeats(int showtimeId) {
        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        List<Map<String, Integer>> occupiedSeats = new ArrayList<>();
        
        for (Ticket ticket : showtime.getTickets()) {
            if (ticket.getStatus().equals("BOOKED")) {
                Map<String, Integer> seat = new HashMap<>();
                seat.put("row", ticket.getSeat().getSeatRow());
                seat.put("column", ticket.getSeat().getSeatColumn());
                occupiedSeats.add(seat);
            }
        }

        return occupiedSeats;
    }
} 