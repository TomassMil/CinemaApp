package lv.venta.cbs.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;

    private LocalDateTime bookingTimestamp;
    private double totalPrice;
    private String status;

    @ManyToOne
    @JoinColumn(name = "showtimeId")
    private Showtime showtime;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne
    @JoinColumn(name = "seatId")
    private Seat seat;
}
