package lv.venta.cbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lv.venta.cbs.model.Showtime;
import lv.venta.cbs.service.MovieService;
import lv.venta.cbs.service.ShowtimeService;
import lv.venta.cbs.service.TheaterRoomService;

@Controller
@RequestMapping("/crud/showtime")
public class ShowtimeCRUDController {

    private final ShowtimeService showtimeService;
    private final MovieService movieService;
    private final TheaterRoomService roomService;

    @Autowired
    public ShowtimeCRUDController(ShowtimeService showtimeService, MovieService movieService, TheaterRoomService roomService) {
        this.showtimeService = showtimeService;
        this.movieService = movieService;
        this.roomService = roomService;
    }

    @GetMapping
    public String listShowtimes(Model model) {
        List<Showtime> showtimes = showtimeService.getAllShowtimes();
        model.addAttribute("showtimes", showtimes);
        return "crud/showtime/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Showtime showtime = new Showtime();
        showtime.setShowtimeId(null);
        model.addAttribute("showtime", showtime);
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("rooms", roomService.getAllRooms());
        return "crud/showtime/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Showtime showtime = showtimeService.getShowtimeById(id);
        model.addAttribute("showtime", showtime);
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("rooms", roomService.getAllRooms());
        return "crud/showtime/form";
    }

    @PostMapping("/update")
    public String updateShowtime(@ModelAttribute("showtime") Showtime showtime, RedirectAttributes redirectAttributes) {
        try {
            showtimeService.updateShowtime(showtime.getShowtimeId(), showtime);
            redirectAttributes.addFlashAttribute("successMessage", "Showtime updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating showtime: " + e.getMessage());
        }
        return "redirect:/crud/showtime";
    }

    @PostMapping
    public String createShowtime(@ModelAttribute("showtime") Showtime showtime, RedirectAttributes redirectAttributes) {
        try {
            showtimeService.createShowtime(showtime);
            redirectAttributes.addFlashAttribute("successMessage", "Showtime created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating showtime: " + e.getMessage());
        }
        return "redirect:/crud/showtime";
    }

    @PostMapping("/delete/{id}")
    public String deleteShowtime(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            showtimeService.deleteShowtime(id);
            redirectAttributes.addFlashAttribute("successMessage", "Showtime deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting showtime: " + e.getMessage());
        }
        return "redirect:/crud/showtime";
    }
} 