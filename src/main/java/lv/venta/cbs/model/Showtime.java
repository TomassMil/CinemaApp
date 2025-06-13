package lv.venta.cbs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
public class Showtime {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShowtimeId")
    private int showtimeId;

	@NotNull
    @Future
    @Column(name = "ShowDateTime")
    private LocalDateTime showDateTime;

	@ManyToOne(optional = false)
    @JoinColumn(name = "MovieId", nullable = false)
    private Movie movie;

    @ManyToOne(optional = false)
    @JoinColumn(name = "RoomId", nullable = false)
    private TheaterRoom room;

    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Seat> seats = new ArrayList<>();
    
     // showtime.getSeats().remove(seat); - Only this line
	 // With orphanRemoval = true - the seat will be deleted from DB 
	 // Without it - the seat will stay in DB as an "orphan" 
    
    public Showtime(LocalDateTime showDateTime, Movie movie, TheaterRoom room) {
        setShowDateTime(showDateTime);
        setMovie(movie);
        setRoom(room);
    }

    public void setShowDateTime(LocalDateTime showDateTime) {
        this.showDateTime = (showDateTime != null && showDateTime.isAfter(LocalDateTime.now()))
                ? showDateTime
                : LocalDateTime.now().plusDays(1);
    }

    public void setMovie(Movie movie) {
        this.movie = Objects.requireNonNull(movie, "Movie must not be null");
    }

    public void setRoom(TheaterRoom room) {
        this.room = Objects.requireNonNull(room, "Room must not be null");
    }
}
