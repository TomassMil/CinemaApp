package lv.venta.cbs.service;

import java.util.List;


import lv.venta.cbs.model.Movie;
import lv.venta.cbs.model.MovieReview;

public interface MovieReviewService {
    MovieReview getMovieReviewById(int id);
    MovieReview createMovieReview(MovieReview movieReview);
    
	List<MovieReview> getMovieReviewsByMovie(Movie movie);
}
