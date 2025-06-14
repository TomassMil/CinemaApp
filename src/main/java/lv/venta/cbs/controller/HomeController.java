package lv.venta.cbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lv.venta.cbs.model.Movie;
import lv.venta.cbs.model.User;
import lv.venta.cbs.service.MovieService;

@Controller
public class HomeController {

    private final MovieService movieService;

    @Autowired
    public HomeController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Get upcoming movies (status = UPCOMING)
        List<Movie> upcomingMovies = movieService.getMoviesByStatus(Movie.MovieStatus.UPCOMING);
        model.addAttribute("upcomingMovies", upcomingMovies);

        // Get current movies (status = BOOKABLE)
        List<Movie> bookableMovies = movieService.getMoviesByStatus(Movie.MovieStatus.BOOKABLE);
        model.addAttribute("bookableMovies", bookableMovies);

        // Add current user to model if authenticated
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            User currentUser = (User) auth.getPrincipal();
            model.addAttribute("currentUser", currentUser);
        }

        return "home";
    }
} 