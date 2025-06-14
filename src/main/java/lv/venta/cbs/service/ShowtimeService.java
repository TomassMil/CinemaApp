package lv.venta.cbs.service;

import java.util.List;

import lv.venta.cbs.model.Movie;
import lv.venta.cbs.model.Showtime;

public interface ShowtimeService {
    List<Showtime> getAllShowtimes();
    Showtime getShowtimeById(int id);
    Showtime createShowtime(Showtime showtime);
    Showtime updateShowtime(int id, Showtime showtime);
    void deleteShowtime(int id);
    List<Showtime> getShowtimesByMovie(Movie movie);
} 