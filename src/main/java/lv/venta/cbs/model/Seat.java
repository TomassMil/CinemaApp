package lv.venta.cbs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatId;

    private int seatNumber;
    private String seatType;
    private int seatColumn;
    private int seatRow;

    @ManyToOne
    @JoinColumn(name = "showtimeId")
    private Showtime showtime;

    @OneToOne(mappedBy = "seat")
    private Ticket ticket;
}
