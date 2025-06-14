package lv.venta.cbs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "theater_rooms")
public class TheaterRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;

    @NotBlank(message = "Room name is required")
    @Column(nullable = false)
    private String roomName;

    // amount
    @NotNull(message = "seatcolumns is required")
    @Min(value = 1, message = "seatcolumns must be at least 1")
    @Column(nullable = false)
    private int seatcolumns;

    // amount
    @NotNull(message = "seatrows is required")
    @Min(value = 1, message = "seatrows must be at least 1")
    @Column(nullable = false)
    private int seatrows;

    public TheaterRoom() {
    }

    public TheaterRoom(String roomName, int seatcolumns, int seatrows) {
        this.roomName = roomName;
        this.seatcolumns = seatcolumns;
        this.seatrows = seatrows;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getCapacity() {
        return seatcolumns * seatrows;
    }

    public int getSeatcolumns() {
        return seatcolumns;
    }

    public void setSeatcolumns(int seatcolumns) {
        this.seatcolumns = seatcolumns;
    }

    public int getSeatrows() {
        return seatrows;
    }

    public void setSeatrows(int seatrows) {
        this.seatrows = seatrows;
    }
} 