package lv.venta.cbs.controller;

import lv.venta.cbs.model.User;
import lv.venta.cbs.model.Movie;
import lv.venta.cbs.model.MovieReview;
import lv.venta.cbs.model.Showtime;
import lv.venta.cbs.service.MovieService;
import lv.venta.cbs.service.UserService;
import lv.venta.cbs.service.ShowtimeService;
import lv.venta.cbs.service.MovieReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;
    private final ShowtimeService showtimeService;
    private final MovieReviewService movieReviewService;
    private final UserService userService;

    @Autowired
    public MovieController(MovieService movieService, ShowtimeService showtimeService, MovieReviewService movieReviewService, UserService userService) {
        this.movieService = movieService;
        this.showtimeService = showtimeService;
        this.movieReviewService = movieReviewService;
        this.userService = userService;
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
        List<MovieReview> movieReviews = movieReviewService.getMovieReviewsByMovie(movie);
        
        // profile (user class) | currentUser (for authentication)
        User profile = userService.getCurrentAuthenticatedUser();
        
        model.addAttribute("movie", movie);
        model.addAttribute("showtimes", showtimes);
        model.addAttribute("moviesReviews", movieReviews);
        model.addAttribute("profile", profile);
        return "movie/details";
    }
    
    // Review addition
    @PostMapping("/{id}/review")
    public String submitReview(@PathVariable("id") int movieId,
                               @RequestParam("comment") String comment,
                               @RequestParam("rating") int rating) {
                            	   
    	Movie movie = movieService.getMovieById(movieId).orElse(null);
    	User user = userService.getCurrentAuthenticatedUser();
        LocalDateTime now = LocalDateTime.now();

        MovieReview review = new MovieReview(rating, comment, now, movie, user);
        movieReviewService.createMovieReview(review);

        return "redirect:/movie/" + movieId; 
    }
    
} 