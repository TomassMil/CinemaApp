package lv.venta.cbs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class TheaterRoom {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoomId")
    private int roomId;

    @NotNull
    @Pattern(regexp = "[A-Z][A-Za-z0-9\\s]{1,20}")
    @Column(name = "RoomName")
    private String roomName;

    @Min(10)
    @Max(500)
    @Column(name = "Capacity")
    private int capacity;
    
    @Min(1)
    @Column(name = "NumberOfRows")
    private int numberOfRows;

    @Min(1)
    @Column(name = "SeatsPerRow")
    private int seatsPerRow;
    
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Showtime> showtimes = new ArrayList<>();
    
    public TheaterRoom(String roomName, int capacity, int numberOfRows, int seatsPerRow) {
        setRoomName(roomName);
        setCapacity(capacity);
        setNumberOfRows(numberOfRows);
        setSeatsPerRow(seatsPerRow);
    }

    // Setters with validation
    public void setRoomName(String roomName) {
        if (roomName != null && roomName.matches("[A-Z][A-Za-z0-9\\s]{1,20}")) {
            this.roomName = roomName;
        } else {
            this.roomName = "RoomX";
        }
    }

    public void setCapacity(int capacity) {
        this.capacity = (capacity >= 10 && capacity <= 500) ? capacity : 100;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = Math.max(1, numberOfRows);
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = Math.max(1, seatsPerRow);
    }
    
}
