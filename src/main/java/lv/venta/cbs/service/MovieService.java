package lv.venta.cbs.service;

import java.util.List;
import java.util.Optional;

import lv.venta.cbs.model.Movie;

public interface MovieService {
    List<Movie> getAllMovies();
    Optional<Movie> getMovieById(Integer id);
    Movie createMovie(Movie movie);
    Movie updateMovie(Movie movie);
    void deleteMovie(Integer id);
    void toggleMovieStatus(int id);
    List<Movie> getMoviesByStatus(Movie.MovieStatus status);
} 