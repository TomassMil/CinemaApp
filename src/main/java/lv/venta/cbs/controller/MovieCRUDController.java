package lv.venta.cbs.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lv.venta.cbs.model.Movie;
import lv.venta.cbs.service.MovieService;

@Controller
@RequestMapping("/crud/movie")
public class MovieCRUDController {
    private static final Logger logger = LoggerFactory.getLogger(MovieCRUDController.class);
    private final MovieService movieService;
    private final HttpServletRequest request;

    @Autowired
    public MovieCRUDController(MovieService movieService, HttpServletRequest request) {
        this.movieService = movieService;
        this.request = request;
    }

    @GetMapping
    public String listMovies(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "crud/movie/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Movie movie = new Movie();
        movie.setMovieId(null);
        model.addAttribute("movie", movie);
        return "crud/movie/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<Movie> movieOpt = movieService.getMovieById(id);
        if (movieOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid movie ID: " + id);
        }
        model.addAttribute("movie", movieOpt.get());
        return "crud/movie/form";
    }

    @PostMapping
    public String createMovie(@ModelAttribute Movie movie, RedirectAttributes redirectAttributes) {
        logger.info("Received movie creation request: {}", movie);
        try {
            // Log all form parameters
            request.getParameterMap().forEach((key, value) -> {
                logger.info("Form parameter - {}: {}", key, String.join(", ", value));
            });

            // Convert the release date string to LocalDate
            String dateStr = request.getParameter("releaseDate");
            logger.info("Release date from form: {}", dateStr);
            
            if (dateStr != null && !dateStr.isEmpty()) {
                LocalDate releaseDate = LocalDate.parse(dateStr);
                logger.info("Parsed release date: {}", releaseDate);
                movie.setReleaseDate(releaseDate);
            }
            
            logger.info("About to create movie: {}", movie);
            Movie savedMovie = movieService.createMovie(movie);
            logger.info("Movie created successfully with ID: {}", savedMovie.getMovieId());
            
            redirectAttributes.addFlashAttribute("successMessage", "Movie created successfully!");
        } catch (Exception e) {
            logger.error("Error creating movie", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating movie: " + e.getMessage());
        }
        return "redirect:/crud/movie";
    }

    @PostMapping("/update")
    public String updateMovie(@ModelAttribute Movie movie, RedirectAttributes redirectAttributes) {
        logger.info("Received movie update request: {}", movie);
        try {
            // Convert the release date string to LocalDate
            String dateStr = request.getParameter("releaseDate");
            logger.info("Release date from form: {}", dateStr);
            
            if (dateStr != null && !dateStr.isEmpty()) {
                LocalDate releaseDate = LocalDate.parse(dateStr);
                logger.info("Parsed release date: {}", releaseDate);
                movie.setReleaseDate(releaseDate);
            }
            
            logger.info("About to update movie: {}", movie);
            Movie updatedMovie = movieService.updateMovie(movie);
            logger.info("Movie updated successfully with ID: {}", updatedMovie.getMovieId());
            
            redirectAttributes.addFlashAttribute("successMessage", "Movie updated successfully!");
        } catch (Exception e) {
            logger.error("Error updating movie", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating movie: " + e.getMessage());
        }
        return "redirect:/crud/movie";
    }

    @PostMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        logger.info("Received movie deletion request for ID: {}", id);
        try {
            movieService.deleteMovie(id);
            logger.info("Movie deleted successfully with ID: {}", id);
            redirectAttributes.addFlashAttribute("successMessage", "Movie deleted successfully!");
        } catch (Exception e) {
            logger.error("Error deleting movie", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting movie: " + e.getMessage());
        }
        return "redirect:/crud/movie";
    }
} 