package lv.venta.cbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lv.venta.cbs.model.Movie;
import lv.venta.cbs.model.Showtime;
import lv.venta.cbs.repository.ShowtimeRepository;
import lv.venta.cbs.service.ShowtimeService;

@Service
public class ShowtimeServiceImpl implements ShowtimeService {

    private final ShowtimeRepository showtimeRepository;

    @Autowired
    public ShowtimeServiceImpl(ShowtimeRepository showtimeRepository) {
        this.showtimeRepository = showtimeRepository;
    }

    @Override
    public List<Showtime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }

    @Override
    public Showtime getShowtimeById(int id) {
        return showtimeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Showtime not found with id: " + id));
    }

    @Override
    @Transactional
    public Showtime createShowtime(Showtime showtime) {
        return showtimeRepository.save(showtime);
    }

    @Override
    public Showtime updateShowtime(int id, Showtime showtime) {
        Showtime existingShowtime = getShowtimeById(id);
        existingShowtime.setMovie(showtime.getMovie());
        existingShowtime.setRoom(showtime.getRoom());
        existingShowtime.setShowDateTime(showtime.getShowDateTime());
        return showtimeRepository.save(existingShowtime);
    }

    @Override
    @Transactional
    public void deleteShowtime(int id) {
        if (!showtimeRepository.existsById(id)) {
            throw new RuntimeException("Showtime not found with id: " + id);
        }
        showtimeRepository.deleteById(id);
    }

    @Override
    public List<Showtime> getShowtimesByMovie(Movie movie) {
        return showtimeRepository.findByMovie(movie);
    }
} 