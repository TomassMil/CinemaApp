package lv.venta.cbs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
public class Ticket {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TicketId")
    private int ticketId;

    @NotNull
    @PastOrPresent
    @Column(name = "BookingTimestamp")
    private LocalDateTime bookingTimestamp;

    @Min(0)
    @Column(name = "TotalPrice")
    private double totalPrice;

    @NotNull
    @Pattern(regexp = "BOOKED|CANCELLED|USED", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(name = "Status")
    private String status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ShowtimeId", nullable = false)
    private Showtime showtime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    // izdzests optional bet tad redzes
    @OneToOne
    @JoinColumn(name = "SeatId", nullable = false)
    private Seat seat;
    
    public Ticket(LocalDateTime bookingTimestamp, double totalPrice, String status,
            Showtime showtime, User user, Seat seat) {
	  setBookingTimestamp(bookingTimestamp);
	  setTotalPrice(totalPrice);
	  setStatus(status);
	  setShowtime(showtime);
	  setUser(user);
	  setSeat(seat);
	}
	
	// Setters with validation
	public void setBookingTimestamp(LocalDateTime bookingTimestamp) {
	  this.bookingTimestamp = (bookingTimestamp != null && !bookingTimestamp.isAfter(LocalDateTime.now()))
	          ? bookingTimestamp
	          : LocalDateTime.now();
	}
	
	public void setTotalPrice(double totalPrice) {
	  this.totalPrice = Math.max(0, totalPrice);
	}
	
	public void setStatus(String status) {
	  if (status != null && status.matches("(?i)BOOKED|CANCELLED|USED")) {
	      this.status = status.toUpperCase();
	  } else {
	      this.status = "BOOKED";
	  }
	}
	
	public void setShowtime(Showtime showtime) {
	  this.showtime = Objects.requireNonNull(showtime, "Showtime cannot be null");
	}
	
	public void setUser(User user) {
	  this.user = Objects.requireNonNull(user, "User cannot be null");
	}
	
	public void setSeat(Seat seat) {
	  this.seat = Objects.requireNonNull(seat, "Seat cannot be null");
	}
}
