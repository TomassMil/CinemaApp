package lv.venta.cbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lv.venta.cbs.model.Movie;
import lv.venta.cbs.model.MovieReview;

//@Service
public interface MovieReviewService {
    MovieReview getMovieReviewById(int id);
    MovieReview createMovieReview(MovieReview movieReview);
    
	List<MovieReview> getMovieReviewsByMovie(Movie movie);
}
