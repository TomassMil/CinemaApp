package lv.venta.cbs.controller;

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
        
        List<Showtime> showtimes = showtimeService.getShowtimesByMovie(movie);
        model.addAttribute("movie", movie);
        model.addAttribute("showtimes", showtimes);
        return "movie/details";
    }
} 