package lv.venta.cbs.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class TheaterRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;

    private String roomName;
    private int capacity;
    private int numberOfRows;
    private int seatsPerRow;

    @OneToMany(mappedBy = "room")
    private List<Showtime> showtimes;
}
