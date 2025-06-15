package lv.venta.cbs.controller;

import lv.venta.cbs.model.User;
import lv.venta.cbs.model.Movie;
import lv.venta.cbs.model.Showtime;
import lv.venta.cbs.service.MovieService;
import lv.venta.cbs.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;
    private final ShowtimeService showtimeService;

    @Autowired
    public MovieController(MovieService movieService, ShowtimeService showtimeService) {
        this.movieService = movieService;
        this.showtimeService = showtimeService;
    }

    @GetMapping("/{id}")
    public String getMovieDetails(@PathVariable("id") int id, Model model) {
        Movie movie = movieService.getMovieById(id).orElse(null);
        if (movie == null) {
            return "redirect:/";
        }
        // Add current user to model if authenticated
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            User currentUser = (User) auth.getPrincipal();
            model.addAttribute("currentUser", currentUser);
        }
        
        List<Showtime> showtimes = showtimeService.getShowtimesByMovie(movie);
        model.addAttribute("movie", movie);
        model.addAttribute("showtimes", showtimes);
        return "movie/details";
    }
} 