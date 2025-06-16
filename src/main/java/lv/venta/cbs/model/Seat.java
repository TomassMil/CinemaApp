package lv.venta.cbs.model;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SeatId")
    private int seatId;

    @Min(1)
    @Column(name = "SeatNumber")
    private int seatNumber;

    @NotNull
    @Pattern(regexp = "STANDARD|VIP|ACCESSIBLE", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(name = "SeatType")
    private String seatType;
    

    @Min(1)
    @Column(name = "SeatColumn")
    private int seatColumn;


    @Min(1)
    @Column(name = "SeatRow")
    private int seatRow;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ShowtimeId", nullable = false)
    private Showtime showtime;

    @OneToOne(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
    private Ticket ticket;
    
    public Seat(int seatNumber, String seatType, int seatColumn, int seatRow, Showtime showtime) {
        setSeatNumber(seatNumber);
        setSeatType(seatType);
        setSeatColumn(seatColumn);
        setSeatRow(seatRow);
        setShowtime(showtime);
    }
    
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = (seatNumber > 0) ? seatNumber : 1;
    }

    public void setSeatType(String seatType) {
        if (seatType != null && seatType.matches("(?i)STANDARD|VIP|ACCESSIBLE")) {
            this.seatType = seatType.toUpperCase();
        } else {
            this.seatType = "STANDARD";
        }
    }

    public void setSeatColumn(int seatColumn) {
        this.seatColumn = (seatColumn > 0) ? seatColumn : 1;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = (seatRow > 0) ? seatRow : 1;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = Objects.requireNonNull(showtime, "Showtime must not be null");
    }
}
