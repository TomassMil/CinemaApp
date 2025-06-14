package lv.venta.cbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lv.venta.cbs.model.User;
import lv.venta.cbs.service.BookingService;
import lv.venta.cbs.service.MovieService;
import lv.venta.cbs.service.ShowtimeService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/booking")
public class BookingController {
	
	private final MovieService movieService;
	private final ShowtimeService showtimeService;
	private final BookingService bookingService;
	private final ObjectMapper objectMapper;

    @Autowired
    public BookingController(MovieService movieService, ShowtimeService showtimeService, BookingService bookingService) {
        this.movieService = movieService;
        this.showtimeService = showtimeService;
        this.bookingService = bookingService;
        this.objectMapper = new ObjectMapper();
    }

    @PostMapping("/create")
    @ResponseBody
    public String createBooking(
            @RequestParam("showtimeId") int showtimeId,
            @RequestParam("selectedSeats") String selectedSeatsJson,
            @AuthenticationPrincipal User user,
            RedirectAttributes redirectAttributes) {
        try {
            List<Map<String, Integer>> selectedSeats = objectMapper.readValue(
                selectedSeatsJson, 
                new TypeReference<List<Map<String, Integer>>>() {}
            );
            bookingService.createBooking(showtimeId, selectedSeats, user);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error: " + e.getMessage();
        }
    }

    @GetMapping("/check-seats/{showtimeId}")
    @ResponseBody
    public List<Map<String, Integer>> getOccupiedSeats(@PathVariable int showtimeId) {
        return bookingService.getOccupiedSeats(showtimeId);
    }
}
