package lv.venta.cbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lv.venta.cbs.model.Movie;
import lv.venta.cbs.model.Showtime;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Integer> {
    List<Showtime> findByMovie(Movie movie);
} 