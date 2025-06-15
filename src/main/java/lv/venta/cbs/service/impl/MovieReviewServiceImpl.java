package lv.venta.cbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import lv.venta.cbs.model.Movie;
import lv.venta.cbs.model.MovieReview;
import lv.venta.cbs.repository.MovieReviewRepository;
import lv.venta.cbs.service.MovieReviewService;

public class MovieReviewServiceImpl implements MovieReviewService {

	private final MovieReviewRepository movieReviewRepository;
	

    @Autowired
    public MovieReviewServiceImpl(MovieReviewRepository movieReviewRepository) {
        this.movieReviewRepository = movieReviewRepository;
    }
    

	@Override
	public MovieReview getMovieReviewById(int id) {
		return movieReviewRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Showtime not found with id: " + id));
	}
	@Override
	@Transactional
	public MovieReview createMovieReview(MovieReview movieReview) {
		return movieReviewRepository.save(movieReview);
	}
	
	@Override
	public List<MovieReview> getMovieReviewsByMovie(Movie movie) {
		return movieReviewRepository.findByMovie(movie);
	}

}
