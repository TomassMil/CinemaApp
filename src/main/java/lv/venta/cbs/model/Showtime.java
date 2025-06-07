package lv.venta.cbs.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int showtimeId;

    private LocalDateTime showDateTime;

    @ManyToOne
    @JoinColumn(name = "movieId")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private TheaterRoom room;

    @OneToMany(mappedBy = "showtime")
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "showtime")
    private List<Seat> seats;
}
