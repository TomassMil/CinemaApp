package lv.venta.cbs.service;

import java.util.List;
import java.util.Map;

import lv.venta.cbs.model.User;

public interface BookingService {
    void createBooking(int showtimeId, List<Map<String, Integer>> selectedSeats, User user);
    List<Map<String, Integer>> getOccupiedSeats(int showtimeId);
} 