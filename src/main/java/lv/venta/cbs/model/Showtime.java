package lv.venta.cbs.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "showtimes")
public class Showtime {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showtime_id")
    private Integer showtimeId;
    
    @NotNull(message = "Movie is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;
    
    @NotNull(message = "Room is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private TheaterRoom room;
    
    @NotNull(message = "Show date and time is required")
    @Column(name = "show_date_time", nullable = false)
    private LocalDateTime showDateTime;
    
    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();
    
    public Showtime() {
    }
    
    public Showtime(Movie movie, TheaterRoom room, LocalDateTime showDateTime) {
        this.movie = movie;
        this.room = room;
        this.showDateTime = showDateTime;
    }
    
    // Getters and Setters
    public Integer getShowtimeId() {
        return showtimeId;
    }
    
    public void setShowtimeId(Integer showtimeId) {
        this.showtimeId = showtimeId;
    }
    
    public Movie getMovie() {
        return movie;
    }
    
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    
    public TheaterRoom getRoom() {
        return room;
    }
    
    public void setRoom(TheaterRoom room) {
        this.room = room;
    }
    
    public LocalDateTime getShowDateTime() {
        return showDateTime;
    }
    
    public void setShowDateTime(LocalDateTime showDateTime) {
        this.showDateTime = showDateTime;
    }
    
    public List<Ticket> getTickets() {
        return tickets;
    }
    
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    
    public int getAvailableSeats() {
        return room.getCapacity() - tickets.size();
    }
    
    public String getSeatsInfo() {
        return getAvailableSeats() + " / " + room.getCapacity();
    }
}
