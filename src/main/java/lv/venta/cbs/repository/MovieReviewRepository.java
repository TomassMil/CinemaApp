package lv.venta.cbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lv.venta.cbs.model.Movie;
import lv.venta.cbs.model.MovieReview;

@Repository
public interface MovieReviewRepository  extends JpaRepository<MovieReview, Integer>{

	List<MovieReview> findByMovie(Movie movie);

}
