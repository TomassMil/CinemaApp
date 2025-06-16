package lv.venta.cbs.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lv.venta.cbs.model.Movie;
import lv.venta.cbs.repository.MovieRepository;
import lv.venta.cbs.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Optional<Movie> getMovieById(Integer id) {
        return movieRepository.findById(id);
    }

    @Override
    @Transactional
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    @Transactional
    public Movie updateMovie(Movie movie) {
        if (!movieRepository.existsById(movie.getMovieId())) {
            throw new IllegalArgumentException("Movie not found with id: " + movie.getMovieId());
        }
        return movieRepository.save(movie);
    }

    @Override
    @Transactional
    public void deleteMovie(Integer id) {
    	Movie movie = movieRepository.findById(id)
		    .orElseThrow(() -> new IllegalArgumentException("Movie not found with id: " + id));
		movieRepository.delete(movie); // lets cascading work properly
    }

    @Override
    @Transactional
    public void toggleMovieStatus(int id) {
        Movie movie = getMovieById(id)
            .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
        movie.toggleStatus();
        movieRepository.save(movie);
    }

    @Override
    public List<Movie> getMoviesByStatus(Movie.MovieStatus status) {
        return movieRepository.findByStatus(status);
    }
}